<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="../../header.jsp" />

<link rel="stylesheet" type="text/css"
      href="<%= ctxPath%>/css/kimkc/showMallByCate.css">

<link rel="stylesheet" type="text/css"
      href="<%= ctxPath%>/css/kimkc/sliderControl.css">

<script type="text/javascript"
        src="<%= ctxPath%>/js/kimkc/sliderControl.js"></script>


<div class="search-container">


    <!-- ==================================================
         전체 쇼핑 영역
    =================================================== -->

    <div class="shop-layout">


        <!-- ==================================================
             왼쪽 필터
        =================================================== -->

        <aside class="filter-sidebar">

            <!-- 필터 제목 -->
            <div class="filter-header">

                <strong>FILTER</strong>

                <button type="button" class="filter-toggle">
                    ‹
                </button>

            </div>


            <!-- ==================================================
                 브랜드
            =================================================== -->

            <div class="filter-section">

                <div class="filter-section-header">
                    <strong>브랜드</strong>

                    <span class="filter-arrow">⌃</span>
                </div>


                <div class="filter-section-content">

                    <select class="brand-select"
                            name="brand"
                            multiple
                            size="5">

                        <option value="ADIDAS" selected>ADIDAS</option>
                        <option value="AKIII CLASSIC">AKIII CLASSIC</option>
                        <option value="ANALOG MOOD">ANALOG MOOD</option>
                        <option value="ASICS">ASICS</option>
                        <option value="CONVERSE">CONVERSE</option>
                        <option value="NEW BALANCE">NEW BALANCE</option>
                        <option value="NIKE">NIKE</option>
                        <option value="PUMA">PUMA</option>
                        <option value="VANS">VANS</option>

                    </select>

                </div>

            </div>


            <!-- ==================================================
                 사이즈
            =================================================== -->

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>사이즈</strong>

                    <span class="filter-arrow">⌃</span>

                </div>


                <div class="filter-section-content">

                    <div class="size-filter">

                        <c:forEach begin="200" end="300" step="5" var="size">

                            <label class="size-item">

                                <input type="checkbox"
                                       name="size"
                                       value="${size}">

                                <span>${size}</span>

                            </label>

                        </c:forEach>

                    </div>

                </div>

            </div>


            <!-- ==================================================
                 색상
            =================================================== -->

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>색상</strong>

                    <span class="filter-arrow">⌃</span>

                </div>


                <div class="filter-section-content">

                    <div class="color-filter">


                        <!-- BLACK -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BLACK">

                            <span class="color-chip color-black"></span>
                        </label>


                        <!-- WHITE -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="WHITE">

                            <span class="color-chip color-white"></span>
                        </label>


                        <!-- SILVER -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="SILVER">

                            <span class="color-chip color-silver"></span>
                        </label>


                        <!-- BROWN -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BROWN">

                            <span class="color-chip color-brown"></span>
                        </label>


                        <!-- IVORY -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="IVORY">

                            <span class="color-chip color-ivory"></span>
                        </label>


                        <!-- RED -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="RED">

                            <span class="color-chip color-red"></span>
                        </label>


                        <!-- BLUE -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BLUE">

                            <span class="color-chip color-blue"></span>
                        </label>


                        <!-- YELLOW -->
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="YELLOW">

                            <span class="color-chip color-yellow"></span>
                        </label>


                        <!-- 기타 색상 -->
                        <label class="color-item">

                            <input type="checkbox"
                                   name="color"
                                   value="OTHER">

                            <span class="color-chip color-rainbow"></span>

                        </label>

                    </div>

                </div>

            </div>


            <!-- ==================================================
                 가격
            =================================================== -->

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>가격</strong>

                    <span class="filter-arrow">⌃</span>

                </div>


                <div class="filter-section-content">

                    <div class="price-slider">

                        <div class="price-track"></div>

                        <div class="price-range"></div>

                        <input type="range"
                               class="price-range-input price-min"
                               min="8900"
                               max="568000"
                               value="8900">

                        <input type="range"
                               class="price-range-input price-max"
                               min="8900"
                               max="568000"
                               value="568000">

                    </div>


                    <div class="price-value">
                        8,900~568,000원
                    </div>

                </div>

            </div>


            <!-- ==================================================
                 검색어
            =================================================== -->

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>검색어</strong>

                    <span class="filter-arrow">⌃</span>

                </div>


                <div class="filter-section-content">

                    <div class="keyword-search">

                        <input type="text"
                               name="keyword"
                               placeholder="검색어">

                        <button type="button">
                            +
                        </button>

                    </div>


                    <p class="keyword-info">
                        함께 검색할 검색어를 추가해주세요.
                    </p>

                </div>

            </div>


            <!-- ==================================================
                 필터 버튼
            =================================================== -->

            <div class="filter-buttons">

                <button type="button"
                        class="reset-button">
                    초기화
                </button>

                <button type="button"
                        class="search-button">
                    검색
                </button>

            </div>

        </aside>



        <!-- ==================================================
             오른쪽 상품 영역
        =================================================== -->

        <main class="shop-content">


            <!-- 페이지 제목 -->

            <h1 class="shop-title">
                신발
            </h1>


            <!-- ==================================================
                 카테고리 nav
            =================================================== -->

            <nav class="category-nav">

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=">
                    전체
                </a>

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=운동화">
                    운동화
                </a>

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=스니커즈">
                    스니커즈
                </a>

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=샌들">
                    샌들
                </a>

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=구두">
                    구두
                </a>

                <a class="category-nav-items"
                   href="<%= ctxPath%>/shop/showMallByCate.go?category=부츠">
                    부츠
                </a>

            </nav>



            <!-- ==================================================
                 상품 목록
            =================================================== -->

            <div class="product-list">


                <c:if test="${empty requestScope.prodList}">

                    <span class="empty-product">
                        상품이 없어요.
                    </span>

                </c:if>



                <c:if test="${not empty requestScope.prodList}">

                    <c:forEach var="pdto"
                               items="${requestScope.prodList}">


                        <!-- 상품 -->

                        <div class="product-item">

                            <a href="<%=ctxPath %>/shop/productDetail.go?pnum=${pdto.pnum}"
                               class="product-link">


                                <!-- 상품 이미지 -->

                                <div class="product-image">

                                    <img
                                        src="${pageContext.request.contextPath}/images/kimkc/product/${pdto.pimage1}"
                                        alt="${pdto.catalogueDTO.pname}">

                                </div>



                                <!-- 상품 정보 -->

                                <div class="product-info">


                                    <!-- 브랜드 -->

                                    <div class="product-brand">

                                        ${pdto.catalogueDTO.brand}

                                    </div>



                                    <!-- 상품명 -->

                                    <div class="product-name">

                                        <span class="product-badge">
                                            공용
                                        </span>

                                        <span class="product-title">
                                            ${pdto.catalogueDTO.pname}
                                        </span>

                                    </div>



                                    <!-- 가격 -->

                                    <div class="product-price">

                                        <strong>
                                            <fmt:formatNumber
                                                value="${pdto.catalogueDTO.saleprice}"
                                                type="number" />
                                        </strong>

                                        <span>
                                            원
                                        </span>

                                    </div>



                                    <!-- 상품 태그 -->

                                    <div class="product-tags">
                                    </div>


                                </div>

                            </a>

                        </div>


                    </c:forEach>

                </c:if>


            </div>


        </main>

    </div>

</div>



<jsp:include page="../../footer.jsp" />