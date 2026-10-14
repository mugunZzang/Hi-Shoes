package myshop.kth.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import member.kth.domain.AdminMemberDTO;
import myshop.kth.model.AdminProductDAO;
import myshop.kth.model.AdminProductDAO_imple;


public class AdminOnumMemberInfoJson extends AbstractController {
	
private AdminProductDAO pdao = null;
	
	public AdminOnumMemberInfoJson() {
		pdao = new AdminProductDAO_imple();
	}
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		        // === 로그인 유무 검사하기 === //
				/*if(!super.checkLogin(request)) {
					request.setAttribute("message", "주문내역을 조회하려면 먼저 로그인 부터 하세요!!");
					request.setAttribute("loc", "javascript:history.back()"); 
					
				//	super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
					return;
				}  */
		
		 
		  HttpSession session = request.getSession();
          
          AdminMemberDTO loginuser = (AdminMemberDTO)session.getAttribute("loginuser");
          
          // === 로그인 유무 검사하기 === //
	   /*   if(loginuser == null || !"admin".equals(loginuser.getUserid())) {
	            request.setAttribute("message", "로그인 후 이용해 주세요.");
	            request.setAttribute("loc", "javascript:history.back();");

	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
	            return;
	        }  */
				
				
				
				
				
			/*  else {
					//HttpSession session = request.getSession();
					
					//AdminMemberDTO loginuser = (AdminMemberDTO)session.getAttribute("loginuser");
					String userid = loginuser.getUserid();
					
					//관리자가 아닐경우
					if(!"admin".equals(userid) ) {
						String message = "접근불가!! 관리자가 아닙니다.";
						String loc = "javascript:history.back()";
						
						request.setAttribute("message", message);
						request.setAttribute("loc", loc);
						
						super.setRedirect(false);
						super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
						
						return;
					} */
					
					//else {
						// "admin" 으로 로그인 한 경우(관리자)이라면
						
						String onum = request.getParameter("onum");
						
						AdminMemberDTO mdto = pdao.onumOwnerMemberInfo(onum);
						// 영수증전표(odrcode)소유주에 대한 사용자 정보를 조회해오는 것.
						
						JSONObject jsonObj = new JSONObject();
						 
						jsonObj.put("userid", mdto.getUserid());
						jsonObj.put("company", mdto.getCompany());
						jsonObj.put("email", mdto.getEmail());
						jsonObj.put("mobile", mdto.getMobile());
						jsonObj.put("postcode", mdto.getPostcode());
						jsonObj.put("address", mdto.getAddress());
						jsonObj.put("detailaddress", mdto.getDetailaddress());
						jsonObj.put("extraaddress", mdto.getExtraaddress());
						
						
						
						jsonObj.put("registerday", mdto.getRegisterday());
						
						String json = jsonObj.toString();
						request.setAttribute("json", json);	
						
					    super.setRedirect(false);
						super.setViewPage("/WEB-INF/admin/adminJsonview.jsp");	
								
				//	}
			//	}		
		
	}

}
