package member.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class BusiNumDuplicateCheck extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();	//"GET" 도는 "POST"
		
		if("POST".equals(method)) {
			String busiNum = request.getParameter("busiNum");			
			
			boolean isExists = mdao.busiNumDuplicateCheck(busiNum);
			/*
			boolean isExists 에 저장되어진 데이터를 
			/MyMVC/src/main/webapp/js/member/memberRegister.js 로
			넘길 때 XML 또는 JSON 형식으로 만들어서 넘겨주어야만 
			memberRegister.js 에서 사용할 수 있다.
			그래서 우리는 JSON 형식으로 만들기 위해
			https://mvnrepository.com/artifact/org.json/json/20260814 에서 
			json-20260814.jar 파일을 다운 받아서 
			/MyMVC/src/main/webapp/WEB-INF/lib/ 에 저장시켜 두었다.
			*/
			
			JSONObject jsonObj = new JSONObject();	
			jsonObj.put("isExists", isExists);	// {"isExists":true} 또는 {"isExists":false} 로 만들어준다.
			String json = jsonObj.toString();	// 문자열 형태인 {"isExists":true} 또는 {"isExists":false} 로 만들어준다.
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}	
	}

}
