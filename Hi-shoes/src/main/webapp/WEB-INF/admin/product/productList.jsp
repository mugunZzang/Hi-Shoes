<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            상품등록
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    상품등록
                </li>

            </ol>

        </nav>

    </div>

	<div style="background-color: #ffffff;
	                width: 100%;
	                min-height: 500px;
	                border: 1px solid #eeeeee;
	                padding: 25px;">
	                
	                <!-- 내용 들어갈 자리 -->
	</div>





</div>


<jsp:include page="../adminFooter.jsp"/>

