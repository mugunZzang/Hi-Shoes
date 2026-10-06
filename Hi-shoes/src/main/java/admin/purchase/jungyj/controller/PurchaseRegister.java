package admin.purchase.jungyj.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import admin.purchase.jungyj.model.PurchaseDAO;
import admin.purchase.jungyj.model.PurchaseDAO_imple;
import admin.supplier.jungyj.domain.SupplierDTO;
import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseRegister extends AbstractController {
	
	private CatalogueDAO cdao = new CatalogueDAO_imple();
	private SupplierDAO sdao = new SupplierDAO_imple();
	   private PurchaseDAO pdao = new PurchaseDAO_imple();   // DAO 인터페이스/구현체 이름은 맞춰서 변경
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			String isAjax = request.getParameter("isAjax");
			
			if("true".equals(isAjax)) {
				// 제품명 찾기 버튼 클릭 후 모달창의 ajax 요청인 경우
				
			    // 제품 검색어
			    String CatalogueName = request.getParameter("CatalogueName");
//			    System.out.println("카탈로그 이름1 " + CatalogueName);
			    // 카탈로그 이름1 잭
			    
			    if(CatalogueName == null) {
			    	CatalogueName = "";
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

			    paraMap.put("productName", CatalogueName);
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
		else {
			// POST 요청 시 발주하겠다는 의미
			String supname           = request.getParameter("supname");
			String str_catalogueName = request.getParameter("str_catalogueName_join");
			String str_size          = request.getParameter("str_size_join");
			String str_color         = request.getParameter("str_color_join");
			String str_qty           = request.getParameter("str_qty_join");
			String str_price         = request.getParameter("str_price_join");
/*
		    System.out.println("~~~확인용 supname :" + supname);
		    System.out.println("~~~확인용 str_catalogueName :" + str_catalogueName);
		    System.out.println("~~~확인용 str_size :" + str_size);
		    System.out.println("~~~확인용 str_color :" + str_color);
		    System.out.println("~~~확인용 str_qty :" + str_qty);
		    System.out.println("~~~확인용 str_price :" + str_price);
		    ~~~확인용 supname :테스트two
		    ~~~확인용 str_catalogueName :플랫폼 윈터 퍼 부츠 에블린,플랫폼 윈터 퍼 부츠 에블린,플랫폼 윈터 퍼 부츠 에블린,플랫폼 윈터 퍼 부츠 에블린,플랫폼 윈터 퍼 부츠 에블린
		    ~~~확인용 str_size :250,255,270,275,285
		    ~~~확인용 str_color :WHITE,WHITE,WHITE,WHITE,WHITE
		    ~~~확인용 str_qty :2,2,2,2,2
		    ~~~확인용 str_price :65000,65000,65000,65000,65000
*/
			// === Transaction 처리하기 === //
			// 한 번의 발주 = 업체 1곳 = 발주 1건 + 발주상세 N건(OR 발주 상세 1건)
			// 발주번호 채번(SELECT)
			// 발주 테이블 INSERT
			// 발주상세 테이블 INSERT 
			
		  
			int isSuccess = 0;
			String purnum = "";

			if (supname != null && !supname.isBlank() && str_catalogueName != null
			        && str_size != null && str_color != null && str_qty != null) {

			    String[] catalogueName_arr = str_catalogueName.split("\\,");
			    String[] size_arr          = str_size.split("\\,");
			    String[] color_arr         = str_color.split("\\,");
			    String[] qty_arr           = str_qty.split("\\,");
			    String[] price_arr         = str_price.split("\\,");


		        Map<String, Object> paraMap = new HashMap<>();
		        paraMap.put("supname", supname);
		        paraMap.put("catalogueName_arr", catalogueName_arr);
		        paraMap.put("size_arr", size_arr);
		        paraMap.put("color_arr", color_arr);
		        paraMap.put("qty_arr", qty_arr);
		        paraMap.put("price_arr", price_arr);

		        isSuccess = pdao.purchaseAdd(paraMap);

		        if (isSuccess == 1) {
		        	purnum = String.valueOf(paraMap.get("purchaseNo"));   // DAO가 채번한 발주번호
		        }
			    
		        JSONObject jsonObj = new JSONObject();
		        jsonObj.put("isSuccess", isSuccess);
		        jsonObj.put("purnum", purnum);

		        request.setAttribute("json", jsonObj.toString());

		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/jsonview.jsp");
			} // end of if (supname != null && !supname.isBlank() && str_catalogueName != null&& str_size != null && str_color != null && str_qty != null)
			
			
			
		}
	}

}
