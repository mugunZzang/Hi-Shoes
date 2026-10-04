<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

<%
    String ctx_Path = request.getContextPath();
%>
    
<jsp:include page="../adminHeader.jsp" />

<script type="text/javascript">
	const ctx_Path = "<%= ctx_Path %>";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/supplier/supplier.js"></script>

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
            공급업체 목록
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
                        공급관리
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    공급업체 목록
                </li>

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

        <!-- 실제 관리자 페이지 내용 -->
    
	<div>
    	<button type="button"
				class="btn btn-secondary"
				id="supplyRegisterModal">
				    공급업체 등록
		</button>

    </div>



        <!-- 실제 관리자 페이지 내용 -->
	
		<table class="table table-bordered"
		       id="catalogueTbl">

		    <colgroup>
		        <col style="width: 20%;">
		        <col style="width: 20%;">
		        <col style="width: 20%;">
		        <col style="width: 20%;">
		        <col style="width: 20%;">
		    </colgroup>

		    <thead>
		        <tr>
		            <th class="text-center align-middle">
		                업체명
		            </th>

		            <th class="text-center align-middle">
		                사업자등록번호
		            </th>

		            <th class="text-center align-middle">
		                대표명
		            </th>

		            <th class="text-center align-middle">
		                연락처
		            </th>

		            <th class="text-center align-middle">
		                회사이메일
		            </th>

		        </tr>
		    </thead>
		
		    <tbody>
				
		    </tbody>

		</table>
		
	 
</div>
	<%-- === 페이지바 === --%>
	<nav class="my-2">

       <div style="display:flex;
                   width:80%;
                   margin:0 auto;
                   margin-bottom:150px;">

   	     <ul class="pagination" style="margin:auto;" id="pageBar">
   	         ${requestScope.pageBar}
   	     </ul>

   	   </div>

	</nav> 

</div>




<!-- 공급업체 등록 모달 -->
<div class="modal fade" id="supplyModal" tabindex="-1" aria-labelledby="supplyModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="supplyModalLabel">공급업체 등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <div class="modal-body">
        <form id="supplyForm" novalidate>

          <div class="mb-3">
            <label for="supplyName" class="form-label">업체명 <span class="text-danger">*</span></label>
            <input type="text" id="supplyName" class="form-control" maxlength="100"
                   placeholder="한글/영어/특수문자 ( ) 만 가능 (최대 100자)">
            <div class="invalid-feedback">업체명은 한글, 영어, 특수문자 ( ) 만 입력 가능하며 최대 100자입니다.</div>
          </div>

          <div class="mb-3">
            <label for="bizNo" class="form-label">사업자등록번호 <span class="text-danger">*</span></label>
            <input type="text" id="bizNo" class="form-control" maxlength="10" inputmode="numeric"
                   placeholder="숫자 10자리 (- 제외)">
            <div class="invalid-feedback">사업자등록번호는 숫자 10자리로 입력해주세요.</div>
          </div>

          <div class="mb-3">
            <label for="ceoName" class="form-label">대표명 <span class="text-danger">*</span></label>
            <input type="text" id="ceoName" class="form-control" maxlength="10"
                   placeholder="한글/영어 (최대 10자)">
            <div class="invalid-feedback">대표명은 한글, 영어만 입력 가능하며 최대 10자입니다.</div>
          </div>

          <div class="mb-3">
            <label for="supplyTel" class="form-label">연락처 <span class="text-danger">*</span></label>
            <input type="text" id="supplyTel" class="form-control" maxlength="11" inputmode="numeric"
                   placeholder="숫자만 입력 (- 제외)">
            <div class="invalid-feedback">연락처는 숫자만 입력해주세요. (9~11자리)</div>
          </div>

          <div class="mb-3">
            <label for="supplyEmail" class="form-label">회사 이메일 <span class="text-danger">*</span></label>
            <input type="email" id="supplyEmail" class="form-control" maxlength="100"
                   placeholder="example@company.com">
            <div class="invalid-feedback">올바른 이메일 형식이 아닙니다.</div>
          </div>

        </form>
      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" id="btnSupplySave">등록</button>
      </div>
    </div>
  </div>
</div>

<jsp:include page="../adminFooter.jsp" />
