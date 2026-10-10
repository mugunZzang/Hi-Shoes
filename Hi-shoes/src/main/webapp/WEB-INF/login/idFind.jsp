<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
	<form class="row mb-3">
		<div class="col-9 my-auto">
			  <label for="inlineFormInputName3" class="d-none">이름</label>
			  <input type="text" class="form-control mb-3 me-sm-2" id="inlineFormInputName3" placeholder="이름을 입력 해주세요.">
			
			  <label for="inlineFormInputGroupUsername3" class="d-none">이메일</label>		  			    
		      <input type="text" class="form-control" id="inlineFormInputGroupUsername3" placeholder="이메일을 입력해주세요.">
		</div>
	  	
	  	<div class="col-3">
	  		<button type="submit" class="btn btn-md btn-primary w-100 h-100">아이디<br/>찾기</button>	
	  	</div>
	
	  
	</form>
	<div class="result-wrap">
		<span>고객님의 아이디: </span> <span>Leess</span>
	</div>
</div>
