package shop.kimkc.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class GoDeleteQnaJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (request.getMethod().equals("POST")) {
			
			String qnanum = request.getParameter("qnanum");
			
			int n = pdao.deleteQna(qnanum);
			
			JSONObject jsonObj = new JSONObject();
		    jsonObj.put("n", n);
		    String json = jsonObj.toString();

		    request.setAttribute("json", json);

		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		
		else {
			request.setAttribute("message", "잘못된 접근입니다.");
		    request.setAttribute("loc", request.getContextPath() + "/index.go");
		         
		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/msg.jsp");
		         
		    return;
		}

	}

}
