<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${not empty requestScope.qnaList}">
					
	<c:forEach var="qna" items="${requestScope.qnaList}">

	    <%-- Q&A 항목 --%>
	    <div class="qna-item"
	         data-can-open="<c:choose>
	             <c:when test="${qna.islock eq '공개'
	                 or (not empty sessionScope.loginuser
	                 and qna.fk_userid eq sessionScope.loginuser.userid)}">true</c:when>
	             <c:otherwise>false</c:otherwise>
	         </c:choose>">
	
	        <%-- 질문 제목 및 기본 정보 --%>
	        <div class="qna-question">
	
	            <span class="qna-title">
	                ${qna.qtitle}
	            </span>
	
	            <%-- 공개 여부 --%>
	            <c:if test="${qna.islock eq '비공개'}">
	                <span class="qna-lock">🔒</span>
	            </c:if>
	
	            <c:if test="${qna.islock eq '공개'}">
	                <span class="qna-lock empty"></span>
	            </c:if>
	
	            <%-- 작성자 id는 3글자만 보이기 --%>
	            <span class="qna-user">
	                ${fn:substring(qna.fk_userid, 0, 3)}<c:forEach
	                    begin="3"
	                    end="${fn:length(qna.fk_userid) - 1}">*</c:forEach>
	            </span>
	
	            <%-- 작성일 --%>
	            <span class="qna-date">
	                ${qna.qwritedate}
	            </span>
	
	            <%-- 답변 상태 --%>
	            <span class="qna-status">
	                <c:if test="${not empty qna.qanswer}">
	                    답변완료
	                </c:if>
	                <c:if test="${empty qna.qanswer}">
	                    등록상태
	                </c:if>
	            </span>
	
	        </div>
	
	        <%-- 질문 및 답변은 기본적으로 숨김 --%>
	        <div class="qna-answer-area">
	
	            <%-- 질문 내용 --%>
	            <div class="qna-question-content pb-5">
	                ${qna.qcontents}
	            </div>
	
	            <%-- 답변이 존재할 때만 표시 --%>
	            <c:if test="${not empty qna.qanswer}">
	                <div class="qna-answer">
	
	                    <div class="answer-icon">A</div>
	
	                    <div class="answer-content">
	                        ${qna.qanswer}
	                    </div>
	
	                </div>
	            </c:if>
	
	        </div>
	
	    </div>
	
	</c:forEach>
          
</c:if>