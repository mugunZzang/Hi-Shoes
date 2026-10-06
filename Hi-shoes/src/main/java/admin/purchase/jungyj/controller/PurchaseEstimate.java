package admin.purchase.jungyj.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseEstimate extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/purchase/adminurchaseEstimate.jsp");
		}

	}

}
