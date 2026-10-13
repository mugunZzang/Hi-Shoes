package user.km.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;

public class NoticeDetail extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			String nnum = request.getParameter("nnum");
			
			Map<String, String> paraMap = new HashMap<>();
	        paraMap.put("nnum", nnum);
	
	        NoticeDTO ndto = ndao.selectNoticeOne(paraMap);
	
	        request.setAttribute("ndto", ndto);
	
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/user/noticeDetail.jsp");
		}
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "로그인을 해야 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
