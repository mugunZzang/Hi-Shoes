<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String ctxPath = request.getContextPath();
%>

<c:if test="${not empty requestScope.reviewList}">
			
<c:forEach var="review" items="${requestScope.reviewList}">
     		<%-- 리뷰 1개 --%>
       <div class="review-item">

		<div class="review-big-rating">
			
	    	<c:forEach begin="1" end="${review.rating}" var="i">
	            ★
	        </c:forEach>
	        <c:forEach begin="1" end="${5 - review.rating}" var="i">
	            ☆
	        </c:forEach>
        </div>
	
	    <%-- 리뷰 정보 --%>
	    <div class="review-info">
	    	
			
			
	        <div class="review-option-area">
	            <span class="review-option">옵션</span>
	            <span class="review-option-value">${review.stockDTO.ssize}, ${review.stockDTO.color}</span>
	        </div>
	
	        <div class="review-user-info">
	
	            <span class="review-user">
	                작성자 : ${fn:substring(review.fk_userid, 0, 3)}<c:forEach
	                    begin="3"
	                    end="${fn:length(review.fk_userid) - 1}">*</c:forEach>
	            </span>
	
	            <span class="review-date">
	                ${review.rwritedate}
	            </span>
	
	        </div>
	
	
	    </div>
	
	    <%-- 리뷰 내용 --%>
	    <div class="review-content">
	
	        ${review.rcontents}
	
	    </div>
	    
	    <%-- 리뷰 이미지 --%>
	    <c:if test="${not empty review.rimage}">
			<div class="review-image mt-3">
				<img alt="리뷰이미지" src="<%=ctxPath%>/images/kimkc/product/${review.rimage}">
			</div>
		</c:if>

	</div>
	
</c:forEach>

</c:if>