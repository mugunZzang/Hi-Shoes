package admin.category.jungyj.controller;

import org.json.JSONObject;

import admin.category.jungyj.model.CategoryDAO;
import admin.category.jungyj.model.CategoryDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryDelete extends AbstractController {
	
	private CategoryDAO catedao = new CategoryDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String cateno = request.getParameter("cateno");
			
			int result = catedao.categoryDelete(cateno);
			
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("result", result);
            
            request.setAttribute("json", jsonObj.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
		}

	}

}
