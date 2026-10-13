package admin.category.jungyj.controller;

import java.util.List;

import org.json.JSONObject;

import admin.category.jungyj.domain.CategoryDTO;
import admin.category.jungyj.model.CategoryDAO;
import admin.category.jungyj.model.CategoryDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class CategoryList extends AbstractController {

	private CategoryDAO catedao = new CategoryDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
	    HttpSession session = request.getSession();
	      
	    MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
	      
	    if(loginuser != null && "admin".equals(loginuser.getUserid())) {
	       // 관리자(admin)로 로그인 했을 경우

			String method = request.getMethod();
			
			if("GET".equals(method)) {
				
				List<CategoryDTO> categoryList = catedao.selectCategoryList();
				
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("categoryList", categoryList);
	            
	            request.setAttribute("json", jsonObj.toString());
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");
				
			} // end of if("GET".equals(method))--------------
	    } else {
	         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
            String message = "관리자만 접근이 가능합니다.";
            String loc = "javascript:history.back()";
            
            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
            
            
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
	    } // end of if~else------------------

	}

}
