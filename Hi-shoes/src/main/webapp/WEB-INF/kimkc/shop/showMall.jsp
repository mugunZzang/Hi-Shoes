<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>



<jsp:include page="../../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/showMall.css" >


=

<div class="search-container">

    <!-- =========================
         검색 결과 제목
    ========================== -->

    <h1><span>${requestScope.searchKeyword}</span>에 대한 검색결과</h1>


    <!-- =========================
         상품 목록
    ========================== -->

    <div class="product-list">

		<c:if test="${empty requestScope.prodList}">
			<span>상품이 없어요.</span>
		</c:if>

		<c:if test="${not empty requestScope.prodList}"> 
			<c:forEach var="pdto" items="${requestScope.prodList}">
		        <!-- 상품 1 -->
		        <div class="product-item">
		            <a href="<%=ctxPath %>/shop/productDetail.go?pnum=${pdto.pnum}" class="product-link">
		
		                <!-- 상품 이미지 -->
		                <div class="product-image">
		                    <img
		                        src="${pageContext.request.contextPath}/images/kimkc/product/${pdto.pimage1}"
		                        alt="아디다스 오즈가이아">
		                </div>
		
		
		                <!-- 상품 정보 -->
		                <div class="product-info">
		
		                    <!-- 브랜드 -->
		                    <div class="product-brand">
		                        ${pdto.catalogueDTO.brand}
		                    </div>
		
		
		                    <!-- 상품명 -->
		                    <div class="product-name">
		                        <span class="product-badge">
		                            공용
		                        </span>
		
		                        <span class="product-title">
		                            ${pdto.catalogueDTO.pname}
		                        </span>
		                    </div>
		
		
		                    <!-- 가격 -->
		                    <div class="product-price">
		                        <strong>
		                            <fmt:formatNumber value="${pdto.catalogueDTO.saleprice}" type="number" />
		                        </strong>
		
		                        <span>원</span>
		                    </div>
		
		
		                    <!-- 상품 태그 -->
		                    <div class="product-tags">
		
		                    </div>
		                </div>
		            </a>
		        </div>

			</c:forEach>
		</c:if>

    </div>
    
    

</div>









<%-- <div id="container" class="container">

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
						<div class="card" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.go';">	여기에 판매번호 넘겨야 함
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
	한 행에 4개씩만 보여주기
	
</div> --%>

<jsp:include page="../../footer.jsp" />