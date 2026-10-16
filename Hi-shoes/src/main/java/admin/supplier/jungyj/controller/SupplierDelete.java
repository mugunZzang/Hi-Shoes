package admin.supplier.jungyj.controller;

import org.json.JSONObject;

import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SupplierDelete extends AbstractController {

	private SupplierDAO sdao = new SupplierDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String supname = request.getParameter("supname");
			System.out.println("~~~확인용 supname" + supname);
			
			
			int result = sdao.supplyDelete(supname);
			
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("result", result);
            
            request.setAttribute("json", jsonObj.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
		}

	}

}
