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

public class DeleteFromCartListJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userid = ((MemberDTO) request.getSession().getAttribute("loginuser")).getUserid();
		String snum = request.getParameter("snum");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("snum", snum);
		paraMap.put("userid", userid);
		
		int n = pdao.deleteFromCartList(paraMap);
		
		JSONObject jsonObj = new JSONObject();
	    jsonObj.put("n", n);
	    String json = jsonObj.toString();

	    request.setAttribute("json", json);

	    super.setRedirect(false);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
