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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/purchase/purchaseRegister.js"></script>

<style type="text/css">

	/* 공통 CSS 확정 전까지 유지 (mx-required 와 함께 사용) */
	.required {
	    color: #dc3545;
	    margin-left: 2px;
	}

	/* ===== 사이즈 칩 (이 페이지 전용 / 나중에 mxExtra.css 로 옮겨도 됨) ===== */
	#sizeGroup .mx-chip {
	    position: relative;
	    display: inline-flex;
	    align-items: center;
	    justify-content: center;
	    min-width: 58px;
	    margin: 0;
	    padding: 6px 12px;
	    border: 1px solid #ced4da;
	    border-radius: 20px;
	    background: #ffffff;
	    color: #495057;
	    font-size: 12pt;
	    cursor: pointer;
	    user-select: none;
	    transition: background-color 0.15s, border-color 0.15s, color 0.15s;
	}
	#sizeGroup .mx-chip:hover {
	    border-color: #336600;
	    color: #336600;
	}
	#sizeGroup .mx-chip input {          /* 체크박스는 숨기고 칩 전체를 클릭 영역으로 */
	    position: absolute;
	    opacity: 0;
	    width: 0;
	    height: 0;
	}
	#sizeGroup .mx-chip.is-selected {
	    background: #336600;
	    border-color: #336600;
	    color: #ffffff;
	}
	#sizeGroup .mx-chip:focus-within {   /* 키보드로 이동할 때 표시 */
	    outline: 2px solid #336600;
	    outline-offset: 2px;
	}

	/* 제품 검색 모달: 클릭 가능한 행 강조 */
	#catalogueTbl tbody tr.catalogue-item:hover td {
	    background-color: #f2f7ef;
	}
	
</style>

<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold"> </span>
     	   발주 등록
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="<%= ctx_Path %>/admin/adminMain.go" class="text-decoration-none">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#" class="text-decoration-none">발주관리</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">발주 등록</li>
            </ol>
        </nav>
    </div>


    <!-- 실제 페이지 작업 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 50px;
                border: 1px solid #eeeeee;
                border-radius: 6px;
                padding: 25px;
                margin-bottom: 50px;">

        <!-- 이용 안내 -->
        <div class="mx-notice mb-4">
            <i class="bi bi-info-circle"></i>
            업체와 제품을 선택하고 사이즈, 색상, 수량을 입력한 뒤 <strong>추가하기</strong>를 누르세요.
            목록을 확인하고 <strong>발주하기</strong>를 누르면 발주가 등록되고 견적서가 만들어집니다.
        </div>

        <!-- ===== 1. 발주할 상품 등록 폼 ===== -->
        <p class="mx-section-title mb-3">발주할 상품 등록</p>

        <form id="purchaseItemForm">

            <div class="row mb-3 align-items-center">
                <label for="supplierSelect" class="col-sm-2 col-form-label fw-semibold">업체명<span class="required mx-required">*</span></label>
                <div class="col-sm-5">
                    <select id="supplierSelect" class="form-select infoData">
                        <option value="">공급업체를 선택하세요</option>
                            <c:forEach var="sdto" items="${requestScope.supplierList}">
						        <option value="${sdto.supname}"><c:out value="${sdto.supname}"/></option>
						    </c:forEach>
                    </select>
                </div>
            </div>

			<div class="row mb-3 align-items-center">
			    <label for="catalogueName" class="col-sm-2 col-form-label fw-semibold">
			        제품명<span class="required mx-required">*</span>
			    </label>
			    <div class="col-sm-5">
			        <div class="input-group">
			
			            <input type="text"
			                   id="catalogueName"
			                   class="form-control infoData"
			                   placeholder="제품명을 선택하세요"
			                   readonly>
			
			            <button type="button"
			                    class="btn btn-outline-secondary"
			                    id="btncatalogueSearch">
			                <i class="bi bi-search"></i> 제품 찾기
			            </button>
			
			        </div>
			        <div class="mx-hint mt-1">제품명은 직접 입력할 수 없습니다. '제품 찾기'로 선택하세요.</div>
			    </div>
			</div>

            <div class="row mb-3 align-items-start">
                <label class="col-sm-2 col-form-label fw-semibold">사이즈<span class="required mx-required">*</span></label>
                <div class="col-sm-10">
					<div id="sizeGroup" style="display: flex; flex-wrap: wrap; gap: 12px; width: 420px;">		
				        <!-- JS로 200~300 생성 -->
				    </div>
				    <div class="mx-hint mt-2">발주할 사이즈를 선택하세요.</div>
				</div>
            </div>

            <div class="row mb-3 align-items-center">
                <label for="colorSelect" class="col-sm-2 col-form-label fw-semibold ">색상<span class="required mx-required">*</span></label>
                <div class="col-sm-5">
		        <select id="colorSelect" class="form-select infoData">
		            <option value="">색상을 선택하세요</option>
		            <option value="BLACK">검정</option>
		            <option value="WHITE">흰색</option>
		            <option value="SILVER">실버</option>
		            <option value="BROWN">갈색</option>
		            <option value="BLUE">파랑</option>
		            <option value="YELLOW">노랑</option>
		            <option value="RED">빨강</option>
		            <option value="IVORY">아이보리</option>
		        </select>
                </div>
            </div>

            <div class="row mb-3 align-items-center">
                <label for="purchaseQty" class="col-sm-2 col-form-label fw-semibold">수량<span class="required mx-required">*</span></label>
                <div class="col-sm-5">
                    <input type="text" id="purchaseQty" class="form-control infoData"
                           maxlength="5" inputmode="numeric" placeholder="숫자만 입력">
                    <div class="invalid-feedback">수량은 1 이상의 숫자로 입력해주세요.</div>
                </div>
            </div>

            <div class="text-end">
                <button type="button" class="btn btn-secondary" id="btnAddItem">
                    <i class="bi bi-plus-lg"></i> 추가하기
                </button>
            </div>

        </form>

        <hr class="my-4">

        <!-- ===== 2. 발주할 상품 목록 ===== -->
        <p class="mx-section-title mb-3">발주할 상품 목록</p>

        <div class="mx-table-wrap">
        <table class="table table-bordered" id="purchaseItemTbl">

            <colgroup>
                <col style="width: 20%;">
                <col style="width: 25%;">
                <col style="width: 10%;">
                <col style="width: 10%;">
                <col style="width: 10%;">
                <col style="width: 15%;">
                <col style="width: 10%;">
            </colgroup>

            <thead>
                <tr>
                    <th class="text-center align-middle">업체명</th>
                    <th class="text-center align-middle">제품명</th>
                    <th class="text-center align-middle">사이즈</th>
                    <th class="text-center align-middle">색상</th>
                    <th class="text-center align-middle">수량</th>
                    <th class="text-center align-middle">금액</th>
                    <th class="text-center align-middle">삭제</th>
                </tr>
            </thead>

            <tbody>
                <tr id="emptyRow">
                    <td colspan="7" class="text-center align-middle">
                        <div class="mx-empty">
                            <i class="bi bi-inbox"></i>
                            <p>추가된 발주 상품이 없습니다.</p>
                        </div>
                    </td>
                </tr>
            </tbody>

        </table>
        </div>

        <!-- ===== 3. 결제 금액 + 발주 버튼 ===== -->
        <div class="d-flex justify-content-end align-items-center gap-3 mt-4">

            <div class="mx-total-box">
                <span class="fw-semibold">결제 금액</span>
                <span class="fw-bold text-danger fs-5" id="totalAmount">0</span>원
            </div>

            <button type="button" class="btn btn-secondary" id="btnPurchaseSubmit" disabled>
                <i class="bi bi-check-lg"></i> 발주하기
            </button>
        </div>

    </div>

</div>


<!-- 제품 검색 모달 -->
<div class="modal fade"
     id="catalogueModal"
     tabindex="-1"
     aria-labelledby="catalogueModalLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">

        <div class="modal-content">

            <!-- 모달 헤더 -->
            <div class="modal-header">

                <h5 class="modal-title" id="catalogueModalLabel">
                    <span class="mx-modal-head-icon"><i class="bi bi-search"></i></span>
                    제품 검색
                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close">
                </button>

            </div>


            <!-- 모달 본문 -->
            <div class="modal-body">

                <!-- 제품명 검색 -->
                <div class="input-group mb-3">

                    <input type="text"
                           id="catalogueSearchWord"
                           class="form-control"
                           placeholder="제품명을 입력하세요">

                    <button type="button"
                            class="btn btn-secondary"
                            id="btncatalogueSearchSubmit">
                        <i class="bi bi-search"></i> 검색
                    </button>

                </div>

                <div class="mx-hint mb-2">조회된 제품명을 클릭하면 선택됩니다.</div>

                <!-- 제품 조회 결과 -->
                <table class="table table-bordered"
                       id="catalogueTbl">

                    <colgroup>
                        <col style="width: 100%;">
                    </colgroup>

                    <thead>
                        <tr>
                            <th class="text-center align-middle">
                                제품명
                            </th>
                        </tr>
                    </thead>

                    <tbody>

                        <!-- JS로 검색 결과 생성 -->

                    </tbody>

                </table>
                
                <!-- 페이지 바  -->
                <nav class="my-3">
				    <ul class="pagination justify-content-center" id="cataloguePageBar">
				    </ul>
				</nav>

            </div>


            <!-- 모달 푸터 -->
            <div class="modal-footer">

                <button type="button"
                        class="btn btn-outline-secondary"
                        data-bs-dismiss="modal">
                    닫기
                </button>

            </div>

        </div>

    </div>

</div>

<jsp:include page="../adminFooter.jsp" />
