package question.km.controller;

import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class QuestionEditAnswer extends AbstractController {

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp"); 

	}

}
