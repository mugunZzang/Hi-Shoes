<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>



<jsp:include page="../../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/showMallByCate.css" >

<%-- 가격선택 슬라이더용 --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/sliderControl.css" >
<script type="text/javascript" src="<%= ctxPath%>/js/kimkc/sliderControl.js"></script>




<div class="search-container">

	<%--
		-- 브랜드, 사이즈, 색상, 가격, 검색어
		-- 브랜드는 판매중인 상품들의 '브랜드'만 중복제거해서 긁어와서 select 태그로 구성
		-- 사이즈는 200 <= a <= 300 범위에서 5단위로 끊어서 체크박스로(버튼처럼) 제공하기
		-- 색상은 BLACK, WHITE, SILVER, BROWN, BLUE, YELLOW, RED, IVORY를 팔레트형 checkbox로 제공, 나머지 색상은 무지개색을 넣은 체크박스 선택시 나오게끔
		-- 가격은 slide(mix값과 max값 사이만 갖고옴) > 그럼 min값이랑 max값 넘겨줘야 하지..
		-- 검색어는 상품명에만 like로 DB조회
	 --%>

	



	<%-- 카테고리 nav 바 --%>
	<nav class="category-nav">
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=">전체</a>
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=운동화">운동화</a>
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=스니커즈">스니커즈</a>
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=샌들">샌들</a>
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=구두">구두</a>
		<a class="category-nav-items" href="<%= ctxPath%>/shop/showMallByCate.go?category=부츠">부츠</a>
	</nav>




    <%-- ==== 상품 목록 ==== --%>

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











<jsp:include page="../../footer.jsp" />