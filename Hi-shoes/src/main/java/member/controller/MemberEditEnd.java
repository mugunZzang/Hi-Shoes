package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberEditEnd extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			String method = request.getMethod(); // "GET" 또는 "POST" 
	      
	      if("POST".equals(method)) {
	           // **** POST 방식으로 넘어온 것이라면 **** //	    	  
	    	  String company = request.getParameter("company");
	    	  String busiNum = request.getParameter("busiNum");
	    	  String userid = request.getParameter("userid");   	        
	          String pwd = request.getParameter("pwd");
	          String email = request.getParameter("email");
	          String hp1 = request.getParameter("hp1");
	          String hp2 = request.getParameter("hp2");
	          String hp3 = request.getParameter("hp3");
	          String postcode = request.getParameter("postcode");
	          String address = request.getParameter("address");
	          String detailaddress = request.getParameter("detailaddress");
	          String extraaddress = request.getParameter("extraaddress");
	          
	          String mobile = hp1+hp2+hp3;
	          
	          MemberDTO member = new MemberDTO();
	          member.setUserid(userid);
	          member.setPwd(pwd);
	          member.setCompany(company);
	          member.setBusiNum(busiNum);
	          member.setEmail(email);
	          member.setMobile(mobile);
	          member.setPostcode(postcode);
	          member.setAddress(address);
	          member.setDetailaddress(detailaddress);
	          member.setExtraaddress(extraaddress);
	          
	       // 문자열이 2001-09-02 처럼 표준 ISO 형식일 경우는 
	      // 포맷 지정 없이 바로 LocalDate 타입으로 변환이 가능하다. 
	      /*
	         [참고]
	         문자열이 20010902 나 2001.09.02 처럼 표준 ISO 형식이 아닐 경우는 
	         DateTimeFormatter 를 사용하여 포맷을 지정해주어야 한다.
	         
	         String birth_1 = "20010902";
	         member.setBirthday(LocalDate.parse(birth_1, DateTimeFormatter.ofPattern("yyyyMMdd"))); 
	         
	         String birth_2 = "2001.09.02";
	         member.setBirthday(LocalDate.parse(birth_2, DateTimeFormatter.ofPattern("yyyy.MM.dd")));  
	      */
	         
	        // === 회원수정이 성공되어지면 "회원정보 수정 성공!!" 이라는 alert 를 띄우고 시작페이지로 이동한다.
	        String message = "";
	        String loc = "";
	        try {
	            int n = mdao.updateMember(member);
	            
	            if(n==1) {
	            	
	            	// !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보 값으로 변경해주어야 한다. !!!! 
	            	HttpSession session = request.getSession();
	            	MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
	            	
	            	loginuser.setPwd(pwd);
	            	loginuser.setEmail(email);
	            	loginuser.setMobile(mobile);
	            	loginuser.setPostcode(postcode);
	            	loginuser.setAddress(address);
	            	loginuser.setDetailaddress(detailaddress);
	            	loginuser.setExtraaddress(extraaddress);
	            	
	            	message = "회원정보 수정 성공!!";
	            	loc = request.getContextPath() + "/index.go";	//시작페이지로 이동한다.
	            }
	        }catch(SQLException e) {
	        	message = "SQL 구문 에러 발생";
	        	loc = "javascript:history.back()";	//자바스크립트를 이용한 이전 페이지 이동하는 것.
	        	e.printStackTrace();
	        }
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        request.setAttribute("memberEditEnd", true);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/msg.jsp");
	        
	        
	      }	// end of if("POST".equalsIgnoreCase(method))------------
		
	}

}
