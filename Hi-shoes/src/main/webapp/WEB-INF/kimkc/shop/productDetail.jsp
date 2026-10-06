<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/productDetail.css" >
<jsp:include page="../../header.jsp" />

<script>


	$(function() {
		
		// 색상 선택은 사이즈 선택 전까지 비활성화
		$('select[id="product-color"]').prop('disabled', true);
		
		$('input:radio[class="radio-ssize"]').on("change", function() {
			//alert($(this).val());
			const select_ssize = $(this).val();
			
			$.ajax({
				url: "<%= ctxPath%>/shop/getColorsBySsizeJSON.go",
				data:{'ssize' : select_ssize,
						'pname' : '${requestScope.pdto.fk_pname}'},
				dataType:"json",
				success:function(json) {
					// 일단 한번 비워주기
					$(select[id="product-color"]).empty();
					
					let html = "<option value=''>색상 선택</option>";
					
					$.each(function(index, item) {
						html += "<option value='" + $(item) + "'>" + $(item) + "</option>"
					});
					
					// 사이즈 선택한 후에는 선택 가능하게
					$('select[id="product-color"]').prop('disabled', false);
					
					//let html = "<option value="">블랙</option>"
				},
				
				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }	
						
			});
		});
		
	});


</script>


<div class="product-container">

    <!-- 왼쪽: 상품 이미지 영역 -->
    <div class="product-images">

        <!-- 대표 이미지 -->
        <div class="main-image">
            <img src="<%=ctxPath %>/images/kimkc/product/${requestScope.pdto.pimage1}" alt="상품 대표 이미지">
        </div>

        <!-- 추가 이미지 목록 -->
        <div class="thumbnail-list">

            <button type="button" class="thumbnail">
                <img src="<%=ctxPath %>/images/kimkc/product/${requestScope.pdto.pimage2}" alt="상품 측면 이미지">
            </button>

            <c:if test="${not empty requestScope.pdto.prodImageDTOList}">
            	<c:forEach var="pidto" items="${requestScope.pdto.prodImageDTOList}">
            		<button type="button" class="thumbnail">
                		<img src="<%=ctxPath %>/images/kimkc/product/${pidto.image_name}" alt="상품 측면 이미지">
            		</button>
            	</c:forEach>
            </c:if>

        </div>

    </div>


    <!-- 오른쪽: 상품 정보 영역 -->
    <div class="product-detail">

        <!-- 브랜드 및 상품명 -->
        <div class="product-info">

            <div class="product-brand">
                ${requestScope.pdto.catalogueDTO.brand}
            </div>

            <div class="product-header">
                <h1>${requestScope.pdto.fk_pname}</h1>

                <button type="button" class="wishlist">
                    ♡
                </button>
            </div>

            <!-- 가격 -->
            <div class="product-price">
                <strong>
                	<fmt:formatNumber value="${requestScope.pdto.catalogueDTO.saleprice}" type="number" />
                </strong>
            </div>

            <!-- 할인 정보 -->
            <!-- <div class="discount-info">
                <span>최대 혜택가</span>
                <strong>53,100원</strong>
                <span>10%</span>
            </div> -->

        </div>


        <!-- 상품 옵션 -->
        <div class="product-options">

            <!-- 사이즈 선택 -->
            <div class="option size-option">

                <label>사이즈</label>

                <div class="size-list">
                	<c:if test="${not empty requestScope.sizeList}">
                		<c:forEach var="size" items="${requestScope.sizeList}" varStatus="idx" >
                			<%-- <button type="button" class="btn-select-ssize">${size}</button> --%>
                			<!-- 라디오 버튼과 label의 'id' 및 'for' 값을 반드시 일치시켜야 합니다 -->

							
							  <input type="radio" id="ssize-option${idx.index}" name="radio-group-ssize" class="radio-ssize" value="${size}">
							  <label for="ssize-option${idx.index}" class="label-radio-ssize">${size}</label>
                		</c:forEach>
			<!-- 	    <button type="button">225</button>
	                    <button type="button">230</button>
	                    <button type="button">235</button>
	                    <button type="button">240</button>
	                    <button type="button">245</button>
	                    <button type="button">250</button> -->
                    </c:if>
                </div>

            </div>

            <!-- 색상 선택 -->
            <div class="option color-option">

                <label for="product-color">색상</label>

                <select id="product-color" name="color">
                    <option value="">색상 선택</option>
                    <!-- <option value="beige">베이지</option>
                    <option value="black">블랙</option>
                    <option value="brown">브라운</option> -->
                </select>

            </div>

        </div>


        <!-- 결제 금액 -->
        <div class="total-price">
            <span>총 결제금액</span>
            <strong>0원</strong>
        </div>


        <!-- 구매 버튼 -->
        <div class="product-buttons">
            <button type="button">장바구니</button>
            <button type="button">바로구매</button>
        </div>

    </div>
    
    

</div>
	<%-- <div class="content-wrap row text-center">
		상품이미지 영역
		<div class="col-lg-6">
			
			<div class="pimage-wrap">
				<img src="<%=ctxPath %>/images/kimkc/product/ML408K 1_뉴발란스.jpg" class="pimage"/>
			</div>
		</div>

		상품정보 및 옵션선택 영역
		<div class="col-lg-6">
			<div class="productInfo_wrap">
				<span class="productInfo_brand">브랜드</span>
				<span class="productInfo_pname">상품명</span>
				<span class="productInfo_pname">상품보증서</span>
				<span class="productInfo_regprice">정가</span>
				<span class="productInfo_saleprice">판매가</span>
				<button class="productInfo_btnSize">사이즈용</button>
				
				<form action="" method="post">
					<button class="productInfo_btnPurchase">결제</button>
					<button class="productInfo_btnCart">장바구니</button>
				</form>
				
			</div>
			
		
		</div>
	</div> --%>

<jsp:include page="../../footer.jsp" />