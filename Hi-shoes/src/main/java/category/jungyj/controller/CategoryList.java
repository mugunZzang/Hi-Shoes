package category.jungyj.controller;

import java.util.List;

import org.json.JSONObject;

import category.jungyj.domain.CategoryDTO;
import category.jungyj.model.CategoryDAO;
import category.jungyj.model.CategoryDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryList extends AbstractController {

	private CategoryDAO catedao = new CategoryDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equals(method)) {
			
			List<CategoryDTO> categoryList = catedao.selectCategoryList();
			
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("categoryList", categoryList);
            
            request.setAttribute("json", jsonObj.toString());

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}

	}

}
