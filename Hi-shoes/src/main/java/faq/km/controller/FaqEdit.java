package faq.km.controller;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class FaqEdit extends AbstractController {

    private FaqDAO fdao = new FaqDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우
	    	String method = request.getMethod();
	        // GET : 수정 페이지 보여주기
	        if ("GET".equals(method)) {
	
	            String fnum = request.getParameter("fnum");
	
	            FaqDTO fdto = fdao.selectFaqOne(Integer.parseInt(fnum));
	
	            request.setAttribute("fdto", fdto);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/admin/callcenter/faqEdit.jsp");
	        }
	
	        // POST : 수정하기
	        else {
	
	            String fnum = request.getParameter("fnum");
	            String category = request.getParameter("category");
	            String fsubject = request.getParameter("fsubject");
	            String fcontents = request.getParameter("fcontents");
	
	            FaqDTO fdto = new FaqDTO();
	
	            fdto.setFnum(Integer.parseInt(fnum));
	            fdto.setFcategory(category);
	            fdto.setFsubject(fsubject);
	            fdto.setFcontents(fcontents);
	
	            int n = fdao.faqUpdate(fdto);
	
	            if (n == 1) {
	
	                super.setRedirect(true);
	                super.setViewPage(request.getContextPath()+ "/admin/callcenter/callcenter.go?tab=faq");
	            }
	        }
		}
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
    }
}