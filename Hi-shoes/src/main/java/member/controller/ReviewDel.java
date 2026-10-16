package member.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class ReviewDel extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			// **** POST 방식으로 넘어온 것이라면 **** //
			
			String reviewNo = request.getParameter("reviewNo");
			
			int n = 0;
			try {
				n = mdao.reviewDel(reviewNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			JSONObject jsObj = new JSONObject();	//{}
			jsObj.put("n", n);	// {"n":1} 또는 {"n":0}
			
			String json = jsObj.toString();	//문자열 형태로 변환해줌.
			// {"n":1} 또는 {"n":0}
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		else {
			// **** POST 방식으로 넘어온 것이 아니라면 **** //
	         
	         String message = "비정상적인 경로를 통해 들어왔습니다.!!";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
