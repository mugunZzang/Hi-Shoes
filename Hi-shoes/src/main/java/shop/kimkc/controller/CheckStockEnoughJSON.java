package shop.kimkc.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class CheckStockEnoughJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		if (request.getMethod().equals("POST")) {
			
			String snum = request.getParameter("snum");
			String cqty = request.getParameter("cqty");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("snum", snum);
			paraMap.put("cqty", cqty);
			
			int n = 0;
			String pname = "";
			
			try {
				pname = pdao.checkStockEnough(paraMap);
				if (!pname.equals("")) {
					n = 1;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				n = -1;
			}
			
			
			JSONObject jsonObj = new JSONObject();
		    jsonObj.put("n", n);
		    jsonObj.put("pname", pname);
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
