<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
	// Hi-shoes
%>

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" > 

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/reset.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/login.css" />

<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>

<script type="text/javascript">

	$(function(){
		
		const method = "${requestScope.method}";
		
		//console.log("~~ 확인용 method : ", method);
		/*
			~~ 확인용 method :  GET
		*/		
		
		if(method == "GET") {
			$('div#div_findResult').hide();
		}
		
		if(method == "POST") {
			$('input:text[name="userid"]').val("${requestScope.userid}");
			$('input:text[name="email"]').val("${requestScope.email}");
			
			if(${requestScope.isUserExist == true}) {
				$('button.btn-searchPwd').hide();
			}
			
		}
		
		$('button.btn-searchPwd').on('click', function(){
			goFind();
		});
		
		$('input:text[name="email"]').on('keyup', function(e){
			if(e.keyCode == 13) {
				goFind();
			}
		});
		
		// === 인증하기 버튼 클릭 시 이벤트 처리하기 시작 === //
		$('button.btn-info').on('click', () => {
			const input_confirmCode = $('input:text[name="input_confirmCode"]').val().trim();
			
			if(input_confirmCode == "") {
				alert("인증코드를 입력하세요!");
				return;
			}
			
			const frm = document.verifyCertificationFrm;
			frm.userCertificationCode.value = input_confirmCode;
			frm.userid.value = $('input:text[name="userid"]').val();
			
			frm.action = "<%=ctxPath%>/login/verifyCertification.go";
			frm.method = "post";
			frm.submit();
			
		});
		// === 인증하기 버튼 클릭 시 이벤트 처리하기 끝 === //
		
	});// end of $(function(){})-------------------
	
	
	// Function Declaration
	function goFind(){
		
	  const userid = $('input:text[name="userid"]').val().trim();
	  
	  if(userid == "") {
		  alert("아이디를 입력하세요!!");
		  return; // goFind() 함수 종료
	  }
	  
	  const email = $('input:text[name="email"]').val();
	  
	  const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		// 이메일 정규표현식 객체 생성 
		
	  const bool = regExp_email.test(email);
		
	  if(!bool) {
		  // 이메일이 정규표현식에 위배된 경우
		  alert("이메일을 올바르게 입력하세요!!");
		  return; // goFind() 함수 종료
	  }
	  
	  const frm = document.pwdFindFrm;
	  frm.action = "<%= ctxPath%>/login/pwdFind.go";
	  frm.method = "post";
	  frm.submit();		
		
	}// end of function goFind()-------------------

</script>

<div class="container my-3">
	<form class="row gap-3 mb-3" name="pwdFindFrm">
		<div class="col-12 my-auto">
			  <label for="userid" class="d-none">아이디</label>
			  <input type="text" class="form-control mb-3 me-sm-2" id="userid" name="userid" size="25" autocomplete="off" placeholder="아이디를 입력 해주세요.">
			
			  <label for="email" class="d-none">이메일</label>		  			    
		      <input type="text" class="form-control" id="email" name="email" size="25" autocomplete="off" placeholder="이메일을 입력해주세요.">
		</div>
	  	
	  	<div class="col-12">
	  		<button type="button" class="btn-searchPwd btn btn-md btn-primary w-100">비밀번호 찾기</button>	
	  	</div>
	  
	</form>
	
	<div class="row gap-3" id="div_findResult">
	
		<c:if test="${requestScope.isUserExist == false}">
			<div class="col-12 my-auto">
				<p class="error">사용자 정보가 없습니다.</p> 
			</div>			
		</c:if>
	
		<c:if test="${requestScope.isUserExist == true && requestScope.sendMailSuccess == true}">
			<div class="col-12 my-auto">
				<p class="request-text">
					인증코드를 <span>${requestScope.email}</span>으로 발송하였습니다.
					인증코드를 입력해주세요.
				</p> 
				<input type="text" class="form-control" name="input_confirmCode" placeholder="인증코드 입력"/>
			</div>
			
			<div class="col-12">
				<button type="button" class="btn btn-md btn-info w-100">인증하기</button>
			</div>			
		</c:if>
		
		<c:if test="${requestScope.isUserExist == true && requestScope.sendMailSuccess == false}">
			<div class="col-12 my-auto">
				<p class="error">메일 발송을 실패하였습니다.</p>
			</div>				
		</c:if>
		
	</div>	
	
</div>

<%-- 인증하기 form --%>
<form name="verifyCertificationFrm">
	<input type="hidden" name="userCertificationCode">
	<input type="hidden" name="userid">
</form>
    
