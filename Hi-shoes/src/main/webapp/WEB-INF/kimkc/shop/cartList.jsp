<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>


<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/cartList.css" >
<jsp:include page="../../header.jsp" />

<div class="container">
    <!-- 장바구니 -->


<div class="cart-container">

    <!-- 페이지 제목 -->
    <h1>장바구니</h1>


    <!-- 전체 선택 -->
    <div class="cart-select-all">
        <label>
            <input type="checkbox" id="selectAll">
            전체선택
        </label>
    </div>


    <!-- 일반배송 상품 영역 -->
    <div class="cart-section">

        <!-- 배송 종류 + 상품 개수 -->
        <div class="cart-section-title">
            <span>일반배송 상품</span>
            <strong>3개</strong>
        </div>


        <!-- 상품 목록 -->
        <div class="cart-product-list">


            <!-- 상품 1 -->
            <div class="cart-product">

                <!-- 선택 -->
                <div class="product-check">
                    <input type="checkbox" class="product-checkbox">
                </div>


                <!-- 상품 이미지 -->
                <div class="product-image">
                    <img src="images/product1.jpg" alt="나이키 운동화">
                </div>


                <!-- 상품 정보 -->
                <div class="product-info">

                    <strong class="brand">
                        나이키
                    </strong>

                    <span class="product-name">
                        NIKE COURT VISION LO NN 100
                    </span>

                    <span class="product-size">
                        245
                    </span>

                </div>


                <!-- 수량 -->
                <div class="product-quantity">

                    <div class="quantity-control">
                        <button type="button">-</button>
                        <input type="text" value="1">
                        <button type="button">+</button>
                    </div>

                    <button type="button" class="option-change">
                        변경
                    </button>

                </div>


                <!-- 가격 -->
                <div class="product-price">

                    <del>99,000원</del>

                    <strong>
                        79,000원
                    </strong>

                </div>


                <!-- 바로구매 / 삭제 -->
                <div class="product-actions">

                    <button type="button" class="buy-button">
                        바로구매
                    </button>

                    <button type="button" class="delete-button">
                        삭제
                    </button>

                </div>

            </div>


            <!-- 상품 2 -->
            <div class="cart-product">

                <div class="product-check">
                    <input type="checkbox" class="product-checkbox">
                </div>

                <div class="product-image">
                    <img src="images/product2.jpg" alt="나이키 운동화">
                </div>

                <div class="product-info">

                    <strong class="brand">
                        나이키
                    </strong>

                    <span class="product-name">
                        NIKE COURT VISION LO SUEDE 001
                    </span>

                    <span class="product-size">
                        240
                    </span>

                </div>


                <div class="product-quantity">

                    <div class="quantity-control">
                        <button type="button">-</button>
                        <input type="text" value="1">
                        <button type="button">+</button>
                    </div>

                    <button type="button" class="option-change">
                        변경
                    </button>

                </div>


                <div class="product-price">

                    <strong>
                        99,000원
                    </strong>

                </div>


                <div class="product-actions">

                    <button type="button" class="buy-button">
                        바로구매
                    </button>

                    <button type="button" class="delete-button">
                        삭제
                    </button>

                </div>

            </div>


            <!-- 상품 3 -->
            <div class="cart-product">

                <div class="product-check">
                    <input type="checkbox" class="product-checkbox">
                </div>

                <div class="product-image">
                    <img src="images/product3.jpg" alt="아디다스 운동화">
                </div>

                <div class="product-info">

                    <strong class="brand">
                        아디다스
                    </strong>

                    <span class="product-name">
                        OZGAIA W CBLACK/CBLACK/FTWWHT
                    </span>

                    <span class="product-size">
                        220
                    </span>

                </div>


                <div class="product-quantity">

                    <div class="quantity-control">
                        <button type="button">-</button>
                        <input type="text" value="2">
                        <button type="button">+</button>
                    </div>

                    <button type="button" class="option-change">
                        변경
                    </button>

                </div>


                <div class="product-price">

                    <strong>
                        218,000원
                    </strong>

                </div>


                <div class="product-actions">

                    <button type="button" class="buy-button">
                        바로구매
                    </button>

                    <button type="button" class="delete-button">
                        삭제
                    </button>

                </div>

            </div>

        </div>


        <!-- 선택 삭제 -->
        <div class="cart-select-delete">

            <button type="button">
                선택 삭제
            </button>

        </div>

    </div>


    <!-- ==========================
         결제 금액
    =========================== -->

    <div class="cart-summary">

        <!-- 주문금액 -->
        <div class="summary-item">

            <span>주문금액</span>

            <strong>
                416,000원
            </strong>

        </div>


        <div class="summary-symbol">
            -
        </div>


        <!-- 총 할인금액 -->
        <div class="summary-item">

            <span>총 할인금액</span>

            <strong>
                20,000원
            </strong>

        </div>


        <div class="summary-symbol">
            =
        </div>


        <!-- 결제 예정 금액 -->
        <div class="summary-item">

            <span>결제예정금액</span>

            <strong class="final-price">
                396,000원
            </strong>

        </div>

    </div>


    <!-- 금액 상세 -->
    <div class="price-detail">

        <div class="price-detail-item">

            <div>
                <span>상품금액</span>
                <span>추가 배송비</span>
            </div>

            <div>
                <span>416,000원</span>
                <span>0원</span>
            </div>

        </div>


        <div class="price-detail-item">

            <div>
                <span>상품할인</span>
            </div>

            <div>
                <span>20,000원</span>
            </div>

        </div>

    </div>


    <!-- ==========================
         하단 버튼
    =========================== -->

    <div class="cart-buttons">

        <button type="button" class="continue-shopping">
            계속 쇼핑하기
        </button>

        <button type="button" class="selected-order">
            일반배송 선택상품 주문하기
        </button>

        <button type="button" class="all-order">
            일반배송 전체상품 주문하기
        </button>

    </div>

</div>

</div>


<jsp:include page="../../footer.jsp" />