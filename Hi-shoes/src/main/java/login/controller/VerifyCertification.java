package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyCertification extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String userCertificationCode = request.getParameter("userCertificationCode");
			String userid = request.getParameter("userid");					
					
			//세션 불러오기
			HttpSession session = request.getSession();
			String certification_code = (String) session.getAttribute("certification_code");
			
			String message = "";
			String loc = "";
			if(certification_code.equals(userCertificationCode)) {
				message = "인증성공!";
				loc = request.getContextPath()+"/login/pwdUpdateEnd.go?userid=" + userid;
			}
			else {
				message = "발급된 인증코드가 아닙니다.\\n인증코드를 다시 발급 받으세요.";
				loc = request.getContextPath()+"/login/pwdFind.go";
				
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	// end of if("POST".equals(method)) ----------

	}

}
