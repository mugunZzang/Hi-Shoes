package shop.kimkc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.domain.QnADTO;
import shop.kimkc.domain.ReviewDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class ProductDetail extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		// ==== 여기 나중에 지워야 함 ==================== //
		//MemberDTO loginuser = new MemberDTO();
		//loginuser.setUserid("kimkc");
		//HttpSession session = request.getSession();
		//session.setAttribute("loginuser", loginuser);
		//-------------------------------------------//
		// 로그인 했는지 검사해야 함.
		MemberDTO loginuser = (MemberDTO) request.getSession().getAttribute("loginuser");
		if (loginuser == null) {
			request.setAttribute("message", "서비스 이용을 원하시면 로그인 하시길바랍니다.");
		    request.setAttribute("loc", request.getContextPath() + "/index.go");
		         
		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/msg.jsp");
		         
		    return;
		}
		
		// --------------------------------------------------------------------------
		
		//String userid = "kimkc";
		String userid = loginuser.getUserid();
		String pnum = request.getParameter("pnum");
		
		request.setAttribute("pnum", pnum);
		
		// 클릭한 상품의 정보를 갖고온다.
		ProductDTO pdto = pdao.getProductInfo(pnum);
		request.setAttribute("pdto", pdto);

		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pnum", pnum);
		// 사용자가 해당 상품을 찜하였는지 알아오기
		int isJjim = pdao.isWishlisted(paraMap);
		request.setAttribute("isJjim", isJjim);
		
		// 선택 상품의 사이즈 목록 갖고오기
		List<Integer> sizeList = pdao.getProductSizes(pnum);
		request.setAttribute("sizeList", sizeList);

		// ================================================================================================================================
		
		// 리뷰 목록 갖고오기
		// 한 페이지당 5개
		
		// ===== 페이지바 만들기 ===== //
		String currentShowPageNo_review = request.getParameter("currentShowPageNo_QnA");
		
		if(currentShowPageNo_review == null) {
			currentShowPageNo_review = "1";
		}
		
		// 상품의 QnA목록 총 페이지수 가져오기
		int totalProductPage_review = pdao.getTotalReviewPage(pnum);
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.
		try {
		     if(Integer.parseInt(currentShowPageNo_review) > totalProductPage_review ||
		    	Integer.parseInt(currentShowPageNo_review) <= 0 ) {
		    	 currentShowPageNo_review = "1";
		     }
		} catch(NumberFormatException e) {
			currentShowPageNo_review = "1";
		}
		
		paraMap = new HashMap<>();
		paraMap.put("pnum", pnum);
		paraMap.put("currentShowPageNo_review", currentShowPageNo_review);
		// 특정 페이지번호에 해당하는 QnA 목록을 조회해온다.
		List<ReviewDTO> reviewList = pdao.getReviewList(paraMap);
		
		request.setAttribute("reviewList", reviewList);
		
		
		// html 에 페이지바 넣기 //
		String pageBar_review = "";
		
		int blockSize_review = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 
		
		int loop_review = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		int pageNo_review  = ( (Integer.parseInt(currentShowPageNo_review) - 1)/blockSize_review ) * blockSize_review + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		
		// *** [맨처음][이전] 만들기 *** //
		pageBar_review += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_review=1'>&lt;&lt;</a></li>";
		
		if(pageNo_review != 1) {
	    	pageBar_review += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_review="+(pageNo_review-1)+"'>&lt;</a></li>"; 
	    }
		
	    while( !(loop_review > blockSize_review || pageNo_review > totalProductPage_review) ) {
	    	
	    	if(pageNo_review == Integer.parseInt(currentShowPageNo_review)){
	    		pageBar_review += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo_review+"</a></li>"; 
	    	}
	    	else {
	    		pageBar_review += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_review="+pageNo_review+"'>"+pageNo_review+"</a></li>"; 
	    	}
	    	
	    	loop_review++;   // 1 2 3 4 5 6 7 8 9 10 
	    	
	    	pageNo_review++; //  1  2  3  4  5  6  7  8  9 10
	    	          // 11 12 13 14 15 16 17 18 19 20
	    	          // 21 22 23 24 25 26 27 28 29 30
	    	          // 31 32 33 34 35 36 37 38 39 40
	    	          // 41 42 
	    }// end of while( !(loop > blockSize || pageNo > totalPage) )---------------------------------
		
	    
	    // *** [다음][마지막] 만들기 *** //
	    // pageNo ==> 11
	    
	    if(pageNo_review <= totalProductPage_review) {
	    	pageBar_review += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_review="+pageNo_review+"'>&gt;</a></li>"; 
	    }
	    pageBar_review += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_review="+totalProductPage_review+"'>&gt;&gt;</a></li>"; 
	    
	    
		request.setAttribute("pageBar_review", pageBar_review);
		
		// ******* 페이지바 만들기 끝 ******** //
		
		
		
		
		
		
		
		
		
		// ================================================================================================================================		
		
		// QnA 갖고오기
		// 한 페이지당 10개
		
		// ===== 페이지바 만들기 ===== //
		String currentShowPageNo_QnA = request.getParameter("currentShowPageNo_QnA");
		
		if(currentShowPageNo_QnA == null) {
			currentShowPageNo_QnA = "1";
		}
		
		// 상품의 QnA목록 총 페이지수 가져오기
		int totalProductPage_QnA = pdao.getTotalQnAPage(pnum);
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.
		try {
		     if(Integer.parseInt(currentShowPageNo_QnA) > totalProductPage_QnA ||
		    	Integer.parseInt(currentShowPageNo_QnA) <= 0 ) {
		    	 currentShowPageNo_QnA = "1";
		     }
		} catch(NumberFormatException e) {
			currentShowPageNo_QnA = "1";
		}
		
		paraMap = new HashMap<>();
		paraMap.put("pnum", pnum);
		paraMap.put("currentShowPageNo_QnA", currentShowPageNo_QnA);
		// 특정 페이지번호에 해당하는 QnA 목록을 조회해온다.
		List<QnADTO> qnaList = pdao.getQnAList(paraMap);
		
		request.setAttribute("qnaList", qnaList);
		
		
		
		// html 에 페이지바 넣기 //
		String pageBar_QnA = "";
		
		int blockSize_QnA = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 
		
		int loop_QnA = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		int pageNo_QnA  = ( (Integer.parseInt(currentShowPageNo_QnA) - 1)/blockSize_QnA ) * blockSize_QnA + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		
		// *** [맨처음][이전] 만들기 *** //
		pageBar_QnA += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_QnA=1'>&lt;&lt;</a></li>";
		
		if(pageNo_QnA != 1) {
	    	pageBar_QnA += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_QnA="+(pageNo_QnA-1)+"'>&lt;</a></li>"; 
	    }
		
	    while( !(loop_QnA > blockSize_QnA || pageNo_QnA > totalProductPage_QnA) ) {
	    	
	    	if(pageNo_QnA == Integer.parseInt(currentShowPageNo_QnA)){
	    		pageBar_QnA += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo_QnA+"</a></li>"; 
	    	}
	    	else {
	    		pageBar_QnA += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_QnA="+pageNo_QnA+"'>"+pageNo_QnA+"</a></li>"; 
	    	}
	    	
	    	loop_QnA++;   // 1 2 3 4 5 6 7 8 9 10 
	    	
	    	pageNo_QnA++; //  1  2  3  4  5  6  7  8  9 10
	    	          // 11 12 13 14 15 16 17 18 19 20
	    	          // 21 22 23 24 25 26 27 28 29 30
	    	          // 31 32 33 34 35 36 37 38 39 40
	    	          // 41 42 
	    }// end of while( !(loop > blockSize || pageNo > totalPage) )---------------------------------
		
	    
	    // *** [다음][마지막] 만들기 *** //
	    // pageNo ==> 11
	    
	    if(pageNo_QnA <= totalProductPage_QnA) {
	    	pageBar_QnA += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_QnA="+pageNo_QnA+"'>&gt;</a></li>"; 
	    }
	    pageBar_QnA += "<li class='page-item'><a class='page-link' href='productDetail.go?pnum="+pnum+"&currentShowPageNo_QnA="+totalProductPage_QnA+"'>&gt;&gt;</a></li>"; 
	    
	    
		request.setAttribute("pageBar_QnA", pageBar_QnA);
		
		// ******* 페이지바 만들기 끝 ******** //
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/productDetail.jsp");
		
		
		
		
	}

}
