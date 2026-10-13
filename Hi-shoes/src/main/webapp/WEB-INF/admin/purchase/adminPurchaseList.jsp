<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctx_Path = request.getContextPath();
%>

<jsp:include page="../adminHeader.jsp" />

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/mxExtra.css">

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

        <p class="mb-0 fs-4 fw-semibold"></span>
            발주목록 조회
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="<%= ctx_Path %>/admin/adminMain.go"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item">
                    <a href="<%= ctx_Path %>/admin/catalogue/catalogueList.go"
                       class="text-decoration-none">
                        발주관리
                    </a>
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
                border-radius: 6px;
                padding: 25px;">

        <!-- ==================== 발주 상태 탭 ==================== -->

        <ul class="nav nav-tabs mb-4">

			<li class="nav-item">
			    <a class="nav-link ${requestScope.status == 'all' ? 'active' : ''}"
			       href="?status=all${requestScope.baseQuery}">
			        전체 발주
			    </a>
			</li>

			<li class="nav-item">
			    <a class="nav-link ${requestScope.status == 'wait' ? 'active' : ''}"
			       href="?status=wait${requestScope.baseQuery}">
			        입고 대기
			    </a>
			</li>

			<li class="nav-item">
			    <a class="nav-link ${requestScope.status == 'done' ? 'active' : ''}"
			       href="?status=done${requestScope.baseQuery}">
			        입고 완료
			    </a>
			</li>

			<li class="nav-item">
			    <a class="nav-link ${requestScope.status == 'delay' ? 'active' : ''}"
			       href="?status=delay${requestScope.baseQuery}">
			        입고 지연
			    </a>
			</li>

        </ul>


        <!-- ==================== 검색 영역 ==================== -->

		<form name="searchFrm" method="get" class="mx-search-box mb-4">

		  <!-- 탭에서 검색유지하기 위한 input 태그 -->
		  <input type="hidden" name="status" value="${requestScope.status}">

		    <div class="row g-3 align-items-center">

		        <!-- 공급업체 드롭다운 -->
		        <div class="col-md-6">
		            <label class="form-label fw-semibold">공급업체</label>
		            <select name="supname" class="form-select">
		                <option value="">전체</option>                <!-- supplierList 에는 공급업체 이름들이 들어있음 -->
		                <c:forEach var="sup" items="${requestScope.supplierList}">
		                    <option value="${sup}" ${sup == requestScope.supname ? 'selected' : ''}>
		                        ${sup}
		                    </option>
		                </c:forEach>
		            </select>
		        </div>

		        <!-- 발주일 -->
		        <div class="col-md-6">
		            <label class="form-label fw-semibold">발주일</label>
		            <div class="d-flex align-items-center gap-2">
		                <input type="date" name="startDate" class="form-control"
		                       value="${requestScope.startDate}">
		                <span>~</span>
		                <input type="date" name="endDate" class="form-control"
		                       value="${requestScope.endDate}">
		            </div>
		        </div>

		    </div>

		    <!-- 검색 버튼 -->
		    <div class="d-flex justify-content-end mt-4">
		        <button type="button" id="btnReset" class="btn btn-outline-secondary me-2">
		            <i class="bi bi-arrow-counterclockwise"></i> 초기화
		        </button>
		        <button type="submit" class="btn btn-secondary">
		            <i class="bi bi-search"></i> 검색
		        </button>
		    </div>

		</form>

        <!-- ==================== 목록 상단 ==================== -->

        <div class="mx-list-head d-flex justify-content-between align-items-center mb-2">

            <div>
                <span class="mx-section-title">
                    발주 목록
                </span>

                <span class="mx-list-count ms-2">
                    총 <strong>${requestScope.totalCountPurchase}</strong>건
                </span>
            </div>

        </div>


        <!-- ==================== 발주 목록 ==================== -->

        <div class="table-responsive mx-table-wrap">
            <table class="table table-hover text-center align-middle">

                <thead>
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
				            <td colspan="8" class="text-center">
				                <div class="mx-empty">
				                    <i class="bi bi-inbox"></i>
				                    <p>발주내역이 없습니다.</p>
				                </div>
				            </td>
				        </tr>
				    </c:if>

				    <c:if test="${not empty requestScope.purchaseList}">
				        <c:forEach var="purchase" items="${requestScope.purchaseList}">
				            <tr>
				                <td class="mx-strong">${purchase.purnum}</td>
				                <td><c:out value="${purchase.fk_supname}"/></td>
				                <td>${purchase.purtime}</td>
				                <td>${purchase.purdeadline}</td>
				                <td class="mx-num text-center">${purchase.product_count}건</td>
				                <td class="mx-num text-center">${purchase.total_quantity}개</td>
				                <td>
									<c:choose>
									    <c:when test="${purchase.stock_status == 'done'}">
									        <span class="mx-badge mx-badge-done">입고 완료</span>
									    </c:when>
									    <c:when test="${purchase.stock_status == 'delay'}">
									        <span class="mx-badge mx-badge-delay">입고 지연</span>
									    </c:when>
									    <c:otherwise>
									        <span class="mx-badge mx-badge-wait">입고 대기</span>
									    </c:otherwise>
									</c:choose>
				                </td>
				                <td>
								    <button type="button"
								            class="btn btn-sm btn-outline-dark btnQuotation"
								            data-purnum="${purchase.purnum}">
								        <i class="bi bi-file-earmark-text"></i> 견적서
								    </button>

								    <c:if test="${purchase.instock != '입고'}">
								        <%-- 반복문 안의 버튼이라 id 대신 class(btnInstockUpdate)로 JS 와 연결 --%>
								        <button type="button"
								                class="btn btn-sm btn-secondary btnInstockUpdate"
								                data-purnum="${purchase.purnum}"
								                data-instock="${purchase.instock}">
								            <i class="bi bi-box-arrow-in-down"></i> 입고처리
								        </button>
								    </c:if>
								</td>
				            </tr>
				        </c:forEach>
				    </c:if>
                </tbody>

            </table>

        </div>

    </div>
    
        <!-- ==================== 페이징 ==================== -->

		<nav class="my-2">
		    <div id="pageBar" style="margin-bottom:150px;">
		        <ul class="pagination">
		            ${requestScope.pageBar}
		        </ul>
		    </div>
		</nav>
		
</div>

<!-- 견적서 모달  -->
<div class="modal fade" id="quotationModal" tabindex="-1">

    <div class="modal-dialog modal-xl modal-dialog-scrollable">

        <div class="modal-content">

            <div class="modal-header">

                <h5 class="modal-title">
                    <span class="mx-modal-head-icon"><i class="bi bi-file-earmark-text"></i></span>
                    견적서
                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                </button>

            </div>

            <div class="modal-body" id="quotationModalBody">

                <!-- AJAX로 견적서 내용 삽입 -->

            </div>

            <div class="modal-footer">

                <button type="button"
                        class="btn btn-outline-dark"
                        id="btnQuotationPrint">
                    <i class="bi bi-printer"></i> 인쇄/PDF 저장
                </button>

                <button type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">
                    닫기
                </button>

            </div>

        </div>

    </div>

</div>

<jsp:include page="../adminFooter.jsp" />
