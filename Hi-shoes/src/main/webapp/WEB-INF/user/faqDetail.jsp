<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>


<style>
    .faq-detail-container {
        width: 100%;
        max-width: 1000px;
        margin: 40px auto;
        padding: 0 20px;
        color: #222;
    }

    /* FAQ 상단 */
    .faq-detail-header {
        padding: 25px 10px 22px;
        border-top: 2px solid #222;
        border-bottom: 1px solid #ddd;
    }

    .faq-detail-label {
        display: inline-block;
        margin-bottom: 12px;
        padding: 5px 12px;
        background-color: #f1f3f5;
        color: #495057;
        font-size: 13px;
        font-weight: 600;
        border-radius: 4px;
    }

    .faq-detail-title {
        margin: 0 0 18px;
        font-size: 27px;
        font-weight: 700;
        line-height: 1.5;
        word-break: keep-all;
    }

    .faq-detail-category {
        color: #777;
        font-size: 14px;
    }

    /* FAQ 본문 */
    .faq-detail-content {
        min-height: 300px;
        padding: 35px 15px;
        font-size: 15px;
        line-height: 1.9;
        color: #333;
        white-space: pre-wrap;
        overflow-wrap: anywhere;
    }

    /* 본문 앞 질문 표시 */
    .faq-question-mark {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 30px;
        height: 30px;
        margin-right: 10px;
        border-radius: 50%;
        background-color: #343a40;
        color: white;
        font-size: 16px;
        font-weight: 700;
        vertical-align: middle;
    }

    /* 하단 */
    .faq-detail-footer {
        padding: 20px 0;
        border-top: 1px solid #ddd;
        text-align: center;
    }

    .faq-list-btn {
        min-width: 110px;
        padding: 10px 24px;
        border: none;
        border-radius: 4px;
        background-color: #343a40;
        color: white;
        font-size: 14px;
        cursor: pointer;
    }

    .faq-list-btn:hover {
        background-color: #212529;
    }

    @media (max-width: 576px) {
        .faq-detail-container {
            margin: 25px auto;
            padding: 0 15px;
        }

        .faq-detail-title {
            font-size: 22px;
        }

        .faq-detail-content {
            padding: 25px 5px;
        }
    }
</style>

<div class="faq-detail-container">

    <!-- FAQ 제목 영역 -->
    <div class="faq-detail-header">

        <span class="faq-detail-label">FAQ</span>

        <h2 class="faq-detail-title">
            <span class="faq-question-mark">Q</span>
            <c:out value="${requestScope.fdto.fsubject}" />
        </h2>

        <div class="faq-detail-category">
            카테고리 :
            <c:out value="${requestScope.fdto.fcategory}" />
        </div>

    </div>

    <!-- FAQ 내용 영역 -->
    <div class="faq-detail-content">
        <c:out value="${requestScope.fdto.fcontents}" />
    </div>

    <!-- 목록 버튼 -->
    <div class="faq-detail-footer">
        <button type="button"
                class="faq-list-btn"
                onclick="location.href='usercallcenter.go?tab=faq'">
            목록으로
        </button>
    </div>

</div>



<jsp:include page="../footer.jsp"/>