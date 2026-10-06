<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   String ctxPath = request.getContextPath();
	// Hi-shoes
%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberRegister.css" /> 
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/member/memberRegister.js"></script>

<section class="bodycont container-fluid d-flex center justify-content-center align-items-start">
	<section class="cont-wrap">
		<div class="tit-wrap">
			<h2 class="section-tit">회원가입</h2>
			<p>* 표시는 필수 입력사항</p>
		</div>
		
		<form name="registerFrm">
			<div class="mb-3">
			  <label for="agree" class="form-check-label">이용약관에 동의합니다.&nbsp;<span class="star">*</span></label>&nbsp;&nbsp;<input  class="form-check-input" type="checkbox" id="agree" />
			  <iframe src="<%=ctxPath%>/iframe_agree/agree.html" width="100%" height="150px" style="border: solid 1px navy;"></iframe>
			</div>
			
			<div class="mb-3 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="name" class="form-label">이름&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
			  		<input type="text" class="form-control" name="name" id="name" maxlength="30" class="requiredInfo" placeholder="이름을 입력 해주세요.">
			  	</div>
			  	
			  	<p class="error">이름은 필수입력 사항입니다.</p>
			  </div>			  
			</div>
			
			<div class="mb-3 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="userid" class="form-label">아이디&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
				  	<input type="text" class="form-control" name="userid" id="userid" maxlength="40" class="requiredInfo" placeholder="아이디를 입력 해주세요.">					
			  	</div>
			  	<div  class="col-12 col-lg-6 my-auto">
			  		<button type="button" id="idcheck" class="d-grid d-lg-block btn btn-sm btn-danger">아이디 중복 확인</button>
			  	</div>
		  		
				<p id="idcheckResult">쉬발라마</p>
		  		<p class="error">아이디는 필수입력 사항입니다.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-3 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="pwd" class="form-label">비밀번호&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
			  		<input type="password" class="form-control" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호를 입력 해주세요.">
			  	</div>
			  	
			  	<p class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</p>
			  </div>			  
			</div>
			
			<div class="mb-3 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="pwdcheck" class="form-label">비밀번호 확인&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
			  		<input type="password" class="form-control" name="pwdcheck" id="pwdcheck" maxlength="15" class="requiredInfo" placeholder="비밀번호를 다시 입력 해주세요.">
			  	</div>
			  	
			  	<p class="error">암호가 일치하지 않습니다.</p>
			  </div>			  
			</div>
		</form>
	</section>
</section>

<jsp:include page="../footer.jsp" />
