package catalogue.jungyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catalogue.jungyj.model.CatalogueDAO;
import catalogue.jungyj.model.CatalogueDAO_imple;
import category.jungyj.domain.CategoryDTO;
import category.jungyj.model.CategoryDAO;
import category.jungyj.model.CategoryDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CatalogueRegister extends AbstractController {

	private CategoryDAO catedao = new CategoryDAO_imple();
	private CatalogueDAO cataldao = new CatalogueDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			// 카테고리 목록을 조회해오기
			List<CategoryDTO> categoryList = catedao.selectCategoryList();
			request.setAttribute("categoryList", categoryList);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/catalogue/adminCatalogue.jsp");
		} else {
			// POST 로 온 경우
			String pname = request.getParameter("pname");                     // 제품명
			String fk_catenum = request.getParameter("fk_catenum");           // 카테고리 코드
			String pnamepurprice = request.getParameter("purprice");
			String regprice = request.getParameter("regprice");
			String saleprice = request.getParameter("saleprice");
			String brand = request.getParameter("brand");
			
			/*
			System.out.println("~~~확인용 pname : " + pname);
			System.out.println("~~~확인용 fk_catenum : " + fk_catenum);
			System.out.println("~~~확인용 pnamepurprice : " + pnamepurprice);
			System.out.println("~~~확인용 regprice : " + regprice);
			System.out.println("~~~확인용 saleprice : " + saleprice);
			System.out.println("~~~확인용 brand : " + brand);
			*/
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("pname", pname);
			paraMap.put("fk_catenum", fk_catenum);
			paraMap.put("pnamepurprice", pnamepurprice);
			paraMap.put("regprice", regprice);
			paraMap.put("saleprice", saleprice);
			paraMap.put("brand", brand);
			
			int n = cataldao.catalogueRegister(paraMap);
			
			if(n == 1) {
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/catalogue/adminCatalogueList.jsp");  // 카탈로그 조회 페이지로 이동
			} else {
				System.out.println("에러발생");
			}
		}

	}

}
