package category.jungyj.controller;

import org.json.JSONObject;

import category.jungyj.model.CategoryDAO;
import category.jungyj.model.CategoryDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryRegister extends AbstractController {
	
	private CategoryDAO catedao = new CategoryDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			
			String catename = request.getParameter("catename");
			
			int result = catedao.categoryRegister(catename);
			
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("result", result);
            
            request.setAttribute("json", jsonObj.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		
	}

}
