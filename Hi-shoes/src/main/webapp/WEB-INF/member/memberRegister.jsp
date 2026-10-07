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
		
		<form name="registerFrm" class="p-4">
			<div class="mb-4">
				<div class="agree-tit">
					<label for="agree" class="form-check-label">이용약관에 동의합니다.&nbsp;<span class="star">*</span></label>&nbsp;&nbsp;<input  class="form-check-input" type="checkbox" id="agree" />
				</div>
			  	<div class="agree-cont">
			  		<iframe src="<%=ctxPath%>/iframe_agree/agree.html"></iframe>	
			  	</div>			  
			</div>
			
			<div class="mb-4 row">
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
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
			  		<label for="userid" class="form-label">아이디&nbsp;<span class="star">*</span></label>
			  	</div>
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-6">
				  	<input type="text" class="form-control" name="userid" id="userid" maxlength="40" class="requiredInfo" placeholder="아이디를 입력 해주세요.">					
			  	</div>
			  	<div  class="col-12 col-lg-6 mt-1">
			  		<%-- 아이디중복체크 --%>
			  		<button type="button" id="idcheck" class="d-grid d-lg-block btn btn-sm btn-danger">아이디 중복 확인</button>
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
			  		<label for="hp2" class="form-label">연락처</label>
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
			  		<label for="postcode" class="form-label">우편번호</label>
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
					<label for="address" class="form-label">주소</label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-10">
			  		<div class="col-12 col-lg-6">
			  			<input type="text" class="form-control" name="address" id="address" size="40" maxlength="200" placeholder="주소"/>
			  		</div>
			  		<div class="col-12 col-lg-12 d-flex justify-content-between align-items-center gap-2">
				  		<input type="text" class="form-control" name="detailaddress" id="detailaddress" size="40" maxlength="200" placeholder="상세주소"/>
				  		<input type="text" class="form-control" name="extraaddress" id="extraaddress" size="40" maxlength="200" placeholder="참고항목"/>
				  	</div>
			  	</div>			  	
			  	<p class="error">주소를 입력하세요.</p>
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="gender" class="form-label">성별</label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-2 d-flex justify-content-start align-items-center gap-2">
			  		<input type="radio" class="form-check-input" name="gender" value="1" id="male" /><label for="male">남자</label>
			  	</div>
				<div class="col-12 col-lg-2 d-flex justify-content-start align-items-center gap-2">
					<input type="radio" class="form-check-input" name="gender" value="2" id="female" /><label for="female">여자</label>
				</div>	  				  					  	
			  </div>			  
			</div>
			
			<div class="mb-4 row">
				<div class="col-12 col-lg-2 pt-2">
					<label for="birthday" class="form-label">생년월일</label>
				</div>			  
			  <div class="col-12 col-lg-10 row">
			  	<div class="col-12 col-lg-2">
			  		<input type="date" name="birthday">
			  	</div>			  	  				  					  	
			  </div>			  
			</div>
			
			<div class="row">
				<div class="col-12 col-lg-6 mx-auto text-center">
					<input type="button" class="btn btn-success btn-lg container-fluid" value="가입하기" onclick="goRegister()" />
				</div>
			</div>
		</form>
	</section>
</section>

<jsp:include page="../footer.jsp" />
