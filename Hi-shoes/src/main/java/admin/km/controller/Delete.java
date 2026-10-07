package admin.km.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;

public class Delete extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	private FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String num = request.getParameter("num");
		String type = request.getParameter("type");
		
		int n = 0;
		if("notice".equals(type)) {
			n = ndao.noticeDelete(num);
			
		}
		else if("faq".equals(type)) {
			n = fdao.faqDelete(num);
		}
		
		if(n==1) {
	
			JSONObject jsonObj = new JSONObject();
	        jsonObj.put("result", n);  
	        
	        String json = jsonObj.toString();  
	        
	        request.setAttribute("json", json);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		
	}

}
