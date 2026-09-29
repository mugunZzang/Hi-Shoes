package test;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Test extends AbstractController{

	private TestDAO tdao = new TestDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		if(method.equals("GET")) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/index.jsp");
		}
		else if(method.equals("POST")) {
			String testVal = request.getParameter("val");			
			
			int n = tdao.test(testVal);
			
			if(n==1) {
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/index.go");
			}
			
		}	
				
		
	}
	
}
