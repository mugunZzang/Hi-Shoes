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
	
		// 메인에서 들어올 때, 무조건 카테고리는 달고 들어옴
		/*
		 * String category = request.getParameter("category"); String str_ssize =
		 * request.getParameter("str_ssize"); String str_brand =
		 * request.getParameter("str_brand"); String str_color =
		 * request.getParameter("str_color"); String keyword =
		 * request.getParameter("keyword"); String min_price =
		 * request.getParameter("min_price"); String max_price =
		 * request.getParameter("max_price");
		 */
		/*
		String category = "운동화";	// 임의로 하나
		String str_ssize = "270,275,280";		// 사이즈 "270,275,290" 요런식으로 받아옴
		String str_brand = "아디다스,나이키";		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
		String str_color = "BLACK,WHITE";		// 색상 "BLACK,WHITE,SILVER" 
		String keyword = "";		// 검색어
		String min_price = "30000";		// 최소가격 "10000"
		String max_price = "90000";		// 최대가격 "1000000"
		*/
		
		String category = "";	// 임의로 하나
		String str_ssize = "";		// 사이즈 "270,275,290" 요런식으로 받아옴
		String str_brand = "";		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
		String str_color = "";		// 색상 "BLACK,WHITE,SILVER" 
		String keyword = "";		// 검색어
		String min_price = "";		// 최소가격 "10000"
		String max_price = "";		// 최대가격 "1000000"
		
		
		// === 각 검색필터들이 비어있는지 검사, 들어있다면 값 재조정 === //
		if (str_brand.isBlank()) {
			str_brand = "";
		}
		else {
			String[] arr_brand = str_brand.split("\\,");
			str_brand = "'" + String.join("','", arr_brand) + "'";
		}
		
		if (str_color.isBlank()) {
			str_color = "";
		}
		else {
			String[] arr_color = str_color.split("\\,");
			str_color = "'" + String.join("','", arr_color) + "'";
		}
		
		if (str_ssize.isBlank()) {
			str_ssize = "";
		}
		
		if (min_price.isBlank()) {
			min_price = "10000";
		}
		if (max_price.isBlank()) {
			max_price = "1000000";
		}
		
		

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("category", category);
		paraMap.put("str_ssize", str_ssize);
		paraMap.put("str_brand",str_brand);
		paraMap.put("str_color", str_color);
		paraMap.put("keyword", keyword);
		paraMap.put("min_price", min_price);
		paraMap.put("max_price", max_price);
		
		
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// 판매등록된 브랜드 목록 갖고오기
		//List<CatalogueDTO> brandList = pdao.getBrandList();

		// 카테고리는 5 + 전체 개 고정
		// 사이즈는 200 ~300 고정
		// 색상은 7개 + 기타
		// 가격은 10,000 ~ 1,000,000 사이로
		
		
		
		
		
		
		
		// 0. 브랜드, 색상, 사이즈, 가격, 검색어, 카테고리 입력 없는경우엔 전체 조회
		
		// 1. 카테고리 nav에서 하나 누르면 검색조건 초기화 및 그냥 카테고리에 맞는 애들만 갖고옴(검색조건 무시하고 카테고리만)
		
		// 2-1. 카테고리 안누른채로 와서 검색조건 걸면, 카테고리 상관없이 검색조건만 맞춰서 갖고옴
		
		// 2-2. 카테고리 누르고 난 후, 검색조건 걸면, 카테고리에 걸리는 애들 중 검색조건에 맞는 애들만 갖고옴
		

		
		
		
		
		
		// 브랜드, 색상, 사이즈, 가격, 검색어, 카테고리 입력 없는경우엔 전체 조회
		//List<ProductDTO> prodList = pdao.getProductList("");
		
		//request.setAttribute("prodList", prodList);
		
		/*
		 * String searchKeyword = request.getParameter("searchKeyword"); // 없으면 null 임
		 * System.out.println("searchKeyword" + searchKeyword + "123");
		 */
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/showMallByCate.jsp");
	}

}
