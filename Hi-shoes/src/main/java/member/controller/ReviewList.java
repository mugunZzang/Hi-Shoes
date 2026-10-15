package member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class ReviewList extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();
	
	public ReviewList() {
		mdao = new MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String referer = request.getHeader("referer"); 
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
		if(referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우이다. 
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.go");
			return;
		}
		
		// 리뷰목록 보기는 반드시 해당사용자가 로그인을 해야만 볼 수 있다.
		if(!super.checkLogin(request)) {
			request.setAttribute("message", "리뷰목록을 조회하려면 먼저 로그인 부터 하세요!!");
			request.setAttribute("loc", "javascript:history.back()"); 
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			// 로그인을 했을 경우
			
			// *** 페이징 처리한 주문 목록 보여주기 *** //
			
			String currentShowPageNo = request.getParameter("currentShowPageNo");
			// currentShowPageNo 은 사용자가 보고자하는 페이지바의 페이지번호 이다.
			// 카테고리 메뉴에서 카테고리명만을 클릭했을 경우에는 currentShowPageNo 은 null 이 된다.
			// currentShowPageNo 이 null 이라면 currentShowPageNo 을 1 페이지로 바꾸어야 한다.
						
			if(currentShowPageNo == null) {
				currentShowPageNo = "1";
			}
			
			// === 페이징 처리를 위해 총페이지수 알아오기 === //
			
			// 1. 일반 사용자로 로그인 한 경우는 자신이 주문한 내역만 조회를 해오고,
			// 2. 관리자(admin)으로 로그인 한 경우 모든 사용자들의 주문한 내역을 조회해온다.
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			
			int totalCountReview = mdao.getTotalCountReview(loginuser.getUserid());
			//	System.out.println("~~~~ 확인용 totalCountOrder => " + totalCountOrder); 
			//  ~~~~ 확인용 totalCountOrder => 16	admin 으로 로그인 한 경우
			//  ~~~~ 확인용 totalCountOrder => 15 seoyh 으로 로그인 한 경우
			//	~~~~ 확인용 totalCountOrder => 1	
			
			// === 한 페이지당 보여줄 주문내역의 개수는 10개로 한다.
			int sizePerPage = 10;
						
			// === 전체 페이지 개수 ===
			int totalPage = (int) Math.ceil((double) totalCountReview/sizePerPage); 
			//  16.0/10 ==> 1.6 ==> 2.0 ==> 2
			//  15.0/10 ==> 1.5 ==> 2.0 ==> 2
		    //  1.0/10  ==> 0.1 ==> 1.0 ==> 1
						
			//	System.out.println("~~~ 확인용 totalPage : " + totalPage);
			
			//  사용자의 리뷰목록을 페이징 처리하여 조회해온다. 
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", loginuser.getUserid());
			paraMap.put("currentShowPageNo", currentShowPageNo);
			
			List<Map<String, String>> review_map_List = mdao.getReviewList(paraMap);
			System.out.println("~~~ 확인용 review_map_List.size() => " + review_map_List.size());
			
			request.setAttribute("review_map_List", review_map_List);
			
			String pageBar = "";
			
			int blockSize = 10;
			// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
			
			int loop = 1;
			// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
			
			// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
			int pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
			// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
			
			// **** [맨처음][이전] 만들기 **** //
			// 
			pageBar += "<li class='page-item'><a class='page-link' href='reviewList.go?currentShowPageNo=1'>[맨처음]</a></li>"; 
			
			if( pageNo != 1 ) {
				pageBar += "<li class='page-item'><a class='page-link' href='reviewList.go?currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
			}
			
			while( !(loop > blockSize || pageNo > totalPage) ) {
				
				if(pageNo == Integer.parseInt(currentShowPageNo)) {
					pageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>"; 
				}
				else {
					pageBar += "<li class='page-item'><a class='page-link' href='reviewList.go?currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
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
				pageBar += "<li class='page-item'><a class='page-link' href='reviewList.go?currentShowPageNo="+pageNo+"'>[다음]</a></li>"; 
			}
			pageBar += "<li class='page-item'><a class='page-link' href='reviewList.go?currentShowPageNo="+totalPage+"'>[마지막]</a></li>";

			// *** ====== 페이지바 만들기 끝 ====== *** //
			
			request.setAttribute("pageBar", pageBar);
			
			/* >>> 뷰단(orderList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
	               주문내역 총개수 알아오기 시작 <<< */
		    request.setAttribute("totalCountReview", totalCountReview);
		    request.setAttribute("currentShowPageNo", currentShowPageNo); // 뷰단에서 "페이징 처리시 보여주는 순번 공식" 에서 사용할 용도임. 
		    request.setAttribute("sizePerPage", sizePerPage);
		    /* 주문내역 총개수 알아오기 끝 */
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/myReview.jsp");
		}
		
	}

}
