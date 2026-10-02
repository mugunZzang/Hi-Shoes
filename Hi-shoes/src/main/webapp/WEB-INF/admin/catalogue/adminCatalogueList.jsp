<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    

<%
    String ctx_Path = request.getContextPath();
%>
    
<jsp:include page="../adminHeader.jsp" />


<script type="text/javascript">

	// 마지막으로 검색한 조건 (초기값: 조건 없음)
	let searchCond = { productName: "", categoryName: "", brandName: "" };
	
	$(function(){

		$(document).on("click", "#pageBar a[data-page]", function(e){
		    e.preventDefault();
		    loadList($(this).data("page"));
		});
		

	    //========================================================================================================
		
	    // 수정 버튼 클릭
	    $(document).on("click", ".btn-edit", function(){

	        const row = $(this).closest("tr");

	        // 수정 전 기존 값 가져오기
			const purprice = row.find("td").eq(3).text().trim();
			const regprice = row.find("td").eq(4).text().trim();
			const saleprice = row.find("td").eq(5).text().trim();
			const brand = row.find("td").eq(6).text().trim();
			
			// 화면에 원래 표시되어 있던 값을 그대로 저장
			row.data("original", {
			    purprice: purprice,
			    regprice: regprice,
			    saleprice: saleprice,
			    brand: brand
			});
			
			// input에는 콤마와 원을 제거한 값 사용
			const purpriceInput = purprice.replace(/,/g, "").replace("원", "");
			const regpriceInput = regprice.replace(/,/g, "").replace("원", "");
			const salepriceInput = saleprice.replace(/,/g, "").replace("원", "");
			
	        // 작업 버튼 변경
	        row.find("td").eq(0).html(
	            "<button type='button' class='btn btn-primary btn-save ' disabled>저장</button> " +
	            "<button type='button' class='btn btn-secondary btn-cancel'>취소</button>"
	        );
	        
	        // 구매가
	        row.find("td").eq(3).html(
	            "<input type='text' class='form-control positiveNumber infoData' value='" + purpriceInput + "' maxlength='9'>"
	        );

	        // 정가
	        row.find("td").eq(4).html(
	            "<input type='text' class='form-control positiveNumber infoData' value='" + regpriceInput + "' maxlength='9'>"
	        );

	        // 판매가
	        row.find("td").eq(5).html(
	            "<input type='text' class='form-control positiveNumber infoData' value='" + salepriceInput + "' maxlength='9'>"
	        );

	        // 브랜드
	        row.find("td").eq(6).html(
	            "<input type='text' class='form-control brandInput infoData' value='" + brand + "' maxlength='20'>"
	        );

//	        console.log(row);

	    });
	    

	    //========================================================================================================
	    
	    
	    // 사용자가 값을 이전의 값 이외의 값으로 변경한 경우
	    $(document).on("change", "input.infoData", function(){

//	    	console.log("change 발생!");
	    	// change 발생!
	        const row = $(this).closest("tr");

	        const original = row.data("original");
//	        console.log("original :", original);
	        // original : {purprice: '142000', regprice: '149000', saleprice: '145000', brand: '아디다스'}


	        const purprice = row.find("td").eq(3).find("input").val().trim();
	        const regprice = row.find("td").eq(4).find("input").val().trim();
	        const saleprice = row.find("td").eq(5).find("input").val().trim();
	        const brand = row.find("td").eq(6).find("input").val().trim();

//	        console.log("현재값 :", purprice, regprice, saleprice, brand);
	        // 현재값 : 12000 149000 145000 아디다스
	        
	        const isChanged =
	            purprice !== original.purprice ||
	            regprice !== original.regprice ||
	            saleprice !== original.saleprice ||
	            brand !== original.brand;

//	        console.log("변경 여부 :", isChanged);
	        // 변경 여부 : true
	        
	        row.find(".btn-save").prop("disabled", !isChanged);
	        const saveBtn = row.find(".btn-save");

//	        console.log("버튼 개수 :", saveBtn.length);
//	        console.log("disabled :", saveBtn.prop("disabled"));
//	        console.log("html :", saveBtn[0].outerHTML);
	        // class 안에 disabled 넣어서 안 바뀌었던 것이었음....(해결)
	        
	    });
	    
	    

	    //========================================================================================================
	    
	    // 수정 후 바뀐 버튼에서 취소 버튼을 클릭했을 때
	    // 1. 작업 버튼 원상복구
	    // 2. 기존에 저장되어 있던 값으로 복구
	    $(document).on("click", ".btn-cancel", function(){

	        const row = $(this).closest("tr");

	        const original = row.data("original");


	        // 작업 버튼 원상복구
	        row.find("td").eq(0).html(
	            "<button type='button' class='btn btn-outline-secondary btn-edit'>수정</button> " +
	            "<button type='button' class='btn btn-outline-danger btn-delete'>삭제</button>"
	        );


	        // 구매가 원상복구
	        row.find("td").eq(3).text(original.purprice);

	        // 정가 원상복구
	        row.find("td").eq(4).text(original.regprice);

	        // 판매가 원상복구
	        row.find("td").eq(5).text(original.saleprice);

	        // 브랜드 원상복구
	        row.find("td").eq(6).text(original.brand);


	        // 저장했던 원래 값 삭제
	        row.removeData("original");

	    });
	    
	    

	    //========================================================================================================
	    
	    	
	    // 수정버튼 클릭 후 저장버튼을 클릭했을 떄 이벤트 발생
	    $(document).on("click", ".btn-save", function(){

	        const row = $(this).closest("tr");

	        //제품명
	        const pname = row.find("td").eq(1).text().trim();

			// 유효성 검사
	        let is_infoData_OK = true;
	        
	        // 필수 입력사항 검사
	        row.find(".infoData").each(function(index, elmt){
	            const val = $(elmt).val().trim();
	            if(val == ""){
	                $(elmt).next().show();
	                is_infoData_OK = false;
	                alert("수정사항에 공백은 존재하면 안됩니다.");
	                return false; // break
	            }
	        });


	        // 제품정가, 제품판매가, 제품구매가
	        // 숫자만 입력 가능
	        const regExp_positiveNumber = /^[0-9]+$/;
	        row.find("input.positiveNumber").each(function(index, elmt){
	            const value = $(elmt).val().trim();
	            if(value != ""){
	                if(!regExp_positiveNumber.test(value)){
	                    alert("구매가, 정가, 판매가는 숫자만 입력할 수 있습니다.");
	                    $(elmt).focus();
	                    is_infoData_OK = false;
	                    return false;
	                }
	            }
	        });


	        // 브랜드는 한글과 영어만 가능
	        const regExp_brand = /^[가-힣a-zA-Z]+$/;
	        const brandName = row.find("input.brandInput").val().trim();
	        if(brandName != ""){
	            if(!regExp_brand.test(brandName)){
	                alert("브랜드는 영어와 한글만 입력할 수 있습니다.");
	                row.find("input.brandInput").focus();
	                is_infoData_OK = false;
	            }
	        }


	        // 유효성 검사에 실패하면 저장하지 않음
	        if(!is_infoData_OK){
	            return;
	        }
	        
	        // ====== 유효성 검사 끝 =======
	        	
	        // input 값
	        const purprice = row.find("td").eq(3).find("input").val().trim();
	        const regprice = row.find("td").eq(4).find("input").val().trim();
	        const saleprice = row.find("td").eq(5).find("input").val().trim();
	        const brand = row.find("td").eq(6).find("input").val().trim();
	        
	        /*
	        console.log("pname :", pname);
	        console.log("purprice :", purprice);
	        console.log("regprice :", regprice);
	        console.log("saleprice :", saleprice);
	        console.log("brand :", brand);
	        pname : 플렉스 워크
	        purprice : 38000
	        regprice : 75000
	        saleprice : 65000
	        brand : 휠라
	        */
	        
	        // AJAX 요청 보내기
	        $.ajax({
				url: "<%= ctx_Path%>/admin/catalogue/catalogueEdit.go",
				type: "post",
				data: {
					"pname": pname,
					"purprice": purprice,
					"regprice": regprice,
					"saleprice": saleprice,
					"brand": brand
				},
				dataType: "json",
				success: function(json){
					
//					console.log(json);   결과 1 나옴

			        // 작업 버튼을 다시 수정 / 삭제 버튼으로 변경
			        row.find("td").eq(0).html(
			            "<button type='button' class='btn btn-outline-secondary btn-edit'>수정</button> " +
			            "<button type='button' class='btn btn-outline-danger btn-delete'>삭제</button>"
			        );

			        // input을 수정된 값으로 일반 text로 변경
			        row.find("td").eq(3).text(Number(purprice).toLocaleString() + "원");
			        row.find("td").eq(4).text(Number(regprice).toLocaleString() + "원");
			        row.find("td").eq(5).text(Number(saleprice).toLocaleString() + "원");
			        row.find("td").eq(6).text(brand);

			        // 기존값 데이터 삭제
			        row.removeData("original");
					
				},
				error: function(request, status, error){
					alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
				}
	        
	        });
	    });
	    
	    
	    //========================================================================================================
	    	
	    	
	    	
	    // 제품 삭제하기
	    $(document).on("click", ".btn-delete", function(){

	        const row = $(this).closest("tr");

	        const pname = row.find("td").eq(1).text().trim();
/*
	        console.log("삭제할 제품명 :", pname);
	        삭제할 제품명 : 핸드볼 스페지알 로우 프로
	        삭제할 제품명 : 플렉스 워크
*/
	        $.ajax({
				url: "<%= ctx_Path%>/admin/catalogue/catalogueDelete.go",
				type: "post",
				data: {
					"pname": pname
				},
				dataType: "json",
				success: function(json){
					
//					console.log(json);   1 나옴
					if(json.result == 1){
						
						// 삭제된 행을 화면에서 제거
						row.remove();
						
					}
					
				},
				error: function(request, status, error){
					alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
				}
	        
	        });

	    });
	    
	    
	    
	    
	    
	    
	    
	    //=============================================================================================================
	    	
	    // 카테고리 조회 및 등록 버튼 클릭시
	    $(document).on("click", "#btnCategoryModal", function(){

	        // 1. 등록 입력창/에러 메시지 초기화
	        $("#newCategoryName").val("");
	        $("#categoryErrMsg").addClass("d-none").text("");
	        
	        // 2. 항상 조회 탭으로 초기화
	        bootstrap.Tab.getOrCreateInstance(document.getElementById("tab-list")).show();

	        // 3. 모달 열기 전에 목록 먼저 조회
	        loadCategoryList();

	        // 4. 모달 열기
	        const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("categoryModal"));
	        modal.show();
	    });	
	    
	    
	    //======================================================================
	    	
	    // 카테고리 등록 버튼 클릭시 이벤트 발생
	    $(document).on("click", "#btnAddCategory", function(){
			const catename = $("#newCategoryName").val().trim();
			
			// 유효성 검사
			// 공백인 경우
			if(catename == ""){
				$("#categoryErrMsg").removeClass("d-none").text("카테고리명을 입력하세요.");
		        $("#newCategoryName").focus();
		        return;
			}
			
			// 한글만 가능
		    const regExp_category = /^[가-힣]+$/;
		    if(!regExp_category.test(catename)){
		        $("#categoryErrMsg").removeClass("d-none").text("카테고리명은 한글만 입력할 수 있습니다.");
		        $("#newCategoryName").focus();
		        return;
		    }
		    
		    // 검사 통과 시 에러 메시지 숨기고 AJAX 진행
		    $("#categoryErrMsg").addClass("d-none").text("");
			
		    
			// ======================= ajax 요청 보내기 =================================
		    $.ajax({
				url: "<%= ctx_Path%>/admin/category/categoryRegister.go",
				type: "post",
				data: {
					"catename": catename
				},
				dataType: "json",
				success: function(json){
					
//					console.log(json);   1 나옴
					if(json.result == 1){

				        // 1) 입력창/에러 메시지 초기화
				        $("#newCategoryName").val("");
				        $("#categoryErrMsg").addClass("d-none").text("");

				        // 2) 조회 탭으로 이동
				        bootstrap.Tab.getOrCreateInstance(document.getElementById("tab-list")).show();

				        // 3) 목록 다시 조회 → 테이블 갱신
				        loadCategoryList();
					} else {
				        $("#categoryErrMsg").removeClass("d-none").text("카테고리 등록에 실패했습니다.");
				    }
					
				},
				error: function(request, status, error){
					alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
				}
				
		    });
			
	    });	
	    
	    //===========================================================================================================
	    	
	   // 카테고리 삭제 버튼 클릭시
	   $(document).on("click", "#categoryTbl .btn-category-delete", function(){

	        const row = $(this).closest("tr");
	        const cateno = row.find(".cate-name").data("cateno");
	        const catename = row.find(".cate-name").text().trim();

	        if(!confirm("'" + catename + "' 카테고리를 삭제하시겠습니까?")){
	            return;
	        }
/*
	        console.log("삭제할 제품명 :", pname);
	        삭제할 제품명 : 핸드볼 스페지알 로우 프로
	        삭제할 제품명 : 플렉스 워크
*/

	        $.ajax({
				url: "<%= ctx_Path%>/admin/category/categoryDelete.go",
				type: "post",
				data: {
					"cateno": cateno
				},
				dataType: "json",
				success: function(json){

//					console.log(json);    1 나옴

					if(json.result == 1){

					    row.remove();   // 해당 행만 화면에서 제거

					    // 남은 행이 없으면 안내 문구 표시
					    if($("#categoryTbl tbody tr").length === 0){
					        $("#categoryTbl tbody").html(
					            "<tr>" +
					                "<td colspan='3' class='text-center align-middle'>" +
					                    "<span style='color:red; font-weight:bold;'>등록된 카테고리가 없습니다.</span>" +
					                "</td>" +
					            "</tr>"
					        );
					    }
					    
					} else{
						console.log("카테고리 삭제 실패");
					}

					
				},
				error: function(request, status, error){
					alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
				}
	        
	        });
	        	
			
		   
	   });
	    
	    
	   //===========================================================================================================
	    	
		
	});  // end of $(function(){})----------------------------
	


    // Function Declaration
	// 검색 버튼 클릭: 조건 저장 후 1페이지 조회
	function goSearch(){

		const productName  = $('input:text[name="pname"]').val().trim();
		const categoryName = $('input:text[name="cname"]').val().trim();
		const brandName    = $('input:text[name="brand"]').val().trim();

		// 검색 시점의 조건을 저장 (이후 페이지 이동은 이 조건 사용)
		searchCond = { productName, categoryName, brandName };

		loadList(1);
	}
	
	// 실제 조회 (검색 / 페이지 이동 공용)
	function loadList(pageNo){

		$.ajax({
			url: "<%= ctx_Path %>/admin/catalogue/catalogueList.go",
			data: {
				"productName": searchCond.productName,
				"categoryName": searchCond.categoryName,
				"brandName": searchCond.brandName,
				"currentShowPageNo": pageNo,
				"isAjax": "1"
			},
			method: "get",
			async: true,
			dataType: "json",

			success: function(json){

				let html = "";

				if (json.catalogue_map_List.length === 0) {
					html += "<tr>" +
					            "<td colspan='7' class='text-center align-middle'>" +
					                "<span style='color:red; font-weight:bold;'>상품내역이 없습니다.</span>" +
					            "</td>" +
					        "</tr>";
				}
				else {
					$.each(json.catalogue_map_List, function(index, item) {
						html += "<tr>" +
							"<td class='text-center align-middle'>" +
								"<button type='button' class='btn btn-outline-secondary btn-edit'>수정</button> " +
								"<button type='button' class='btn btn-outline-danger btn-delete'>삭제</button>" +
							"</td>" +
							"<td class='text-center align-middle'>" + item.pname + "</td>" +
							"<td class='text-center align-middle'>" + item.catename + "</td>" +
							"<td class='text-center align-middle'>" + Number(item.purprice).toLocaleString('en') + "원</td>" +
							"<td class='text-center align-middle'>" + Number(item.regprice).toLocaleString('en') + "원</td>" +
							"<td class='text-center align-middle'>" + Number(item.saleprice).toLocaleString('en') + "원</td>" +
							"<td class='text-center align-middle'>" + item.brand + "</td>" +
						"</tr>";
					});
				}

				// 테이블과 페이지바 교체
				$("#catalogueTbl tbody").html(html);
				$("#pageBar").html(json.pageBar);
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
	
	//======================================================================================================
		
		
		
	function loadCategoryList(){
		$.ajax({
			url: "<%= ctx_Path %>/admin/category/categoryList.go",
			method: "get",
			async: true,
			dataType: "json",

			success: function(json){
//				console.log(json);
				// {categoryList: Array(5)}
				
				
			    let html = "";

			    if(json.categoryList.length === 0){
			        html += "<tr>" +
			                    "<td colspan='3' class='text-center align-middle'>" +
			                        "<span style='color:red; font-weight:bold;'>등록된 카테고리가 없습니다.</span>" +
			                    "</td>" +
			                "</tr>";
			    }
			    else {
			        $.each(json.categoryList, function(index, item){
/*
			        	console.log(item.cateNum);
			        	console.log(item.cateName);
						1
						운동화
						2
						스니커즈
						3
						구두
						4
						부츠
						5
						샌들
						
*/
			            html += "<tr>" +
			                        "<td class='text-center align-middle'>" + (index + 1) + "</td>" +
			                        "<td class='text-center align-middle cate-name' data-cateno='" + item.cateNum + "'>" +
			                            item.cateName +
			                        "</td>" +
			                        "<td class='text-center align-middle'>" +
			                            "<button type='button' class='btn btn-sm btn-outline-danger btn-category-delete'>삭제</button>" +
			                        "</td>" +
			                    "</tr>";
			        });
			    }

			    $("#categoryTbl tbody").html(html);
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
    
    <div>
    	<button type="button"
				class="btn btn-secondary"
				onclick="location.href='<%= ctx_Path %>/admin/catalogueRegister.go'">
				    카탈로그 등록
		</button>
	<!-- 버튼 -->
	<button type="button" 
	        class="btn btn-secondary" 
	        id="btnCategoryModal">
	    카테고리 조회 및 등록
	</button>
	
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
		                                class="btn btn-outline-secondary btn-edit">
		                            수정
		                        </button>
		
		                        <button type="button"
		                                class="btn btn-outline-danger btn-delete">
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
		                        <fmt:formatNumber value="${catalmap.purprice}" pattern="###,###"/>원
		                    </td>
		
		                    <td class="text-center align-middle">
		                        <fmt:formatNumber value="${catalmap.regprice}" pattern="###,###"/>원
		                    </td>
		
		                    <td class="text-center align-middle">
		                        <fmt:formatNumber value="${catalmap.saleprice}" pattern="###,###"/>원
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

   	     <ul class="pagination" style="margin:auto;" id="pageBar">
   	         ${requestScope.pageBar}
   	     </ul>

   	   </div>

	</nav> 
	

</div>

	<!-- 카테고리 모달 -->
	<div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="categoryModalLabel">카테고리 목록</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      
				
			
			  <!-- 탭 메뉴 -->
			  <ul class="nav nav-tabs mb-3" id="categoryTab" role="tablist">
			    <li class="nav-item" role="presentation">
			      <button class="nav-link active" id="tab-list" data-bs-toggle="tab"
			              data-bs-target="#pane-list" type="button" role="tab">
			        카테고리 조회
			      </button>
			    </li>
			    <li class="nav-item" role="presentation">
			      <button class="nav-link" id="tab-register" data-bs-toggle="tab"
			              data-bs-target="#pane-register" type="button" role="tab">
			        카테고리 등록
			      </button>
			    </li>
			  </ul>
			
			  <!-- 탭 내용 -->
			  <div class="tab-content">
			
			    <!-- 조회 화면 -->
			    <div class="tab-pane fade show active" id="pane-list" role="tabpanel">
			      <div id="loadingSpinner" class="text-center my-4 d-none">
			        <div class="spinner-border text-primary" role="status"></div>
			      </div>
			        <table class="table table-bordered" id="categoryTbl">
				    <colgroup>
				      <col style="width: 15%;">
				      <col style="width: 65%;">
				      <col style="width: 20%;">
				    </colgroup>
				    <thead>
				      <tr>
				        <th class="text-center align-middle">번호</th>
				        <th class="text-center align-middle">카테고리명</th>
				        <th class="text-center align-middle">작업</th>
				      </tr>
				    </thead>
				    <tbody>
				      <!-- JS로 동적 생성 -->
				    </tbody>
				  </table>
			    </div>
			
			    <!-- 등록 화면 -->
			    <div class="tab-pane fade" id="pane-register" role="tabpanel">
			      <div class="mb-3">
			        <label for="newCategoryName" class="form-label">카테고리명</label>
			        <input type="text" id="newCategoryName" class="form-control"
			               maxlength="100" placeholder="새 카테고리명을 입력하세요">
			        <div class="form-text text-danger d-none" id="categoryErrMsg"></div>
			      </div>
			      <div class="text-end">
			        <button type="button" class="btn btn-primary" id="btnAddCategory">등록</button>
			      </div>
			    </div>
			
			  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>


<jsp:include page="../adminFooter.jsp" />
