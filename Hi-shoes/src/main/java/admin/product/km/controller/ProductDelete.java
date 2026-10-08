package admin.product.km.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductDelete extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pnum = request.getParameter("pnum");
		
		int n = pdao.productDelete(pnum);
		
		if(n==1) {
			
			JSONObject jsonObj = new JSONObject();
	        jsonObj.put("result", n);  
	        
	        String json = jsonObj.toString();  
	        
	        request.setAttribute("json", json);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		
		
	}

}
