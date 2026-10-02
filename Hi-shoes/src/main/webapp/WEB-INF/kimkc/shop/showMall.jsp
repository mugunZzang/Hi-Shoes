<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
	String ctxPath = request.getContextPath();
%>



<jsp:include page="../../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/showMall.css" >

<div id="container" class="container">

	<div id="search_sidebar">
		
	</div>
		
		
	<div id="contents" class="text-center">
	
		<div id="category_nav" >
			<ul id="category_nav_list" >
				<li>cate1</li>
				<li>cate2</li>
				<li>cate3</li>
				<li>cate3</li>
				<li>cate3</li>
			</ul>
		</div>
		
		
		 
		<div id="prodList" class="text-center row">
			
			<c:if test="${empty requestScope.prodList}">
				<span>상품이 없어요.</span>
			</c:if>
			<c:if test="${not empty requestScope.prodList}">
				<c:forEach var="pdto" items="${requestScope.prodList}">
					<div class="col-lg-3 col-md-6">
						<div class="card" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.go';">	<%-- 여기에 판매번호 넘겨야 함 --%>
							<img src="${pageContext.request.contextPath}/images/kimkc/product/${pdto.pimage1}" class="card-img-top" alt="...">
							<div class="card-body">
							  	<p class="card-title">${pdto.catalogueDTO.brand}</p>
							  	<p class="card-text">${pdto.catalogueDTO.pname}</p>
								<p class="card-text">${pdto.catalogueDTO.regprice}</p>
								<p class="card-text">${pdto.catalogueDTO.saleprice}</p>
							
							  	<a href="/Hi-shoes/shop/productDetail.go" class="card-link stretched-link" style="display: none;">Card link</a>
							  	
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
			
			
			
			
		</div>
		
		<nav class="my-5">
       		<div style='display:flex; width:80%; margin: 0 auto;'>
   	     		<ul class="pagination" style='margin:auto;'></ul>
   	   		</div>
		</nav>
			
	</div>
	<%-- 한 행에 4개씩만 보여주기 --%>
	
</div>

<jsp:include page="../../footer.jsp" />