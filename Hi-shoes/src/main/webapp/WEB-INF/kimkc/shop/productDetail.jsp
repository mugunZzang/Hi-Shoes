<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/productDetail.css" >
<jsp:include page="../../header.jsp" />


	<div class="content-wrap row text-center">
		<%-- 상품이미지 영역 --%>
		<div class="col-lg-6">
			
			<div class="pimage-wrap">
				<img src="<%=ctxPath %>/images/kimkc/product/ML408K 1_뉴발란스.jpg" class="pimage"/>
			</div>
		</div>

		<%-- 상품정보 및 옵션선택 영역 --%>
		<div class="col-lg-6">
			<div class="productInfo_wrap">
				<span class="productInfo_brand">브랜드</span>
				<span class="productInfo_pname">상품명</span>
				<span class="productInfo_pname">상품보증서</span>
				<span class="productInfo_regprice">정가</span>
				<span class="productInfo_saleprice">판매가</span>
				<button class="productInfo_btnSize">사이즈용</button>
				
				<form action="" method="post">
					<button class="productInfo_btnPurchase">결제</button>
					<button class="productInfo_btnCart">장바구니</button>
				</form>
				
			</div>
			
		
		</div>
	</div>

<jsp:include page="../../footer.jsp" />