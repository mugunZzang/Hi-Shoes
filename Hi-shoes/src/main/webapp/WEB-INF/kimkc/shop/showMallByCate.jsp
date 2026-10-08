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



<script type="text/javascript"
        src="<%= ctxPath%>/js/kimkc/sliderControl.js"></script>


<script type="text/javascript">

	$(function() {
		
		// 필터 - 초기화 버튼 클릭 시
		$('button#btn-filter-reset').on("click", function() {
			// 브랜드 선택 해제
			$('select[class="brand-select"]').val('');
			
			// 사이즈 선택 해제
			$('input:checkbox[class="checkbox-sizes"]').each(function(index,elmt) {
				$(elmt).prop('checked', false);
			});
			
			// 색상 선택 해제
			$('input:checkbox[name="color"]').each(function(index,elmt) {
				$(elmt).prop('checked', false);
			});
			
			// 가격 범위 초기화
			$('input#price-min').val(10000);
			$('input#price-max').val(500000);
			$('span.span-price-min').html( Number( $('input#price-min').val() ).toLocaleString('en') );
			$('span.span-price-max').html( Number( $('input#price-max').val() ).toLocaleString('en') );
			
			
			// 검색어 초기화
			$('input#filter-keyword').val("");
			
		});
		
		
		
		// 필터 - 검색 버튼 클릭 시
		$('button#btn-filter-submit').on("click", function() {
			
			// 사이즈
			const ssizeArr = [];
			$('input:checkbox[class="checkbox-sizes"]').each(function(index,elmt) {
				if($(elmt).prop('checked')) {
					ssizeArr.push($(elmt).val());
				}
			});
			const str_ssize = ssizeArr.join(',');
			
			// 브랜드
			const brand = $('select[class="brand-select"]').val();
			
			// 색상			
			const colorArr = [];
			$('input:checkbox[name="color"]').each(function(index,elmt) {
				if($(elmt).prop('checked')) {
					colorArr.push($(elmt).val());
				}
			});
			const str_color = colorArr.join(',');
			
			// 검색어
			const keyword = $('input#filter-keyword').val().trim();
			
			// 최소가격
			const min_price = $('input#price-min').val();	
			// 최대가격
			const max_price = $('input#price-max').val();
			
			
			
/* 			String str_ssize = "";		// 사이즈 "270,275,290" 요런식으로 받아옴
			String str_brand = "";		// 브랜드 "아디다스,아식스,뉴발란스" 요런식으로 받아옴
			String str_color = "";		// 색상 "BLACK,WHITE,SILVER" 
			String keyword = "";		// 검색어
			String min_price = "";		// 최소가격 "10000"
			String max_price = ""; */
			 	
			location.href =
		        "<%=ctxPath%>/shop/showMallByCate.go"
		        + "?category=" + "${requestScope.category}"
		        + "&str_ssize=" + encodeURIComponent(str_ssize)
		        + "&brand=" + encodeURIComponent(brand || '')
		        + "&str_color=" + encodeURIComponent(str_color)
		        + "&keyword=" + encodeURIComponent(keyword)
		        + "&min_price=" + encodeURIComponent(min_price)
		        + "&max_price=" + encodeURIComponent(max_price);
			 
			
		});
		
	});







</script>



<div class="search-container">


    <%-- 전체 쇼핑몰 영역 --%>

    <div class="shop-layout">


        <%-- 좌측 필터 영역 --%>
		

        <aside class="filter-sidebar">

            <!-- 필터 제목 -->
            <div class="filter-header">

                <strong>FILTER</strong>

                

            </div>


            <%-- 브랜드 필터 --%>

            <div class="filter-section">

                <div class="filter-section-header">
                    <strong>브랜드</strong>

                    
                </div>


                <div class="filter-section-content">

                    <select class="brand-select"
                            name="brand"
                            multiple
                            size="5">

                        
                        <c:if test="${not empty requestScope.brandList}">
                        	<c:forEach var="cdto" items="${requestScope.brandList}">
                        		<option value="${cdto.brand}">${cdto.brand}</option>
                        	</c:forEach>
                        
                        </c:if>
                        <%-- <option value="AKIII CLASSIC">AKIII CLASSIC</option>--%>


                    </select>

                </div>

            </div>


           	<%-- 사이즈 필터 --%>

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>사이즈</strong>

                    

                </div>


                <div class="filter-section-content">

                    <div class="size-filter">

                        <c:forEach begin="200" end="300" step="5" var="size">

                            <label class="size-item">

                                <input type="checkbox"
                                	   class="checkbox-sizes"
                                       name="size"
                                       value="${size}">

                                <span>${size}</span>

                            </label>

                        </c:forEach>

                    </div>

                </div>

            </div>


            <%-- 색상 필터 --%>

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>색상</strong>

                    

                </div>


                <div class="filter-section-content">

                    <div class="color-filter">


                        <%-- BLACK --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BLACK">

                            <span class="color-chip color-black"></span>
                        </label>

						
                        <%-- WHITE --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="WHITE">

                            <span class="color-chip color-white"></span>
                        </label>


                        <%-- SILVER --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="SILVER">

                            <span class="color-chip color-silver"></span>
                        </label>


                        <%-- BROWN --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BROWN">

                            <span class="color-chip color-brown"></span>
                        </label>


                        <%-- IVORY --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="IVORY">

                            <span class="color-chip color-ivory"></span>
                        </label>


                        <%-- RED --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="RED">

                            <span class="color-chip color-red"></span>
                        </label>


                        <%-- BLUE --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="BLUE">

                            <span class="color-chip color-blue"></span>
                        </label>


                        <%-- YELLOW --%>
                        <label class="color-item">
                            <input type="checkbox"
                                   name="color"
                                   value="YELLOW">

                            <span class="color-chip color-yellow"></span>
                        </label>


                        <%-- 기타 색상 --%>
                        <label class="color-item">

                            <input type="checkbox"
                                   name="color"
                                   value="OTHER">

                            <span class="color-chip color-rainbow"></span>

                        </label>

                    </div>

                </div>

            </div> 


            
			<%-- 가격 필터 (양방향 슬라이더) --%>

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>가격</strong>

                    

                </div>


                <div class="filter-section-content">

                    <div class="price-slider">

                        <div class="price-track"></div>

                        <div class="price-range"></div>

                        <input type="range"
                               class="price-range-input price-min"
                               id="price-min"
                               step="1000"
                               min="10000"
                               max="500000"
                               value="10000">

                        <input type="range"
                               class="price-range-input price-max"
                               id="price-max"
                               step="1000"
                               min="10000"
                               max="500000"
                               value="500000">

                    </div>


                    <div class="price-value">
                        <span class="span-price-min">10,000</span>~<span class="span-price-max">500,000</span>원
                    </div>

                </div>

            </div>


            <%-- 검색어 필터 --%>

            <div class="filter-section">

                <div class="filter-section-header">

                    <strong>검색어</strong>

                    

                </div>


                <div class="filter-section-content">

                    <div class="keyword-search">

                        <input type="text"
                        	   id="filter-keyword"
                               name="keyword"
                               placeholder="검색어">

                        

                    </div>


                    <p class="keyword-info">
                        함께 검색할 검색어를 추가해주세요.
                    </p>

                </div>

            </div>


            <%-- 필터 버튼들 --%>

            <div class="filter-buttons">

                <button type="button"
                        class="reset-button"
                        id="btn-filter-reset">
                    초기화
                </button>

                <button type="button"
                        class="search-button"
                        id="btn-filter-submit">
                    검색
                </button>

            </div>

        </aside>
        



        <%-- 상품 진열 구역 --%>

        <main class="shop-content">


            <!-- 페이지 제목 -->

            <h1 class="shop-title">
                신발
            </h1>


            <%-- 카테고리 선택 nav --%>

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



            <%-- 실제 상품 진열될 곳 --%>

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
					
					<%-- 페이지바 --%>
					<nav class="my-5">
			           	<div style='display:flex; width:80%; margin: 0 auto;'>
			             	<ul class="pagination" style='margin:auto;'>
			             		${requestScope.pageBar}
			             	</ul>
			          	</div>
		        	</nav>


                </c:if>


            </div>


        </main>

    </div>

</div>



<jsp:include page="../../footer.jsp" />