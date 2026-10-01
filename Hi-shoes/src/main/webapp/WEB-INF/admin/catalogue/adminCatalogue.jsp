<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    String ctx_Path = request.getContextPath();
%>

<jsp:include page="../adminHeader.jsp" />
<script>
    const ctx_Path = "<%= ctx_Path %>";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/catalogue/catalogueRegist.js"></script>


<style type="text/css">
	.form-section {
	    border: 1px solid #dee2e6;
	    margin-bottom: 25px;
	}
	
	.form-section-title {
	    background-color: #f7faf8;
	    border-bottom: 1px solid #dee2e6;
	    padding: 12px 15px;
	    font-size: 14pt;
	    font-weight: 600;
	    color: #343a40;
	}
	
	.form-row {
	    margin: 0;
	    min-height: 55px;
	    border-bottom: 1px solid #eeeeee;
	}
	
	.form-row:last-child {
	    border-bottom: none;
	}
	
	.form-label {
	    margin: 0;
	    padding: 15px;
	    background-color: #fafafa;
	    border-right: 1px solid #eeeeee;
	    font-weight: 400;
	    color: #495057;
	}
	
	.form-row > div {
	    padding: 10px 15px;
	}
	
	.form-control,
	.form-select {
	    border-radius: 4px;
	    font-size: 12pt;
	}
	
	.form-control:focus,
	.form-select:focus {
	    box-shadow: none;
	}
	
	.required {
	    color: #dc3545;
	    margin-left: 2px;
	}
	
	.price-input {
	    width: 200px !important;
	    flex: 0 0 200px !important;
	    text-align: left;
	}

	.input-group-text {
	    background-color: #f8f9fa;
	    color: #6c757d;
	}
	
	.error,
	.positiveNumber_error,
	.positiveBrand_error {
	    display: block;
	    margin-top: 4px;
	    font-size: 12px;
	    color: #dc3545;
	}
	
	.form-buttons {
	    display: flex;
	    justify-content: flex-end;
	    gap: 6px;
	    padding-top: 5px;
	}
	
	.form-buttons .btn {
	    min-width: 80px;
	    border-radius: 4px;
	}
	
	.pname-input-group {
    display: flex;
    align-items: center;
    gap: 6px;
	}
	
	#pnameDuplicateResult {
	    display: block;
	    margin-top: 4px;
	    font-size: 12px;
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

        <p class="mb-0 fs-4 fw-semibold">
            카탈로그 등록
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="<%= ctx_Path %>/admin/adminMain.go"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>
                
                <!-- 이 부분은 아마 발주 목록 조회로 가야하지 않나 생각이 든다. -->
                <li class="breadcrumb-item">
                    <a href="#"    
                       class="text-decoration-none">
                        발주관리
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    카탈로그 등록
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

        <!-- 실제 관리자 페이지 내용 -->
		<form name="catalogue" action="" method="post">
		
		    <div class="form-section">
		        <div class="form-section-title">
		            기본 정보
		        </div>
		
			<div class="row form-row">
			    <label for="e1" class="col-sm-2 form-label">
			        제품명 <span class="required">*</span>
			    </label>
			
			    <div class="col-sm-10">
			
			        <div class="pname-input-group">
			            <input type="text"
			                   id="e1"
			                   class="form-control infoData"
			                   name="pname"
			                   maxlength="100"
			                   style="width: 400px;">
			
			            <input type="button"
			                   id="btnPnameDuplicate"
			                   class="btn btn-secondary"
			                   value="중복체크"
			                   style="margin-left: 30px;">
			        </div>
			
			        <span class="error" style="display: none;">
			            제품명은 필수입력 사항입니다.
			        </span>
			
			        <span id="pnameDuplicateResult"></span>
			
			    </div>
			</div>
		
		        <div class="row form-row">
		            <label for="e2" class="col-sm-2 form-label">
		                카테고리 <span class="required">*</span>
		            </label>
		
		            <div class="col-sm-10">
		                <select name="fk_catenum"
		                        class="form-select infoData"
		                        id="e2"
		                        style="width: 200px;">
		
		                    <option value="">선택하세요.</option>
		
		                    <c:forEach var="catedto"
		                               items="${requestScope.categoryList}">
		
		                        <option value="${catedto.cateNum}">
		                            ${catedto.cateName}
		                        </option>
		
		                    </c:forEach>
		
		                </select>
		
		                <span class="error" style="display: none">
		                    카테고리는 필수 선택사항입니다.
		                </span>
		            </div>
		        </div>
		
		        <div class="row form-row">
		            <label for="e3" class="col-sm-2 form-label">
		                브랜드 <span class="required">*</span>
		            </label>
		
		            <div class="col-sm-10">
		                <input type="text"
		                       class="form-control infoData"
		                       name="brand"
		                       maxlength="20"
		                       style="width: 100px;"
		                       id="e3">
		
		                <span class="error" style="display: none">
		                    브랜드는 필수입력 사항입니다.
		                </span>
		
		                <span class="positiveBrand_error"></span>
		            </div>
		        </div>
		    </div>
		
		
		    <div class="form-section">
		
		        <div class="form-section-title">
		            가격 정보
		        </div>
		
		        <div class="row form-row">
		            <label for="e4" class="col-sm-2 form-label">
		                구매가 <span class="required">*</span>
		            </label>
		
		            <div class="col-sm-10">
		                <div class="input-group">
		                    <input type="text"
		                           class="form-control infoData positiveNumber price-input"
		                           name="purprice"
		                           maxlength="9"
		                           id="e4">
		
		                    <span class="input-group-text">원</span>
		                </div>
		
		                <span class="error" style="display: none">
		                    구매가는 필수입력 사항입니다.
		                </span>
		
		                <span class="positiveNumber_error"></span>
		            </div>
		        </div>
		
		        <div class="row form-row">
		            <label for="e5" class="col-sm-2 form-label">
		                정가 <span class="required">*</span>
		            </label>
		
		            <div class="col-sm-10">
		                <div class="input-group">
		                    <input type="text"
		                           class="form-control infoData positiveNumber price-input"
		                           name="regprice"
		                           maxlength="9"
		                           id="e5">
		
		                    <span class="input-group-text">원</span>
		                </div>
		
		                <span class="error" style="display: none">
		                    정가는 필수입력 사항입니다.
		                </span>
		
		                <span class="positiveNumber_error"></span>
		            </div>
		        </div>
		
		        <div class="row form-row">
		            <label for="e6" class="col-sm-2 form-label">
		                판매가 <span class="required">*</span>
		            </label>
		
		            <div class="col-sm-10">
		                <div class="input-group">
		                    <input type="text"
		                           class="form-control infoData positiveNumber price-input"
		                           name="saleprice"
		                           maxlength="9"
		                           id="e6">
		
		                    <span class="input-group-text">원</span>
		                </div>
		
		                <span class="error" style="display: none">
		                    판매가는 필수입력 사항입니다.
		                </span>
		
		                <span class="positiveNumber_error"></span>
		            </div>
		        </div>
		
		    </div>
		

			<div class="form-buttons">
			    <input type="button"
			           id="btnRegister"
			           class="btn btn-success"
			           value="등록">
			</div>
		
		</form>		

    </div>

</div>


<jsp:include page="../adminFooter.jsp" />