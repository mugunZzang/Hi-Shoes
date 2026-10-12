package member.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class Mypage extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		////////////////////////////////////////////////////////////////////////////////////////
		// **** GET 방식을 막는 또 다른 방법 ==> 웹브라우저 주소창에서 직접입력하지 못하게 막아버리면 된다. **** //
		// 이것의 단점은 웹브라우저에서 북마크(즐겨찾기)를 했을 경우 접속이 안된다는 것이다.
		// 왜냐하면 이전 페이지가 없이 웹브라우저 주소창에서 직접입력한 것과 동일하기 때문이다.
		
		String referer = request.getHeader("referer");
		/*  request.getHeader("referer"); 은 
		현재 페이지 주소인 http://localhost:9090/MyMVC/shop/cartList.up 페이지 주소로 접근하려고 시도하였던 
		이전 페이지 URL 주소를 알려주는 것이다.
		
		request.getHeader("Referer"); 값이 null 이 나오는 경우가 있는데 
		이것은 사용자가 웹브라우저 주소창에 URL주소 (http://localhost:9090/MyMVC/shop/prodView.up?pnum=62) 와 같이 직접 입력하고 들어온 경우이다. 
		*/
		
		System.out.println("referer => " + referer);
		// referer => http://localhost:9090/MyMVC/shop/mallHomeMore.up
		
		if(referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우이다.
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/index.go");
			return;
		}		
		//////////////////////////////////////////////////////////		
		
		// 마이페이지는 반드시 해당사용자가 로그인을 해야만 볼 수 있다.
		if(!super.checkLogin(request)) {
			request.setAttribute("message", "로그인이 필요한 서비스입니다.");
			request.setAttribute("loc", "javascript:history.back()"); 
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			// 로그인을 했을 경우	    	  
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			
			/*
			 * List<CartDTO> cartList = pdao.selectProductCart(loginuser.getUserid());
			 * Map<String, Integer> sumMap =
			 * pdao.selectCartSumPricePoint(loginuser.getUserid());
			 * 
			 * request.setAttribute("cartList", cartList); request.setAttribute("sumMap",
			 * sumMap);
			 */
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/mypage.jsp");	  		
		
		}      
	}

}
