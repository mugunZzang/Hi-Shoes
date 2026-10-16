package member.kth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.kth.domain.AdminMemberDTO;
import member.kth.model.AdminMemberDAO;
import member.kth.model.AdminMemberDAO_imple;

public class AdminMemberList extends AbstractController {
	private AdminMemberDAO mdao = new AdminMemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//=== 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === 
		
	             	
		
		
		
				HttpSession session = request.getSession();
				
				MemberDTO loginuser =(MemberDTO) session.getAttribute("loginuser");
				
		if( loginuser != null || "admin".equals(loginuser.getUserid()) ) {
				 //if(true) {
					// 관리자(admin)로 로그인 했을 경우
					
					String searchType = request.getParameter("searchType");
					String searchWord = request.getParameter("searchWord");
					
					//System.out.println("~~~ 확인용 searchType :" + searchType);
					//System.out.println("~~~ 확인용 searchWord :" + searchWord);
					if(searchType == null || (!"company".equals(searchType) && !"userid".equals(searchType) && !"email".equals(searchType))) {
						searchType = "";
					}
					
					if(searchWord == null || (searchWord !=null && searchWord.trim().isEmpty() )) {
						searchWord = "";
					}
					
					//System.out.println("### 확인용 searchType :" + searchType);
					//System.out.println("### 확인용 searchWord :" + searchWord);
					
					
					
					
					
					Map<String, String> paraMap = new HashMap<>();
					paraMap.put("searchType", searchType);
					paraMap.put("searchWord", searchWord);
					//*** 페이징 처리를 안한 모든 회원 또는 검색한 회원 목록 보여주기***//
					
					//List<MemberDTO> memberList= mdao.select_Member_nopaging(paraMap);
					
					//*** 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기***//
					String currentShowPageNo = request.getParameter("currentShowPageNo");
					//페이지바에서 보고자하는 페이지번호
					
					String sizePerPage = request.getParameter("sizePerPage");
					//한 페이지당 보여줄 행의 개수
					
					if(currentShowPageNo == null ) {
						
						currentShowPageNo = "1";
					}
					
					if(sizePerPage == null || (!"10".equals(sizePerPage) && !"5".equals(sizePerPage) && !"3".equals(sizePerPage))) {
					   sizePerPage ="10";	
					 }
					//System.out.println("### 확인용 currentShowPageNo :" + currentShowPageNo);
					//System.out.println("### 확인용 sizePerPage :" + sizePerPage);
					
					
					paraMap.put("sizePerPage", sizePerPage);
					
				    int totalPage = mdao.getTotalPage(paraMap);
					
					//System.out.println("~~~~ 확인용 totalPage =>" + totalPage);
					
					
					 // === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
			         // === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
			         // === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
			         // 아래처럼 막아주도록 하겠다.
					
		            try {			
					 if(Integer.parseInt(currentShowPageNo) > totalPage || 
							 Integer.parseInt(currentShowPageNo) <=0) {
		              currentShowPageNo="1"; 
					 }
							 
							 
		            } catch(NumberFormatException e) {
		           	 currentShowPageNo = "1";
		           	 
		            }
		            
		            paraMap.put("currentShowPageNo", currentShowPageNo);
					
					
					
					List<AdminMemberDTO> memberList= mdao.select_Member_paging(paraMap);
					
					request.setAttribute("memberList", memberList);
					request.setAttribute("searchType", searchType);
					request.setAttribute("searchWord", searchWord);
					request.setAttribute("sizePerPage", sizePerPage);
					
					
					//****** 페이지바 만들기 시작 ****** //
					/*
			            1개 블럭당 10개씩 잘라서 페이지를 만든다.
			            1개 페이지당 3개행 또는 5개행 또는 10개행을 보여주는데
			                만약에 1개 페이지당 5개행을 보여준다라면 
			                총 몇개 블럭이 나와야 할까? 
			                총 회원수가 207명 이고, 1개 페이지당 보여줄 회원수가 5 이라면
			            207/5 = 41.4 ==> 42(totalPage)        
			                
			            1블럭       [맨처음]  1  2  3  4  5  6  7  8  9  10 [다음][마지막]
			           2블럭   [맨처음][이전] 11 12 13 14 15 16 17 18 19 20 [다음][마지막]
			           3블럭   [맨처음][이전] 21 22 23 24 25 26 27 28 29 30 [다음][마지막]
			           4블럭   [맨처음][이전] 31 32 33 34 35 36 37 38 39 40 [다음][마지막]
			           5블럭   [맨처음][이전] 41 42 [마지막] 
		         */
					//페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수
		            
			
					
					 
		             
		             
			 String pageBar = "";
			  int blockSize = 10;
			  //blockSize는 블럭당 보여지는 페이지 번호의 개수이다.
			  
			  int loop = 1;
			  // loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
			  
			  // ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		      int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		      // pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		      
		      // [맨처음] 버튼 만들기 //
		      pageBar += " <li class='page-item'><a class='page-link' href='adminMemberList.go?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1&sizePerPage="+sizePerPage+"'>[맨처음]</a></li> ";
		      if(pageNo != 1 ) {
		          pageBar += " <li class='page-item'><a class='page-link' href='adminMemberList.go?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"'>[이전]</a></li> ";
		         }
		      
		      
		      while( !(loop > blockSize || pageNo > totalPage) ) {
		    	  
		    	  if(pageNo == Integer.parseInt(currentShowPageNo)) {
		    		  pageBar += " <li class='page-item active'><a class='page-link' href='#'>" + pageNo +" </a></li> ";
		    		  
		    	  } else {
		    	  
		    	  pageBar += " <li class='page-item'><a class='page-link' href='adminMemberList.go?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"'>" + pageNo +" </a></li> ";
		    	  
		    	  }
		    	  
		    	  
		    	  loop++; // 1 2 3 4 5 6 7 8 9 10
		    	  
		    	  pageNo++;  // 1 2 3 4 5 6 7 8 9 10 
		    	             // 11 12 13 14 15 16 17 18 19 20
		                     // 21 22 23 24 25 26 27 28 29 30 
		    	             // 31 32 33 34 35 36 37 38 39 40
		    	             // 41 42
		    	             
		     	  
		    	  
		      }//end of while( !(loop > blockSize || pageNo > totalPage) )---------
		      
		       // *** [다음][마지막]버튼 만들기 시작 *** //
		       if(pageNo <= totalPage ) {
		        pageBar += " <li class='page-item'><a class='page-link' href='adminMemberList.go?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"'>[다음]</a></li> ";
		       }
		        pageBar += " <li class='page-item'><a class='page-link' href='adminMemberList.go?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"&sizePerPage="+sizePerPage+"'>[마지막]</a></li> ";
		       
		      // *** [다음][마지막]버튼 만들기 끝 *** //
					request.setAttribute("pageBar", pageBar);
					
					//****** 페이지바 만들기 끝 ****** //
					
					//뷰단에서 페이징 처리시 보여주는 순번 공식에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 구하기
					 int totalMemberCount = mdao.getTotalMemberCount(paraMap);
					// System.out.println(" ~~~~ 확인용 totalMemberCount:" + totalMemberCount);
					 request.setAttribute("totalMemberCount", totalMemberCount);
					 request.setAttribute("pageNo",Integer.parseInt(currentShowPageNo));
					 request.setAttribute("sizePerPage",Integer.parseInt(sizePerPage));
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/admin/adminMemberList.jsp");
					
					
				}
			    else {// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 (url)
		        String message = "관리자만 접근이 가능합니다.";
		        String loc = "javascript:history.back()";
		        
		        request.setAttribute("message", message);
		        request.setAttribute("loc", loc);
		        
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/member/admin/adminMsg.jsp");
				
				
			} 
		
	}

}
