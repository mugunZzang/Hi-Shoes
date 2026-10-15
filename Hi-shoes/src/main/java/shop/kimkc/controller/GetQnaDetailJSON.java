package shop.kimkc.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import shop.kimkc.domain.QnADTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class GetQnaDetailJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		if (request.getMethod().equals("POST")) {
		// 
			MemberDTO loginuser = (MemberDTO) request.getSession().getAttribute("loginuser");
			
			
			String qnanum = request.getParameter("qnanum");
			String userid = loginuser.getUserid();
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("qnanum", qnanum);
			
			 
			QnADTO qnaDetail = pdao.getQnaDetail(qnanum);	
			
			
			//공개거나, 내가 쓴 글일때만 qcontents, qanswer 를 리턴한다.
			if(qnaDetail.getFk_userid().equals(userid) || "공개".equals(qnaDetail.getIslock())) {
			
				JSONObject json = new JSONObject();
				json.put("n", 1);
				json.put("qcontents", qnaDetail.getQcontents());
				json.put("qanswer",
						qnaDetail.getQanswer() == null ? "" : qnaDetail.getQanswer());
				
				if (qnaDetail.getFk_userid().equals(userid)) {
					json.put("isWriter", 1);
				}
				else {
					json.put("isWriter", 0);
				}
				
				
				request.setAttribute("json", json);

			}
			else {
				// 열람 권한이 없는 경우
			    JSONObject json = new JSONObject();
			    json.put("result", "fail");
			    json.put("message", "해당 QnA를 열람할 권한이 없습니다.");


			    request.setAttribute("json", json);

			}
		
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
