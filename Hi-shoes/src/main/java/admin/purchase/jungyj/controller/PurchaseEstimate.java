package admin.purchase.jungyj.controller;

import java.util.List;
import java.util.Map;

import admin.purchase.jungyj.model.PurchaseDAO;
import admin.purchase.jungyj.model.PurchaseDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseEstimate extends AbstractController {
	
	private PurchaseDAO pdao = new PurchaseDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			String purnum = request.getParameter("purnum");
			String isAjax = request.getParameter("isAjax");
//			System.out.println("확인용 punum" + punum);
//			확인용 punum1
			
			// 발주 + 공급업체 1건 검색해오기
			Map<String, String> purchaseMap = pdao.selectPurchase(purnum);
			
			// DAO가 항상 new HashMap<>()을 반환하므로 null이 아니라 isEmpty로 체크
			if(purchaseMap.isEmpty()) {
//				request.setAttribute("message", "존재하지 않는 발주입니다.");
//				request.setAttribute("loc", request.getContextPath() + "/admin/purchase/purchaseList.go");
				System.out.println("현재 존재하지 않는 발주 + 공급업체임");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			
			// 발주 상세 N 건 조회
			List<Map<String, String>> detailList = pdao.selectPurchaseDetail(purnum);
			
			// 4. 최종 견적가 (amount가 문자열이므로 long으로 변환해서 합산)
			int total = 0;
			for(Map<String, String> d : detailList) {
				total += Integer.parseInt(d.get("amount"));
			}
			
			request.setAttribute("purchaseMap", purchaseMap);
			request.setAttribute("detailList", detailList);
			request.setAttribute("total", total);
			
			if ("true".equals(isAjax)) {
			    super.setRedirect(false);
			    super.setViewPage("/WEB-INF/admin/purchase/quotationContent.jsp");
			}
			else {
			    super.setRedirect(false);
			    super.setViewPage("/WEB-INF/admin/purchase/adminurchaseEstimate.jsp");
			}
		}

	}

}
