package member.controller;

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

public class WishListDelete extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
	    HttpSession session = request.getSession();
	    
	    MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");

		if(!"POST".equals(method)) {
			// GET 방식으로 접근한 경우
			
	        String message = "비정상적인 경로로 들어왔습니다.";
	        String loc = "javascript:history.back();";
	         
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	         
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/msg.jsp");
	         
	        return; // 종료 
            
		} else {
			// POST 방식으로 접근한 경우
			String[] pnumArr = request.getParameterValues("pnum");
			
        	StringBuilder sb = new StringBuilder();
        	
			for(String str: pnumArr) {
				
				System.out.println("확인용" + str);

				
				sb.append(str+",");
			}  // end of for-------------------
	
        	String pnum = sb.toString();
        	
        	// 맨뒤의 콤마(,) 제거하기
        	// 처음부터 맨 뒤의 한 글자 빼라
        	pnum = pnum.substring(0, pnum.length()-1);
        	
        	System.out.println("~~~ 확인용 pnum : " + pnum);  
			
        	Map<String, String> paraMap = new HashMap<>();
        	paraMap.put("pnum", pnum);
        	paraMap.put("userid", loginuser.getUserid());
        	
        	int n = 0;
        	
        	try {
        		n = mdao.wishListDelete(paraMap);
        	} catch(SQLException e) {
        		e.printStackTrace();
        	}
        	
        	JSONObject jsonObj = new JSONObject();
        	jsonObj.put("n", n);

        	request.setAttribute("json", jsonObj.toString());

        	super.setRedirect(false);
        	super.setViewPage("/WEB-INF/jsonview.jsp");   // 본인 프로젝트의 JSON 출력용 JSP
        	
		}

	}

}
