<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	
    String ctxPath = request.getContextPath();
%>

<jsp:include page="../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/mypage.css" />

<script type="text/javascript">
	//리뷰보기 호출
	function showReview() {
	    fetch("<%= ctxPath%>/member/reviewList.go")
	        .then(response => {
	            if (!response.ok) {
	                throw new Error("페이지를 불러오지 못했습니다.");
	            }
	            return response.text();
	        })
	        .then(html => {
	            document.getElementById("reviewArea").innerHTML = html;
	        })
	        .catch(error => {
	            console.error(error);
	            alert("페이지를 불러오는 중 오류가 발생했습니다.");
	        });
	}	
	
	// 리뷰보기 리스트 체크박스 라디오 버튼 처리
	function onlyOneCheck(obj) {

	    const checkboxList = document.querySelectorAll('input[name="reviewEditChk"]');
	
	    checkboxList.forEach(elmt => {
	        if(elmt != obj){    // elmt != obj 은 체크박스에 체크를 하지 않은 나머지 모든 체크박스를 말한다.
	            elmt.checked = false;   // 체크박스에 체크를 못하도록 한다.
	        }
	    });    
	    
	} // end of function onlyOneCheck(obj)------------
	
	
		
	//리뷰보기 페이지 조회 함수
	$(document).on("click", ".pagination a[data-link]", function(event) {	
	   
	    // 일반 페이지 이동 방지
	    event.preventDefault();
	
	 	// 클릭한 페이지 번호 가져오기
        const pageNo = $(this).data("link");
	
	    // AJAX로 해당 페이지 요청
	    fetch("reviewList.go?currentShowPageNo=" + pageNo)
	        .then(response => {
	            if (!response.ok) {
	                throw new Error("페이지 조회 실패");
	            }
	            return response.text();
	        })
	        .then(html => {
	            document.getElementById("reviewArea").innerHTML = html;
	        })
	        .catch(error => {
	            console.error(error);
	        });
	
	});
		
	
</script>

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
		<div class="body-wrap p-3">
			<div class="row">
			  <div class="col-12 col-lg-3">
			    <div class="list-group list-group-flush" id="list-tab" role="tablist">
			      <a class="list-group-item list-group-item-action active" id="list-order-list" data-bs-toggle="list" href="#list-order" role="tab" aria-controls="list-order">주문내역</a>
			      <a class="list-group-item list-group-item-action" id="list-wish-list" data-bs-toggle="list" href="#list-wish" role="tab" aria-controls="list-wish">위시리스트</a>
			      <a class="list-group-item list-group-item-action" id="list-review-list" data-bs-toggle="list" href="#list-review" role="tab" aria-controls="list-review" onclick="showReview()">리뷰보기</a>
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
			      	<div id="reviewArea">
			      	</div>
			      </div>
			      <div class="tab-pane fade" id="list-editInfo" role="tabpanel" aria-labelledby="list-editInfo-list">
			      
			      	<jsp:include page="/WEB-INF/member/editMyinfo.jsp" />
			      	
			      </div>
			      <div class="tab-pane fade" id="list-qna" role="tabpanel" aria-labelledby="list-qna-list">
			      	문의내역
			      </div>
			     
			    </div>
			  </div>
			</div>
		</div>
	</section>
	
</section>

<jsp:include page="../footer.jsp" />