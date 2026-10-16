package myshop.kth.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.kth.domain.AdminMemberDTO;
import myshop.kth.model.AdminProductDAO;
import myshop.kth.model.AdminProductDAO_imple;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class AdminDeliverEnd extends AbstractController {

	private AdminProductDAO pdao = new AdminProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	
				String method = request.getMethod();
			      
			      if(!"POST".equalsIgnoreCase(method)) {
			         String message = "비정상적인 경로로 들어왔습니다.";
			         String loc = "javascript:history.back();";
			         
			         request.setAttribute("message", message);
			         request.setAttribute("loc", loc);
			         
			         super.setRedirect(false);
			         super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
			         
			         return; // 종료 
			      }
			      
			      // === 로그인 유무 검사하기 === //
			     // (common.controller에 메서드 생성 아직 안함.)
			      
			  /*    if(!super.checkLogin(request)) {
			         request.setAttribute("message", "배송완료를 하시려면 먼저 로그인 부터 하세요!!");
			         request.setAttribute("loc", "javascript:history.back()"); 
			         
			         //super.setRedirect(false);
			         super.setViewPage("/WEB-INF/msg.jsp");
			         return;  // 종료
			      } 
			      
			 */     
			      
			      
			      HttpSession session = request.getSession();
		          
		          MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		          
		       // === 로그인 유무 검사하기 === //
			      if(loginuser == null && !"admin".equals(loginuser.getUserid())) {
			            request.setAttribute("message", "로그인 후 이용해 주세요.");
			            request.setAttribute("loc", "javascript:history.back();");

			            super.setRedirect(false);
			            super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
			            return;
			        }
			      
			      else {
			    	  
			          String userid = loginuser.getUserid();
			          
			          //관리자가 아닐 경우
			          if(!"admin".equals(userid) ) {
			             String message = "접근불가!! 관리자가 아닙니다.";
			             String loc = "javascript:history.back()";
			             
			             request.setAttribute("message", message);
			             request.setAttribute("loc", loc);
			             
			             super.setRedirect(false);
			             super.setViewPage("/WEB-INF/msg.jsp");
			             
			             return;  // 종료
			          } else { //admin으로 로그인 한 경우
			        	  String[] onumArr = request.getParameterValues("onum");
		      	        String[] snumArr = request.getParameterValues("snum");
		      	        
		      	        StringBuilder sb = new StringBuilder();
		      	        //s20260930-1/65
		      	        //s20261002-21/65
		      	        
		      	       //  's20261002-22/3', 's20261002-22/4', 's20261002-22/61', 's20261001-21/5', 's20261001-21/6', 's20261001-21/4' 
		      	         /*
		      	             's20261002-22/3' 에서 's20261002-22 는 주문코드(전표)'이고 /뒤에 붙은 3 은 제품번호이다.
		      	             
		      	             .... 
		      	             
		      	             's20261001-21/4' 에서 's20261001-21 는 주문코드(전표)'이고 /뒤에 붙은 4 는 제품번호이다.
		      	                
		      	             이것은 오라클에서 WHERE fk_odrcode || '/' || fk_pnum IN('주문코드(전표)/제품번호', '주문코드(전표)/제품번호', '주문코드(전표)/제품번호') 로 하겠다는 말이다.    
		      	          */
		      	        System.out.println("onumArr 배열 시작");
		      	        System.out.println("확인용 snumArr : " + snumArr.length);
		      	        for(int i=0; i<onumArr.length; i++) {
		      	        	System.out.println(onumArr[i]);
		      	        	System.out.println("확인용 snum: " + snumArr[i]);
		      	        	sb.append("\'" +onumArr[i]+"/"+snumArr[i]+"\',");
		      	        	
		      	        }//end of for---
		      	        
		      	        String onumSnum = sb.toString();
		      	        
		      	        //맨뒤의 콤마(,) 제거하기
		      	      onumSnum=onumSnum.substring(0,onumSnum.length()-1);
		      	        
		      	        //System.out.println("~~~확인용 odrcodePnum =>" + odrcodePnum);
		      	        //~~~확인용 odrcodePnum =>'s20261002-21/65
		      	        
		                //1.tbl_orderdetail 테이블의 deliverstatus(배송상태) 컬럼의 값을 2(배송시작)로 변경하기     
		      	       int n = 0;
		      	       try {
		      	    	   n= pdao.updateDeliverEnd(onumSnum);
		      	    	 //1.tbl_orderdetail 테이블의 deliverstatus(배송상태) 컬럼의 값을 3(배송완료)로 변경하기     
		      	       }catch (SQLException e) {
		      	    	   e.printStackTrace();
		      	       }
		      	        
		      	        
		      	        
		      	        if(n > 0) {
		                   
			        	   
		      	        //2.=== *** 배송을 했다라는 확인 문자(SMS)를 주문을 한 사람(여러명)에게 보내기 시작 ***===
		   	        	 Map<String, String> onumMap = new HashMap<>(); 
		   	               // 동일한 전표에 서로 다른 제품들을 구매한 경우 동일 전표를 가진 사람에게는 SMS(문자)를 1번만 보내야 하므로
		   	               // 중복을 허락치 않는 HashMap 을 사용하기로 한다. 
		   	        	 
		   	        	 for(String onum : onumArr) {
		   	        		onumMap.put(onum, onum);
		   	                  // odrcodeMap 에 전표를 넣기(HashMap 이므로 중복된 전표가 있으면 덮어씌우므로 고유한 값만 존재하게 된다). 
		   	               }
		   	        	 Set<String>onumMapKeysets = onumMap.keySet();
		   	               // 중복을 허락치 않는 키값들 얻어오기
		   	        	 for(String key:onumMapKeysets) {
		   	        		 AdminMemberDTO mdto = pdao.onumOwnerMemberInfo(key);
		   	        		// >> SMS발송 <<
		        	     		//   HashMap 에 받는사람번호, 보내는사람번호, 문자내용 등 을 저장한뒤 Coolsms 클래스의 send를 이용해 보냅니다.
		        	     		// String api_key = "발급받은 본인의 API Key";  // 발급받은 본인 API Key
		        	     		String api_key = "NCSDQAN1XVNDK94R";
		        	     		// String api_secret = "발급받은 본인의 API Secret";  // 발급받은 본인 API Secret
		        	     		String api_secret = "H4LQ5CLN14QOVVXKPSGGMLSYYU5MVI09";  // 발급받은 본인 API Secret
		        	     		
		        	     		Message coolsms = new Message(api_key, api_secret);
		        	     		// net.nurigo.java_sdk.api.Message 임. 
		        	     	    // 먼저 다운 받은  javaSDK-2.2.jar 와 json-simple-1.1.1.jar 를 /MyMVC/src/main/webapp/WEB-INF/lib/ 안에 넣어서  build 시켜야 함.
		        	     	    String mobile = request.getParameter("mobile");
		        	     	    String smsContent = request.getParameter("smsContent");
		        	     	    
		        	     	   // == 4개 파라미터(to, from, type, text)는 필수사항이다. ==
		        	     	    HashMap<String,String> paraMap = new HashMap<>();
		        	     	   
								paraMap.put("to", mdto.getMobile()); //수신번호
		        	     	    paraMap.put("from", "01063625385"); //발신번호
		        	     	   //2020년 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다
		        	     	    paraMap.put("type", "SMS");// Message type ( SMS(단문), LMS(장문), MMS, ATA )
		        	     	    paraMap.put("text", "Hi-shoes 쇼핑몰에서 "+mdto.getCompany()+"님 께서 주문하신 상품["+key+"]을 출고완료 하였습니다."); //문자내용
		        	     	    
		        	     	    String datetime = request.getParameter("datetime");
		        	     	    if(datetime != null) {
		        	     	    	paraMap.put("datetime",datetime);
		        	     	    }
		        	     	    
		        	     	    paraMap.put("app_version", "JAVA SDK v2.2"); // application name and version 
		        	     	    
		        	     	    try {
		        	     	    
		        	     	    JSONObject jsonObj = (JSONObject) coolsms.send(paraMap);
		        	     	    /*
		        	             org.json.JSONObject 이 아니라 
		        	             org.json.simple.JSONObject 이어야 한다.  
		        	          */
		        	     	//   System.out.println("확인용 jsobj.toString() => " + jsobj.toString());
		                        //  확인용 jsobj.toString() => {"group_id":"GID5DF05F5949CB3","success_count":1,"error_count":0}
		        	     	    }catch(CoolsmsException e) {
		        	     	    	e.printStackTrace();
		        	     	    }
		   	        	 }
		   	        	 
		   	        		
		   	     	    
		   	        	//  === *** 배송을 했다라는 확인 문자(SMS)를 주문을 한 사람(여러명)에게 보내기 끝 ***===
		   	        	   String message = "선택하신 제품들은 출고완료로 변경되었습니다.";
		   	               String loc = request.getContextPath()+"/admin/adminOrderList.go";
		   	               
		   	               request.setAttribute("message", message);
		   	               request.setAttribute("loc", loc);
		   	               
		   	               super.setRedirect(false);
		   	               super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
		   	               
		   	        } else{   
		   	    	 String message = "선택하신 제품들은 출고완료로 변경되었습니다.";
		   	    	 String loc = "javascript:history.back();";
			               
			               request.setAttribute("message", message);
			               request.setAttribute("loc", loc);
			               
			               super.setRedirect(false);
			               super.setViewPage("/WEB-INF/admin/adminMsg.jsp");
			              
		   	     }
			          }
			      }
				
			}

			


		
	}


