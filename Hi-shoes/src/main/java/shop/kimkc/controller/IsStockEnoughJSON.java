package shop.kimkc.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class IsStockEnoughJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pname = request.getParameter("pname");
		String color = request.getParameter("color");
		String ssize = request.getParameter("ssize");
		String selectedCnt = request.getParameter("selectedCnt");
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("pname", pname);
		paraMap.put("color", color);
		paraMap.put("ssize", ssize);
		
		
		int stock = pdao.isStockEnough(paraMap);
		//System.out.println("재고량 : " +stock);
		
		// selectedCnt < stock 이면 n = 1
		// selectedCnt >= stock 이면 n = -1
		// 선택한 수량이 재고보다 적으면 1, 아니면 -1
		int cnt = Integer.parseInt(selectedCnt);
	    int n = (cnt < stock) ? 1 : -1;
	    //System.out.println("n = " + n);

	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("n", n);
	    String json = jsonObj.toString();

	    request.setAttribute("json", json);

	    super.setRedirect(false);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
		

	}

}
