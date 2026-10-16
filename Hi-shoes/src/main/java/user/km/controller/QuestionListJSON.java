package user.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class QuestionListJSON extends AbstractController {

	private QuestionDAO qdao = new QuestionDAO_imple();
	
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
		
		String userid = loginuser.getUserid();
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		// 페이지 기본값
		if(currentShowPageNo == null || currentShowPageNo.isBlank()) {
		
		currentShowPageNo = "1";
		}
		
		
		// 잘못된 페이지 번호가 넘어오는 경우
		int pageNo;
		
		try {
		
		pageNo = Integer.parseInt(currentShowPageNo);
		
		}
		catch(NumberFormatException e) {
		pageNo = 1;
		}
		
		
		if(pageNo < 1) {
		
		pageNo = 1;
		}
		
		
		currentShowPageNo = String.valueOf(pageNo);
		
		
		// =====================================================
		// 2. 검색조건 Map
		// =====================================================
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("userid",userid);
		paraMap.put("currentShowPageNo",currentShowPageNo);
		
		int sizePerPage = 10;
		int blockSize = 10;
		int totalCount = 0;
		
		List<Map<String, String>> questionList = qdao.select_user_question_list(paraMap);
		
		totalCount = qdao.getTotalUserCountOrder(paraMap);
		
		int totalPage =(int)Math.ceil((double)totalCount/ sizePerPage);
		
		
		if(totalPage == 0) {
		
		totalPage = 1;
		}
		
		
		String questionPageBar = makePageBar(pageNo,
		totalPage,
		blockSize
		);
		
		JSONObject jsonObj = new JSONObject();
        jsonObj.put("questionList", questionList);  
        jsonObj.put("questionPageBar", questionPageBar);  
        jsonObj.put("userid", userid);  
        jsonObj.put("totalCountOrder", totalCount);  
        jsonObj.put("currentShowPageNo", currentShowPageNo);  
        jsonObj.put("sizePerPage", sizePerPage);  
        
        String json = jsonObj.toString();  
        
        request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");	  		
		
		}      
		}
		
		private String makePageBar( int currentPage,
		int totalPage,
		int blockSize) {
		
		
		StringBuilder pageBar = new StringBuilder();
		
		int startPage = ((currentPage - 1) / blockSize)* blockSize + 1;
		
		int endPage =Math.min(startPage + blockSize - 1,totalPage);
		
		
		
		// 맨처음
		
		pageBar.append("<li class='page-item'>");
		pageBar.append("<a class='page-link' ");
		pageBar.append("href='mypage.go?");
		pageBar.append("currentShowPageNo=1");
		pageBar.append("'>처음</a>");
		pageBar.append("</li>");
		
		// 이전
		
		if(startPage > 1) {
		
		pageBar.append("<li class='page-item'>");
		pageBar.append("<a class='page-link' ");
		pageBar.append("href='mypage.go?");
		pageBar.append("currentShowPageNo=");
		pageBar.append(startPage - 1);
		pageBar.append("'>이전</a>");
		pageBar.append("</li>");
		
		}
		
		
		// 페이지 번호
		
		for(int i = startPage;i <= endPage;i++) {
		
		if(i == currentPage) {
		
		pageBar.append("<li class='page-item active'>");
		pageBar.append("<a class='page-link' href='#'>");
		pageBar.append(i);
		pageBar.append("</a>");
		pageBar.append("</li>");
		}
		else {
		
		pageBar.append("<li class='page-item'>");
		pageBar.append("<a class='page-link' ");
		pageBar.append("href='mypage.go?");
		pageBar.append("currentShowPageNo=");
		pageBar.append(i);
		pageBar.append("'>");
		pageBar.append(i);
		pageBar.append("</a>");
		pageBar.append("</li>");
		
		}
		
		}
		
		// 다음
		
		if(endPage < totalPage) {
		
		pageBar.append("<li class='page-item'>");
		pageBar.append("<a class='page-link' ");
		pageBar.append("href='mypage.go?");
		pageBar.append("currentShowPageNo=");
		pageBar.append(endPage + 1);
		pageBar.append("'>다음</a>");
		pageBar.append("</li>");
		
		}
		
		// 마지막
		pageBar.append("<li class='page-item'>");
		pageBar.append("<a class='page-link' ");
		pageBar.append("href='mypage.go?");
		pageBar.append("currentShowPageNo=");
		pageBar.append(totalPage);
		pageBar.append("'>마지막</a>");
		pageBar.append("</li>");
		
		
		return pageBar.toString();



	}

}
