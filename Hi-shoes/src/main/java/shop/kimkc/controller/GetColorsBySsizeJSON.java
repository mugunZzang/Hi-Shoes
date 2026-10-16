package shop.kimkc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class GetColorsBySsizeJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if (method.equals("POST")) {
			String ssize = request.getParameter("ssize");
			String pname = request.getParameter("pname");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("ssize", ssize);
			paraMap.put("pname", pname);
			
			// 사이즈, 상품명으로 색상목록 갖고오기
			List<String> colorList = pdao.getColorBySsize(paraMap);
			
			JSONArray jsonArray = new JSONArray();
			
			for(int i=0; i<colorList.size(); i++) {
				jsonArray.put(colorList.get(i));
			}
			
			String json = jsonArray.toString();
			//System.out.println("ssize: " + ssize);
			//System.out.println("pname: " + pname);
			//System.out.println("color JSON: " + json);
			
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
