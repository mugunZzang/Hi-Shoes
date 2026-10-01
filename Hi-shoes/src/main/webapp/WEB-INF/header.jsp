<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
%>    

<!DOCTYPE html>
<html>
<head>
<%-- Required meta tags --%>
<meta charset="UTF-8">
<title>신발 살 땐 하이슈즈</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" > 

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/reset.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />

<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>
</head>
<body>
	<header class="container-fluid">
		<section class="header-top d-flex flex-wrap justify-content-center">
			<div class="logo-wrap d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
				<h1>
					<a href="<%= ctxPath %>/index.go">
						<img alt="헤더 로고" src="<%= ctxPath %>/images/Logo.svg">
					</a>
				</h1>								
			</div>
			<div class="join-wrap nav nav-pills">
				<a href="#">
					<img alt="login" src="<%= ctxPath %>/images/login.svg">
					<span>LOGIN</span>
				</a>
				
				<a href="#">
					<img alt="join" src="<%= ctxPath %>/images/join.svg">
					<span>JOIN</span>
				</a>
			</div>
		</section>
		<section class="header-bottom">
			<nav>
				<ul class="main-nav nav nav-pills">
					<li class="nav-item">
						<a href="#">운동화</a>
					</li>
					<li class="nav-item">
						<a href="#">스니커즈</a>
					</li>
					<li class="nav-item">
						<a href="#">구두</a>
					</li>
					<li class="nav-item">
						<a href="#">부츠</a>
					</li>
					<li class="nav-item">
						<a href="#">샌들</a>
					</li>
				</ul>
			</nav>
		
			<form class="col-12 searchbar d-flex rounded-pill" role="search">
			    <div class="input-group">
			      <input type="search" class="form-control" placeholder="검색어를 입력해주세요." aria-label="Search">
			      <button class="search-icon" type="submit">검색</button>
			    </div>
		  	</form>
			
		</section>
	</header>
