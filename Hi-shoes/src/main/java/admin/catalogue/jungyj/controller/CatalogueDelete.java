package admin.catalogue.jungyj.controller;

import org.json.JSONObject;

import admin.catalogue.jungyj.model.CatalogueDAO;
import admin.catalogue.jungyj.model.CatalogueDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class CatalogueDelete extends AbstractController {

	private CatalogueDAO cataldao = new CatalogueDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
	    HttpSession session = request.getSession();
	      
	    MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
	      
	    if(loginuser != null && "admin".equals(loginuser.getUserid())) {
	    	// 관리자(admin)로 로그인 했을 경우
			if("POST".equals(method)) {
				String pname = request.getParameter("pname");
				
				int result = cataldao.catalogueDelete(pname);
				
				if(result == 1) {
					// 카탈로그 삭제 성공
					JSONObject jsonObj = new JSONObject();
					
					jsonObj.put("result", result);
					
		            request.setAttribute("json", jsonObj.toString());
		            
		            super.setRedirect(false);
		            super.setViewPage("/WEB-INF/jsonview.jsp");				
					
				} else {
					System.out.println("[WARN] catalogue.jungyj.controller.CatalogueDelete 제품 삭제 실패");
				}
			} // end of if("POST".equals(method))----------
			
	    }else {
	         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
            String message = "관리자만 접근이 가능합니다.";
            String loc = "javascript:history.back()";
            
            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
            
            
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
		} // end of if(loginuser != null && "admin".equals(loginuser.getUserid()))--------------
	}
}
