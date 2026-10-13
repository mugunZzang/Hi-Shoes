package member.kth.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.kth.domain.AdminMemberDTO;
import member.kth.model.AdminMemberDAO;
import member.kth.model.AdminMemberDAO_imple;

public class AdminMemberOneDetail extends AbstractController {
	private AdminMemberDAO mdao = new AdminMemberDAO_imple();
	
			
			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을 때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession(); 
		
		AdminMemberDTO loginuser = (AdminMemberDTO) session.getAttribute("loginuser");
		
		//(로그인안한 상태로 페이지 보려고 만듬)
		if(loginuser == null) {
		    loginuser = new AdminMemberDTO();
		    loginuser.setUserid("admin");
		}
	
	 
	
		
		/*if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {
			// 관리자(admin)로 로그인 했을 경우 */
			
			String method = request.getMethod(); 
			
			if("POST".equals(method)) { // POST 방식일때 
				
				String userid = request.getParameter("userid"); 
				
				String referer = request.getHeader("Referer");
			/*  request.getHeader("Referer"); 은 
				현재 페이지 주소인 http://localhost:9090/MyMVC/member/memberOneDetail.up 페이지 주소로 접근하려고 시도하였던 
			    이전 페이지 URL 주소를 알려주는 것이다.
			     
			    request.getHeader("Referer"); 값이 null 이 나오는 경우가 있는데 
				이것은 사용자가 웹브라우저 주소창에 URL주소 (http://localhost:9090/MyMVC/member/memberOneDetail.up) 를 직접 입력하고 들어온 경우이다.
			*/	
			//	System.out.println("~~~~~ 확인용 referer : " + referer);
				// ~~~~~ 확인용 referer : http://localhost:9090/MyMVC/member/memberList.up?searchType=name&searchWord=%EC%9C%A0&currentShowPageNo=7&sizePerPage=5 
				
				if(referer == null) {
					// referer == null 이 나오는 경우는 
					//	사용자가 웹브라우저 주소창에 URL주소 (http://localhost:9090/MyMVC/member/memberOneDetail.up) 를 직접 입력하고 들어온 경우이다.
					referer = "http://localhost:9090/Hi-shoes/index.go";
				}
			 	
				AdminMemberDTO mdto = mdao.selectOneMember(userid); 
			 	
			 	request.setAttribute("mdto", mdto);
			 	request.setAttribute("referer", referer);
			 				 	
			 	super.setRedirect(false);
			 	super.setViewPage("/WEB-INF/admin/adminMemberOneDetail.jsp");
			}
			
		} 
		/*else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
			String message = "관리자만 접근이 가능합니다.";
			String loc = "javascript:history.back()";
						
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
						
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
		} 
		*/
		
}


		

		
	


