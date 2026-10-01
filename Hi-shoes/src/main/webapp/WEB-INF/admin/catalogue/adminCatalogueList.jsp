<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

<%
    String ctx_Path = request.getContextPath();
%>
    
<jsp:include page="../adminHeader.jsp" />


<script type="text/javascript">

	$(function(){

	});  // end of $(function(){})----------------------------


   // Function Declaration
   function goSearch(){

	  const productName = $('input:text[name="pname"]').val().trim();
	  const categoryName = $('input:text[name="cname"]').val().trim();
	  const brandName = $('input:text[name="brand"]').val().trim();
    
      // 세 입력창이 모두 비어있는 경우
      if (productName === "" && categoryName === "" && brandName === "") {
          alert("검색어를 입력해주세요.");
          return;
      }
	  
	  // 밑에 부분은 비동기처리 ajax 사용
	  $.ajax({
		url: `<%= ctx_Path%>/admin/catalogue/catalogueList.go`,
		data: {
			   "productName": productName, 
               "categoryName": categoryName,
               "brandName": brandName,
               "currentShowPageNo": "1",
               "isAjax": "1"
              },
               
        method : "get",
               
        async : true,   // 비동기
        
        dataType : "json",
        
        success : function(json){

            console.log("받은 JSON:", json);
            console.log("검색 결과 개수:", json.catalogue_map_List.length);
            console.log("첫 번째 데이터:", json.catalogue_map_List[0]);

        	let html = "";

        	// 검색 결과가 없는 경우
        	if (json.catalogue_map_List.length === 0) {

        	    html += `
        	        <tr>
        	            <td colspan="7" class="text-center align-middle">
        	                <span style="color: red; font-weight: bold;">
        	                    상품내역이 없습니다.
        	                </span>
        	            </td>
        	        </tr>
        	    `;

        	}
        	else {

        	    // 검색 결과가 있는 경우
        	    $.each(json.catalogue_map_List, function(index, item) {

        	        html += `
        	            <tr>
        	                <td class="text-center align-middle">
        	                    <button type="button"
        	                            class="btn btn-outline-secondary">
        	                        수정
        	                    </button>

        	                    <button type="button"
        	                            class="btn btn-outline-danger">
        	                        삭제
        	                    </button>
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.pname}
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.catename}
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.purprice}
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.regprice}
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.saleprice}
        	                </td>

        	                <td class="text-center align-middle">
        	                    ${item.brand}
        	                </td>
        	            </tr>
        	        `;
        	    });
        	}

        	// 기존 tbody 내용을 AJAX 검색 결과로 교체
        	$("#catalogueTbl tbody").html(html);


        	// 검색 결과에 맞는 페이지 수 확인용
        	console.log("전체 검색 결과 개수 : " + json.totalCountCatalogue);
        	console.log("현재 페이지 : " + json.currentShowPageNo);
        	console.log("전체 페이지 수 : " + json.totalPage);

        },
        
        error: function(request, status, error){

            alert(
                "code: " + request.status +
                "\nmessage: " + request.responseText +
                "\nerror: " + error
            );
            
        }
	  });  

   }
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
            카탈로그 목록
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
                    카탈로그 목록
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
		<div class="container" style="padding: 2% 0 ;">
			<form name="catalogue_search_frm">
				<div class="form-section mb-4">
					<label for="e1">제품명</label>
					<input type="text"
					       id="e1"
					       name="pname"
					       maxlength="100"
					       style="width: 400px;"/>
				</div>
				
				<div class="form-section">
					<label for="e2">카테고리명</label>
					<input type="text"
					       id="e2"
					       name="cname"
					       maxlength="100"
					       style="width: 400px;"/>
				</div>	
				
				<div class="form-section">
					<label for="e3">브랜드</label>
					<input type="text"
					       id="e3"
					       name="brand"
					       maxlength="100"
					       style="width: 400px;"/>
				</div>	
				
				<button type="button"
				        class="btn btn-secondary"
				        onclick="goSearch()">
				    검색
				</button>
			</form>
		</div>
		
    </div>


    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                border-radius: 6px;
                padding: 25px;">

        <!-- 실제 관리자 페이지 내용 -->
	
		<table class="table table-bordered"
		       id="catalogueTbl">

		    <colgroup>
		        <col style="width: 15%;">
		        <col style="width: 20%;">
		        <col style="width: 15%;">
		        <col style="width: 12%;">
		        <col style="width: 12%;">
		        <col style="width: 12%;">
		        <col style="width: 14%;">
		    </colgroup>

		    <thead>
		        <tr>
		            <th class="text-center align-middle">
		                작업
		            </th>

		            <th class="text-center align-middle">
		                제품명
		            </th>

		            <th class="text-center align-middle">
		                카테고리명
		            </th>

		            <th class="text-center align-middle">
		                구매가
		            </th>

		            <th class="text-center align-middle">
		                정가
		            </th>

		            <th class="text-center align-middle">
		                판매가
		            </th>

		            <th class="text-center align-middle">
		                브랜드
		            </th>
		        </tr>
		    </thead>
		
		    <tbody>
		
		        <c:if test="${empty requestScope.catalogue_map_List}">
		            <tr>
		                <td colspan="7"
		                    class="text-center align-middle">

		                    <span style="color: red; font-weight: bold;">
		                        상품내역이 없습니다.
		                    </span>

		                </td>
		            </tr>
		        </c:if>
		
		        <c:if test="${not empty requestScope.catalogue_map_List}">

		            <c:forEach var="catalmap"
		                       items="${requestScope.catalogue_map_List}"
		                       varStatus="status">
		
		                <tr>

		                    <td class="text-center align-middle">

		                        <button type="button"
		                                class="btn btn-outline-secondary">
		                            수정
		                        </button>
		
		                        <button type="button"
		                                class="btn btn-outline-danger">
		                            삭제
		                        </button>

		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.pname}
		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.catename}
		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.purprice}
		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.regprice}
		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.saleprice}
		                    </td>
		
		                    <td class="text-center align-middle">
		                        ${catalmap.brand}
		                    </td>

		                </tr>
		
		            </c:forEach>

		        </c:if>
		
		    </tbody>

		</table>
		
	 

	</div>


	<%-- === 페이지바 === --%>
	<nav class="my-2">

       <div style="display:flex;
                   width:80%;
                   margin:0 auto;
                   margin-bottom:150px;">

   	     <ul class="pagination"
   	         style="margin:auto;">

   	         ${requestScope.pageBar}

   	     </ul>

   	   </div>

	</nav> 
	

</div>


<jsp:include page="../adminFooter.jsp" />
