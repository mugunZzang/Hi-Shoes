package catalogue.jungyj.controller;

import org.json.JSONObject;

import catalogue.jungyj.model.CatalogueDAO;
import catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PnameDuplicateCheck extends AbstractController {

	private CatalogueDAO cataldao = new CatalogueDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String pname = request.getParameter("pname");
			
			try {
				boolean isExists = cataldao.pnameDuplicateCheck(pname);
				
				JSONObject jsonObj = new JSONObject();       // {} 
				jsonObj.put("isExists", isExists);           // {"isExists" : true} 또는 {"isExists" : false} 으로 만들어준다.
				
				String json = jsonObj.toString();   // 문자열 형태인 "{"isExists" : true}" 또는 "{"isExists" : false}" 으로 만들어준다.
//				System.out.println(">>> 확인용 json => " + json);
				
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/jsonview.jsp");

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("제품명 중복검사 실패");
			}
			
		}
		

	}

}
