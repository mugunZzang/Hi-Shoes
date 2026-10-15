package member.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Authentication extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if ("POST".equals(request.getMethod())) {
	         
	         MemberDTO loginuser = (MemberDTO) request.getSession().getAttribute("loginuser");
	         
	         if (loginuser == null) {
	             request.setAttribute("message", "서비스 이용을 원하시면 로그인 하시길바랍니다.");
	             request.setAttribute("loc", request.getContextPath() + "/index.go");
	                  
	             super.setRedirect(false);
	             super.setViewPage("/WEB-INF/msg.jsp");
	                  
	             return;
	         }
	         
	         
	         // 여기다 할거 작성
	         String userid = loginuser.getUserid();
	         String pwd = request.getParameter("pwd");
	         
	         Map<String, String> paraMap = new HashMap<>();
	         paraMap.put("userid", userid);
	         paraMap.put("pwd", pwd);
	         
	         int n = 0;
	         try {
	        	 n = mdao.authentication(paraMap);
			 } catch (SQLException e) {
				e.printStackTrace();
			 }
	         
         	 JSONObject jsonObj = new JSONObject();
	         jsonObj.put("n", n);
	         
	         String json = jsonObj.toString();

	         request.setAttribute("json", json);

	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/jsonview.jsp");
	      }
	      
	      else {
	         request.setAttribute("message", "잘못된 접근입니다.");
	          request.setAttribute("loc", request.getContextPath() + "/index.go");
	               
	          super.setRedirect(false);
	          super.setViewPage("/WEB-INF/msg.jsp");
	               
	          return;
	      }
    }

}
