<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctx_Path = request.getContextPath();
%>

<jsp:include page="../adminHeader.jsp" />
<script>
    const ctx_Path = "<%= ctx_Path %>";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/purchase/purchaseList.js"></script>


<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold">
            발주목록 조회
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item">
                    발주관리
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    발주조회
                </li>

            </ol>

        </nav>

    </div>


    <!-- 실제 페이지 작업 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">

        <!-- ==================== 발주 상태 탭 ==================== -->

        <ul class="nav nav-tabs mb-4">

            <li class="nav-item">
                <a class="nav-link active"
                   href="#">
                    전체 발주
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="#">
                    입고 대기
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="#">
                    입고 완료
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="#">
                    입고 지연
                </a>
            </li>

        </ul>


        <!-- ==================== 검색 영역 ==================== -->

        <div class="border rounded p-4 mb-4">

            <div class="row g-3 align-items-center">

                <!-- 검색어 -->
                <div class="col-md-6">

                    <label class="form-label fw-semibold">
                        검색
                    </label>

                    <input type="text"
                           class="form-control"
                           placeholder="발주번호 / 업체명 / 제품명">

                </div>


                <!-- 발주일 -->
                <div class="col-md-6">

                    <label class="form-label fw-semibold">
                        발주일
                    </label>

                    <div class="d-flex align-items-center gap-2">

                        <input type="date"
                               class="form-control">

                        <span>~</span>

                        <input type="date"
                               class="form-control">

                    </div>

                </div>

            </div>


            <!-- 검색 버튼 -->
            <div class="d-flex justify-content-end mt-4">

                <button type="button"
                        class="btn btn-outline-secondary me-2">
                    초기화
                </button>

                <button type="button"
                        class="btn btn-dark">
                    검색
                </button>

            </div>

        </div>


        <!-- ==================== 목록 상단 ==================== -->

        <div class="d-flex justify-content-between align-items-center mb-2">

            <div>
                <span class="fw-semibold">
                    발주 목록
                </span>

                <span class="text-muted ms-2">
                    총 4건
                </span>
            </div>


            <!-- 정렬 -->
            <select class="form-select"
                    style="width: 140px;">

                <option selected>
                    최신순
                </option>

                <option>
                    오래된순
                </option>

            </select>

        </div>


        <!-- ==================== 발주 목록 ==================== -->

        <div class="table-responsive">
            <table class="table table-hover text-center align-middle">
            
                <thead class="table-light">
                    <tr>
                        <th>발주번호</th>
                        <th>공급업체</th>
                        <th>발주일시</th>
                        <th>납품기한</th>
                        <th>제품개수</th>
                        <th>총수량</th>
                        <th>입고상태</th>
                        <th>관리</th>
                    </tr>
                </thead>

                <tbody>
				    <c:if test="${empty requestScope.purchaseList}">
				        <tr>
				            <td colspan="7" class="text-center">
				                발주내역이 없습니다.
				            </td>
				        </tr>
				    </c:if>
				
				    <c:if test="${not empty requestScope.purchaseList}">
				        <c:forEach var="purchase" items="${requestScope.purchaseList}">
				            <tr>
				                <td>${purchase.purnum}</td>
				                <td>${purchase.fk_supname}</td>
				                <td>${purchase.purtime}</td>
				                <td>${purchase.purdeadline}</td>
				                <td>${purchase.product_count}건</td>
				                <td>${purchase.total_quantity}개</td>
				                <td>${purchase.instock}</td>
				            </tr>
				        </c:forEach>
				    </c:if>
                </tbody>

            </table>

        </div>

        <!-- ==================== 페이징 ==================== -->

        <nav class="mt-4">

            <ul class="pagination justify-content-center">

                <li class="page-item disabled">
                    <a class="page-link"
                       href="#">
                        이전
                    </a>
                </li>

                <li class="page-item active">
                    <a class="page-link"
                       href="#">
                        1
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link"
                       href="#">
                        2
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link"
                       href="#">
                        3
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link"
                       href="#">
                        다음
                    </a>
                </li>

            </ul>

        </nav>

    </div>

</div>


<jsp:include page="../adminFooter.jsp" />
