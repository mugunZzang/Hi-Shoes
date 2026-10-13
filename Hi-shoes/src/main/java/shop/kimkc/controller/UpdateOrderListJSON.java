package shop.kimkc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class UpdateOrderListJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if(method.equals("POST")) {
			// 넘겨받은 구매목록 정리하기
			List<Map<String, String>> purchaseList = new ArrayList<>();
			//System.out.println("전달받은 JSON: " + request.getParameter("purchaseList"));
			JSONArray jsonArray = new JSONArray(request.getParameter("purchaseList"));
			
			for(int i=0; i<jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("snum", String.valueOf(jsonObject.getInt("snum")));
				map.put("saleprice",String.valueOf(jsonObject.getInt("saleprice")));
				map.put("cqty",String.valueOf(jsonObject.getInt("cqty")));
				
				//System.out.println(jsonObject.getString("snum")); 
				//System.out.println(jsonObject.getString("saleprice")); 
				//System.out.println(jsonObject.getString("cqty"));
				//System.out.println("-----------------");
				
				purchaseList.add(map);
			}
			
			String userid = ((MemberDTO)request.getSession().getAttribute("loginuser")).getUserid();
			
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("purchaseList", purchaseList);
			
			// 각종 DB에 구매정보 반영하기
			int n = pdao.updateOrderList(paraMap);
			
			
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
