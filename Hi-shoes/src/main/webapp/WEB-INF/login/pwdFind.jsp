<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
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


<div class="container my-3">
	<form class="row mb-4">
		<div class="col-9 my-auto">
			  <label for="inlineFormInputName3" class="d-none">아이디</label>
			  <input type="text" class="form-control mb-3 me-sm-2" id="inlineFormInputName3" placeholder="아이디를 입력 해주세요.">
			
			  <label for="inlineFormInputGroupUsername3" class="d-none">이메일</label>		  			    
		      <input type="text" class="form-control" id="inlineFormInputGroupUsername3" placeholder="이메일을 입력해주세요.">
		</div>
	  	
	  	<div class="col-3">
	  		<button type="submit" class="btn btn-md btn-primary w-100 h-100">비밀번호<br/>찾기</button>	
	  	</div>
	  
	</form>
	
	<div class="row" id="temp-certificate">
		<div class="col-9 my-auto">
			<p class="request-text">
				인증코드를 <span>aaa@bbb.com</span>으로 발송하였습니다.
				인증코드를 입력해주세요
			</p> 
			<input type="text" class="form-control" name="input_confirmCode" placeholder="인증코드 입력">
		</div>
		
		<div class="col-3">
			<button type="button" class="btn btn-md btn-info w-100 h-100">인증하기</button>
		</div>			
	</div>
	
	
	<div class="my-3 text-center" if="div_findResult">
		<c:if test="${requestScope.isUserExist == false}">
			<span style="color:red;">사용자 정보가 없습니다.</span> 
		</c:if>
		
		<c:if test="${requestScope.isUserExist == true && requestScope.sendMailSuccess == true}">
			<span style="font-size: 10pt;">
				인증코드가 ${requestScope.email}로 발송되었습니다.<br/>
				인증코드를 입력해주세요
			</span> 
			<br>
			<input type="text" name="input_confirmCode">
			<br><br>
			<button type="button" class="btn btn-info">인증하기</button>
		</c:if>
		
		<c:if test="${requestScope.isUserExist == true && requestScope.sendMailSuccess == false}">
			<span style="color:red;">메일 발송이 실패 했습니다.</span>
		</c:if>
	</div>
</div>

<%-- 인증하기 form --%>
<form name="verifyCertificationFrm">
	<input type="hidden" name="userCertificationCode">
	<input type="hidden" name="userid">
</form>
    
