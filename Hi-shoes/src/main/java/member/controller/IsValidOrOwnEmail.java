package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class IsValidOrOwnEmail extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();	//"GET" 도는 "POST"
		
		if("POST".equals(method)) {
			String email = request.getParameter("email");			
			String userid = request.getParameter("userid");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("email", email);
			paraMap.put("userid", userid);
			
			int isMyEmail = mdao.IsValidOrOwnEmail(paraMap);
			
			
			JSONObject jsonObj = new JSONObject();	
			jsonObj.put("isMyEmail", isMyEmail);	// {"isExists":true} 또는 {"isExists":false} 로 만들어준다.
			String json = jsonObj.toString();	// 문자열 형태인 {"isExists":true} 또는 {"isExists":false} 로 만들어준다.
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}	
	}

}
