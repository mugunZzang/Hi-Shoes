package admin.catalogue.jungyj.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StockList extends AbstractController {

	private CatalogueDAO cdao = new CatalogueDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String pname = request.getParameter("pname");
		
//		System.out.println("~~~확인용 pname : " + pname );
		// ~~~확인용 pname : 핸드볼 스페지알 로우 프로

		// 제품명에 해당하는 색상, 사이즈, 재고량 조회
		List<Map<String, String>> stockList = cdao.stockSelect(pname);

		JSONArray jsonArr = new JSONArray(); // []
		
	    if(stockList.size() > 0) {
	         for(Map<String, String> stockMap : stockList) {
	            JSONObject jsonObj = new JSONObject(); // {}
	            
	            String ssize = stockMap.get("ssize");
	            String color = stockMap.get("color");
	            String sqty = stockMap.get("sqty");
         
//	            System.out.println("~~~ 확인용 ssize" + ssize);
//	            System.out.println("~~~ 확인용 color" + color);
//	            System.out.println("~~~ 확인용 sqty" + sqty);
	            
	            jsonObj.put("ssize", ssize);
	            jsonObj.put("color", color);
	            jsonObj.put("sqty", sqty);

	            jsonArr.put(jsonObj); // [{},{},{},{},{}]
	        }// end of for---------------------
	    }
	    
	    String json = jsonArr.toString();
//	    System.out.println("~~~ 확인용 json" + json);
	    // ~~~ 확인용 json[{"color":"WHITE","sqty":"0","ssize":"200"},{"color":"WHITE","sqty":"0","ssize":"205"},....
        request.setAttribute("json", json);
     
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");

	}

}
