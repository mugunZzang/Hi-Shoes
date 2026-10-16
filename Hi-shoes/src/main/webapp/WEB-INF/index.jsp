<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />

<main class="bodycont">
	
	<div id="main-slide" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
		<div class="carousel-inner">
		  <div class="carousel-item active">
		    <img src="<%=ctxPath%>/images/sample01.jpg" class="d-block" alt="샘플01">
		  </div>
		  <div class="carousel-item">
		    <img src="<%=ctxPath%>/images/sample02.jpg" class="d-block" alt="샘플02">
		  </div>
		  <div class="carousel-item">
		    <img src="<%=ctxPath%>/images/sample03.jpg" class="d-block" alt="샘플03">
		  </div>
		</div>
		<button class="carousel-control-prev" type="button" data-bs-target="#main-slide" data-bs-slide="prev">
		  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		  <span class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button" data-bs-target="#main-slide" data-bs-slide="next">
		  <span class="carousel-control-next-icon" aria-hidden="true"></span>
		  <span class="visually-hidden">Next</span>
		</button>
	</div>
	<div class="sub-contents container-fluid">
		<section class="event-banners">
			<div>
				<a href="#">
					<img alt="이벤트 페이지로 이동1" src="<%=ctxPath%>/images/EventBanner01.png">
				</a>
			</div>
			<div>
				<a href="#">
					<img alt="이벤트 페이지로 이동2" src="<%=ctxPath%>/images/EventBanner02.png">
				</a>
			</div>
		</section>
		
		<section class="bestsellers">
		<h2 class="text-center">베스트셀러</h2>
         <!-- Nav tabs -->
         <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist" >
           <li class="nav-item" role="presentation">
             <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#brand1" type="button" role="tab" aria-controls="home" aria-selected="true">운동화</button>
           </li>
           <li class="nav-item" role="presentation">
             <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#brand2" type="button" role="tab" aria-controls="profile" aria-selected="false">스니커즈</button>
           </li>
           <li class="nav-item" role="presentation">
             <button class="nav-link" id="messages-tab" data-bs-toggle="tab" data-bs-target="#brand3" type="button" role="tab" aria-controls="messages" aria-selected="false">구두</button>
           </li>
           <li class="nav-item" role="presentation">
             <button class="nav-link" id="messages-tab2" data-bs-toggle="tab" data-bs-target="#brand4" type="button" role="tab" aria-controls="messages2" aria-selected="false">부츠</button>
           </li>
           <li class="nav-item" role="presentation">
             <button class="nav-link" id="messages-tab3" data-bs-toggle="tab" data-bs-target="#brand5" type="button" role="tab" aria-controls="messages3" aria-selected="false">샌들</button>
           </li>
         </ul>
         
         <!-- Tab panes -->
         <div class="tab-content">
           <!-- 스니커즈 -->
           <div class="tab-pane active" id="brand1" role="tabpanel" aria-labelledby="home-tab">
              <div>
                 <ul class="">
                    <c:forEach var="product" items="${requestScope.sneakers}">
                       <li class="">
                          <a href="<%=ctxPath%>/shop/productDetail.go?pnum=${product.pnum}">
                             <img alt="상품" src="<%=ctxPath%>/images/${product.pimage1}">
                          </a>
                       </li>
                    </c:forEach>
                 </ul>
              </div>
           </div>
           <!-- 운동화 -->
           <div class="tab-pane" id="brand2" role="tabpanel" aria-labelledby="profile-tab">
              <div>
                 <ul class="">
                    <c:forEach var="product" items="${requestScope.athleticShoes}">
                       <li class="">
                          <a href="<%=ctxPath%>/shop/productDetail.go?pnum=${product.pnum}">
                             <img alt="상품" src="<%=ctxPath%>/images/${product.pimage1}">
                          </a>
                       </li>
                    </c:forEach>
                 </ul>
              </div>
           </div>
           <!-- 구두 -->
           <div class="tab-pane" id="brand3" role="tabpanel" aria-labelledby="messages-tab">
              <div>
                 <ul class="">
                    <c:forEach var="product" items="${requestScope.dressShoes}">
                       <li class="">
                          <a href="<%=ctxPath%>/shop/productDetail.go?pnum=${product.pnum}">
                             <img alt="상품" src="<%=ctxPath%>/images/${product.pimage1}">
                          </a>
                       </li>
                    </c:forEach>
                 </ul>
              </div>
           </div>
           <!-- 부츠 -->
           <div class="tab-pane" id="brand4" role="tabpanel" aria-labelledby="messages-tab2">
              <div>
                 <ul class="">
                    <c:forEach var="product" items="${requestScope.boots}">
                       <li class="">
                          <a href="<%=ctxPath%>/shop/productDetail.go?pnum=${product.pnum}">
                             <img alt="상품" src="<%=ctxPath%>/images/${product.pimage1}">
                          </a>
                       </li>
                    </c:forEach>
                 </ul>
              </div>
           </div>
           <!-- 샌들 -->
           <div class="tab-pane" id="brand5" role="tabpanel" aria-labelledby="messages-tab3">
              <div>
                 <ul class="">
                    <c:forEach var="product" items="${requestScope.sandles}">
                       <li class="">
                          <a href="<%=ctxPath%>/shop/productDetail.go?pnum=${product.pnum}">
                             <img alt="상품" src="<%=ctxPath%>/images/${product.pimage1}">
                          </a>
                       </li>
                    </c:forEach>
                 </ul>
              </div>
           </div>
         </div>
		</section>
	</div>	
</main>

<jsp:include page="footer.jsp" />