<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/purchaseProduct.css" >
<jsp:include page="../../header.jsp" />



<script type="text/javascript">
	
	$(function() {
		getTotalCost();
	});
	
	
	// 결제 금액 정리하기
	function getTotalCost() {
		let totalOrderPrice = 0;    // 정상가 기준 주문금액
	    let totalDiscountPrice = 0; // 총 할인금액
	    let totalDeliveryFee = 0;   // 배송비

	    const allCnt = $(".order-product").length;

	    for(let i=0; i<allCnt; i++) {
	    	const regprice = Number($('del.regular-price').eq(i).attr("data-regprice"));
	    	//$('del.regular-price').eq(i).text(regprice.toLocaleString('en'));
	    	//console.log("정가" + regprice);
	    	
	    	const saleprice = Number($('strong.discount-price').eq(i).attr("data-saleprice"));
	    	//$('del.discount-price').eq(i).text(saleprice.toLocaleString('en'));
	    	//console.log("세일가" + saleprice);
	    	
	    	const cqty = Number($("input.product-cqty").eq(i).val());
	        //console.log("수량" + cqty);
	        
	     	// 정상가 기준 주문금액
	        totalOrderPrice += regprice * cqty;

	        // 정상가보다 판매가가 낮은 상품만 할인 계산
	        if (regprice > saleprice) {
	            totalDiscountPrice += (regprice - saleprice) * cqty;
	        }

	        
	    }
	 	// 배송비는 한 번만 가져온다.
        totalDeliveryFee = Number("${requestScope.deliveryfee}");
	    
	 	// 최종 결제금액
	    const finalPrice =
	        totalOrderPrice - totalDiscountPrice + totalDeliveryFee;

	    // 천 단위 콤마 및 원 표시
	    const formatPrice = price =>
	        price.toLocaleString("en") + "원";

	    //console.log("--------------------");
	    //console.log("총액" + totalOrderPrice);
	    //console.log("할인액" + totalDiscountPrice);
	    //console.log("배송비" + totalDeliveryFee);
	    //console.log("최종금액" + finalPrice);
	    
	    $('strong#total-regprice1').text(formatPrice(totalOrderPrice));
	    $('strong#total-regprice2').text(formatPrice(totalOrderPrice));
	    $('strong#total-discount1').text(formatPrice(totalDiscountPrice));
	    $('strong#total-discount2').text("-" + formatPrice(totalDiscountPrice));
	    $('input.deliveryfee').val(totalDeliveryFee);
	    $('strong#total-price1').text(formatPrice(finalPrice));
	    $('strong#total-price2').text(formatPrice(finalPrice));
	    
	    
	    
	}

</script>


<div class="order-container">


    <h1>주문서작성/결제</h1>


    <!-- =====================================
         주문 리스트
    ====================================== -->

    <section class="order-list-section">

        <h2>주문리스트</h2>

        <!-- 배송 상품 제목 -->
        <div class="order-list-title">
            <span>배송</span>
            <strong>3개</strong>
        </div>


        <!-- 상품 목록 -->
        <div class="order-product-list">


			<c:forEach var="prod" items="${requestScope.prodList}">
	            <!-- 상품 1개 -->
	            <div class="order-product">
	
	                <div class="order-product-image">
	                    <img src="${prod.pimage}"
	                         alt="${prod.pname}">
	                </div>
	
	                <div class="order-product-info">
	
	                    <strong class="brand">
	                        ${prod.brand}
	                    </strong>
	
	                    <span class="product-name">
	                        ${prod.pname}
	                    </span>
	
	                    <span class="product-option">
	                        ${prod.spec} / ${prod.cqty}개
	                    </span>
	                    <input class="product-cqty" type="hidden" value="${prod.cqty}"/>
	
	                </div>
	
	
	                <div class="order-product-price">
						<c:if test="${prod.regprice > prod.saleprice}">
		                    <del class="regular-price" data-regprice="${prod.regprice}">
		                        <fmt:formatNumber value="${prod.regprice}" type="number"/>
		                    </del>
		
		                    <strong class="discount-price" data-saleprice="${prod.saleprice}">
		                        <fmt:formatNumber value="${prod.saleprice}" type="number"/>
		                    </strong>
	                    </c:if>
	                    
	                    <c:if test="${prod.regprice == prod.saleprice}">
	                    	<del class="regular-price" style="display: none;" data-regprice="${prod.regprice}">
		                        <fmt:formatNumber value="${prod.regprice}" type="number"/>
		                    </del>
		                    <strong class="discount-price" id="normal-price" data-saleprice="${prod.saleprice}">
		                        <fmt:formatNumber value="${prod.saleprice}" type="number"/>
		                    </strong>
						</c:if>
						
						
	                    <span>원</span>
	
	                </div>
	
	            </div>
			</c:forEach>

       

        </div>


        <!-- 주문 금액 요약 -->
        <div class="order-price-summary">

            <!-- 주문금액 -->
            <div class="summary-item">

                <span>주문금액</span>

                <strong id="total-regprice1">
                    0원
                </strong>

            </div>


            <!-- - -->
            <div class="summary-symbol">
                -
            </div>


            <!-- 할인금액 -->
            <div class="summary-item">

                <span>총 할인금액</span>

                <strong id="total-discount1">
                    0원
                </strong>

            </div>


            <!-- = -->
            <div class="summary-symbol">
                =
            </div>


            <!-- 결제예정금액 -->
            <div class="summary-item">

                <span>결제예정금액</span>

                <strong class="final-price" id="total-price1">
                    0원
                </strong>

            </div>

        </div>

    </section>



    <!-- =====================================
         주문자 / 결제 영역
    ====================================== -->

    <div class="order-content">


        <!-- =================================
             왼쪽 영역
        ================================== -->

        <main class="order-main">


            <!-- =================================
                 주문 고객정보
            ================================== -->

            <section class="customer-section">

                <div class="section-header">

                    <h2>주문 고객정보</h2>


                </div>


                <div class="customer-form">

                    <!-- 이름 -->
                    <div class="form-row">

                        <label>
                            업체명
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="orderName"
                            value="${requestScope.member.company}"
                            readonly
                        >

                    </div>


                    <!-- 휴대폰번호 -->
                    <div class="form-row">

                        <label>
                            휴대폰번호
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="orderPhone"
                            value="${requestScope.member.mobile}"
                            readonly
                        >

                    </div>


                    <!-- 이메일 -->
                    <div class="form-row">

                        <label>
                            이메일
                            <span>*</span>
                        </label>

                        <input
                            type="email"
                            name="orderEmail"
                            value="${requestScope.member.email}"
                            readonly
                        >

                    </div>

                </div>

            </section>



            <!-- =================================
                 배송 정보
            ================================== -->

            <section class="delivery-section">

                <h2>배송 정보</h2>


                <!-- 배송 방법 -->
                <!-- <div class="delivery-method">

                    일반택배

                </div> -->


                <div class="delivery-form">

                    <!-- 받는 사람 -->
                    <div class="form-row">

                        <label>
                            업체명
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="receiverName"
                            value="${requestScope.member.company}"
                            readonly
                        >

                    </div>


                    <!-- 휴대폰번호 -->
                    <div class="form-row">

                        <label>
                            휴대폰번호
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="receiverPhone"
                            value="${requestScope.member.mobile}"
                            readonly
                        >

                    </div>


                    <!-- 주소 -->
                    <div class="form-row address-row pt-3">

                        <label>
                            주소
                            <span>*</span>
                        </label>

                        <div class="address-input">

                            <div class="zipcode">

                                <input
                                    type="text"
                                    name="zipcode"
                                    placeholder="우편번호"
                                    value="${requestScope.member.postcode}"
                                    readonly
                                >

                                

                            </div>


                            <input
                                type="text"
                                name="address"
                                placeholder="주소"
                                value="${requestScope.member.address}"
                                readonly
                            >


                            <input
                                type="text"
                                name="addressDetail"
                                placeholder="상세주소"
                                value="${requestScope.member.detailaddress}"
                            >
                            
                            <input
                                type="text"
                                name="extraaddress"
                                placeholder="추가정보"
                                value="${requestScope.member.extraaddress}"
                                readonly
                            >

                        </div>

                    </div>


                    <!-- 배송 요청사항 -->
                    

                </div>

            </section>

        </main>



        <!-- =================================
             오른쪽 결제정보
        ================================== -->

        <aside class="payment-section">

            <h2>결제정보</h2>


            <!-- 구분선 -->
            <div class="payment-line"></div>


            <!-- 총 정상가 -->
            <div class="payment-row">

                <span>
                    총 정상가
                </span>

                <strong id="total-regprice1">
                    0 원
                </strong>

            </div>


            <!-- 총 배송비 -->
            <div class="payment-row">

                <span>
                    총 배송비
                </span>

                <strong>
                    <fmt:formatNumber value="${requestScope.deliveryfee}" type="number"/> 원
                </strong>
				<input class="product-deliveryfee" type="hidden" value="${requestScope.deliveryfee}"/>
            </div>


            <!-- 총 할인금액 -->
            <div class="payment-row">

                <span>
                    총 할인금액
                    
                </span>

                <strong id="total-discount2">
                    -0 원
                </strong>

            </div>


            <!-- 결제 예정 금액 -->
            <div class="payment-total">

                <span>
                    총 결제예정금액
                </span>

                <strong id="total-price2">
                    0
                    <small>원</small>
                </strong>

            </div>


            <!-- 결제 버튼 -->
            <button
                type="button"
                class="payment-button">
                결제하기
            </button>

        </aside>

    </div>

</div>



<jsp:include page="../../footer.jsp" />