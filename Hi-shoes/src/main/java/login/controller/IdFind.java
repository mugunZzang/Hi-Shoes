package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IdFind extends AbstractController{

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equals(method)) {
			
		}	// end of if("POST".equals(method))----------
		
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idFind.jsp");
	}

}
