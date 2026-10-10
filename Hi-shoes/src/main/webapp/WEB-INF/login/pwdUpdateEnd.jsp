<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
	// Hi-shoes
%>

<%-- Required meta tags --%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" > 
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/reset.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/login.css" />

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>

<!-- <style type="text/css">
  div.div_pwd {
    width: 70%;
	height: 15%;
	margin-bottom: 5%;
   	margin-left: 10%;
  }
</style> -->

<script type="text/javascript">
	$(() => {
		$('button.btn-pwdChange').on('click', () => {
			const pwd = $('input:password[name="pwd"]').val();
			const pwd2 = $('input:password[id="pwd2"]').val();
			
			if(pwd != pwd2) {
				alert("암호가 일치하지 않습니다.");
				$('input:password[name="pwd"]').val("");
				$('input:password[id="pwd2"]').val("");
			}
			else {
				const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
				// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 개체 생성
				
				const bool = regExp_pwd.test(pwd);
				
				if(!bool) {
					// 암호가 정규표현식에 위배된 경우
					alert("암호는 8글자 이상 15글자 이하에 영문자,숫자,특수기호가 혼합되어야만 합니다.");
					$('input:password[name="pwd"]').val("");
					$('input:password[id="pwd2"]').val("");
					return;	//종료
				}
				else {
					// 암호가 정규표현식에 맞는 경우
					const frm = document.pwdUpdateEndFrm;
					frm.action = "<%=ctxPath%>/login/pwdUpdateEnd.go";
					frm.method = "post";
					frm.submit();
				}				
								
			}
		
		});
	
	});	// end of $(() =>
</script>

<c:if test="${requestScope.method == 'GET'}">
	<div class="container my-3">
		<form class="row gap-3 mb-3" name="pwdUpdateEndFrm">
			<div class="div_pwd col-12 my-auto">
				  <label for="pwd" class="d-none">새 비밀번호</label>
				  <input type="password" class="form-control mb-3 me-sm-2" id="pwd" name="pwd" size="25" placeholder="새 비밀번호를 입력 해주세요.">
				
				  <label for="pwd2" class="d-none">새 비밀번호 확인</label>		  			    
			      <input type="password" class="form-control" id="pwd2" name="pwd2" size="25" placeholder="새 비밀번호를 다시 입력해주세요.">
			</div>
		  	
		  	<input type="hidden" name="userid" value="${requestScope.userid}" />
		  	
		  	<div class="col-12">
		  		<button type="button" class="btn-pwdChange btn btn-md btn-primary w-100">비밀번호 변경하기</button>	
		  	</div>
		  
		</form>
	</div>
</c:if>

<c:if test="${requestScope.method == 'POST'}">
	<div class="container my-3">
		<p class="updeatedPwd text-center">
			<c:if test="${requestScope.n == 1}">
				사용자 ID ${requestScope.userid} 님의 비밀번호가 새로이 변경 되었습니다.
			</c:if>
			
			<c:if test="${requestScope.n == 0}">
				SQL구문 오류가 발생되어 비밀번호 변경을 할 수 없습니다.
			</c:if>
		</p>
	</div>
</c:if>