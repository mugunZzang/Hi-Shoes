package shop.kimkc.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class GoUpdateQnaJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		
		if (request.getMethod().equals("POST")) {
			String qnanum = request.getParameter("qnanum");
			String qtitle = request.getParameter("qtitle");
			String qcontents = request.getParameter("qcontents");
			String islock = request.getParameter("islock");
			//System.out.println(qnanum);
			//System.out.println(qtitle);
			//System.out.println(qcontents);
			//System.out.println(islock);
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("qnanum", qnanum);
			paraMap.put("qtitle", qtitle);
			paraMap.put("qcontents", qcontents);
			paraMap.put("islock", islock);
			
			int n = pdao.updateQna(paraMap);
			
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
