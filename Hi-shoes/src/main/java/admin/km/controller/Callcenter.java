package admin.km.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import faq.km.domain.FaqDTO;
import faq.km.model.FaqDAO;
import faq.km.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.km.domain.MemberDTO;
import notice.km.domain.NoticeDTO;
import notice.km.model.NoticeDAO;
import notice.km.model.NoticeDAO_imple;
import question.km.domin.QuestionDTO;
import question.km.model.QuestionDAO;
import question.km.model.QuestionDAO_imple;

public class Callcenter extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();
	private FaqDAO fdao = new FaqDAO_imple();
	private QuestionDAO qdao = new QuestionDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			List<NoticeDTO> noticeList = null;
			List<FaqDTO> faqList = null;
			List<QuestionDTO> questionList = null;
			
			HttpSession session = request.getSession();
	
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	
			// if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// //관리자(admin)로 로그인 했을 경우
			String tab = request.getParameter("tab");
			String searchType = request.getParameter("searchType");
			String searchWord = request.getParameter("searchWord");
	
			System.out.println("~~~ 확인용 tab :" + tab);
			System.out.println("~~~ 확인용 searchType :" + searchType);
			System.out.println("~~~ 확인용 searchWord :" + searchWord);
	
			if(searchType == null || (!"subject".equals(searchType) &&
			  !"userid".equals(searchType) && !"content".equals(searchType) &&
			  !"category".equals(searchType) && !"pname".equals(searchType) &&
			  !"register".equals(searchType) && !"pay".equals(searchType) && 
			  !"change".equals(searchType) && !"order".equals(searchType) &&
			  !"cancle".equals(searchType) && !"pinfo".equals(searchType) &&
			  !"delivery".equals(searchType))) { 
				searchType= ""; 
			}
			  
			if(searchWord == null || (searchWord != null && searchWord.trim().isEmpty())){ 
				searchWord = "";
			}
			  
			Map<String,String> paraMap = new HashMap<>(); 
			paraMap.put("searchType",searchType); 
			paraMap.put("searchWord", searchWord);
			  
			if("notice".equals(tab)) {
				noticeList = ndao.select_notice_list(paraMap);
			}
			else if("faq".equals(tab)) {
				faqList = fdao.select_faq_list(paraMap);
			}
			else if("question".equals(tab)) {
				questionList = qdao.select_question_list(paraMap);
			}
			
			request.setAttribute("noticeList", noticeList);
			request.setAttribute("faqList", faqList);
			request.setAttribute("questionList", questionList);
	
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/callcenter/callcenter.jsp");
			// }
			// else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
			/*
			 * String message = "관리자만 접근이 가능합니다."; String loc = "javascript:history.back()";
			 * 
			 * request.setAttribute("message", message); request.setAttribute("loc", loc);
			 * 
			 * 
			 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp");
			 */
			// }
	}

}
