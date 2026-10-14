package admin.product.km.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductNameSelect extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우
					
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
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
