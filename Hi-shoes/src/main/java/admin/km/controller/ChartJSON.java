package admin.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;


public class ChartJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	  Map<String,Object> paraMap = new HashMap<>();
		
	  List<Map<String,String>> sup_map_List =  pdao.sup_cnt();
	  // List<Map<String,String>> sup_price_map_List =  pdao.sup_price();
  	  
  	  JSONArray json_arr = new JSONArray(); // []
  	  
  	  if(sup_map_List.size() > 0) {
  		  for(Map<String, String> map : sup_map_List) {
                JSONObject json_obj = new JSONObject(); // {}
                
                
                json_arr.put(json_obj);
             }// end of for--------------
  	  }
  	  
  	  paraMap.put("sup_map_List", json_arr);
  	  
  	  json_arr = new JSONArray();
  	  
  	  if(sup_price_map_List.size() > 0) {
		  for(Map<String, String> map : sup_price_map_List) {
              JSONObject json_obj = new JSONObject(); // {}
              
              
              json_arr.put(json_obj);
           }// end of for--------------
	  }
  	  
  	  paraMap.put("sup_price_map_List", json_arr);
  	  
  	  request.setAttribute("json", json_arr.toString());
  	  
  	  super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
