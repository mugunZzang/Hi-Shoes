package admin.purchase.jungyj.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import admin.supplier.jungyj.domain.SupplierDTO;
import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseRegister extends AbstractController {
	
	private CatalogueDAO cdao = new CatalogueDAO_imple();
	private SupplierDAO sdao = new SupplierDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			String isAjax = request.getParameter("isAjax");
			
			if("true".equals(isAjax)) {
				// 제품명 찾기 버튼 클릭 후 모달창의 ajax 요청인 경우
				
			    // 제품 검색어
			    String productName = request.getParameter("productName");

			    if(productName == null) {
			        productName = "";
			    }


			    // 현재 페이지 번호
			    String currentShowPageNo = request.getParameter("currentShowPageNo");

			    if(currentShowPageNo == null) {
			        currentShowPageNo = "1";
			    }


			    // 한 페이지에 보여줄 개수
			    int sizePerPage = 10;


			    // DAO에 전달할 데이터
			    Map<String, String> paraMap = new HashMap<>();

			    paraMap.put("productName", productName);
			    paraMap.put("currentShowPageNo", currentShowPageNo);


			    // 전체 카탈로그 개수
			    int totalCount = cdao.getTotalCountCatalogue(paraMap);


			    // 전체 페이지 수
			    int totalPage = (int)Math.ceil((double)totalCount / sizePerPage);


			    // 현재 페이지의 카탈로그 목록
			    List<Map<String, String>> catalogueList = cdao.getCatalogueList(paraMap);
			 
			    // 페이지바 만들기
			    String pageBar = "";

			    int currentPage = Integer.parseInt(currentShowPageNo);

			    // 이전
			    if(currentPage > 1) {
			        pageBar += "<li class='page-item'>";
			        pageBar += "<a class='page-link' href='#' data-page='" + (currentPage - 1) + "'>이전</a>";
			        pageBar += "</li>";
			    }

			    // 전체 페이지 번호
			    for(int page = 1; page <= totalPage; page++) {

			        if(page == currentPage) {
			            pageBar += "<li class='page-item active'>";
			        }
			        else {
			            pageBar += "<li class='page-item'>";
			        }

			        pageBar += "<a class='page-link' href='#' data-page='" + page + "'>";
			        pageBar += page;
			        pageBar += "</a>";
			        pageBar += "</li>";
			    }

			    // 다음
			    if(currentPage < totalPage) {
			        pageBar += "<li class='page-item'>";
			        pageBar += "<a class='page-link' href='#' data-page='" + (currentPage + 1) + "'>다음</a>";
			        pageBar += "</li>";
			    }
			    
//			    System.out.println("pageBar = " + pageBar);
			    
			    // JSON 생성
			    JSONObject jsonObj = new JSONObject();

			    jsonObj.put("catalogueList", catalogueList);
			    jsonObj.put("totalCount", totalCount);
			    jsonObj.put("currentShowPageNo", currentShowPageNo);
			    jsonObj.put("sizePerPage", sizePerPage);
			    jsonObj.put("totalPage", totalPage);
			    jsonObj.put("pageBar", pageBar);

			    // jsonview.jsp로 전달
			    request.setAttribute("json", jsonObj.toString());

			    super.setRedirect(false);
			    super.setViewPage("/WEB-INF/jsonview.jsp");
				
			} else {
				// 제품명 찾기 버튼 클릭 후 모달창의 ajax 요청이 아닌 경우
				
				
				// 공급업체 목록
				List<SupplierDTO> supplierList = sdao.selectSuppliernopaging();
				request.setAttribute("supplierList", supplierList);
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/purchase/adminpurchaseRegister.jsp");
				
			} // end of if("true".equals(isAjax)) ~ else----------------------------
			
		} // end of if("GET".equals(method)) ~ else----------------------------------
	}

}
