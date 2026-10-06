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
		
		
		// 사이즈 선택 이벤트
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
					$('select[id="product-color"]').empty();
					
					let html = "<option value=''>색상 선택</option>";
					
					$.each(json, function(index, item) {
						html += "<option value='" + item + "'>" + item + "</option>";
					});
					
					$('select[id="product-color"]').html(html);
					
					// 사이즈 선택한 후에는 선택 가능하게
					$('select[id="product-color"]').prop('disabled', false);
					
					//let html = "<option value="">블랙</option>"
				},
				
				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }	
						
			});
		});
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		// 색상 선택 이벤트
		$('select[id="product-color"]').on("change", function() {
			const ssize = $('input:radio[class="radio-ssize"]:checked').val();
			const color = $(this).val();
			
			// 이미 해당 스펙을 선택한게 있는지 검사하기 위한것
			const spec = ssize + ', ' + color;
			let isExist = false;
			
			$('span.span-shoe-spec').each(function() {
				// 이미 선택한 스펙이라면 수량만 + 1 해준다.
				if($(this).text() == spec) {
					$(this).siblings("div").find("button.plus-quantity").click();
					isExist = true;
				}
			});
			
			// 선택하지 않았던 스펙이라면
			if(!isExist) {
				// 선택한 항목을 추가한다.
				let html = '<div class="selected-spec">';
				html += '<span class="span-shoe-spec">' + spec + '</span>';
				html += '<div class="quantity-control">';						
				html += '<button type="button" class="minus-quantity">-</button>';
				html += '<input type="text" value="1" class="spec-quantity" readonly>';
				html += '<button type="button" class="plus-quantity">+</button>';				                
				html += ' </div></div>';
				
				$('div[class="selected-spec-list"]').append(html);
			}
			
			
			
			
			// 색상선택란 초기화 및 비활성화
			$('select[id="product-color"]').empty().html("<option value=''>색상 선택</option>").prop('disabled', true);
			
			// 사이즈도 선택 취소(초기화)
			$('input:radio[class="radio-ssize"]:checked').prop('checked', false);
				              
		});
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		
		// 선택 옵션의 +버튼 클릭시(수량 증가)
		$(document).on("click", "button.plus-quantity", function() {
		 	const cur_qty = Number($(this).siblings("input").val());
		 	$(this).siblings("input").val(cur_qty + 1);
		 	
		
		 	// 최대선택가능 수량을 999개로 제한한다.
		 	if($(this).siblings("input").val() >= 999) {
		 		$(this).siblings("input").val(999);
		 		$(this).siblings("input").text(999);
		 	}
			
	
 			$(this).siblings("input").text(cur_qty + 1);
		});
		
		
		
		// 선택 옵션의 -버튼 클릭시(수량 감소)
		$(document).on("click", "button.minus-quantity", function() {
			const cur_qty = Number($(this).siblings("input").val());
			$(this).siblings("input").val(cur_qty - 1);
			
			// 0이되면 그냥 삭제함
			if($(this).siblings("input").val() <= 0) {
				 $(this).closest(".selected-spec").remove();
		 		
		 	}
			
	
 			$(this).siblings("input").text(cur_qty - 1);
		}); 
		
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		// 추가 이미지 클릭 시 대표이미지 전환
		$('button[class="thumbnail"]').on("click", function() {
			const imgSrc = $(this).find("img").attr("src");
			$('div[class="main-image"]').find("img").attr("src", imgSrc);
			
		});
		
		
		
		
		// 추가 이미지 컨트롤(화살표 버튼 및 캐러젤)
		let currentIndex = 0;

		const totalCount = $(".thumbnail").length;

		if (totalCount <= 5) {
		    $(".btn-change-images").css("visibility", "hidden");
		}


		$(".btn-change-images.next").click(function() {

		    const maxIndex = $(".thumbnail").length - 5;

		    if (currentIndex < maxIndex) {
		        currentIndex++;
		        moveThumbnail();
		    }

		});


		$(".btn-change-images.prev").click(function() {

		    if (currentIndex > 0) {
		        currentIndex--;
		        moveThumbnail();
		    }

		});


		
	
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		// 장바구니 버튼 클릭 시
		$('button#button-cart').on("click", function() {
			// 선택한 옵션의 재고가 충분한지 검사한다.
			checkStock();
		});
		
		
		// 바로구매 버튼 클릭 시
		$('button#button-purchase').on("click", function() {
			// 선택한 옵션의 재고가 충분한지 검사한다.
			checkStock();
		});
		
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		// 상품 상세 탭 클릭
		$('.product-tab').on('click', function() {

		    const tabId = $(this).data('tab');

		    // 모든 탭 비활성화
		    $('.product-tab').removeClass('active');

		    // 클릭한 탭 활성화
		    $(this).addClass('active');

		    // 모든 내용 숨김
		    $('.product-tab-content').removeClass('active');

		    // 선택한 내용 표시
		    $('#' + tabId).addClass('active');

		});
		
	});
	
	
	
	
	// ---------------------------------------------------------------------------------------------------------
	
	
	// 추가이미지 캐러젤
	function moveThumbnail() {

	    const thumbnailWidth = $(".thumbnail").outerWidth();
	    const gap = 10;

	    $(".thumbnail-track").css(
	        "transform",
	        "translateX(-" + ((thumbnailWidth + gap) * currentIndex) + "px)"
	    );

	}
	
	// ---------------------------------------------------------------------------------------------------------
	
	// 선택한 옵션의 재고가 충분한지 검사한다.
	function checkStock() {

		// 스펙 목록들을 한 번에 보내기 위한 배열
		let specList = [];
		
		// 선택한 스펙 목록의 스펙을 가져와 db를 검사...
		$('div.selected-spec').each(function() {
			
			const spec = $(this).find('span.span-shoe-spec').text();
			const arrSpec = spec.split(',');
			
			const ssize = arrSpec[0].trim();	// 사이즈
			const color = arrSpec[1].trim();	// 색상
			const qty = Number($(this).find('input.spec-quantity').val()); // 선택한 수량
			
			specList.push({
				ssize:  ssize,
				color: color,
				qty: qty
			});
			
		});
		
		const pname = "${requestScope.pdto.fk_pname}";	// 상품명
		
		// ajax로 보내기
		
	}

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

			
			<button type="button" class="btn-change-images">&lt;</button>

			<!-- 이미지 영역 -->
			<div class="thumbnail-viewport">
	        	<div class="thumbnail-track">
	
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
	
			<button type="button" class="btn-change-images">&gt;</button>
			
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
                	<fmt:formatNumber value="${requestScope.pdto.catalogueDTO.saleprice}" type="number" />원 
                </strong>
            </div>
            
            
            <!-- 배송비 -->
            <div class="deliveryfee">
                <span>
                	배송비 <fmt:formatNumber value="${requestScope.pdto.deliveryfee}" type="number" />원 
                </span>
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


		<%-- 선택한 상품의 스펙 및 수량 나타내는 곳 --%>
		<div class="selected-spec-list">
			<!-- <div class="selected-spec">
				<span class="span-shoe-spec">123</span>
			
				<div class="quantity-control">
	                <button type="button" id="minus-quantity">-</button>
	                <input type="text" value="1">
	                <button type="button" id="plus-quantity">+</button>
	            </div>
            </div> -->
		</div>

        <!-- 결제 금액 -->
        <div class="total-price">
            <span>총 결제금액</span>
            <strong>0원</strong>
        </div>


        <!-- 구매 버튼 -->
        <div class="product-buttons">
            <button type="button" id="button-cart">장바구니</button>
            <button type="button" id="button-purchase">바로구매</button>
        </div>

    </div>
    
    

</div>



<!-- 상품 상세 하단 탭 영역 -->
<div class="product-bottom">

    <!-- 탭 메뉴 -->
    <div class="product-tab-menu">

        <button type="button"
                class="product-tab active"
                data-tab="product-info">
            상품정보
        </button>

        <button type="button"
                class="product-tab"
                data-tab="product-review">
            상품후기 (<span>163</span>)
        </button>

        <button type="button"
                class="product-tab"
                data-tab="product-qna">
            상품 Q&A (<span>12</span>)
        </button>

    </div>




	<%-- 상품 상세정보 --%>
	<div id="product-info" class="product-tab-content active">
	
		<div class="product-detail-images">
			<img src="<%=ctxPath %>/images/kimkc/product/${requestScope.pdto.pimage2}" />
			
			<c:if test="${not empty requestScope.pdto.prodImageDTOList}">
		    	<c:forEach var="pidto" items="${requestScope.pdto.prodImageDTOList}">
		       		<img src="<%=ctxPath %>/images/kimkc/product/${pidto.image_name}" alt="상품 측면 이미지">
		    	</c:forEach>
		    </c:if>
			
		
		</div>
	</div>




	<!-- ============================== -->
    <!-- 상품후기 -->
    <!-- ============================== -->
    <div id="product-review" class="product-tab-content">

        <div class="review-header">

            <div class="review-count">
                총 <strong>163</strong>개의 후기가 있습니다.
            </div>

            <select class="review-sort">
                <option value="helpful">도움돼요순</option>
                <option value="latest">최신순</option>
                <option value="rating">별점순</option>
            </select>

        </div>


        <!-- 후기 1개 -->
        <div class="review-item">

			<div class="review-big-rating">
		            ★★★★★
	        </div>
		
		    <!-- 후기 기본 정보 -->
		    <div class="review-info">
		
		        <div class="review-option-area">
		            <span class="review-option">옵션</span>
		            <span class="review-option-value">100, 250</span>
		        </div>
		
		        <div class="review-user-info">
		
		            <span class="review-user">
		                작성자 : h****3
		            </span>
		
		            <span class="review-date">
		                2023.06.04 05:08:06
		            </span>
		
		        </div>
		
		
		    </div>
		
		    <!-- 후기 내용 -->
		    <div class="review-content">
		
		        저희 아이 신발 사줬어요<br>
		        평소 반스는 250 좀 작은데 나이키는 250이 딱이네요<br>
		        편하고 좋다고 하네요~
		
		    </div>

		</div>


        <!-- 후기 2개째 -->
        <div class="review-item">

			<div class="review-big-rating">
		            ★★★★★
	        </div>
	        
		    <!-- 후기 기본 정보 -->
		    <div class="review-info">
		
				
		
		        <div class="review-option-area">
		            <span class="review-option">옵션</span>
		            <span class="review-option-value">100, 250</span>
		        </div>
		
		        <div class="review-user-info">
		
		            <span class="review-user">
		                작성자 : h****3
		            </span>
		
		            <span class="review-date">
		                2023.06.04 05:08:06
		            </span>
		
		        </div>

		
		    </div>
		
		    <!-- 후기 내용 -->
		    <div class="review-content">
		
		        저희 아이 신발 사줬어요<br>
		        평소 반스는 250 좀 작은데 나이키는 250이 딱이네요<br>
		        편하고 좋다고 하네요~
		
		    </div>

		</div>

    </div>


    <!-- ============================== -->
    <!-- 상품 Q&A -->
    <!-- ============================== -->
    <div id="product-qna" class="product-tab-content">

        <div class="qna-list">

            <!-- Q&A 1 -->
            <div class="qna-item">

                <div class="qna-question">

                    <span class="qna-title">
                        사이즈교환
                    </span>

                    <span class="qna-lock">
                        🔒
                    </span>

                    <span class="qna-user">
                        s******6
                    </span>

                    <span class="qna-date">
                        2026.08.31
                    </span>

                    <span class="qna-status">
                        답변완료
                    </span>

                </div>

            </div>


            <!-- Q&A 2 -->
            <div class="qna-item">

                <div class="qna-question">

                    <span class="qna-title">
                        배송
                    </span>

                    <span class="qna-lock">
                        🔒
                    </span>

                    <span class="qna-user">
                        h*k
                    </span>

                    <span class="qna-date">
                        2026.07.21
                    </span>

                    <span class="qna-status">
                        답변완료
                    </span>

                </div>

            </div>


            <!-- Q&A 3 -->
            <div class="qna-item">

                <div class="qna-question">

                    <span class="qna-title">
                        260 사이즈는 아예 단종인가요?
                    </span>

					<span class="qna-lock empty">
					
					</span>
					
                    <span class="qna-user">
                        s*****8
                    </span>

                    <span class="qna-date">
                        2025.01.26
                    </span>

                    <span class="qna-status">
                        답변완료
                    </span>

                </div>


                <!-- 질문 내용 -->
                <div class="qna-answer-area">

                    <div class="qna-question-content">

                        260사이 검색이 안되네요ㅠㅠ<br>
                        260 사이즈가 필요해요

                    </div>


                    <!-- 답변 -->
                    <div class="qna-answer">

                        <div class="answer-icon">
                            A
                        </div>

                        <div class="answer-content">

                            안녕하십니까,<br>
                            함께 그린 세상 ABC마트 고객센터입니다.<br><br>

                            문의주신 나이키 코트 비전 로우 넥스트 네이처
                            260사이즈 상품의 온라인 재고는 정확한 재입고 예정에
                            있는 상품이 아니므로,<br>
                            정확한 입고 시기에 대한 답변은 드리기 어려운 점
                            양해 부탁드립니다.<br><br>

                            만족스러운 답변을 드리지 못하여 죄송합니다.

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>


</div>




<jsp:include page="../../footer.jsp" />