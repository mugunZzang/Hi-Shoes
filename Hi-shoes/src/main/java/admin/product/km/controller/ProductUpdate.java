package admin.product.km.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;

public class ProductUpdate extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우
					
			String pnum = request.getParameter("pnum");
			String pcontent = request.getParameter("pcontent");
			pcontent = pcontent.replaceAll("<", "&lt;");
	        pcontent = pcontent.replaceAll(">", "&gt;");
	        
	        // 입력한 내용에서 엔터는 <br>로 변환하기
	        pcontent = pcontent.replaceAll("\r\n", "<br>");
			String deliveryfee = request.getParameter("deliveryfee");
			
			Map<String,String> paraMap = new HashMap<>();
			
			paraMap.put("pnum", pnum);
			paraMap.put("pcontent", pcontent);
			paraMap.put("deliveryfee", deliveryfee);
			
			int result = pdao.productUpdate(paraMap);
			
			JSONObject jsonObj = new JSONObject(); // {}
	        jsonObj.put("result", result); // {"result":1}
	        
	        String json = jsonObj.toString(); // 문자열로 변환
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
