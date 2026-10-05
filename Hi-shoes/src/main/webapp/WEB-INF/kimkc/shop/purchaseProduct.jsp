<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/purchaseProduct.css" >
<jsp:include page="../../header.jsp" />



<div class="order-container">

    <!-- =====================================
         페이지 제목
    ====================================== -->

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


            <!-- 상품 1 -->
            <div class="order-product">

                <!-- 상품 이미지 -->
                <div class="order-product-image">
                    <img src="images/product1.jpg"
                         alt="아디다스 OZGAIA W">
                </div>


                <!-- 상품 정보 -->
                <div class="order-product-info">

                    <strong class="brand">
                        아디다스
                    </strong>

                    <span class="product-name">
                        OZGAIA W
                    </span>

                    <span class="product-option">
                        220 / 2개
                    </span>

                </div>


                <!-- 상품 가격 -->
                <div class="order-product-price">

                    <strong>
                        218,000
                    </strong>

                    <span>원</span>

                </div>

            </div>


            <!-- 상품 2 -->
            <div class="order-product">

                <div class="order-product-image">
                    <img src="images/product2.jpg"
                         alt="나이키 NIKE COURT VISION LO NN">
                </div>


                <div class="order-product-info">

                    <strong class="brand">
                        나이키
                    </strong>

                    <span class="product-name">
                        NIKE COURT VISION LO NN
                    </span>

                    <span class="product-option">
                        245 / 1개
                    </span>

                </div>


                <div class="order-product-price">

                    <del>
                        99,000 원
                    </del>

                    <strong class="discount-price">
                        79,000
                    </strong>

                    <span>원</span>

                </div>

            </div>


            <!-- 상품 3 -->
            <div class="order-product">

                <div class="order-product-image">
                    <img src="images/product3.jpg"
                         alt="나이키 NIKE COURT VISION LO SUEDE">
                </div>


                <div class="order-product-info">

                    <strong class="brand">
                        나이키
                    </strong>

                    <span class="product-name">
                        NIKE COURT VISION LO SUEDE
                    </span>

                    <span class="product-option">
                        240 / 1개
                    </span>

                </div>


                <div class="order-product-price">

                    <strong>
                        99,000
                    </strong>

                    <span>원</span>

                </div>

            </div>

        </div>


        <!-- 주문 금액 요약 -->
        <div class="order-price-summary">

            <!-- 주문금액 -->
            <div class="summary-item">

                <span>주문금액</span>

                <strong>
                    416,000원
                </strong>

            </div>


            <!-- - -->
            <div class="summary-symbol">
                -
            </div>


            <!-- 할인금액 -->
            <div class="summary-item">

                <span>총 할인금액</span>

                <strong>
                    20,000원
                </strong>

            </div>


            <!-- = -->
            <div class="summary-symbol">
                =
            </div>


            <!-- 결제예정금액 -->
            <div class="summary-item">

                <span>결제예정금액</span>

                <strong class="final-price">
                    396,000원
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

                    <label>
                        <input type="checkbox" checked>
                        회원정보와 동일
                    </label>

                </div>


                <div class="customer-form">

                    <!-- 이름 -->
                    <div class="form-row">

                        <label>
                            이름
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="orderName"
                            value="김기찬"
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
                            value="01044633370"
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
                            value="asybal23@naver.com"
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
                <div class="delivery-method">

                    일반택배

                </div>


                <div class="delivery-form">

                    <!-- 받는 사람 -->
                    <div class="form-row">

                        <label>
                            이름
                            <span>*</span>
                        </label>

                        <input
                            type="text"
                            name="receiverName"
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
                        >

                    </div>


                    <!-- 주소 -->
                    <div class="form-row address-row">

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
                                >

                                <button type="button">
                                    우편번호 찾기
                                </button>

                            </div>


                            <input
                                type="text"
                                name="address"
                                placeholder="주소"
                            >


                            <input
                                type="text"
                                name="addressDetail"
                                placeholder="상세주소"
                            >

                        </div>

                    </div>


                    <!-- 배송 요청사항 -->
                    <div class="form-row delivery-request-row">

                        <label>
                            배송시 요청사항
                        </label>

                        <div class="delivery-request">

                            <select name="deliveryRequest">

                                <option value="">
                                    배송 시 요청사항을 선택해주세요.
                                </option>

                                <option value="door">
                                    문 앞에 놓아주세요.
                                </option>

                                <option value="security">
                                    경비실에 맡겨주세요.
                                </option>

                                <option value="call">
                                    배송 전 연락해주세요.
                                </option>

                                <option value="etc">
                                    직접 입력
                                </option>

                            </select>


                            <input
                                type="text"
                                name="deliveryMessage"
                                placeholder="배송 메시지는 40자 이내로 입력해 주세요."
                            >

                        </div>

                    </div>

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

                <strong>
                    416,000 원
                </strong>

            </div>


            <!-- 총 배송비 -->
            <div class="payment-row">

                <span>
                    총 배송비
                </span>

                <strong>
                    0 원
                </strong>

            </div>


            <!-- 총 할인금액 -->
            <div class="payment-row">

                <span>
                    총 할인금액
                    <button type="button" class="discount-detail">
                        ▼
                    </button>
                </span>

                <strong>
                    -20,000 원
                </strong>

            </div>


            <!-- 결제 예정 금액 -->
            <div class="payment-total">

                <span>
                    총 결제예정금액
                </span>

                <strong>
                    396,000
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