package admin.kth.controller;

import java.util.List;
import java.util.Map;

import admin.kth.model.AdminMainDAO;
import admin.kth.model.AdminMainDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class AdminMain extends AbstractController {
	private AdminMainDAO mdao = new AdminMainDAO_imple();
	
	 @Override
	 public void execute(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
	       
		 String method = request.getMethod();
	      
	     
		 
		 
	 	   HttpSession session = request.getSession();
	    // 로그인 정보 가져오기
	       MemberDTO loginuser= (MemberDTO)session.getAttribute("loginuser");
	    
	    // 로그인하지 않았거나 관리자가 아니면 접근 차단
	   if (loginuser==null || !"admin".equals(loginuser.getUserid())){
		  super.setRedirect(true);
		  super.setViewPage(request.getContextPath()+"/index.go");
	      return;
       }// 관리자라면 관리자 메인페이지로 이동 */
		 
	   
	      int orderCount=mdao.getOrderCount();
		  int approveCount=mdao.getApproveCount();
		  int deliveryCount=mdao.getDeliveryCount();
		  
		  
		  int todayDeliveryCount=mdao.getTodayDeliveryCount();
		  int todaySalesCount=mdao.getTodaySalesCount();
		  int todaySalesSum=mdao.getTodaySalesSum();
		  
		  
		  
		
		  
		  
		  List<Map<String, String>> stockList = mdao.getStockList();
		  List<Map<String, String>> noticeList = mdao.getNoticeList();
		  List<Map<String, String>> qnaList = mdao.getQnaList();
		  
		  
		  
		  request.setAttribute("orderCount",orderCount);
		  request.setAttribute("approveCount",approveCount);
		  request.setAttribute("deliveryCount",deliveryCount);
		  request.setAttribute("todayDeliveryCount",todayDeliveryCount);
		  request.setAttribute("todaySalesCount",todaySalesCount);
		  request.setAttribute("todaySalesSum",todaySalesSum);
		  request.setAttribute("stockList", stockList);
		  request.setAttribute("noticeList", noticeList);
		  request.setAttribute("qnaList", qnaList);
	
		  
		  
	      super.setRedirect(false);
	      super.setViewPage("/WEB-INF/admin/adminMain.jsp");
	   
    } 
	
			

}