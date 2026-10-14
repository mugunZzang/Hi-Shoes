<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/productDetail.css" >
<jsp:include page="../../header.jsp" />

<script>


	$(function() {
		
		// 하트(찜하기) 버튼 클릭 시
		$('button.wishlist').on("click", function() {
			const isJjim = $(this).text().trim();
			
			if (isJjim == '♡') {
				// 찜하기전 > 찜 등록함 만들기
				$.ajax({
					url: "<%= ctxPath%>/shop/changeWishStatusJSON.go",
					data:{'pnum' : '${requestScope.pnum}',
							'isJjim' : '0'},
					method: "post",
					dataType:"json",
					success:function(json) {
						alert("상품이 위시리스트에 등록되었습니다.");
						$('button.wishlist').text('♥');
					},
					
					error: function(request, status, error){
				    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				    }	
				});
			}
			else {
				// 찜 등록됨 > 찜 해제 만들기
				$.ajax({
					url: "<%= ctxPath%>/shop/changeWishStatusJSON.go",
					data:{'userid' : '${sessionScope.loginuser.userid}',
							'pnum' : '${requestScope.pnum}',
							'isJjim' : '1'},
					method: "post",
					dataType:"json",
					success:function(json) {
						alert("상품이 위시리스트에서 제거하였습니다.");
						$('button.wishlist').text('♡');
					},
					
					error: function(request, status, error){
				    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				    }	
				});
			}
		});
		
		
		
		// 색상 선택은 사이즈 선택 전까지 비활성화
		$('select[id="product-color"]').prop('disabled', true);
		
		
		// 사이즈 선택 이벤트
		$('input:radio[class="radio-ssize"]').on("change", function() {
			//alert($(this).val());
			const select_ssize = $(this).val();
			
			$.ajax({
				url: "<%= ctxPath%>/shop/getColorsBySsizeJSON.go",
				method: "post",
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
				
				// 근데 이미 선택한 스펙이 5개 이상이면
				if ($('div.selected-spec-list').children('div.selected-spec').length >= 5) {
        			alert("상품 옵션은 최대 5개까지 선택할 수 있습니다.");
        		// 그렇지 않다면
    			} else {
    				// 선택한 항목을 추가한다.
    				let html = '<div class="selected-spec">';
    				html += '<span class="span-shoe-spec">' + spec + '</span>';
    				html += '<div class="quantity-control">';						
    				html += '<button type="button" class="minus-quantity">-</button>';
    				html += '<input type="text" value="1" class="spec-quantity" readonly>';
    				html += '<button type="button" class="plus-quantity">+</button>';				                
    				html += ' </div></div>';
    				
    				$('div[class="selected-spec-list"]').append(html);	

    	 			// 총 결제금액 반영
    				calcTotalcredit();
    			}
				
				
			}
			
			
			
			// 색상선택란 초기화 및 비활성화
			$('select[id="product-color"]').empty().html("<option value=''>색상 선택</option>").prop('disabled', true);
			
			// 사이즈도 선택 취소(초기화)
			$('input:radio[class="radio-ssize"]:checked').prop('checked', false);
				              
		});
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		
		// 선택 옵션의 +버튼 클릭시(수량 증가)
		$(document).on("click", "button.plus-quantity", function() {
			const input = $(this).siblings("input");
		 	const cur_qty = Number(input.val());
		 	
		 	// 최대선택가능 수량을 999개로 제한한다.
		 	if (cur_qty >= 999) {
		 		input.val(999);
		 		return;
		 	}
		 	
		 	// 해당 스펙 가져옴
		 	const spec = $(this).closest(".selected-spec").find(".span-shoe-spec").text();
		 	//console.log(spec);
		 	const specArr = spec.split(", ");
		 	const ssize = specArr[0];
		 	const color = specArr[1];
		 	
		 	
		 	// ajax로 재고량 비교
		 	$.ajax({
		 		url: "<%= ctxPath%>/shop/isStockEnoughJSON.go",
				data:{'color' : color, 
						'ssize' : ssize,
						'pname' : '${requestScope.pdto.fk_pname}',
						'selectedCnt' : cur_qty},
				dataType:"json",
				method: "post",
				success:function(json) {
					//console.log(json);
					if (json.n == 1) {
						// 재고가 충분하면 반영하기
					 	input.val(cur_qty + 1);
						//console.log(input.val());
					 	// 총 결제금액 반영
						calcTotalcredit();
					}
					else {
						alert("해당 옵션의 재고가 부족합니다.");
					}
				},
				
				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }	
		 	});
		 	

		 		
		});
		
		
		
		// 선택 옵션의 -버튼 클릭시(수량 감소)
		$(document).on("click", "button.minus-quantity", function() {
			const cur_qty = Number($(this).siblings("input").val());
			$(this).siblings("input").val(cur_qty - 1);
			
			// 총 결제금액 반영
			calcTotalcredit();
			
			// 0이되면 그냥 삭제함
			if($(this).siblings("input").val() <= 0) {
				 $(this).closest(".selected-spec").remove();
		 		
		 	}
			
	
 			$(this).siblings("input").text(cur_qty - 1);
		}); 
		
		
		
		// ---------------------------------------------------------------------------------------------------------
		

		/* 추가 이미지 캐러셀 */
		const $track = $(".thumbnail-track");
		const $prev = $(".btn-change-images.prev");
		const $next = $(".btn-change-images.next");

		const visibleCount = 5;
		let currentIndex = 0;

		// JSP에서 출력된 추가 이미지 목록을 배열로 보관
		const imgList = $track.find(".thumbnail img").map(function () {
		    return {
		        src: $(this).attr("src"),
		        alt: $(this).attr("alt") || "상품 추가 이미지"
		    };
		}).get();
	    
		// 현재 인덱스 기준으로 썸네일 갱신
		function renderThumbnails() {
		    const totalCount = imgList.length;
		    const maxIndex = Math.max(0, totalCount - visibleCount);

		    // 인덱스 범위 제한
		    currentIndex = Math.max(0, Math.min(currentIndex, maxIndex));

		    // 기존 썸네일만 비우고 현재 구간의 이미지 출력
		    $track.empty();

		    const endIndex = Math.min(
		        currentIndex + visibleCount,
		        totalCount
		    );

		    for (let i = currentIndex; i < endIndex; i++) {
		        const $button = $("<button>", {
		            type: "button",
		            class: "thumbnail"
		        });

		        const $img = $("<img>", {
		            src: imgList[i].src,
		            alt: imgList[i].alt
		        });

		        $button.append($img);
		        $track.append($button);
		    }

		    // 5개 초과일 때만 화살표 표시
		    const showArrows = totalCount > visibleCount;

		    $prev.add($next).css(
		        "visibility",
		        showArrows ? "visible" : "hidden"
		    );

		    // 첫 번째 / 마지막 위치에서는 해당 방향 비활성화
		    $prev.prop("disabled", currentIndex === 0);
		    $next.prop("disabled", currentIndex >= maxIndex);
		}
		
		// 썸네일을 직접 클릭했을 때만 대표 이미지 변경
		$track.on("click", ".thumbnail", function () {
		    const imgSrc = $(this).find("img").attr("src");
		    $(".main-image img").attr("src", imgSrc);
		});

		// 이전 이미지 한 칸
		$prev.on("click", function () {
		    if (currentIndex > 0) {
		        currentIndex--;
		        renderThumbnails();
		    }
		});

		// 다음 이미지 한 칸
		$next.on("click", function () {
		    const maxIndex = Math.max(0, imgList.length - visibleCount);

		    if (currentIndex < maxIndex) {
		        currentIndex++;
		        renderThumbnails();
		    }
		});

		// 최초 출력
		renderThumbnails();
	
		
		// ---------------------------------------------------------------------------------------------------------
		
		
		// 장바구니 버튼 클릭 시
		$('button#button-cart').on("click", function() {
			
			// 옵션이 최소 1개 이상 선택되어야 함
			const selectedSpecs = $('div.selected-spec');

			if (selectedSpecs.length === 0) {
				alert("상품 옵션을 선택해 주세요.");
				return;
			}
			
			// 선택한 옵션들을 배열로 수집
			const options = [];

			selectedSpecs.each(function() {

				const spec = $(this).find('.span-shoe-spec').text();
				const specArr = spec.split(', ');

				const ssize = specArr[0];
				const color = specArr[1];
				const cqty = Number(
					$(this).find('input.spec-quantity').val()
				);

				options.push({
					ssize: ssize,
					color: color,
					cqty: cqty
				});

			});
			//console.log(options);
			
			// 장바구니 등록
			$.ajax({
				url: "<%= ctxPath%>/shop/addCartJSON.go",
				type: "POST",
				data: {
					pnum: '${requestScope.pnum}',
					options: JSON.stringify(options)
				},
				dataType: "json",

				success: function(json) {
					if (json.n == 1) {
						if (confirm("장바구니에 상품을 담았습니다. 장바구니로 이동하시겠습니까?")) {
							// 장바구니 페이지로 이동
							<%-- location.href = "<%= ctxPath%>/shop/cartList.go"; --%>
							// POST 방식으로 장바구니 페이지 이동
						    const form = document.createElement("form");
						    form.method = "POST";
						    form.action = "<%= ctxPath%>/shop/cartList.go";

						    document.body.appendChild(form);
						    form.submit();
						}
					}
					else {
						//console.log("여기에요~");
						alert("장바구니 등록에 실패하였습니다.");
					}
					
				},

				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }
			}); 
			
		});
		
		
		
		
		// 바로구매 버튼 클릭 시
		$('button#button-purchase').on("click", function() {
			
			// 옵션이 최소 1개 이상 선택되어야 함
			const selectedSpecs = $('div.selected-spec');

			if (selectedSpecs.length === 0) {
				alert("상품 옵션을 선택해 주세요.");
				return;
			}
			
			const pimage = "<%=ctxPath%>/images/kimkc/product/${requestScope.pdto.pimage1}";
			//console.log("대표이미지" + pimage);
			const pname = "${requestScope.pdto.fk_pname}";
			const brand = "${requestScope.pdto.catalogueDTO.brand}";
			const regprice = "${requestScope.pdto.catalogueDTO.regprice}";
			const saleprice = "${requestScope.pdto.catalogueDTO.saleprice}";
			

			// 담을 곳
			const prodList = [];
			
			// 선택 옵션란들
			//const selectedSpecs = $('div.selected-spec');
			selectedSpecs.each(function() {

				const spec = $(this).find('.span-shoe-spec').text();
				
				const cqty = $(this).find('input.spec-quantity').val();

				const snum = '';
				
				
				prodList.push({
					 //대표이미지, 제품명, 브랜드, 옵션, snum, 수량, 정가, 판매가 등...
					"pimage" : pimage,
					"pname" : pname,
					"brand" : brand,
					"spec" : spec,
					"snum" : snum,
					"cqty" : cqty,
					"regprice" : regprice,
					"saleprice" : saleprice,
					"purtype" : "바로구매"
				});

			});
			


			console.log(prodList);
			// 이제 결제 화면에 넘겨줘야 함...
			const frm = document.getElementById('frm-purchase');
			const input1 = document.querySelector('input[name="prodList"]');
			
			frm.method = "POST";
			frm.action = "<%= ctxPath %>/shop/purchaseProduct.go";
			input1.value = JSON.stringify(prodList);	
			
			frm.submit();
			
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
		
		
		
		// ---------------------------------------------------------------------------------------------------------
		
		// QnA 항목 클릭 시 질문 및 답변 펼치기/접기
		$(document).on("click", ".qna-item > .qna-question", function() {
			
		    const qnaItem = $(this).closest(".qna-item");
		    const canOpen = qnaItem.attr("data-can-open")?.trim();

		    // 열람 권한이 없으면 아무 동작도 하지 않음
		    if (canOpen !== "true") {
		    	
		        return;
		    }
		   
		    // 클릭한 항목만 펼치거나 접기
		    qnaItem.toggleClass("open");

		});
		
	});
	
	
	
	
	
	
	
	// ---------------------------------------------------------------------------------------------------------
	
	// 총 결제금액 반영하기
	function calcTotalcredit() {
		//console.log("가격 반영하기");
		let cnt = 0;
		$('div.selected-spec').each(function() {
			cnt += Number($(this).find('input.spec-quantity').val());
		});
	
		const total_price = cnt * Number("${requestScope.pdto.catalogueDTO.saleprice}");
		//console.log(total_price);
		$("strong#span-total-price").html(total_price.toLocaleString('en'));
		
	}
	
	
	
	// ---------------------------------------------------------------------------------------------------------
	
	// QnA 작성 버튼
	function showQnAWriteModal() {
		// 모달 띄워주기
		const modalElement = document.getElementById('qnaWriteModal');

	    const qnaModal = bootstrap.Modal.getOrCreateInstance(modalElement);

	    qnaModal.show();

	}
	
	// QnA 모달 - 등록 버튼
	function goWriteQnA() {
		//alert("등록 눌림");
		const title = $('input#qnaTitle').val();
		const content = $('textarea#qnaContents').val();
		let checked = '공개';
		
		if ($('input#checkPublic').prop("checked")) {
			checked = '비공개';
		}
		
		// 유효성 검사 살짝
		if (!title || title.trim() == "") {
			console.log(title);
			alert("제목을 입력하세요.");
			return;
		}
		if (!content || content.trim() == "") {
			console.log(content);
			alert("질문 내용을 입력하세요.");
			return;
		}

		// 실제 DB에 저장
		if(confirm("해당 문의를 등록하시겠습니까?")) {
			$.ajax({
				url: "<%= ctxPath%>/shop/addQnAJSON.go",
				type: "POST",
				data: {
					pnum: '${requestScope.pnum}',
					title: title,
					content: content,
					checked: checked
				},
				dataType: "json",

				success: function(json) {
					if (json.n == 1) {
						alert("문의사항이 등록되었습니다.");	
						location.reload();
					}
					else {
						alert("문의사항 등록에 실패하였습니다.");
					}
				},

				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }
			}); 
			
		}
		
	}
	
	//id="btnQnARegister"
	
	
	// =================================================================================================
	
	// QnA 페이지바 클릭 시
	function getQnaList(pnum, currentPageNo_QnA) {
		//console.log("현재페이지" + currentPageNo_QnA);
		$.ajax({
	        url: "<%= ctxPath %>/shop/getQnAList.go",
	        type: "GET",
	        data: {
	            pnum: pnum,
	            currentShowPageNo_QnA: currentPageNo_QnA
	        },
	        dataType: "html",

	        success: function(html) {
	            // QnA 목록과 페이지바만 교체
	            $("#qna-ajax-area").html(html);
	        },

	        error: function(request, status, error) {
	            alert(
	                "code: " + request.status +
	                "\nmessage: " + request.responseText +
	                "\nerror: " + error
	            );
	        }
	    });
	}
	
	
	// =================================================================================================
		
	// 리뷰 페이지바 클릭 시
	function getReviewList(pnum, currentPageNo_review) {
		//console.log("현재페이지" + currentPageNo_review);
		$.ajax({
	        url: "<%= ctxPath %>/shop/getReviewList.go",
	        type: "GET",
	        data: {
	            pnum: pnum,
	            currentShowPageNo_review: currentPageNo_review
	        },
	        dataType: "html",

	        success: function(html) {
	            // 리뷰 목록과 페이지바만 교체
	            $("#reivew-ajax-area").html(html);
	        },

	        error: function(request, status, error) {
	            alert(
	                "code: " + request.status +
	                "\nmessage: " + request.responseText +
	                "\nerror: " + error
	            );
	        }
	    });
	}	
		

</script>


<div class="product-container">

    <%-- ===================================================== >
         왼쪽 (상품 이미지 구역)
    < ===================================================== --%>
    <div class="product-images">

        <%-- 대표 이미지 --%>
        <div class="main-image">
            <img src="<%=ctxPath %>/images/${requestScope.pdto.pimage1}" alt="상품 대표 이미지">
        </div>

        <%-- 추가이미지들 --%>
        <div class="thumbnail-list">

			
			<button type="button" class="btn-change-images prev">&lt;</button>

			<%-- 이미지영역 --%>
			<div class="thumbnail-viewport">
	        	<div class="thumbnail-track">
	
		            <button type="button" class="thumbnail">
		                <img src="<%=ctxPath %>/images/${requestScope.pdto.pimage2}" alt="상품 측면 이미지">
		            </button>
		
		            <c:if test="${not empty requestScope.pdto.prodImageDTOList}">
		            	<c:forEach var="pidto" items="${requestScope.pdto.prodImageDTOList}">
		            		<button type="button" class="thumbnail">
		                		<img src="<%=ctxPath %>/images/${pidto.image_name}" alt="상품 측면 이미지">
		            		</button>
		            	</c:forEach>
		            </c:if>
		            
		        </div>
		    </div>
	
			<button type="button" class="btn-change-images next">&gt;</button>
			
        </div>

    </div>


    <%-- ===================================================== >
         오른쪽 (상품 정보들)
    < ===================================================== --%>
    <div class="product-detail">

        <%-- 브랜드랑 상품명 --%>
        <div class="product-info">

            <div class="product-brand">
                ${requestScope.pdto.catalogueDTO.brand}
            </div>

            <div class="product-header">
                <h1>${requestScope.pdto.fk_pname}</h1>

                <button type="button" class="wishlist">
                	<c:if test="${requestScope.isJjim == 1}">♥</c:if>
                    <c:if test="${requestScope.isJjim == 0}">♡</c:if>
                </button>
            </div>

            <%-- 가격들 --%>
            <div class="product-price">
            	<strong>
          			<c:if test="${requestScope.pdto.catalogueDTO.regprice > requestScope.pdto.catalogueDTO.saleprice}">
          				<span class="ref-price">
          					<fmt:formatNumber value="${requestScope.pdto.catalogueDTO.regprice}" type="number" />
          				</span>
          				<span class="sale-price">
          					<fmt:formatNumber value="${requestScope.pdto.catalogueDTO.saleprice}" type="number" /> 원
          				</span>
          			</c:if>
          			<c:if test="${requestScope.pdto.catalogueDTO.regprice <= requestScope.pdto.catalogueDTO.saleprice}">
          				<span class="normal-price">
          					<fmt:formatNumber value="${requestScope.pdto.catalogueDTO.saleprice}" type="number" /> 원
          				</span>
          			</c:if>
      			</strong>
      

            </div>
            
            
            <%-- 배송비 --%>
            <div class="deliveryfee">
                <span>
                	배송비 <fmt:formatNumber value="${requestScope.pdto.deliveryfee}" type="number" />원 
                </span>
            </div>


        </div>


        <%-- 옵션 선택구역 --%>
        <div class="product-options">

            <%-- 사이즈 --%>
            <div class="option size-option">

                <label>사이즈</label>

                <div class="size-list">
                	<c:if test="${not empty requestScope.sizeList}">
                		<c:forEach var="size" items="${requestScope.sizeList}" varStatus="idx" >
	
							  <input type="radio" id="ssize-option${idx.index}" name="radio-group-ssize" class="radio-ssize" value="${size}">
							  <label for="ssize-option${idx.index}" class="label-radio-ssize">${size}</label>
                		</c:forEach>

                    </c:if>
                </div>

            </div>

            <%-- 색상 --%>
            <div class="option color-option">

                <label for="product-color">색상</label>

                <select id="product-color" name="color">
                    <option value="">색상 선택</option>

                </select>

            </div>

        </div>


		<%-- 선택한 옵션들 나타내는 곳 --%>
		<div class="selected-spec-list">

		</div>

        <%-- 총 결제금액 --%>
        <div class="total-price">
            <span>총 결제금액</span>
            <strong id="span-total-price">0원</strong>
        </div>


        <%-- 장바구니/바로구매 버튼 --%>
        <div class="product-buttons">
            <button type="button" id="button-cart" class="cart-button">장바구니</button>
            
            <form id="frm-purchase">
            	<input name="deliveryfee" type="hidden" value="${requestScope.pdto.deliveryfee}"/>
            	<input name="prodList" type="hidden"/>
            	<button type="button" id="button-purchase" class="buy-button">바로구매</button>
            </form>
        </div>

    </div>
    
    

</div>



<%-- ===================================================== >
     상품 하단(상세정보, 리뷰, QnA)
< ===================================================== --%>
<div class="product-bottom">

    <%-- 탭 메뉴 --%>
    <div class="product-tab-menu">

        <button type="button"
                class="product-tab active"
                data-tab="product-info">
            상품정보
        </button>

        <button type="button"
                class="product-tab"
                data-tab="product-review">
            상품후기
        </button>

        <button type="button"
                class="product-tab"
                data-tab="product-qna">
            상품 Q&A
        </button>

    </div>




	<%-- 상품 상세정보 --%>
	<div id="product-info" class="product-tab-content active">
	
		<div class="product-detail-images pt-5">
			<img src="<%=ctxPath %>/images/${requestScope.pdto.pimage2}" />
			
			<c:if test="${not empty requestScope.pdto.prodImageDTOList}">
		    	<c:forEach var="pidto" items="${requestScope.pdto.prodImageDTOList}">
		       		<img src="<%=ctxPath %>/images/${pidto.image_name}" alt="상품 측면 이미지">
		    	</c:forEach>
		    </c:if>
			
		
		</div>
	</div>




    <%-- ===================================================== >
         상품 후기
    < ===================================================== --%>
    <div id="product-review" class="product-tab-content pt-5">

        <div class="review-header">

            <div class="review-count">
                총 <strong>${fn:length(requestScope.reviewList)}</strong>개의 후기가 있습니다.
            </div>

            <select class="review-sort">
                <option value="helpful">도움돼요순</option>
                <option value="latest">최신순</option>
                <option value="rating">별점순</option>
            </select>

        </div>

		<div id="reivew-ajax-area">

			<c:if test="${not empty requestScope.reviewList}">
			
				<c:forEach var="review" items="${requestScope.reviewList}">
	        		<%-- 리뷰 1개 --%>
			        <div class="review-item">
			
						<div class="review-big-rating">
							
					    	<c:forEach begin="1" end="${review.rating}" var="i">
					            ★
					        </c:forEach>
					        <c:forEach begin="1" end="${5 - review.rating}" var="i">
					            ☆
					        </c:forEach>
				        </div>
					
					    <%-- 리뷰 정보 --%>
					    <div class="review-info">
					    	
							
							
					        <div class="review-option-area">
					            <span class="review-option">옵션</span>
					            <span class="review-option-value">${review.stockDTO.ssize}, ${review.stockDTO.color}</span>
					        </div>
					
					        <div class="review-user-info">
					
					            <span class="review-user">
					                작성자 : ${fn:substring(review.fk_userid, 0, 3)}<c:forEach
					                    begin="3"
					                    end="${fn:length(review.fk_userid) - 1}">*</c:forEach>
					            </span>
					
					            <span class="review-date">
					                ${review.rwritedate}
					            </span>
					
					        </div>
					
					
					    </div>
					
					    <%-- 리뷰 내용 --%>
					    <div class="review-content">
					
					        ${review.rcontents}
					
					    </div>
					    
					    <%-- 리뷰 이미지 --%>
					    <c:if test="${not empty review.rimage}">
							<div class="review-image mt-3">
								<img alt="리뷰이미지" src="<%=ctxPath%>/images/${review.rimage}">
							</div>
						</c:if>
			
					</div>
					
				</c:forEach>
	
			</c:if>
		
		</div>
		
		<c:if test="${not empty requestScope.reviewList}"> 
	   		 <%-- 페이지바 --%>
			<nav class="my-5">
	           	<div style='display:flex; width:80%; margin: 0 auto;'>
	             	<ul class="pagination" style='margin:auto;'>
	             		${requestScope.pageBar_review}
	             	</ul>
	          	</div>
	       	</nav>
       	
    	</c:if>

        

    </div>


    <%-- ===================================================== >
         상품 QnA
    < ===================================================== --%>
    <div id="product-qna" class="product-tab-content mb-5">

        <div class="qna-list pb-5 pt-5">
			
			
			<button class="ms-2 write-qna-button" onclick="showQnAWriteModal()">Q&A 작성</button>
			
			
			<div id="qna-ajax-area">
			
				<c:if test="${not empty requestScope.qnaList}">
					
					<c:forEach var="qna" items="${requestScope.qnaList}">
	
					    <%-- Q&A 항목 --%>
					    <div class="qna-item"
					         data-can-open="<c:choose>
					             <c:when test="${qna.islock eq '공개'
					                 or (not empty sessionScope.loginuser
					                 and qna.fk_userid eq sessionScope.loginuser.userid)}">true</c:when>
					             <c:otherwise>false</c:otherwise>
					         </c:choose>">
					
					        <%-- 질문 제목 및 기본 정보 --%>
					        <div class="qna-question">
					
					            <span class="qna-title">
					                ${qna.qtitle}
					            </span>
					
					            <%-- 공개 여부 --%>
					            <c:if test="${qna.islock eq '비공개'}">
					                <span class="qna-lock">🔒</span>
					            </c:if>
					
					            <c:if test="${qna.islock eq '공개'}">
					                <span class="qna-lock empty"></span>
					            </c:if>
					
					            <%-- 작성자 id는 3글자만 보이기 --%>
					            <span class="qna-user">
					                ${fn:substring(qna.fk_userid, 0, 3)}<c:forEach
					                    begin="3"
					                    end="${fn:length(qna.fk_userid) - 1}">*</c:forEach>
					            </span>
					
					            <%-- 작성일 --%>
					            <span class="qna-date">
					                ${qna.qwritedate}
					            </span>
					
					            <%-- 답변 상태 --%>
					            <span class="qna-status">
					                <c:if test="${not empty qna.qanswer}">
					                    답변완료
					                </c:if>
					                <c:if test="${empty qna.qanswer}">
					                    등록상태
					                </c:if>
					            </span>
					
					        </div>
					
					        <%-- 질문 및 답변은 기본적으로 숨김 --%>
					        <div class="qna-answer-area">
					
					            <%-- 질문 내용 --%>
					            <div class="qna-question-content pb-5">
					                ${qna.qcontents}
					            </div>
					
					            <%-- 답변이 존재할 때만 표시 --%>
					            <c:if test="${not empty qna.qanswer}">
					                <div class="qna-answer">
					
					                    <div class="answer-icon">A</div>
					
					                    <div class="answer-content">
					                        ${qna.qanswer}
					                    </div>
					
					                </div>
					            </c:if>
					
					        </div>
					
					    </div>
					
					</c:forEach>
		            
	            </c:if>

			</div>
            
            

        </div>
        
        
        
        <c:if test="${not empty requestScope.qnaList}"> 
	   		 <%-- 페이지바 --%>
			<nav class="my-5">
	           	<div style='display:flex; width:80%; margin: 0 auto;'>
	             	<ul class="pagination" style='margin:auto;'>
	             		${requestScope.pageBar_QnA}
	             	</ul>
	          	</div>
	       	</nav>
       	
    	</c:if>
        

    </div>


</div>



<%--  QnA 작성 모달 --%>
<div class="modal fade" id="qnaWriteModal"
     tabindex="-1"
     aria-labelledby="qnaWriteModalLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <%-- 모달 제목 --%>
            <div class="modal-header">
                <h5 class="modal-title" id="qnaWriteModalLabel">
                    상품 Q&A 작성
                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="닫기"></button>
            </div>

            <%-- 모달 본문 --%>
            
            
            
            <div class="modal-body">
            
	            <div class="mb-3">
		            <label for="checkPublic">
		            	비공개
		            	<input type="checkbox" id="checkPublic" checked/>
		            </label>
	            </div>
            
                <div class="mb-3">
                    <label for="qnaTitle" class="form-label">
                        제목
                    </label>
                    <input type="text"
                           id="qnaTitle"
                           class="form-control"
                           placeholder="제목">
                </div>

                <div class="mb-3">
                    <label for="qnaContents" class="form-label">
                        내용
                    </label>
                    <textarea id="qnaContents"
                              class="form-control"
                              rows="6"
                              placeholder="질문 내용"></textarea>
                </div>
            </div>

            <%-- 하단 버튼들 --%>
            <div class="modal-footer">
                <button type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">
                    취소
                </button>

                <button type="button"
                        class="btn btn-primary"
                        id="btnQnARegister"
                        onclick="goWriteQnA()">
                    등록
                </button>
            </div>

        </div>
    </div>
</div>


<jsp:include page="../../footer.jsp" />