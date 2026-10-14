package faq.km.controller;


import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;


public class FaqWrite extends AbstractController {


    private FaqDAO fdao = new FaqDAO_imple();


    @Override
    public void execute(HttpServletRequest request,HttpServletResponse response)throws Exception {

    	// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
    	// 관리자(admin)로 로그인 했을 경우

	        String method = request.getMethod();
	
	        if("GET".equalsIgnoreCase(method)) {
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/admin/callcenter/faqWrite.jsp");
	
	            return;
	
	        }
	
	        String category =request.getParameter("category");
	        String fsubject =request.getParameter("fsubject");
	        String fcontents =request.getParameter("fcontents");
	
	        // 공백 제거
	        if(category != null) {
	            category = category.trim();
	        }
	
	
	        if(fsubject != null) {
	            fsubject = fsubject.trim();
	        }
	
	        if(fcontents != null) {
	            fcontents = fcontents.trim();
	        }
	
	        // 필수값 검사
	        if(category == null
	                || category.isEmpty()
	                || fsubject == null
	                || fsubject.isEmpty()
	                || fcontents == null
	                || fcontents.isEmpty()) {
	
	
	            // 잘못된 요청이면 다시 작성 페이지
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/admin/callcenter/faqWrite.jsp");
	            
	            return;
	
	        }
	
	        FaqDTO fdto = new FaqDTO();
	
	        fdto.setFcategory(category);
	        fdto.setFsubject(fsubject);
	        fdto.setFcontents(fcontents);
	
	        int n = fdao.faqInsert(fdto);
	
	
	        if(n == 1) {
	
	            super.setRedirect(true);
	            super.setViewPage(request.getContextPath()+"/admin/callcenter/callcenter.go?tab=faq");
	
	
	        }
	
	
	        else {
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/admin/callcenter/faqWrite.jsp");
	
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