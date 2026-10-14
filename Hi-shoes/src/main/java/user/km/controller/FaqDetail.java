package user.km.controller;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class FaqDetail extends AbstractController {

	private FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
					
			String fnum = request.getParameter("fnum");
			
	        FaqDTO fdto = fdao.selectFaqOne(Integer.parseInt(fnum));
	
	        request.setAttribute("fdto", fdto);
	
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/user/faqDetail.jsp");
		}
        else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "로그인을 해야 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
