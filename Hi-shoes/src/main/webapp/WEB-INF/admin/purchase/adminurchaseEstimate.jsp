<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String ctx_Path = request.getContextPath();
%>


<jsp:include page="../adminHeader.jsp" />

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/mxExtra.css">

<%-- 테스트용: 로그인 구현되면 삭제 --%>
<c:if test="${empty sessionScope.loginuser}">
    <c:set var="tmpName" value="홍길동"/>
</c:if>

<%-- .paper(견적서 문서) 스타일은 quotationContent.jsp 에만 있다. 여기에 다시 정의하지 않는다. --%>

<div class="container-fluid" id="container"
     style="position: relative; top: 90px; padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center no-print"
         style="padding: 20px 0px; margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold"></span>
        견적서
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="<%= ctx_Path %>/admin/adminMain.go" class="text-decoration-none">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#" class="text-decoration-none">발주관리</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">견적서</li>
            </ol>
        </nav>
    </div>

    <!-- 견적서 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 50px;
                border: 1px solid #eeeeee;
                border-radius: 6px;
                padding: 25px;
                margin-bottom: 50px;">

	<!-- 안내 (인쇄 시 숨김) -->
	<div class="mx-notice mb-4 no-print">
	    <i class="bi bi-info-circle"></i>
	    견적서를 확인한 뒤 <strong>인쇄/PDF 저장</strong>으로 보관하거나 업체에 전달할 수 있습니다.
	</div>
	
	<!-- 견적서 jsp 파일 -->
	<jsp:include page="quotationContent.jsp" />   
	
	<div class="d-flex justify-content-center gap-2 mt-4 no-print">
	    <button type="button" class="btn btn-secondary" onclick="window.print()">
	        <i class="bi bi-printer"></i> 인쇄/PDF 저장
	    </button>
	    <a href="<%= ctx_Path %>/admin/purchase/purchaseRegister.go" class="btn btn-outline-secondary">
	        <i class="bi bi-plus-lg"></i> 추가 발주
	    </a>
	    <a href="<%= ctx_Path %>/admin/purchase/purchaseList.go" class="btn btn-outline-secondary">
	        <i class="bi bi-list-ul"></i> 발주 목록
	    </a>
	    <a href="<%= ctx_Path %>/admin/adminMain.go" class="btn btn-outline-secondary">
	        <i class="bi bi-house"></i> 홈으로
	    </a>
	</div>
</div>
</div>

<jsp:include page="../adminFooter.jsp" />
