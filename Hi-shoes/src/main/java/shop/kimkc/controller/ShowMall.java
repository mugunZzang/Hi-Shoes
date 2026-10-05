package shop.kimkc.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;


// 상품 목록 페이지 컨트롤러
public class ShowMall extends AbstractController {
	
	private ProductDAO pdao = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 헤더의 검색바의 검색키워드
		//String searchKeyword = request.getParameter("searchKeyword");
		String searchKeyword = "";
		
		// 검색키워드를 적용한 상품목록 가져오기
		List<ProductDTO> prodList = pdao.getProductList(searchKeyword);
		//System.out.println("prodList 사이즈 " + prodList.size());
		
		request.setAttribute("prodList", prodList);
		request.setAttribute("searchKeyword", searchKeyword);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/showMall.jsp");
	}

}
