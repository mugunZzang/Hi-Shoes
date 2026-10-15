<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	
    String ctxPath = request.getContextPath();
%>

<jsp:include page="../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/mypage.css" />

<section class="bodycont container-fluid d-flex center justify-content-center align-items-start">

	<section class="cont-wrap">
		<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider:url('<%=ctxPath%>/images/right-arrow.svg');">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item home"><a href="<%= ctxPath %>/index.go">Home</a></li>
		    <li class="breadcrumb-item active" aria-current="page">마이페이지</li>
		  </ol>
		</nav>
		<div class="tit-wrap">
			<h2 class="section-tit">마이페이지</h2>
		</div>
		<div class="body-wrap row p-3 col-12 m-auto">
			<div class="row">
			  <div class="col-12 col-lg-3">
			    <div class="list-group list-group-flush" id="list-tab" role="tablist">
			      <a class="list-group-item list-group-item-action active" id="list-order-list" data-bs-toggle="list" href="#list-order" role="tab" aria-controls="list-order">주문내역</a>
			      <a class="list-group-item list-group-item-action" id="list-wish-list" data-bs-toggle="list" href="#list-wish" role="tab" aria-controls="list-wish">위시리스트</a>
			      <a class="list-group-item list-group-item-action" id="list-review-list" data-bs-toggle="list" href="#list-review" role="tab" aria-controls="list-review">리뷰보기</a>
			      <a class="list-group-item list-group-item-action" id="list-editInfo-list" data-bs-toggle="list" href="#list-editInfo" role="tab" aria-controls="list-editInfo">정보수정</a>
			      <a class="list-group-item list-group-item-action" id="list-qna-list" data-bs-toggle="list" href="#list-qna" role="tab" aria-controls="list-qna">문의내역</a>
			      
			    </div>
			  </div>
			  <div class="col-12 col-lg-9">
			    <div class="tab-content" id="nav-tabContent">
			      <div class="tab-pane fade show active" id="list-order" role="tabpanel" aria-labelledby="list-order-list">
			      	주문내역
			      </div>
			      <div class="tab-pane fade" id="list-wish" role="tabpanel" aria-labelledby="list-wish-list">
			      	위시리스트
			      </div>
			      <div class="tab-pane fade" id="list-review" role="tabpanel" aria-labelledby="list-review-list">
			      	리뷰보기
			      </div>
			      <div class="tab-pane fade" id="list-editInfo" role="tabpanel" aria-labelledby="list-editInfo-list">
			      
					<jsp:include page="/WEB-INF/member/editMyinfo.jsp" />
			      	
			      </div>
			      <div class="tab-pane fade" id="list-qna" role="tabpanel" aria-labelledby="list-qna-list">
			      	 <jsp:include page="/WEB-INF/user/userqna.jsp" />
			      </div>
			     
			    </div>
			  </div>
			</div>
		</div>
	</section>
	
</section>

<jsp:include page="../footer.jsp" />