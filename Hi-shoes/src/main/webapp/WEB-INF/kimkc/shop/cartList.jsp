<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>


<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/cartList.css" >
<jsp:include page="../../header.jsp" />

<script type="text/javascript">
	
	$(function() {
		
		// 결제금액 불러오기
		getTotalCost();
		
		// 체크박스 전체해제/선택
		$('input#selectAll').on("change", function() {
			const bool = $(this).prop("checked");
			
			$('input.product-checkbox').prop("checked", bool);
		});
		
		
		// 장바구니 목록 중 하나라도 체크가 해제되면 전체선택도 해제됨
		$('input.product-checkbox').on("change", function() {
			let flag = true;
			
			$('input.product-checkbox').each(function(index, elmt) {
				const ischeck = $(elmt).prop("checked");
				if(!ischeck) {
					$('input#selectAll').prop("checked", false);
					flag = false;
					return false;
				}
			});

			if (flag) {
				$('input#selectAll').prop("checked", true);
			}
			
		});
		
		// -------------------------------------------------------------------------

	    // 수량 감소 버튼
	    $(document).on("click", ".minus-quantity", function() {
	    	//console.log("- 클릭됨");
	        const $input = $(this).siblings(".cart-quantity");
	        let qty = Number($input.val());

	        if (qty < 1) {
	            qty = 1;
	        }

	        // 최소 수량은 1개
	        if (qty > 1) {
	            qty--;
	        }

	        $input.val(qty);
	    });


	    // 수량 증가 버튼
	    $(document).on("click", ".plus-quantity", function() {
			//console.log("+ 클릭됨");
	        const $input = $(this).siblings(".cart-quantity");
	        let qty = Number($input.val());

	        if (qty < 1) {
	            qty = 1;
	        }

	        // 최대 수량은 999개
	        if (qty < 999) {
	            qty++;
	        }

	        $input.val(qty);
	    });
	    
	 	// -------------------------------------------------------------------------
	});
	
	// -------------------------------------------------------------------------
	
	// 총 결제금액 적용하기
	function getTotalCost() {
		let totalOrderPrice = 0;    // 정상가 기준 주문금액
	    let totalDiscountPrice = 0; // 총 할인금액
	    let totalDeliveryFee = 0;   // 배송비

	    const products = $(".cart-product");

	    products.each(function(index) {

	        const product = $(this);

	        // 상품 가격
	        const regprice = Number(product.attr("data-regprice")) || 0;
	        const saleprice = Number(product.attr("data-saleprice")) || 0;

	        // 해당 상품의 현재 수량
	        const cqty = Number(product.find(".cart-quantity").val()) || 0;

	        // 정상가 기준 주문금액
	        totalOrderPrice += saleprice * cqty;

	        // 정상가보다 판매가가 낮은 상품만 할인 계산
	        if (regprice > saleprice) {
	            totalDiscountPrice += (regprice - saleprice) * cqty;
	        }

	        // 배송비는 첫 번째 상품에서 한 번만 가져온다.
	        if (index === 0) {
	            totalDeliveryFee = Number(product.attr("data-deliveryfee")) || 0;
	        }
	    });

	    // 최종 결제금액
	    const finalPrice =
	        totalOrderPrice - totalDiscountPrice + totalDeliveryFee;

	    // 천 단위 콤마 및 원 표시
	    const formatPrice = price =>
	        price.toLocaleString("en") + "원";
	        
	    //console.log("장바구니에서 전달한 배송비" + totalDeliveryFee);

	    $(".total-order-price").text(formatPrice(totalOrderPrice));
	    $(".total-discount-price").text(formatPrice(totalDiscountPrice));
	    $(".total-delivery-fee").text(formatPrice(totalDeliveryFee));
	    $('input[name="deliveryfee"]').val(totalDeliveryFee);
	    $(".final-price").text(formatPrice(finalPrice));
	}
	
	
	
	
	
	
	
	// -------------------------------------------------------------------------
	
	// 수량 변경 버튼
	function changeCartCqty(snum, btn) {
		if (confirm("해당 상품의 수량을 변경하시겠습니까?")) {
			
			// 클릭한 버튼이 속한 상품 영역에서 수량을 가져온다.
		    const product = $(btn).closest(".cart-product");
		    const cqty = Number(product.find(".cart-quantity").val());
		    
			
			$.ajax({
				url: "<%= ctxPath %>/shop/changeCartCqtyJSON.go",
		        type: "POST",
		        data: {
		            snum: snum,
		            userid: "${sessionScope.loginuser.userid}",
		            cqty: cqty
		        },
		        dataType: "json",
		        success: function(json) {
					if(json.n == 1) {
						alert("수량이 변경되었습니다.");
						// 수량 변경후 결제금액 적용
						getTotalCost();
					}
					else if(json.n == -1) {
						alert("재고수량 이상으로 수량을 변경할 수 없습니다.");
					}
					else {
						alert("수량변경에 실패하였습니다.");
					}
		        	
				},
				
				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }	 
			});
		}
	}
	
	// -------------------------------------------------------------------------
	
	// 장바구니 삭제버튼
	function deleteFromCartList(snum) {
		if (confirm("해당 상품을 장바구니에서 삭제하시겠습니까?")) {
			
			$.ajax({
				url: "<%= ctxPath %>/shop/deleteFromCartListJSON.go",
		        type: "POST",
		        data: {
		            snum: snum
		        },
		        dataType: "json",
		        success: function(json) {
					if(json.n == 1) {
						alert("해당 상품이 장바구니에서 삭제되었습니다.");
						// 삭제이후 결제금액 적용
						//getTotalCost();
						location.reload();	// 새로고침 때리기
					}
					
					else {
						alert("삭제에 실패하였습니다.");
					}
		        	
				},
				
				error: function(request, status, error){
			    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			    }	 
			});
		}
	}
	
	
	// -------------------------------------------------------------------------
	
	// 계속 쇼핑하기 버튼
	function gotoIndex() {
		location.href = "<%=ctxPath %>/index.go";
	}
	
	
	// -------------------------------------------------------------------------------------------
	
	

	// 선택상품 주문하기 버튼 !!!!!!
	function gotoPurchase() {
		
		// 적어도 하나는 체크되어야 함.
		if($('input.product-checkbox:checked').length == 0) {
			alert("최소 1개 이상의 상품을 선택해 주세요.");
			return;
		}
		
		// 일단, 각 항목들의 재고가 충분한지 검사해야 함.
		const allCnt = $('input.product-checkbox').length;	// 전체 장바구니 목록 개수
		
		let alertMsg = '선택하신\n';	// 제품명(spec)<br> 요런식으로 붙여갈거임
		let flag = true;	// 하나라도 재고가 부족하면 false;
		
		let prodList = [];
		
		
		for(let i=0; i<allCnt; i++) {
			if($('input.product-checkbox').eq(i).prop("checked")) {
				const pimage = $('div.product-image').eq(i).find('img').attr('src');
				const pname = $('span.product-name').eq(i).text();
				const brand = $('strong.brand').eq(i).text();
				const spec = $('span.product-size').eq(i).text().trim();
				const snum = $('input.cart-snum').eq(i).val(); 
				const cqty = $('input.cart-quantity').eq(i).val();
				const regprice = $('div.cart-product').eq(i).attr("data-regprice");
				const saleprice = $('div.cart-product').eq(i).attr("data-saleprice");
				
				
				prodList.push({
					 //대표이미지, 제품명, 브랜드, 옵션, snum, 수량, 정가, 판매가 등...
					"pimage" : pimage,
					"pname" : pname,
					"brand" : brand,
					"spec" : spec,
					"snum" : snum,
					"cqty" : cqty,
					"regprice" : regprice,
					"saleprice" : saleprice
				});
				
				//console.log(snum);
				//console.log(cqty);
				//console.log(spec);
				
				$.ajax({
					url: "<%= ctxPath %>/shop/checkStockEnough.go",
			        type: "POST",
			        async: false,
			        data: {
			            snum: snum,
			            cqty: cqty
			        },
			        dataType: "json",
			        success: function(json) {
						if(json.n == 1) {	// 1 이면 재고부족
							flag = false;
							alertMsg += json.pname + "(" + spec + ")\n";
						}
						else if(json.n == -1) {
							alert("재고 확인 중 오류가 발생하였습니다.");
							return;
						}
						
					},
					
					error: function(request, status, error){
				    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				    }	
				});
			}
		}
		
		// 선택항목 중 하나라도 재고가 부족하면
		if (!flag) {
			alert(alertMsg + "상품의 재고가 부족합니다. 선택 수량을 변경해주세요.");
			return;
		}
		/* 
		if (flag) {
			alert("재고가 모두 충분합니다~");
		}   */
		
		// 이제 결제 화면에 넘겨줘야 함...		
		// 대표이미지, 브랜드, 옵션, 수량, 정가, 판매가
		// snum, cqty 세트로 넘기기
		const frm = document.getElementById('frm-purchase');
		const input1 = document.querySelector('input[name="prodList"]');
		//const input2 = document.querySelector('input[name="deliveryfee"]');
		
		frm.method = "POST";
		frm.action = "<%= ctxPath %>/shop/purchaseProduct.go";
		input1.value = JSON.stringify(prodList);	
		
		frm.submit();
		
	}
	
	
</script>














<div class="container">
    


<div class="cart-container">

    
    <h1>장바구니</h1>


    <%-- 전체 선택 --%>
    <div class="cart-select-all">
        <label>
            <input type="checkbox" id="selectAll">
            전체선택
        </label>
    </div>


    <%-- 상품 영역 --%>
    <div class="cart-section">

        <%-- 상품 개수 --%>
        <div class="cart-section-title">
            <span>장바구니 상품</span>
           	<strong>${fn:length(requestScope.myCartList)}개</strong>
            <%-- size 왜 안먹는지 몰겠음 ;; --%>
        </div>


        <%-- 상품 목록 --%>
        <div class="cart-product-list">

		
			 <c:choose>

		        <%-- 장바구니가 비어 있는 경우 --%>
		        <c:when test="${empty requestScope.myCartList}">
		            <div class="cart-empty">
		                <p>장바구니에 담긴 상품이 없습니다.</p>
		                <p>마음에 드는 상품을 장바구니에 담아보세요.</p>
		            </div>
		        </c:when>
		
		        <%-- 장바구니에 상품이 있는 경우 --%>
		        <c:otherwise>
		
		            <c:forEach var="cart" items="${requestScope.myCartList}">
		
		                <div class="cart-product"
	                		data-regprice="${cart.regprice}"
					     	data-saleprice="${cart.saleprice}"
				    	 	data-deliveryfee="${cart.deliveryfee}">	<%-- 결제금액 계산하려고 넣어둠 --%>
		
		                    <%-- 선택 --%>
		                    <div class="product-check">
		                        <input type="checkbox" class="product-checkbox">
		                    </div>
		
		                    <%-- 상품 이미지 --%>
		                    <div class="product-image">
		                        <img src="<%=ctxPath %>/images/kimkc/product/${cart.pimage1}"
		                             alt="${cart.pname}">
		                    </div>
		
		                    <%-- 상품 정보 --%>
		                    <div class="product-info">
		                        <strong class="brand">${cart.brand}</strong>
		                        <span class="product-name">${cart.pname}</span>
		                        <span class="product-size">
		                            ${cart.ssize} / ${cart.color}
		                        </span>
		                        <input type="hidden" class="cart-snum" value="${cart.snum}"/>
		                    </div>
		
		                    <%-- 수량 --%>
		                    <div class="product-quantity">
		                        <div class="quantity-control">
		                            <button type="button" class="minus-quantity">-</button>
		                            <input type="text" value="${cart.cqty}" class="cart-quantity" readonly>
		                            <button type="button" class="plus-quantity">+</button>
		                        </div>
		
		                        <button type="button" class="option-change" onclick="changeCartCqty(${cart.snum}, this)">
		                            변경
		                        </button>
		                    </div>
		
		                    <%-- 가격 --%>
		                    <div class="product-price">
		                        <c:if test="${cart.regprice > cart.saleprice}">
		                            <del><fmt:formatNumber value="${cart.regprice}" type="number"/>원</del>
		                        </c:if>
		
		                        <strong><fmt:formatNumber value="${cart.saleprice}" type="number"/>원</strong>
		                    </div>
		
		                    <%-- 삭제 --%>
		                    <div class="product-actions">
		                        <button type="button" class="delete-button" onclick="deleteFromCartList(${cart.snum})">
		                            삭제
		                        </button>
		                    </div>
		
		                </div>
		
		            </c:forEach>
		
		        </c:otherwise>
		
		    </c:choose>
        

        </div>


    </div>


    <%-- ===================================================== >
         결제 금액
    < ===================================================== --%>

    <div class="cart-summary">

        <%-- 주문금액 --%>
        <div class="summary-item">

            <span>주문금액</span>

            <strong class="total-order-price">0원</strong>

        </div>


        <div class="summary-symbol">
            -
        </div>


        <%-- 총 할인금액 --%>
        <div class="summary-item">

            <span>총 할인금액</span>

            <strong class="total-discount-price">0원</strong>

        </div>


        <div class="summary-symbol">
            =
        </div>


        <%-- 결제예정금액 --%>
        <div class="summary-item">

            <span>결제예정금액</span>

            <strong class="final-price">0원</strong>

        </div>

    </div>


    <%-- 금액 상세 --%>
    <div class="price-detail">

        <div class="price-detail-item">

            <div>
                <span>상품금액</span>
                <span>추가 배송비</span>
            </div>

            <div>
                <span class="total-order-price">0원</span>
                <span class="total-delivery-fee">0원</span>
            </div>

        </div>


        <div class="price-detail-item">

            <div>
                <span>상품할인</span>
            </div>

            <div>
                <span class="total-discount-price">0원</span>
            </div>

        </div>

    </div>


    <%-- ===================================================== >
         하단 버튼 구간
    < ===================================================== --%>

    <div class="cart-buttons">

        <button type="button" class="continue-shopping" onclick="gotoIndex()">
            계속 쇼핑하기
        </button>

		<form id="frm-purchase">
			<input name="prodList" type="hidden"/>
			<input name="deliveryfee" type="hidden"/>
	        <button type="button" class="selected-order" onclick="gotoPurchase()">
	            선택상품 주문하기
	        </button>
		</form>
        

    </div>

</div>

</div>


<jsp:include page="../../footer.jsp" />