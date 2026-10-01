package admin.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.km.domain.MemberDTO;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class Callcenter extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	private FaqDAO fdao = new FaqDAO_imple();
	private QuestionDAO qdao = new QuestionDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			List<NoticeDTO> noticeList = null;
			List<FaqDTO> faqList = null;
			List<Map<String,String>> questionList = null;
			
			HttpSession session = request.getSession();
	
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	
			// if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// //관리자(admin)로 로그인 했을 경우
			String tab = request.getParameter("tab");
			String searchType = request.getParameter("searchType");
			String searchWord = request.getParameter("searchWord");
	
			if(tab == null || tab.isBlank()) {
			    tab = "notice";
			}
	
			if(searchType == null || (!"subject".equals(searchType) &&
			  !"userid".equals(searchType) && !"content".equals(searchType) &&
			  !"category".equals(searchType) && !"pname".equals(searchType) &&
			  !"register".equals(searchType) && !"pay".equals(searchType) && 
			  !"change".equals(searchType) && !"order".equals(searchType) &&
			  !"cancle".equals(searchType) && !"pinfo".equals(searchType) &&
			  !"delivery".equals(searchType))) { 
				searchType= ""; 
			}
			  
			if(searchWord == null || (searchWord != null && searchWord.trim().isEmpty())){ 
				searchWord = "";
			}
			
			// *** 페이징 처리한 주문 목록 보여주기 *** //
			
			String currentShowPageNo = request.getParameter("currentShowPageNo");
			  
			Map<String,String> paraMap = new HashMap<>(); 
			paraMap.put("searchType",searchType); 
			paraMap.put("searchWord", searchWord);
			
			
			if(currentShowPageNo == null) {
				currentShowPageNo = "1";
			}
			
			paraMap.put("currentShowPageNo", currentShowPageNo);
			
			// === 한 페이지당 보여줄 주문내역의 개수는 10개로 한다.
			int sizePerPage = 10;
			
			// === 전체 페이지 개수 ===
			int totalCountOrder = 0;
			  
			if("notice".equals(tab)) {
				noticeList = ndao.select_notice_list(paraMap);
				totalCountOrder = ndao.getTotalCountOrder(); // === 전체 페이지 개수 ===
				request.setAttribute("noticeList", noticeList);
				
				// === 전체 페이지 개수 ===
				int totalPage = (int) Math.ceil((double) totalCountOrder/sizePerPage); 
				
				int blockSize = 10;
				// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
				
				int loop = 1;
				// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
				
				// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
				int pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
				// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
				
				
				// **** [맨처음][이전] 만들기 **** //
				// 
				String noticePageBar = "<li class='page-item'><a class='page-link' href='callcenter.go?tab=notice&currentShowPageNo=1'>[맨처음]</a></li>"; 
				
				if( pageNo != 1 ) {
					noticePageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=notice&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
				}
				
				while( !(loop > blockSize || pageNo > totalPage) ) {
					
					if(pageNo == Integer.parseInt(currentShowPageNo)) {
						noticePageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>"; 
					}
					else {
						noticePageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=notice&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
					}
					
					loop++;    //  1 2 3 4 5 6 7 8 9 10
					
					pageNo++;  //  1  2  3  4  5  6  7  8  9 10
					           // 11 12 13 14 15 16 17 18 19 20
					           // 21 22 23 24 25 26 27 28 29 30
					           // 31 32 33 34 35 36 37 38 39 40
					           // 41 42 
				}// end of while------------------
				
				// **** [다음][마지막] 만들기 **** //
				// pageNo ==> 11
				if( pageNo <= totalPage ) {
					noticePageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=notice&currentShowPageNo="+pageNo+"'>[다음]</a></li>"; 
				}
				noticePageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=notice&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";

				// *** ====== 페이지바 만들기 끝 ====== *** //
				
				request.setAttribute("noticePageBar", noticePageBar);
			}
			else if("faq".equals(tab)) {
				faqList = fdao.select_faq_list(paraMap);
				totalCountOrder = fdao.getTotalCountOrder(); // === 전체 페이지 개수 ===
				request.setAttribute("faqList", faqList);
			
				
				// === 전체 페이지 개수 ===
				int totalPage = (int) Math.ceil((double) totalCountOrder/sizePerPage); 
				
				int blockSize = 10;
				// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
				
				int loop = 1;
				// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
				
				// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
				int pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
				// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
				
				
				// **** [맨처음][이전] 만들기 **** //
				// 
				String faqPageBar = "<li class='page-item'><a class='page-link' href='callcenter.go?tab=faq&currentShowPageNo=1'>[맨처음]</a></li>"; 
				
				if( pageNo != 1 ) {
					faqPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=faq&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
				}
				
				while( !(loop > blockSize || pageNo > totalPage) ) {
					
					if(pageNo == Integer.parseInt(currentShowPageNo)) {
						faqPageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>"; 
					}
					else {
						faqPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=faq&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
					}
					
					loop++;    //  1 2 3 4 5 6 7 8 9 10
					
					pageNo++;  //  1  2  3  4  5  6  7  8  9 10
					           // 11 12 13 14 15 16 17 18 19 20
					           // 21 22 23 24 25 26 27 28 29 30
					           // 31 32 33 34 35 36 37 38 39 40
					           // 41 42 
				}// end of while------------------
				
				// **** [다음][마지막] 만들기 **** //
				// pageNo ==> 11
				if( pageNo <= totalPage ) {
					faqPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=faq&currentShowPageNo="+pageNo+"'>[다음]</a></li>"; 
				}
				faqPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=faq&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";

				// *** ====== 페이지바 만들기 끝 ====== *** //
				
				request.setAttribute("faqPageBar", faqPageBar);
			}
			else if("question".equals(tab)) {
				questionList = qdao.select_question_list(paraMap);
				totalCountOrder = qdao.getTotalCountOrder(); // === 전체 페이지 개수 ===
				request.setAttribute("questionList", questionList);
				
				// === 전체 페이지 개수 ===
				int totalPage = (int) Math.ceil((double) totalCountOrder/sizePerPage); 
				
				int blockSize = 10;
				// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
				
				int loop = 1;
				// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
				
				// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
				int pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
				// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
				
				
				// **** [맨처음][이전] 만들기 **** //
				// 
				String questionPageBar = "<li class='page-item'><a class='page-link' href='callcenter.go?tab=question&currentShowPageNo=1'>[맨처음]</a></li>"; 
				
				if( pageNo != 1 ) {
					questionPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=question&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
				}
				
				while( !(loop > blockSize || pageNo > totalPage) ) {
					
					if(pageNo == Integer.parseInt(currentShowPageNo)) {
						questionPageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>"; 
					}
					else {
						questionPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=question&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
					}
					
					loop++;    //  1 2 3 4 5 6 7 8 9 10
					
					pageNo++;  //  1  2  3  4  5  6  7  8  9 10
					           // 11 12 13 14 15 16 17 18 19 20
					           // 21 22 23 24 25 26 27 28 29 30
					           // 31 32 33 34 35 36 37 38 39 40
					           // 41 42 
				}// end of while------------------
				
				// **** [다음][마지막] 만들기 **** //
				// pageNo ==> 11
				if( pageNo <= totalPage ) {
					questionPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=question&currentShowPageNo="+pageNo+"'>[다음]</a></li>"; 
				}
				questionPageBar += "<li class='page-item'><a class='page-link' href='callcenter.go?tab=question&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";

				// *** ====== 페이지바 만들기 끝 ====== *** //
				
				request.setAttribute("questionPageBar", questionPageBar);
				
				
			}
			
			
			/* >>> 뷰단(orderList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
	               주문내역 총개수 알아오기 시작 <<< */
			request.setAttribute("tab", tab);
		    request.setAttribute("totalCountOrder", totalCountOrder);
		    request.setAttribute("currentShowPageNo", currentShowPageNo); // 뷰단에서 "페이징 처리시 보여주는 순번 공식" 에서 사용할 용도임. 
		    request.setAttribute("sizePerPage", sizePerPage);
		    /* 주문내역 총개수 알아오기 끝 */
	
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/callcenter/callcenter.jsp");
			// }
			// else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
			/*
			 * String message = "관리자만 접근이 가능합니다."; String loc = "javascript:history.back()";
			 * 
			 * request.setAttribute("message", message); request.setAttribute("loc", loc);
			 * 
			 * 
			 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp");
			 */
			// }
	}

}
