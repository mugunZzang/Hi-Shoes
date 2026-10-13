package question.km.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;


public class QuestionEdit extends AbstractController {
	
	private QuestionDAO qdao = new QuestionDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우

		
	        if ("GET".equals(method)) {
	
	        	String qnanum = request.getParameter("qnanum");
	    		
	    		Map<String, String> question = qdao.selectQuestionOne(qnanum);
	    		
	    		request.setAttribute("question", question);
	    		
	    		super.setRedirect(false);
	    		super.setViewPage("/WEB-INF/admin/callcenter/questionEdit.jsp"); 
	        }
	
	        else {
	        	
	        	String qnanum = request.getParameter("qnanum");
	            String qanswer = request.getParameter("qanswer");
	            
	            Map<String,String> paraMap = new HashMap<>();
	            paraMap.put("qnanum", qnanum);
	            paraMap.put("qanswer", qanswer);
	            
	            int n = qdao.wirteAnswer(paraMap);
	
	            if (n == 1) {
	                super.setRedirect(true);
	                super.setViewPage(request.getContextPath()+ "/admin/callcenter/callcenter.go?tab=question");
	            }
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


