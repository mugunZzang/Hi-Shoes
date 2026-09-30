<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" > 
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script> 

<style type="text/css">
	* {
		font-family: "Noto Sans KR", sans-serif;
		  font-optical-sizing: auto;
		  font-weight: 400;
		  font-style: normal; 
	}
	
	
</style>

</head>
<body>
 <!-- 상단 네비게이션 시작 -->
   <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top mx-4 py-3">
      
      <!-- Brand/logo --> <!-- Font Awesome 6 Icons -->
      <a class="navbar-brand" href="<%= ctxPath %>/index.up" style="margin-right: 1%; padding-left: 5%;"><img src="<%= ctxPath %>/images/Logo.svg" /></a>
      
      <!-- 아코디언 같은 Navigation Bar 만들기 -->
       <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
         <span class="navbar-toggler-icon"></span>
       </button>
      
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="nav " style="font-size: 16pt;">
            <li class="nav-item active">
              <a class="nav-link menufont_size text-black" href="<%= ctxPath %>/shop/mallHomeMore.up">회원조회</a>
           </li>
           
           <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle menufont_size text-black" href="#" id="navbarDropdown" data-bs-toggle="dropdown"> 
                 주문관리                               <%-- .text-primary 는 글자색으로 파랑색임 --%>  
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                 <a class="dropdown-item text-primary" href="<%= ctxPath %>/shop/cartList.up">장바구니</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/orderList.up">나의주문내역</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/chart.up">주문통계차트</a>
                 
              </div>
           </li>
           
           <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle menufont_size text-black" href="#" id="navbarDropdown" data-bs-toggle="dropdown"> 
                 상품관리                               <%-- .text-primary 는 글자색으로 파랑색임 --%>  
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                 <a class="dropdown-item text-primary" href="<%= ctxPath %>/shop/cartList.up">장바구니</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/orderList.up">나의주문내역</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/chart.up">주문통계차트</a>
                 
              </div>
           </li>
           
           <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle menufont_size text-black" href="#" id="navbarDropdown" data-bs-toggle="dropdown"> 
                 공급관리                             <%-- .text-primary 는 글자색으로 파랑색임 --%>  
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                 <a class="dropdown-item text-primary" href="<%= ctxPath %>/shop/cartList.up">장바구니</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/orderList.up">나의주문내역</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/chart.up">주문통계차트</a>
                 
              </div>
           </li>
           
           <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle menufont_size text-black" href="#" id="navbarDropdown" data-bs-toggle="dropdown"> 
                 발주관리                               <%-- .text-primary 는 글자색으로 파랑색임 --%>  
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                 <a class="dropdown-item text-primary" href="<%= ctxPath %>/shop/cartList.up">장바구니</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/orderList.up">나의주문내역</a>
                 <a class="dropdown-item text-primary" href="<%= ctxPath%>/shop/chart.up">주문통계차트</a>
                 
              </div>
           </li>
          
          
          
          
          
            <li class="nav-item active">
              <a class="nav-link menufont_size text-black" href="<%= ctxPath %>/shop/mallHomeMore.up">고객센터</a>
           </li>
             <li class="nav-item active">
              <a class="nav-link menufont_size text-black" href="<%= ctxPath %>/shop/mallHomeScroll.up">차트관리</a>
           </li>
           

           
           
          
     
           
        </ul>
         <div class="ms-auto d-flex align-items-center" style="padding:0% 5%;">
        <%-- <div style="margin-left: auto;">--%>
        <i class="fa-solid fa-user fa-2x"></i>&nbsp;&nbsp;<span style="font-size: 14pt;">관리자 로그인중..</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <a class="text-decoration-none text-secondary " href="<%= ctxPath%>login/logout.up"><i class="fa-solid fa-right-from-bracket fa-2x"></i>&nbsp;&nbsp;<span style="font-size: 14pt;">로그아웃</span></a>
         </div>
      </div>
   </nav>
   <!-- 상단 네비게이션 끝 -->

 




