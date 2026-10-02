package faq.km.controller;


import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FaqWrite extends AbstractController {

	private FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		System.out.println("현재 method = " + method);
		System.out.println("현재 URI = " + request.getRequestURI());
		
		if("GET".equals(method)) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/callcenter/faqWrite.jsp");
		}
		else {
			// post 방식으로 받아옴
			
			String fsubject = request.getParameter("fsubject");
			String fcontents = request.getParameter("fcontents");
			String category = request.getParameter("category");
			
			System.out.println(category);
		    
		    FaqDTO fdto = new FaqDTO();
		    fdto.setFcategory(category);
		    fdto.setFsubject(fsubject);
		    fdto.setFcontents(fcontents);
		    
		    // 공지사항에 넣어주기
		    int n = fdao.faqInsert(fdto);
		    
		    if(n==1) {
		    	super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/callcenter/callcenter.jsp");
		    }

	    		 
		}
		
		
	}

}
