package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberRegister extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("GET".equals(method)) {
			//super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberRegister.jsp");
		}
		else {
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
						
			String mobile = hp1 + hp2 + hp3;
			
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
			
			/*
			 * [참고]
			 * 문자열이 20010902 나 2001.09.02 처럼 표준 ISO 형식이 아닐 경우는 
			 * DateTimeformatter 를 사용하여 포맷을 지정해주어야 한다.
			 
			String birth_1 = "20010902";				
			member.setBirthday(LocalDate.parse(birth_1, DateTimeFormatter.ofPattern("yyyyMMdd")));
			
			String birth_2 = "2001.09.02";				
			member.setBirthday(LocalDate.parse(birth_2, DateTimeFormatter.ofPattern("yyyy.MM.dd")));
			
			*/
			
			// #### 회원가입이 성공되어지면 자동으로 로그인 되도록 하겠다. #### //
			try {
				int n = mdao.registerMember(member);
				
				if(n==1) {
					
					Map<String, String> paraMap = new HashMap<>();
					paraMap.put("userid", userid);
					paraMap.put("pwd", pwd);
					paraMap.put("clientip", request.getRemoteAddr());
					
					MemberDTO loginuser = mdao.login(paraMap);
					
					HttpSession session = request.getSession();
					// WAS 메모리에 생성되어져 있는 session을 불러오는 것이다.
					
					session.setAttribute("loginuser", loginuser);
					// session(세션)에 로그인 되어진 사용자 정보인 loginuser 를 키이름을 "loginuser" 으로 저장시켜두는 것이다. 
					
					String message = company + "님의 회원가입을 환영합니다!";
					String loc = request.getContextPath() + "/index.go";  // 시작페이지로 이동한다.
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			
				super.setRedirect(true); 
				super.setViewPage(request.getContextPath() + "/error.go");
			}

		}			
	}

}
