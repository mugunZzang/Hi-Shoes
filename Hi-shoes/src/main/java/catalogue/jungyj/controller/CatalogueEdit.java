package catalogue.jungyj.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import catalogue.jungyj.model.CatalogueDAO;
import catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CatalogueEdit extends AbstractController {

	private CatalogueDAO cataldao = new CatalogueDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			String pname = request.getParameter("pname");
			String purprice = request.getParameter("purprice");
			String regprice = request.getParameter("regprice");
			String saleprice = request.getParameter("saleprice");
			String brand = request.getParameter("brand");
			
			/*
			System.out.println("~~~확인용 pname: " + pname);
			System.out.println("~~~확인용 purprice: " + purprice);
			System.out.println("~~~확인용 regprice: " + regprice);
			System.out.println("~~~확인용 saleprice: " + saleprice);
			System.out.println("~~~확인용 brand: " + brand);
			~~~확인용 pname: 핸드볼 스페지알 로우 프로
			~~~확인용 purprice: 1232000
			~~~확인용 regprice: 149000
			~~~확인용 saleprice: 145000
			~~~확인용 brand: 아디다스
			*/
			
			// 값을 Map 에 담아서 전달함 
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("pname", pname);
			paraMap.put("purprice", purprice);
			paraMap.put("regprice", regprice);
			paraMap.put("saleprice", saleprice);
			paraMap.put("brand", brand);
			
			// 관리자가 입력한 새로운 값으로 변경하기
			int result = cataldao.catalogueEdit(paraMap);
			
			if(result == 1) {
				// 변경 성공
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("result", result);
				
	            request.setAttribute("json", jsonObj.toString());
	            

	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");

	            return;
			} else {
				System.out.println("관리자 CatalogueEdit 에서 값 변경 실패");
			}
			
			
		}

	}

}
