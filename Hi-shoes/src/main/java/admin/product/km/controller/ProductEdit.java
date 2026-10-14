package admin.product.km.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import product.km.domain.ProductDTO;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductEdit extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
					// 관리자(admin)로 로그인 했을 경우
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
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
