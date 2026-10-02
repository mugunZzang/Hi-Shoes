<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

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
			<h2 class="text-center">브랜드 별 베스트셀러</h2>
			<!-- Nav tabs -->
			<ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist" >
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#brand1" type="button" role="tab" aria-controls="home" aria-selected="true">나이키</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#brand2" type="button" role="tab" aria-controls="profile" aria-selected="false">아디다스</button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="messages-tab" data-bs-toggle="tab" data-bs-target="#brand3" type="button" role="tab" aria-controls="messages" aria-selected="false">아식스</button>
			  </li>
			</ul>
			
			<!-- Tab panes -->
			<div class="tab-content">
			  <div class="tab-pane active" id="brand1" role="tabpanel" aria-labelledby="home-tab">
			  	<div>
			  		<ul class="">
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9050_detail_070.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9049_detail_013.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9048_detail_036.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9040_detail_070.jpg">
			  				</a>
			  			</li>
			  		</ul>
			  	</div>
			  </div>
			  <div class="tab-pane" id="brand2" role="tabpanel" aria-labelledby="profile-tab">
			  	<div>
			  		<ul class="">
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9090_detail_076.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9082_detail_091.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9081_detail_03.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/9080_detail_039.jpg">
			  				</a>
			  			</li>
			  		</ul>
			  	</div>
			  </div>
			  <div class="tab-pane" id="brand3" role="tabpanel" aria-labelledby="messages-tab">
			  	<div>
			  		<ul class="">
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/68639_1695633642289.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/17488_1707870306485.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/00631_1751870842066.jpg">
			  				</a>
			  			</li>
			  			<li class="">
			  				<a href="#">
			  					<img alt="상품" src="<%=ctxPath%>/images/67001_1706255553897.jpg">
			  				</a>
			  			</li>
			  		</ul>
			  	</div>
			  </div>
			</div>
		</section>
	</div>	
</main>

<jsp:include page="footer.jsp" />