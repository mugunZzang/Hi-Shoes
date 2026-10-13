package question.km.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;


public class QuestionEdit extends AbstractController {
	
	private QuestionDAO qdao = new QuestionDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
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
		
		

}


