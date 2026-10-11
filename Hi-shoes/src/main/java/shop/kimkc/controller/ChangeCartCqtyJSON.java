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

public class ChangeCartCqtyJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = ((MemberDTO) request.getSession().getAttribute("loginuser")).getUserid();
		String snum = request.getParameter("snum");
		String cqty = request.getParameter("cqty");
		
		
		
		int n = 0;
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("snum", snum);
		paraMap.put("cqty", cqty);
		
		// 변경하기 전에, 재고가 충분한지 검사한다.
		if(pdao.isStockEnoughForCart(paraMap)) {
			
			// 충분하면 변경하고
			paraMap = new HashMap<>();
			paraMap.put("snum", snum);
			paraMap.put("userid", userid);
			paraMap.put("cqty", cqty);
			
			n = pdao.changeCartCqty(paraMap);
		}
		// 부족하면 안함
		else {
			n = -1;
		}
		
		

		JSONObject jsonObj = new JSONObject();
	    jsonObj.put("n", n);
	    String json = jsonObj.toString();

	    request.setAttribute("json", json);

	    super.setRedirect(false);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
