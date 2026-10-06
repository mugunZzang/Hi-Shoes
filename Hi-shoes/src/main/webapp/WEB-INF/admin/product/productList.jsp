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
            상품목록
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
                    상품목록
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
	         
	   <form name="productSearchFrm"
          action="productList.go"
          method="get"
          class="d-flex">

        <input type="text"
               name="searchWord"
               value="${requestScope.searchWord}"
               class="form-control"
               style="width:250px;"
               placeholder="상품명을 입력하세요">

        <button type="submit"
                class="btn btn-secondary ms-2">
            검색
        </button>

    	   </form>       
	   <table class="table table-hover">

            <thead>
                <tr class="table-light">
                    <th style="width:10%; text-align:center;">상품번호</th>
                    <th style="width:25%; text-align:center;">상품명</th>
                    <th style="width:40%; text-align:center;">상품내용</th>
                    <th style="width:15%; text-align:center;">배송비</th>
                </tr>
            </thead>

            <tbody>

                <c:if test="${not empty requestScope.productList}">

                    <c:forEach var="product"
                               items="${requestScope.productList}"
                               varStatus="status">

                        <tr>

                            <td align="center">
                                ${product.pnum}
                            </td>

                            <td>
                                ${product.fk_pname}
                            </td>

                            <td>
                                ${product.pcontent}
                            </td>

                            <td align="center">
                                ${product.deliveryfee}원
                            </td>

                        </tr>

                    </c:forEach>

                </c:if>


                <c:if test="${empty requestScope.productList}">

                    <tr>
                        <td colspan="4"
                            style="text-align:center;
                                   height:200px;
                                   vertical-align:middle;">
                            상품이 없습니다.
                        </td>
                    </tr>

                </c:if>

            </tbody>

        </table>


        <!-- 페이징 -->
        <nav class="my-5">

            <div style="display:flex;
                        width:80%;
                        margin:0 auto;">

                <ul class="pagination"
                    style="margin:auto;">

                    ${requestScope.productPageBar}

                </ul>

            </div>

        </nav>
	                
	</div>





</div>


<jsp:include page="../adminFooter.jsp"/>

