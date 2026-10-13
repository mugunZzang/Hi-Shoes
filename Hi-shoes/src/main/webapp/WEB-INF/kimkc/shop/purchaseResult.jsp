<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/purchaseResult.css" >

<jsp:include page="../../header.jsp" />



	<div class="purchase-result-container">
	
	    <!-- 주문 완료 안내 -->
	    <section class="purchase-complete">
	        <div class="complete-icon">
	            <span>✓</span>
	        </div>
	
	        <h2>주문이 완료되었습니다!</h2>
	        <p class="complete-message">
	            이용해 주셔서 감사합니다.
	        </p>
	    </section>
	
	    <!-- 주문 정보 -->
	    <section class="result-section">
	        <h3 class="section-title">주문 정보</h3>
	
	        <div class="result-info-box">
	            <div class="result-info-row">
	                <span class="info-label">주문번호</span>
	                <strong class="info-value">
	                    ${requestScope.orderMap.onum}
	                </strong>
	            </div>
	
	            <div class="result-info-row">
	                <span class="info-label">주문일시</span>
	                <span class="info-value">
	                    ${requestScope.orderMap.orderdate}
	                </span>
	            </div>
	
	            <div class="result-info-row total-price-row">
	                <span class="info-label">결제금액</span>
	                <strong class="info-value total-price">
	                    <fmt:formatNumber value="${requestScope.orderMap.totalprice}" type="number" />
	                </strong>
	            </div>
	        </div>
	    </section>
	
	    <!-- 주문자 정보 -->
	    <section class="result-section">
	        <h3 class="section-title">주문자 정보</h3>
	
	        <div class="result-info-box">
	            <div class="result-info-row">
	                <span class="info-label">업체명</span>
	                <span class="info-value">
	                    ${requestScope.loginuser.company}
	                </span>
	            </div>
	
	            <div class="result-info-row">
	                <span class="info-label">전화번호</span>
	                <span class="info-value">
	                    ${requestScope.loginuser.mobile}
	                </span>
	            </div>
	
	            <div class="result-info-row">
	                <span class="info-label">이메일</span>
	                <span class="info-value">
	                    ${requestScope.loginuser.email}
	                </span>
	            </div>
	
	            <div class="result-info-row address-row">
	                <span class="info-label">배송 주소</span>
	                <span class="info-value address-value">
	                   [${requestScope.loginuser.postcode}]
						${requestScope.loginuser.address},
						${requestScope.loginuser.detailaddress}<c:if test="${not empty requestScope.loginuser.extraaddress}"> ${requestScope.loginuser.extraaddress}</c:if>
	                </span>
	            </div>
	        </div>
	    </section>
	
	    <!-- 하단 버튼 -->
	    <div class="result-button-group">
	        <a href="<%= ctxPath %>/index.go" class="result-btn btn-home">
	            홈으로
	        </a>
	
	        <a href="#" class="result-btn btn-order-list">
	            주문내역
	        </a>
	    </div>
	
	</div>



<jsp:include page="../../footer.jsp" />