package admin.supplier.jungyj.controller;

import java.util.List;

import admin.supplier.jungyj.domain.SupplierDTO;
import admin.supplier.jungyj.model.SupplierDAO;
import admin.supplier.jungyj.model.SupplierDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class SupplierList extends AbstractController {

	private SupplierDAO sdao = new SupplierDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// === 관리자(admin)로 로그인 했을때만 회원목록 조회가 가능하도록 해야 한다. === //
	    HttpSession session = request.getSession();
	      
	    MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
	      
	    if(loginuser != null && "admin".equals(loginuser.getUserid())) {
		     // 관리자(admin)로 로그인 했을 경우
			String method = request.getMethod();
			
			if("GET".equals(method)) {
				
				// 페이징 처리 안 한 공급업체 목록 조회
		        List<SupplierDTO> supplierList = sdao.selectSuppliernopaging();
		        
		        request.setAttribute("supplierList", supplierList);
	
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/admin/supplier/adminsupplierList.jsp");
			} 
	    } else {
	        // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
            String message = "관리자만 접근이 가능합니다.";
            String loc = "javascript:history.back()";
            
            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
            
            
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            
	    } // end of if~else-------------------------
		 
	}

}
