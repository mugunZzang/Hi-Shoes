<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String ctxPath = request.getContextPath();    
%>
<jsp:include page="../header.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/user/userLogin.css" />

<section class="bodycont login">
	<div class="container">
		<div class="title row text-center">
			<h2>로그인</h2>
		</div>
		<div class="cont row">
			<form action="" class="container">
			
				<div class="his-input">
				  <label>
				  	<input type="text" placeholder="아이디를 입력해주세요." size="20" autocomplete="off" >
				  </label>
				</div>
				
				<div class="his-input">
				  <label>
				  	<input type="password" placeholder="비밀번호를 입력해주세요." size="20">
				  </label>
				</div>
				
				<div>
					<input type="checkbox" id="saveid"> <label for="saveid">아이디 저장</label>
				</div>				
				
				<button type="submit" class="btn btn-dark row w-100 m-auto my-3">로그인</button>
			</form>
			
			<section class="other-service text-center">
				<a href="#">아이디 찾기</a>	
				<a href="#">비밀번호 찾기</a>	
				<a href="#">회원가입</a>	
			</section>	
		</div>		
	</div>
</section>

<jsp:include page="../footer.jsp" />