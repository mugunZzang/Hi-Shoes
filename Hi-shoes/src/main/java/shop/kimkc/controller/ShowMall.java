package shop.kimkc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.kimkc.domain.MemberDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;


// 상품 목록 페이지 컨트롤러
public class ShowMall extends AbstractController {
	
	private ProductDAO pdao = new ProductDAO_imple();
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// ==== 여기 나중에 지워야 함 ==================== //
		MemberDTO loginuser = new MemberDTO();
		loginuser.setUserid("kimkc");
		HttpSession session = request.getSession();
		session.setAttribute("loginuser", loginuser);
		//-------------------------------------------//
		
		
		// 헤더의 검색바의 검색키워드
		//String searchKeyword = request.getParameter("searchKeyword");
		String searchKeyword = "";
		
		
		/////////////////////////////////////////////////////////////////////////////////////
		// ===== 페이지바 만들기 ===== //
		
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		// currentShowPageNo 은 사용자가 보고자하는 페이지바의 페이지번호 이다.
        // 카테고리 메뉴에서 카테고리명만을 클릭했을 경우에는 currentShowPageNo 은 null 이 된다.
        // currentShowPageNo 이 null 이라면 currentShowPageNo 을 1 페이지로 바꾸어야 한다.
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		
		// 검색결과로 나온 상품목록의 총 페이지수 가져오기
		int totalProductPage = pdao.getTotalProductPage(searchKeyword);
		
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.
		try {
		     if(Integer.parseInt(currentShowPageNo) > totalProductPage ||
		    	Integer.parseInt(currentShowPageNo) <= 0 ) {
		    	 currentShowPageNo = "1";
		     }
		} catch(NumberFormatException e) {
			currentShowPageNo = "1";
		}
		
		
		// ==== 특정 검색어에 의한 결과를 사용자가 보고자 하는 특정 페이지번호에 해당하는 제품들을 조회해온다. ==== //
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchKeyword", searchKeyword);
		paraMap.put("currentShowPageNo", currentShowPageNo);
		
		List<ProductDTO> prodList = pdao.selectProductByKeyword(paraMap);
		//System.out.println("~~~~ 확인용 productList size : " + productList.size());
		/*
		~~~~ 확인용 productList size : 12
		~~~~ 확인용 productList size : 2
		~~~~ 확인용 productList size : 2
		*/
		
		request.setAttribute("prodList", prodList);
		
		
		// --------------------------------------------------------------------------------
		// html 에 페이지바 넣기
		
		String pageBar = "";
		
		int blockSize = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 
		
		int loop = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		
		// *** [맨처음][이전] 만들기 *** //
		pageBar += "<li class='page-item'><a class='page-link' href='showMall.go?searchKeyword="+searchKeyword+"&currentShowPageNo=1'>[맨처음]</a></li>";
		
		if(pageNo != 1) {
	    	pageBar += "<li class='page-item'><a class='page-link' href='showMall.go?searchKeyword="+searchKeyword+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>"; 
	    }
		
	    while( !(loop > blockSize || pageNo > totalProductPage) ) {
	    	
	    	if(pageNo == Integer.parseInt(currentShowPageNo)){
	    		pageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>"; 
	    	}
	    	else {
	    		pageBar += "<li class='page-item'><a class='page-link' href='showMall.go?searchKeyword="+searchKeyword+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
	    	}
	    	
	    	loop++;   // 1 2 3 4 5 6 7 8 9 10 
	    	
	    	pageNo++; //  1  2  3  4  5  6  7  8  9 10
	    	          // 11 12 13 14 15 16 17 18 19 20
	    	          // 21 22 23 24 25 26 27 28 29 30
	    	          // 31 32 33 34 35 36 37 38 39 40
	    	          // 41 42 
	    }// end of while( !(loop > blockSize || pageNo > totalPage) )---------------------------------
		
	    
	    // *** [다음][마지막] 만들기 *** //
	    // pageNo ==> 11
	    
	    if(pageNo <= totalProductPage) {
	    	pageBar += "<li class='page-item'><a class='page-link' href='showMall.go?searchKeyword="+searchKeyword+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>"; 
	    }
	    pageBar += "<li class='page-item'><a class='page-link' href='showMall.go?searchKeyword="+searchKeyword+"&currentShowPageNo="+totalProductPage+"'>[마지막]</a></li>"; 
	    
	    
		request.setAttribute("pageBar", pageBar);
		
		// ******* 페이지바 만들기 끝 ******** //
		
		
		
		//////////////////////////////////////////////////////////////////////////////
		
		// 검색키워드를 적용한 상품목록 가져오기
		//List<ProductDTO> prodList = pdao.getProductList(searchKeyword);
		//System.out.println("prodList 사이즈 " + prodList.size());
		
		//request.setAttribute("prodList", prodList);
		request.setAttribute("searchKeyword", searchKeyword);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/showMall.jsp");
	}

}
