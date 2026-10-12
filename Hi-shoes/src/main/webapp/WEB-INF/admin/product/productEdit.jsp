<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctx_Path = request.getContextPath();
%>

<style type="text/css">
	table#tblProdInput {border: solid gray 0	px; 
	                    border-collapse: collapse; }
	                    
    table#tblProdInput td {border: solid gray 0px; 
	                       padding-left: 10px;
	                       height: 50px; }
	                       
    .prodInputName {background-color: #e6fff2; 
                    font-weight: bold; }	                       	                    
	
	.error {color: red; font-weight: bold; font-size: 9pt;}
	
	div.fileDrop{ display: inline-block; 
                  width: 100%; 
                  height: 100px;
                  overflow: auto;
                  background-color: #fff;
                  padding-left: 10px;}
                 
    div.fileDrop > div.fileList > span.delete{display:inline-block; width: 20px; border: solid 1px gray; text-align: center;} 
    div.fileDrop > div.fileList > span.delete:hover{background-color: #000; color: #fff; cursor: pointer;}
    div.fileDrop > div.fileList > span.fileName{padding-left: 10px;}
    div.fileDrop > div.fileList > span.fileSize{padding-right: 20px; float:right;} 
    span.clear{clear: both;} 
   
</style>

<jsp:include page="../adminHeader.jsp" />  
<script type="text/javascript">
  
  let total_fileSize = 0; // 첨부한 파일의 총량을 누적하는 용도 

  $(function(){
	  
	   
	  $('input:button[id="btnUpdate"]').on('click', function() {

		    const pnum = $('input[name="pnum"]').val();
		    const pcontent = $('textarea[name="pcontent"]').val().trim();
		    const deliveryfee = $('input[name="deliveryfee"]').val().trim();

		    if (pcontent === "") {
		        alert("제품설명을 입력해주세요.");
		        $('textarea[name="pcontent"]').focus();
		        return;
		    }

		    if (deliveryfee === "" || !/^[0-9]+$/.test(deliveryfee)) {
		        alert("배송비는 숫자로 입력해주세요.");
		        $('input[name="deliveryfee"]').focus();
		        return;
		    }

		    $.ajax({
		        url: "<%= ctx_Path%>/admin/product/productUpdate.go",
		        type: "post",
		        data: {
		            "pnum": pnum,
		            "pcontent": pcontent,
		            "deliveryfee": deliveryfee
		        },
		        dataType: "json",
		        success: function(json) {
		            if (json.result == 1) {
		                alert("제품 수정이 완료되었습니다.");
		                location.href ="<%= ctx_Path%>/admin/product/productList.go";
		            } else {
		                alert("제품 수정에 실패했습니다.");
		            }
		        },
		        error: function(request, status, error){
  				    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
  		         }
		    });
		});
	  
	  
  });// end of $(function(){})-----------------------



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
            상품수정
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
                    상품수정
                </li>

            </ol>

        </nav>

    </div>

<div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">
	<%-- !!!!! ==== 중요 ==== !!!!! --%>
	<%-- 폼에서 파일을 업로드 하려면 반드시 method 는 POST 이어야 하고 
	     enctype="multipart/form-data" 으로 지정해주어야 한다.!! --%>
	<form name="prodInputFrm"
          id="prodInputFrm"
          enctype="multipart/form-data"
          method="post">

        <%-- 제품번호 --%>
        <input type="hidden"
               name="pnum"
               value="${requestScope.pdto.pnum}">

        <table id="tblProdInput"
               style="width: 80%; border-collapse: separate; border-spacing: 0 10px;">

            <tbody>

                <%-- 제품명 --%>
                <tr>
                    <td width="25%" class="prodInputName">제품명</td>

                    <td width="75%" align="left"
                        style="border-top: hidden; border-bottom: hidden;">

                        <input type="text"
                               style="width: 300px;"
                               name="pname"
                               class="box infoData"
                               value="${requestScope.pdto.fk_pname}"
                               readonly />

                        <%-- 제품명 선택 기능은 기존 등록 페이지의 모달을 사용한다면 연결 가능 --%>
                    </td>
                </tr>

                <%-- 기존 이미지 1 --%>
				<tr>
				    <td class="prodInputName">제품 이미지 1</td>
				    <td>
				        <c:choose>
				            <c:when test="${not empty requestScope.pdto.pimage1}">
				                ${requestScope.pdto.pimage1}
				            </c:when>
				            <c:otherwise>등록된 이미지 없음</c:otherwise>
				        </c:choose>
				    </td>
				</tr>
				
				<%-- 기존 이미지 2 --%>
				<tr>
				    <td class="prodInputName">제품 이미지 2</td>
				    <td>
				        <c:choose>
				            <c:when test="${not empty requestScope.pdto.pimage2}">
				                ${requestScope.pdto.pimage2}
				            </c:when>
				            <c:otherwise>등록된 이미지 없음</c:otherwise>
				        </c:choose>
				    </td>
				</tr>

                <%-- 제품설명서 --%>
				<tr>
				    <td class="prodInputName">제품설명서</td>
				    <td>
				        <c:choose>
				            <c:when test="${not empty requestScope.pdto.warranty_originfilename}">
				                ${requestScope.pdto.warranty_originfilename}
				            </c:when>
				            <c:otherwise>등록된 파일 없음</c:otherwise>
				        </c:choose>
				    </td>
				</tr>

                <%-- 제품 설명 --%>
                <tr>
                    <td width="25%" class="prodInputName">제품설명</td>

                    <td width="75%" align="left"
                        style="border-top: hidden; border-bottom: hidden;">

                        <textarea name="pcontent"
                                  rows="10"
                                  cols="60"
                                  style="width: 600px; height: 300px;">${requestScope.pdto.pcontent}</textarea>

                    </td>
                </tr>

                <%-- 배송비 --%>
                <tr>
                    <td width="25%" class="prodInputName"
                        style="padding-bottom: 10px;">
                        배송비
                    </td>

                    <td width="75%" align="left"
                        style="border-top: hidden; border-bottom: hidden; padding-bottom: 10px;">

                        <input type="text"
                               style="width: 100px;"
                               name="deliveryfee"
                               class="box infoData positiveNumber"
                               value="${requestScope.pdto.deliveryfee}" />

                        원

                    </td>
                </tr>

                <%-- 추가 이미지 --%>
				<tr>
				    <td class="prodInputName">추가 이미지</td>
				    <td>
				        <c:choose>
				            <c:when test="${not empty requestScope.imageList}">
				                <c:forEach var="img" items="${requestScope.imageList}">
				                    <div>${img}</div>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>등록된 추가 이미지 없음</c:otherwise>
				        </c:choose>
				    </td>
				</tr>

                <%-- 버튼 --%>
                <tr style="height: 70px;">
                    <td colspan="2" align="center"
                        style="border-left: hidden; border-bottom: hidden;
                               border-right: hidden; padding: 50px 0;">

                        <input type="button"
                               value="제품수정"
                               id="btnUpdate"
                               style="width: 120px;"
                               class="btn btn-success btn-lg me-5" />

                        <input type="button"
                               value="취소"
                               style="width: 120px;"
                               class="btn btn-danger btn-lg"
                               onclick="history.back();" />

                    </td>
                </tr>

            </tbody>
        </table>

    </form>
	
</div>

</div>

	<!-- 제품 검색 모달 -->
<div class="modal fade"
     id="productModal"
     tabindex="-1"
     aria-labelledby="productModalLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">

        <div class="modal-content">

            <!-- 모달 헤더 -->
            <div class="modal-header">

                <h5 class="modal-title" id="productModalLabel">
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
                           id="productSearchWord"
                           class="form-control"
                           placeholder="제품명을 입력하세요">

                    <button type="button"
                            class="btn btn-primary"
                            id="btnproductSearchSubmit">
                        검색
                    </button>

                </div>


                <!-- 제품 조회 결과 -->
                <table class="table table-bordered"
                       id="productTbl">

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

                    <tbody id="productNamebody">

                        <!-- JS로 검색 결과 생성 -->

                    </tbody>

                </table>
                
                <!-- 페이지 바  -->
                <nav class="my-3">
                <ul class="pagination justify-content-center" id="productPageBar">
                </ul>
            </nav>

            </div>


            <!-- 모달 푸터 -->
            <div class="modal-footer">

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
    