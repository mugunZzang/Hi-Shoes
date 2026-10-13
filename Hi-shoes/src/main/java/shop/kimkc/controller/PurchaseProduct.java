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
import member.kimkc.model.MemberDAO;
import member.kimkc.model.MemberDAO_imple;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class PurchaseProduct extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getMethod().equals("POST")) {
			
			MemberDTO loginuser = (MemberDTO) request.getSession().getAttribute("loginuser");
			if (loginuser == null) {
				request.setAttribute("message", "서비스 이용을 원하시면 로그인 하시길바랍니다.");
			    request.setAttribute("loc", request.getContextPath() + "/index.go");
			         
			    super.setRedirect(false);
			    super.setViewPage("/WEB-INF/msg.jsp");
			         
			    return;
			}
			

			// 넘겨받은 구매목록 정리하기
			List<Map<String, String>> prodList = new ArrayList<>();
			//System.out.println("전달받은 JSON: " + request.getParameter("prodList"));
			JSONArray jsonArray = new JSONArray(request.getParameter("prodList"));
			
			for(int i=0; i<jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("pimage", jsonObject.getString("pimage"));
				map.put("pname", jsonObject.getString("pname"));
				map.put("brand", jsonObject.getString("brand"));
				map.put("spec", jsonObject.getString("spec"));
				map.put("regprice", jsonObject.getString("regprice"));
				map.put("saleprice", jsonObject.getString("saleprice"));
				
				if(jsonObject.getString("snum").isBlank()) {
					//snum 갖고오기
					Map<String, String> paraMap = new HashMap<>();
					paraMap.put("fk_pname", jsonObject.getString("pname"));
					String ssize = jsonObject.getString("spec").split(", ")[0];
					String color = jsonObject.getString("spec").split(", ")[1];
					paraMap.put("ssize", ssize);
					paraMap.put("color", color);
					String snum = pdao.getSnumBySpec(paraMap);
					map.put("snum", snum);
				}
				else {
					map.put("snum", jsonObject.getString("snum"));
				}
				
				map.put("cqty", jsonObject.getString("cqty"));
				/*
				System.out.println("-------------------------");
				System.out.println(jsonObject.getString("pimage")); 
				System.out.println(jsonObject.getString("pname")); 
				System.out.println(jsonObject.getString("brand"));
				System.out.println(jsonObject.getString("spec"));
				System.out.println(jsonObject.getString("regprice"));
				System.out.println(jsonObject.getString("saleprice"));
				System.out.println(jsonObject.getString("snum"));
				System.out.println(jsonObject.getString("cqty"));
				System.out.println("-------------------------");
				*/
				prodList.add(map);
			}
			
			request.setAttribute("prodList", prodList);
			
			// 구매자 정보 들고오기
			MemberDTO member = mdao.getMemberInfo(loginuser.getUserid());
			request.setAttribute("member", member);
			
			String deliveryfee = request.getParameter("deliveryfee");
			request.setAttribute("deliveryfee", deliveryfee);
			//System.out.println("배송비" + deliveryfee);
			
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/kimkc/shop/purchaseProduct.jsp");
			
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
