package admin.catalogue.jungyj.controller;

import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CatalogueDelete extends AbstractController {

	private CatalogueDAO cataldao = new CatalogueDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String pname = request.getParameter("pname");
			
			int result = cataldao.catalogueDelete(pname);
			
			if(result == 1) {
				// 카탈로그 삭제 성공
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("result", result);
				
	            request.setAttribute("json", jsonObj.toString());
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");				
				
			} else {
				System.out.println("[WARN] catalogue.jungyj.controller.CatalogueDelete 제품 삭제 실패");
			}
			
			
		}
		
	}

}
