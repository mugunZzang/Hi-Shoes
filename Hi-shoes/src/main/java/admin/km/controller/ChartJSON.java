package admin.km.controller;

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


public class ChartJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우


	        List<Map<String, String>> chartList = pdao.selectPurchaseChart();
	
	        JSONArray jsonArr = new JSONArray();
	
	        if (chartList.size() > 0) {
	
	            for (Map<String, String> map : chartList) {
	
	                JSONObject jsonObj = new JSONObject();
	
	                jsonObj.put("purdate", map.get("purdate"));
	                jsonObj.put("supname", map.get("supname"));
	                jsonObj.put("totalamount", Long.parseLong(map.get("totalamount")));
	
	                jsonArr.put(jsonObj);
	            }
	        }
	
	        String json = jsonArr.toString();
	
	        request.setAttribute("json", json);
	
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/jsonview.jsp");
        
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
