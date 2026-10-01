<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
    String ctx_Path = request.getContextPath();
%>
    
<jsp:include page="../adminHeader.jsp" />



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
		<p>카탈로그 리스트 조회용</p>
    </div>

</div>


<jsp:include page="../adminFooter.jsp" />