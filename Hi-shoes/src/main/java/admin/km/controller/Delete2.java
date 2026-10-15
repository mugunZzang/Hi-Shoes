package admin.km.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class Delete2 extends AbstractController {

	private QuestionDAO qdao = new QuestionDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
					
			String num = request.getParameter("num");

			
			int n = 0;


			n = qdao.questionDelete(num);
			
			
			if(n==1) {
		
				JSONObject jsonObj = new JSONObject();
		        jsonObj.put("result", n);  
		        
		        String json = jsonObj.toString();  
		        
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
