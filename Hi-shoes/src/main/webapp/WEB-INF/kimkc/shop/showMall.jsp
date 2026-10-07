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

    <%-- ==== 검색 결과 제목 ==== --%>

    <h1><span>${requestScope.searchKeyword}</span>에 대한 검색결과</h1>


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
			
			
			<%-- 페이지바 --%>
			<nav class="my-5">
	           	<div style='display:flex; width:80%; margin: 0 auto;'>
	             	<ul class="pagination" style='margin:auto;'>
	             		${requestScope.pageBar}
	             	</ul>
	          	</div>
        	</nav>
		</c:if>

    </div>
    
    

</div>











<jsp:include page="../../footer.jsp" />