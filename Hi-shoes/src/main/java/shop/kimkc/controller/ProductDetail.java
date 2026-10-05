package shop.kimkc.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.kimkc.domain.MemberDTO;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class ProductDetail extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * HttpSession session = request.getSession(); MemberDTO mdto = (MemberDTO)
		 * session.getAttribute("loginuser"); 
		 * String userid = mdto.getUserid();
		 */
		
		// 로그인 했는지 검사해야 함.
		
		String pnum = request.getParameter("pnum");
		
		// 클릭한 상품의 정보를 갖고온다.
		ProductDTO pdto = pdao.getProductInfo(pnum);
		request.setAttribute("pdto", pdto);

		
		// 선택 상품의 사이즈 목록 갖고오기
		List<Integer> sizeList = pdao.getProductSizes(pnum);
		request.setAttribute("sizeList", sizeList);
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/productDetail.jsp");
		
		
		
		
	}

}
