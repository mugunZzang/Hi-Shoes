package admin.product.km.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductNameSelect extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String searchWord = request.getParameter("searchWord");
		
		List<Map<String,String>> productNameList = pdao.ProductNameSelect(searchWord);
		
		JSONArray jsArr = new JSONArray(); // []
		
		if(productNameList.size() > 0) {
			for(Map<String,String> nameList : productNameList) {
	            JSONObject jsobj = new JSONObject();                
	            jsobj.put("pname", nameList.get("pname"));     
	            
	            jsArr.put(jsobj);  
	         }// end of for----------------------
			
			String json = jsArr.toString();  // 문자열 형태로 변환해줌.
		      
		    request.setAttribute("json", json);
		    
		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/jsonview.jsp");
		
		}
		
	}

}
