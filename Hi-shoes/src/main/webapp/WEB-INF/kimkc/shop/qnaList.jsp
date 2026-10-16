<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${not empty requestScope.qnaList}">
					
	<c:forEach var="qna" items="${requestScope.qnaList}">

	    <%-- Q&A 항목 --%>
	    <div class="qna-item">
	
	        <%-- 질문 제목 및 기본 정보 --%>
	        <div class="qna-question">
	
				<input class="qna-qnanum" type="hidden" value="${qna.qnanum}" />
	
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
	            <div class="qna-status">
	                <c:if test="${not empty qna.qanswer}">
	                <span id="qna-has-answer">
	                    답변완료
                    </span>
	                </c:if>
	                <c:if test="${empty qna.qanswer}">
	                <span id="qna-has-noanswer">
	                    등록상태
	                </span>
	                </c:if>
	            </div>
	
	        </div>
	
	        <%-- 질문 및 답변은 기본적으로 숨김 --%>
	        <div class="qna-answer-area">
	        </div>
	
	    </div>
	
	</c:forEach>
          
</c:if>