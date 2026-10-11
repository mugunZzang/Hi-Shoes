package shop.kimkc.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class ChangeWishStatusJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		
		String userid = ((MemberDTO) request.getSession().getAttribute("loginuser")).getUserid();
		String pnum = request.getParameter("pnum");
		String isJjim = request.getParameter("isJjim");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pnum", pnum);
		paraMap.put("isJjim", isJjim);
		System.out.println(userid);
		System.out.println(pnum);
		System.out.println(isJjim);
		
		// 위시리스트 등록/해제
		int n = pdao.changeWishStatus(paraMap);
		
		JSONObject jsonObj = new JSONObject();
	    jsonObj.put("n", n);
	    String json = jsonObj.toString();

	    request.setAttribute("json", json);

	    super.setRedirect(false);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
