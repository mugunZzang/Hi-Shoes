package admin.product.km.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.domain.ProductDTO;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductEdit extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pnum = request.getParameter("pnum");
		
		// 해당 pnum에 해당하는 제품 정보를 불러온다.
		ProductDTO pdto = pdao.selectOneProd(pnum);
		
		// 해당 pnum에 해당하는 추가이미지 제품 정보를 불러온다.
		List<String> imageList = pdao.selectProductImages(pnum);
		
		request.setAttribute("pdto", pdto);
		request.setAttribute("imageList", imageList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/product/productEdit.jsp");

	}

}
