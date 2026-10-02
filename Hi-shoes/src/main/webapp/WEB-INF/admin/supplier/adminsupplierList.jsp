<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

<%
    String ctx_Path = request.getContextPath();
%>
    
<jsp:include page="../adminHeader.jsp" />


<script type="text/javascript">

</script>


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
				onclick="location.href='<%= ctx_Path %>/admin/catalogueRegister.go'">
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



<jsp:include page="../adminFooter.jsp" />
