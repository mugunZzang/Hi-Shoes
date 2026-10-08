package shop.kimkc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.kimkc.domain.MemberDTO;
import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.CategoryDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class ShowMallByCate extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// ==== 여기 나중에 지워야 함 ==================== //
		MemberDTO loginuser = new MemberDTO();
		loginuser.setUserid("kimkc");
		HttpSession session = request.getSession();
		session.setAttribute("loginuser", loginuser);
		//-------------------------------------------//
	
		// 브랜드 목록을 가져와 필터용 select 태그안에 option으로 넣어주기
		List<CatalogueDTO> brandList = pdao.getBrandList();
		request.setAttribute("brandList", brandList);
		
		
		/*
		String category = "운동화";	// 임의로 하나
		String str_ssize = "270,275,280";		// 사이즈 "270,275,290" 요런식으로 받아옴
		String str_brand = "아디다스,나이키";		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
		String str_color = "BLACK,WHITE";		// 색상 "BLACK,WHITE,SILVER" 
		String keyword = "";		// 검색어
		String min_price = "30000";		// 최소가격 "10000"
		String max_price = "90000";		// 최대가격 "1000000"
		*/
		String category = request.getParameter("category");
		String brand = request.getParameter("brand");
		String str_ssize = request.getParameter("str_ssize");
		String keyword = request.getParameter("keyword");
		String pre_str_color = request.getParameter("str_color");
		String min_price = request.getParameter("min_price");
		String max_price = request.getParameter("max_price");
		
		String str_color = "";	// 색상 문자열 재조정한거 담을거임
		
		//String category = "";	// 임의로 하나
		//String str_ssize = "";		// 사이즈 "270,275,290" 요런식으로 받아옴
		//String str_brand = "";		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
		//String str_color = "";		// 색상 "BLACK,WHITE,SILVER" 
		//String keyword = "";		// 검색어
		//String min_price = "";		// 최소가격 "10000"
		//String max_price = "";		// 최대가격 "1000000"
		
		
		// === 각 검색필터들이 비어있는지 검사, 들어있다면 값 재조정 === //
 		if(category == null || category.isBlank()) {
 			category = "";
 		}
		
		
		if (brand == null || brand.isBlank()) {
			brand = "";
		}
		
		
		if(pre_str_color == null || pre_str_color.isBlank()) {
			str_color = "";
		} else {
			String[] arr_color = str_color.split("\\,");
			str_color = "'" + String.join("','", arr_color) + "'";
		}
		
		
		if (str_ssize == null || str_ssize.isBlank()) {
			str_ssize = "";
		}
		
		
		
		if (min_price == null || min_price.isBlank()) {
			min_price = "10000";
		}
		if (max_price == null || max_price.isBlank()) {
			max_price = "1000000";
		}
		
		
		if(keyword == null || keyword.isBlank()) {
			keyword = "";
		}
		

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("category", category);
		paraMap.put("str_ssize", str_ssize);
		paraMap.put("brand",brand);
		paraMap.put("str_color", str_color);
		paraMap.put("keyword", keyword);
		paraMap.put("min_price", min_price);
		paraMap.put("max_price", max_price);
		
		
		request.setAttribute("category", category);
		
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
		int totalProductPage = pdao.getTotalProductPageByFilter(paraMap);
		
		
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
		paraMap.put("currentShowPageNo", currentShowPageNo);
		
		List<ProductDTO> prodList = pdao.selectProductByFilter(paraMap);
		//System.out.println("~~~~ 확인용 productList size : " + productList.size());
		/*
		~~~~ 확인용 productList size : 12
		~~~~ 확인용 productList size : 2
		~~~~ 확인용 productList size : 2
		*/
		
		request.setAttribute("prodList", prodList);
		
		

		
		// 페이지바 html에 넣는거 해야함
		String pageBar = "";
		
		int blockSize = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 
		
		int loop = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		/*
		"<%=ctxPath%>/shop/showMallByCate.go"
        + "?category=" + "${requestScope.category}"
        + "&str_ssize=" + encodeURIComponent(str_ssize)
        + "&brand=" + encodeURIComponent(brand || '')
        + "&str_color=" + encodeURIComponent(str_color)
        + "&keyword=" + encodeURIComponent(keyword)
        + "&min_price=" + encodeURIComponent(min_price)
        + "&max_price=" + encodeURIComponent(max_price);
        
        showMallByCate.go?category=카테고리&str_ssize=사이즈목록&brand=브랜드&str_color=색상들&keyword=검색어&min_price=최소가격&max_price=최대가격&currentShowPageNo=페이지수
        이 양식입니다.
        
		*/
		
		// *** [맨처음][이전] 만들기 *** //
		pageBar +=  
			"<li class='page-item'><a class='page-link' href='showMallByCate.go?category="+category+"&str_ssize="+str_ssize+"&brand="+brand+"&str_color="+pre_str_color+"&keyword="+keyword+"&min_price="+min_price+"&max_price="+max_price+"&currentShowPageNo=1'>[맨처음]</a></li>";
				
		
		if(pageNo != 1) {
	    	pageBar += 
			"<li class='page-item'><a class='page-link' href='showMallByCate.go?category="+category+"&str_ssize="+str_ssize+"&brand="+brand+"&str_color="+pre_str_color+"&keyword="+keyword+"&min_price="+min_price+"&max_price="+max_price+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
	    }
		
	    while( !(loop > blockSize || pageNo > totalProductPage) ) {
	    	
	    	if(pageNo == Integer.parseInt(currentShowPageNo)){
	    		pageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>";
	    				
	    	}
	    	else {
	    		pageBar += 
				"<li class='page-item'><a class='page-link' href='showMallByCate.go?category="+category+"&str_ssize="+str_ssize+"&brand="+brand+"&str_color="+pre_str_color+"&keyword="+keyword+"&min_price="+min_price+"&max_price="+max_price+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
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
	    	pageBar += 
			"<li class='page-item'><a class='page-link' href='showMallByCate.go?category="+category+"&str_ssize="+str_ssize+"&brand="+brand+"&str_color="+pre_str_color+"&keyword="+keyword+"&min_price="+min_price+"&max_price="+max_price+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
	    }
	    pageBar += 
		"<li class='page-item'><a class='page-link' href='showMallByCate.go?category="+category+"&str_ssize="+str_ssize+"&brand="+brand+"&str_color="+pre_str_color+"&keyword="+keyword+"&min_price="+min_price+"&max_price="+max_price+"&currentShowPageNo="+totalProductPage+"'>[마지막]</a></li>";
	    
	    
		request.setAttribute("pageBar", pageBar);
		
		// ******* 페이지바 만들기 끝 ******** //
		
		

		
		
		
		
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/showMallByCate.jsp");
	}

}
