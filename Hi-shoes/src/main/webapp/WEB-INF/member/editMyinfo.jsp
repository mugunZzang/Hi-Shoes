<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
  
<%
    String ctx_Path = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/member/editMyinfo.css" />
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/authentication.js"></script>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/editMyinfo.js"></script>

<%-- 처음 화면 --%>
<section id="authenticationSection" class="bodycont d-flex center justify-content-center align-items-center col-12 col-lg-6 mx-auto my-5">
	<div class="center-cont container-sm my-0">
		<div class="title-wrap">
			<h4 class="mb-4">정보수정을 위해 본인인증을 먼저 해주세요.</h4>
		</div>
		<div class="cont">
			<form id="loginFrm" action="<%=ctx_Path%>/member/authentication.go" method="post" class="row g-3 align-items-center">
			  <label for="loginUserid" class="d-none">아이디</label>
			  <input type="text" name="userid" id="loginUserid" class="form-control mb-2 me-sm-2" size="20" autocomplete="off" placeholder="아이디를 입력 해주세요." value="${(sessionScope.loginuser).userid}" disabled>
				
			  <label for="loginPwd" class="d-none">비밀번호</label>		  			    
		      <input type="password" name="pwd" id="loginPwd" class="form-control" size="20" placeholder="비밀번호를 입력해주세요.">						
			  
			
			  <button type="button" id="btnSubmit" class="btn btn-lg btn-primary mt-4">인증하기</button>
			  <p id="errorMessage"
                   class="text-danger mt-2"
                   role="alert"
                   aria-live="polite"></p>
			</form>							
			
		</div>		
	</div>
</section>


<%-- 패스워드가 일치하면 보여줄 화면 --%>
<section id="memberEditSection" style="display:none;">
	<section class="bodycont container-fluid d-flex center justify-content-center align-items-start flex-column">
		<div class="tit-wrap container-fluid">
			<h2 class="section-tit">회원수정</h2>
			<p>* 표시는 필수 입력사항</p>
		</div>
		
		<form name="editFrm" class="p-4">
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="company" class="form-label">사명&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
			  		<input type="text" class="form-control" name="company" id="company" maxlength="30" class="requiredInfo" placeholder="사명을 입력 해주세요." value="${sessionScope.loginuser.company}" disabled>
			  	</div>
			  	
			  	<p class="error">사명은 필수입력 사항입니다.</p>
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="busiNum" class="form-label">사업자등록번호&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
				  	<input type="text" class="form-control" name="busiNum" id="busiNum" maxlength="40" class="requiredInfo" placeholder="사업자등록번호를 입력 해주세요." value="${sessionScope.loginuser.busiNum}" disabled>					
			  	</div>
			  		  		
				<p id="busiNumcheckResult"></p>
		  		<p class="error">사업자등록번호는 하이픈(-) 없이 숫자 10자리로만 입력해주세요.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="userid" class="form-label">아이디&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
				  	<input type="text" class="form-control" name="userid" id="userid" maxlength="40" class="requiredInfo" placeholder="아이디를 입력 해주세요." value="${sessionScope.loginuser.userid}" disabled>					
			  	</div>
				<p id="idcheckResult"></p>
		  		<p class="error">아이디는 필수입력 사항입니다.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="pwd" class="form-label">비밀번호&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
			  		<input type="password" class="form-control" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호를 입력 해주세요.">
			  	</div>
			  	
			  	<p class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</p>
			  	<p id="duplicate_pwd" class="error"></p>
			  </div>			  
			</div>
			
			<div class="mb-4 row">
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
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="email" class="form-label">이메일&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
				  	<input type="text" class="form-control" name="email" id="email" maxlength="60" class="requiredInfo" placeholder="이메일을 입력 해주세요.">					
			  	</div>
			  	<div  class="col-12 col-lg-6 mt-1">
			  		<%-- 이메일중복체크 --%>
			  		<button type="button" id="emailcheck" class="d-grid d-lg-block btn btn-sm btn-danger">이메일 중복 확인</button>
			  	</div>		  		
				<p id="emailCheckResult"></p>
		  		<p class="error">이메일 형식에 맞지 않습니다.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="hp2" class="form-label">연락처&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="phonenumber col-12 col-lg-6 d-flex justify-content-center align-items-center">
				  	<input type="text" class="form-control" name="hp1" id="hp1" maxlength="3" value="010" readonly>
				  	<span> - </span>					
				  	<input type="text" class="form-control" name="hp2" id="hp2" maxlength="4" >
				  	<span> - </span>
				  	<input type="text" class="form-control" name="hp3" id="hp3" maxlength="4" >
			  	</div>
			  	
		  		<p class="error">휴대폰 형식이 아닙니다.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="postcode" class="form-label">우편번호&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-2">
				  	<input type="text" class="form-control" name="postcode" id="postcode" maxlength="5" size="6">					
			  	</div>
			  	<div  class="col-12 col-lg-6 mt-1">
			  		<%-- 우편번호 찾기 --%>
			  		<button type="button" id="zipcodeSearch" class="d-grid d-lg-block btn btn-sm btn-danger">우편번호 찾기</button>
			  	</div>		  		
		  		<p class="error">우편번호 형식에 맞지 않습니다.</p>			  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="address" class="form-label">주소&nbsp;<span class="star">*</span></label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-10">
			  		<div class="col-12 col-lg-6">
			  			<input type="text" class="form-control" name="address" id="address" size="40" maxlength="200" placeholder="주소"/>			  			
			  		</div>
			  		<div class="col-12 col-lg-12 d-flex justify-content-between align-items-center gap-2">
				  		<input type="text" class="form-control" name="detailaddress" id="detailAddress" size="40" maxlength="200" placeholder="상세주소"/>
				  		<input type="text" class="form-control" name="extraaddress" id="extraAddress" size="40" maxlength="200" placeholder="참고항목"/>
				  	</div>
			  	</div>			  	
			  	<p class="error">주소를 입력하세요.</p>
			  </div>			  
			</div>
						
			
			<div class="row">
				<div class="col-12 col-lg-6 mx-auto text-center">
					<input type="button" class="btn btn-primary btn-lg container-fluid" value="수정하기" onclick="goEdit()" />
				</div>
			</div>
		</form>
	</section>
</section>