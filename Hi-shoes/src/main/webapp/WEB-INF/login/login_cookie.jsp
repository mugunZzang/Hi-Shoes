<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
   String ctxPath = request.getContextPath();    
%>
<jsp:include page="../header.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/login.css" />
<script type="text/javascript" src="<%= ctxPath%>/js/login/login.js"></script>

<script type="text/javascript">

	$(function(){
		
		<%-- === 쿠키(Cookie) 값을 읽어올 때 자바스크립트를 이용하는 경우 시작 === --%>
		const cookies = document.cookie;  // 자바스크립트에서 접근 가능한 쿠키값을 읽어오는 것 
		                                  // HttpOnly 쿠키(예: JSESSIONID)는 JavaScript 로 접근할 수 없다.
		                                  
	//	console.log("쿠키 : ", cookies);                                  
		// 쿠키 :  saveid=seoyh; test=TEST; example=EXAMPLE
		// 또는
		// 쿠키 :  
			
		if(cookies != "") {
			// console.log("쿠키가 존재해요");
			
			const arr_cookie = cookies.split("; ");
			
			for(let i=0; i<arr_cookie.length; i++){
				const cookie_name = arr_cookie[i].substring(0, arr_cookie[i].indexOf("=")); 
			 //	console.log(cookie_name);
				/*
				   saveid
				   test
				   example
				*/
				
				if(cookie_name == "saveid") {
					const cookie_value = arr_cookie[i].substring(arr_cookie[i].indexOf("=")+1);
					$('input#loginUserid').val(cookie_value);
					$('input:checkbox[name="saveid"]').prop("checked", true);
					break;
				}
				
			}// end of for--------------
			
		}
		else {
			// console.log("쿠키가 없어요");
		}
		<%-- === 쿠키(Cookie) 값을 읽어올 때 자바스크립트를 이용하는 경우 끝 === --%>
		
		
		
	 // === 아이디 찾기에서 close 버튼을 클릭하면 iframe 의 form 태그에 입력된 값을 지우기 === // 
	    $('button.idFindClose').on('click', function(){
	    	
	    	const iframe_idFind = document.getElementById("iframe_idFind");
	    	// 대상 아이프레임을 선택한다.
	    	// 선택자를 잡을때 jQuery를 사용한 $() 으로 잡으면 안되고, 순수한 자바스크립트를 사용하여 선택자를 잡아야 한다. 
	    	<%-- [참고] .jsp 파일속에 주석문을 만들때 ${} 을 넣고자 한다라면 반드시 JSP 주석문으로 해야 하지, 스크립트 주석문으로 해주면 ${} 때문에 오류가 발생한다. --%>
	    	
	    	const iframe_window = iframe_idFind.contentWindow;
	    	 // iframe 요소에 접근하는 contentWindow 와 contentDocument 의 차이점은 아래와 같다.
	    	 // contentWindow 와 contentDocument 둘 모두 iframe 하위 요소에 접근 할 수 있는 방법이다.
	    	 // contentWindow 는 iframe의 window(전체)을 의미하는 것이다.
	    	 // 참고로, contentWindow.document 은 contentDocument 와 같은 것이다.
	    	 // contentWindow 가 contentDocument 의 상위 요소이다.
	    	 
	    	iframe_window.func_form_reset_empty();
	    	// func_form_reset_empty() 함수는 idFind.jsp 파일에 정의해 둠. 
	    	
	    });	// end of $('button.idFindClose').on('click', function()--------------
	 
	 	$('button.passwdFindClose').on('click', () => {

	 		javascript:history.go(0);
		 	// 현재 페이지를 새로고침을 함으로써 모달창에 입력한 userid 와 email 의 값이 텍스트박스에 남겨있지 않고 삭제하는 효과를 누린다. 
		      
	        /* === 새로고침(다시읽기) 방법 3가지 차이점 ===
	           >>> 1. 일반적인 다시읽기 <<<
	           window.location.reload();
	           ==> 이렇게 하면 컴퓨터의 캐시에서 우선 파일을 찾아본다.
	               없으면 서버에서 받아온다. 
	           
	           >>> 2. 강력하고 강제적인 다시읽기 <<<
	           window.location.reload(true);
	           ==> true 라는 파라미터를 입력하면, 무조건 서버에서 직접 파일을 가져오게 된다.
	               캐시는 완전히 무시된다.
	           
	           >>> 3. 부드럽고 소극적인 다시읽기 <<<
	           history.go(0);
	           ==> 이렇게 하면 캐시에서 현재 페이지의 파일들을 항상 우선적으로 찾는다.
	        */
	 	
	 	});	// end of $('button.passwdFindClose').on('click', () =>--------------
	    
		
	});	// end of $(function()--------------------

</script>


<section class="bodycont container-fluid d-flex center justify-content-center align-items-center">
	<div class="center-cont container-sm my-5">
		<div class="title-wrap">
			<h2 class="section-tit">로그인</h2>
		</div>
		<div class="cont">
			<form name="loginFrm" action="<%= ctxPath%>/login/login.go" method="post" class="row g-3 align-items-center">
			  <label for="loginUserid" class="d-none">아이디</label>
			  <input type="text" name="userid" id="loginUserid" class="form-control mb-2 me-sm-2" size="20" autocomplete="off" placeholder="아이디를 입력 해주세요.">
			
			  <label for="loginPwd" class="d-none">비밀번호</label>		  			    
		      <input type="password" name="pwd" id="loginPwd" class="form-control" size="20" placeholder="비밀번호를 입력해주세요.">
			
			  <div class="form-check mb-2 me-sm-2">
			    <input class="form-check-input" type="checkbox" id="saveid" name="saveid">
			    <label class="form-check-label" for="saveid">
			      아이디 저장
			    </label>
			  </div>
			
			  <button type="button" id="btnSubmit" class="btn btn-lg btn-primary mb-4">로그인</button>
			</form>
			
			<section class="other-service mx-auto container text-center px-0">
				<ul class="row mx-auto px-0 text-cneter d-flex justify-content-center">
					<li class="col-3">
						<a href="#" data-bs-toggle="modal" data-bs-target="#userIdfind">아이디 찾기</a>	
					</li>
					<li class="col-3">
						<a href="#" data-bs-toggle="modal" data-bs-target="#passwdFind">비밀번호 찾기</a>	
					</li>
					<li class="col-3">
						<a href="<%=ctxPath%>/member/memberRegister.go">회원가입</a>	
					</li>
				</ul>				
			</section>	
		</div>		
	</div>
</section>

<%-- ****** 아이디 찾기 Modal 시작 ****** --%>
<%-- <div class="modal fade" id="userIdfind"> --%> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%> 
  <div class="modal fade" id="userIdfind" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">아이디 찾기</h4>
          <button type="button" class="btn-close idFindClose" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="idFind">
          	<iframe id="iframe_idFind" style="border: none; width: 100%; height: 300px;" src="<%= ctxPath%>/login/idFind.go"> 
          	</iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger idFindClose" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div>
<%-- ****** 아이디 찾기 Modal 끝 ****** --%>	


<%-- ****** 비밀번호 찾기 Modal 시작 ****** --%>
  <div class="modal fade" id="passwdFind" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">비밀번호 찾기</h4>
          <button type="button" class="btn-close passwdFindClose" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="pwFind">
          	<iframe style="border: none; width: 100%; height: 400px;" src="<%= ctxPath%>/login/pwdFind.go">  
          	</iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger passwdFindClose" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div> 
<%-- ****** 비밀번호 찾기 Modal 끝 ****** --%>	



<jsp:include page="../footer.jsp" />
