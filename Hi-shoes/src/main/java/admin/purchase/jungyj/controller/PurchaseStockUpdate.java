package admin.purchase.jungyj.controller;

import org.json.JSONObject;

import admin.purchase.jungyj.model.PurchaseDAO;
import admin.purchase.jungyj.model.PurchaseDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseStockUpdate extends AbstractController {
	
	private PurchaseDAO pdao = new PurchaseDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			// GET 방식이면 요청 제한
			// 원래는 msg 해서 에러 보내야 하지만 일단 넘김
			return; // 종료
		} else {
			// POST 방식인 경우
			String purnum = request.getParameter("purnum");
			/*
			System.out.println("~~~확인용 purnum" + purnum);
			~~~확인용 purnum21	
			*/
			
			// 발주 상세 N 건 조회
			// 발주 상세 테이블에서 해당 발주 번호에 대한 발주 상세건 조회
			// 발주 상세건에 대해서 List에 저장
			// 이후 List의 크기만큼 반복하면서 UPDATE
			// Update 시 재고테이블의 snum과 같은것을 확인하여 List에서 꺼낸 후 발주 상세의 수량을 합침
			// 변경 결과는 boolean 이면 될듯
			int result = pdao.purdetailStockUpdate(purnum);
		
		    JSONObject jsonObj = new JSONObject();

		    jsonObj.put("result", result);
		    
		    // jsonview.jsp로 전달
		    request.setAttribute("json", jsonObj.toString());

		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/jsonview.jsp");
		}

	}

}
