package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class IdFind extends AbstractController{

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equals(method)) {
			//아이디 찾기 모달창에서 "찾기" 버튼을 클릭해씅ㄹ 경우
			
			String company = request.getParameter("company");
			String email = request.getParameter("email");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("company", company);
			paraMap.put("email", email);
			
			String userid = mdao.findUserid(paraMap);
			
			if(userid != null) {
				request.setAttribute("userid", userid);
			}
			else {
				request.setAttribute("userid", "아이디가 존재하지 않습니다.");
			}
			
			request.setAttribute("company", company);
			request.setAttribute("email", email);
			
		}	// end of if("POST".equals(method))----------
		
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idFind.jsp");
	}

}
