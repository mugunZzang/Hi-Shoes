package shop.kimkc.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class CartList extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
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
			
			String userid = loginuser.getUserid();
			
			
			// 해당 유저의 장바구니 목록 갖고오기
			List<Map<String, String>> myCartList = pdao.getCartListByUserid(userid);
			
			request.setAttribute("myCartList", myCartList);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/kimkc/shop/cartList.jsp");
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
