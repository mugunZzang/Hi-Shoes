<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String ctx_Path = request.getContextPath();
%>


<jsp:include page="../adminHeader.jsp" />
<%-- 테스트용: 로그인 구현되면 삭제 --%>
<c:if test="${empty sessionScope.loginuser}">
    <c:set var="tmpName" value="홍길동"/>
</c:if>
<style type="text/css">
    .paper {
        width: 794px;              /* A4 폭 */
        margin: 0 auto;
        background: #fff;
        border: 1px solid #ccc;
        padding: 50px 45px;
        font-size: 14px;
        color: #222;
    }
    .paper .doc-title { text-align: center; font-size: 28px; font-weight: 600; margin-bottom: 30px; }

    .paper table { border-collapse: collapse; width: 100%; }
    .paper th, .paper td { border: 1px solid #bbb; padding: 7px 10px; }
    .paper th { background: #f2f2f2; font-weight: 500; text-align: center; width: 90px; }

	
	.supplier-side .label-col { width: 32px; background: #f2f2f2; text-align: center; font-weight: 500; }
    .buyer-box { background: #eee; border: 1px solid #bbb; padding: 14px 18px; }
    .buyer-box .to { background: #ddd; text-align: center; font-weight: 600; padding: 8px; margin: -14px -18px 14px; }

    .items th { background: #e9e9e9; text-align: center; width: auto; }
    .items td { text-align: center; }
    .items .sum-row td { background: #e9e9e9; font-weight: 600; }
    .note-box { border: 1px solid #bbb; padding: 15px; margin-top: 25px; font-size: 13px; text-align: center; }


    @media print {
        .no-print, nav, header, footer, #sidebarToggleIcon, #sidebarToggleBtn { display: none !important; }
        .paper { border: none; width: 100%; padding: 0; }
        #container { top: 0 !important; padding: 0 !important; }
    }
</style>

<div class="container-fluid" id="container"
     style="position: relative; top: 90px; padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px; margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold">견적서</p>

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
	
	<!-- 견적서 jsp 파일 -->
	<jsp:include page="quotationContent.jsp" />   
	
	<div class="d-flex justify-content-center gap-2 mt-4 no-print">
	    <button type="button" class="btn btn-outline-dark" onclick="window.print()">인쇄/PDF 저장</button>
	    <a href="<%= ctx_Path %>/admin/purchase/purchaseRegister.go" class="btn btn-outline-primary">추가 발주</a>
	    <a href="<%= ctx_Path %>/admin/purchase/purchaseList.go" class="btn btn-primary">발주 목록</a>
	    <a href="<%= ctx_Path %>/admin/adminMain.go" class="btn btn-outline-secondary">홈으로</a>
	</div>
</div>
</div>

<jsp:include page="../adminFooter.jsp" />