package admin.purchase.jungyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import admin.purchase.jungyj.model.PurchaseDAO;
import admin.purchase.jungyj.model.PurchaseDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseList extends AbstractController {

	private PurchaseDAO pdao = new PurchaseDAO_imple();
	
	
    private static final int SIZE_PER_PAGE = 10;
    private static final int BLOCK_SIZE = 10;

    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			// 발주 목록 조회(SELECT) 페이징 처리 X
//			List<Map<String, String>> purchaseList = pdao.selectPurchaseList();
			
/*
			if(purchaseList.size() > 0) {
				System.out.println("발주 리스트가 현재 존재함");
			} else {
				System.out.println("발주 리스트가 현재 존재하지 않음");
			}
*/
            // ============================================================
            // 현재 페이지 번호
            // ============================================================

            String currentShowPageNo = request.getParameter("currentShowPageNo");

            if (currentShowPageNo == null) {
                currentShowPageNo = "1";
            }

            int currentPage;

            try {
                currentPage = Integer.parseInt(currentShowPageNo);

                if (currentPage < 1) {
                    currentPage = 1;
                }

            } catch (NumberFormatException e) {
                currentPage = 1;
            }

            currentShowPageNo = String.valueOf(currentPage);


            // ============================================================
            // 페이징 정보
            // ============================================================

            Map<String, String> paraMap = new HashMap<>();

            paraMap.put("currentShowPageNo", currentShowPageNo);


            // 전체 발주 개수
            int totalCountPurchase = pdao.getTotalCountPurchase(paraMap);
/*
            System.out.println("~~~ 확인용 totalCountPurchase: " + totalCountPurchase);
            ~~~ 확인용 totalCountPurchase: 2
*/
            
            // 전체 페이지 수
            int totalPage = (int) Math.ceil((double) totalCountPurchase / SIZE_PER_PAGE);

            // 현재 페이지의 발주 목록
            List<Map<String, String>> purchaseList = pdao.selectPurchaseList(paraMap);

            // 페이지바
            String pageBar = makePageBar(currentPage, totalPage);

            // JSP로 데이터 전달

            request.setAttribute("purchaseList", purchaseList);
            request.setAttribute("pageBar", pageBar);
            request.setAttribute("totalCountPurchase", totalCountPurchase);
            request.setAttribute("currentShowPageNo", currentShowPageNo);
            request.setAttribute("sizePerPage", SIZE_PER_PAGE);


            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/purchase/adminPurchaseList.jsp");
        }
			

	}

	// ================================================================
	// 페이지바 생성
	// ================================================================
	
	private String makePageBar(int currentPage, int totalPage) {	
	    if (totalPage == 0) {
	        return "";
	    }
	
	    StringBuilder sb = new StringBuilder();
	
	    int loop = 1;
	    int pageNo = ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;
	
	    // [맨처음]
	    sb.append(pageItem(1, "[맨처음]", false));
	
	    // [이전]
	    if (pageNo != 1) {
	        sb.append(pageItem(pageNo - 1, "[이전]", false));
	    }
	
	    // 페이지 번호
	    while (!(loop > BLOCK_SIZE || pageNo > totalPage)) {
	        sb.append( pageItem(pageNo, String.valueOf(pageNo), pageNo == currentPage));
	
	        loop++;
	        pageNo++;
	    }
	
	    // [다음]
	    if (pageNo <= totalPage) {
	        sb.append( pageItem(pageNo, "[다음]", false));
	    }
	
	    // [마지막]
	    sb.append(pageItem(totalPage, "[마지막]", false));
	
	    return sb.toString();
	} // end of private String makePageBar(int currentPage, int totalPage)-----------------------
	
	
	// 페이지바의 li 하나 생성
	private String pageItem(int page, String label, boolean active) {
	
	    return "<li class='page-item"
	         + (active ? " active" : "")
	         + "'>"
	         + "<a class='page-link' href='?currentShowPageNo="
	         + page
	         + "'>"
	         + label
	         + "</a>"
	         + "</li>";
	} // end of private String pageItem(int page, String label, boolean active)------------------------------
	
	

}
