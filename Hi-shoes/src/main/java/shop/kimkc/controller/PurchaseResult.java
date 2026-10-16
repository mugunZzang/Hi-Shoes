package shop.kimkc.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import member.kimkc.model.MemberDAO;
import member.kimkc.model.MemberDAO_imple;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class PurchaseResult extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		if(request.getMethod().equals("POST")) {
			MemberDTO loginuser = (MemberDTO)request.getSession().getAttribute("loginuser");
			String userid = loginuser.getUserid();
			
			// 가장 최근 주문의 정보를 갖고옴
			Map<String, String> orderMap = pdao.getRecentOrder(userid); 
			request.setAttribute("orderMap", orderMap);
			
			
			// 주문자 정보 갖고옴
			request.setAttribute("loginuser", loginuser);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/kimkc/shop/purchaseResult.jsp");
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
