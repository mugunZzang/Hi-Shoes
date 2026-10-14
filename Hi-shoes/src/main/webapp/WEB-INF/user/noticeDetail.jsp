<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>

<style>
/* 공지사항 상세 전체 영역 */
.notice-detail-container {
    width: 100%;
    max-width: 1000px;
    margin: 40px auto;
    padding: 0 20px;
    color: #222;
}

/* 제목 영역 */
.notice-detail-header {
    padding: 25px 10px 22px;
    border-top: 2px solid #222;
    border-bottom: 1px solid #ddd;
}

/* 공지사항 라벨 */
.notice-label {
    display: inline-block;
    margin-bottom: 12px;
    padding: 5px 11px;
    background-color: #f1f3f5;
    color: #495057;
    font-size: 13px;
    font-weight: 600;
    border-radius: 4px;
}

/* 제목 */
.notice-detail-title {
    margin: 0 0 18px;
    font-size: 27px;
    font-weight: 700;
    line-height: 1.5;
    word-break: keep-all;
}

/* 작성 정보 */
.notice-detail-info {
    color: #888;
    font-size: 13px;
}

.notice-info-divider {
    margin: 0 8px;
    color: #ccc;
}

/* 본문 */
.notice-detail-content {
    min-height: 300px;
    padding: 35px 15px;
    font-size: 15px;
    line-height: 1.9;
    color: #333;
    white-space: pre-wrap;
    overflow-wrap: anywhere;
}

/* 하단 버튼 영역 */
.notice-detail-footer {
    padding: 20px 0;
    border-top: 1px solid #ddd;
    text-align: center;
}

.notice-detail-footer .btn {
    min-width: 110px;
    padding: 10px 24px;
    font-size: 14px;
}

</style>

<script type="text/javascript">


</script>


<div class="notice-detail-container">

    <!-- 공지사항 제목 -->
    <div class="notice-detail-header">
        <span class="notice-label">공지사항</span>

        <h2 class="notice-detail-title">
            ${requestScope.ndto.nsubject}
        </h2>

        <div class="notice-detail-info">
            <span>고객센터</span>
            <span class="notice-info-divider">|</span>
            <span>공지사항</span>
        </div>
    </div>

    <!-- 공지사항 본문 -->
    <div class="notice-detail-content">
        ${requestScope.ndto.ncontents}
    </div>
    
    <c:if test="${not empty requestScope.ndto.nimage}">
        <div class="notice-detail-image">
            <img src="${pageContext.request.contextPath}/images/${requestScope.ndto.nimage}"
                 alt="공지사항 이미지">
        </div>
    </c:if>

    <!-- 목록으로 돌아가기 -->
    <div class="notice-detail-footer">
        <button type="button"
                class="btn btn-dark"
                onclick="location.href='usercallcenter.go?tab=notice'">
            목록으로
        </button>
    </div>

</div>


<jsp:include page="../footer.jsp"/>