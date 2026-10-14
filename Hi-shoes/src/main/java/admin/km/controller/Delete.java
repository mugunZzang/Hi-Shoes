package admin.km.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;

public class Delete extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	private FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우
					
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
