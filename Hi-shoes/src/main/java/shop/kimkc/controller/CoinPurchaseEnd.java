package shop.kimkc.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class CoinPurchaseEnd extends AbstractController {

	private AES256 aes;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		// 원포트 결제창을 하기 위한 전제조건은 먼저 로그인을 해야 하는 것이다.
		if(request.getSession().getAttribute("loginuser") != null) {
			// 로그인을 했으면
			
			String userid = request.getParameter("userid");
			
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			
			if(loginuser.getUserid().equals(userid)) {
				// 로그인한 사용자가 자신의 코인을 선택하는 경우 
				
				aes = new AES256(SecretMyKey.KEY); 
						
				
				// 최종 결제예정 금액
				int finalTotalPrice = Integer.parseInt(request.getParameter("finalTotalPrice"));
				
				// 결제할 제품명(대표 1개)
				String productName = request.getParameter("primaryProductName") + " 외";
				
				//int productPrice = Integer.parseInt(coinmoney);
				
				request.setAttribute("productName", productName);
			//	request.setAttribute("productPrice", productPrice);
				request.setAttribute("productPrice", 100);
				request.setAttribute("email", loginuser.getEmail());
				request.setAttribute("name", loginuser.getCompany());
				request.setAttribute("mobile", loginuser.getMobile());
				
				request.setAttribute("userid", userid);
				//request.setAttribute("productPrice", finalTotalPrice);
				request.setAttribute("productName", productName);
								
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/kimkc/shop/paymentGateway.jsp"); 
			}
			else {
				// 로그인한 사용자가 다른 사용자의 코인을 충전하려고 결제를 시도하는 경우 
				String message = "잘못된 접근입니다.";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
		}
		
		else {
			// 로그인을 안했으면
			String message = "서비스 이용을 원하시면 로그인을 하셔야 합니다.";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}		

	}

}
