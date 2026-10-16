<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	
    String ctxPath = request.getContextPath();
%>

<jsp:include page="../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/mypage.css" />

<script type="text/javascript">		
	
	// 테흔님 작업 영역 start

	$(document).ready(function() {
		loadOrderList(1);
	});

	// 주문 목록 불러오기
	function loadOrderList(currentShowPageNo) {
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/adminOrderList2.go",
			type: "GET",
			data: {
				currentShowPageNo: currentShowPageNo
			},
			dataType: "html",
			success: function(html) {
				$("#orderListArea").html(html);
			},
			error: function(request, status, error) {
				console.error("주문 목록 HTTP 상태:", request.status);
				console.error("주문 목록 응답:", request.responseText);
				console.error("주문 목록 오류:", error);

				$("#orderListArea").html(
					"주문내역을 불러오지 못했습니다. Console을 확인해주세요."
				);
			}
		});
	}

	// 별점 초기값
	window.selectedReviewRating = 0;

	// 별점 선택
	$(document).on("click", "#reviewStarGroup .review-star", function() {
		window.selectedReviewRating = Number($(this).data("rating"));

		$("#reviewStarGroup .review-star").each(function() {
			const rating = Number($(this).data("rating"));

			$(this).toggleClass(
				"active",
				rating <= window.selectedReviewRating
			);
		});

		$("#reviewRatingText").text(
			window.selectedReviewRating + "점 선택"
		);
	});

	// 리뷰 작성 모달 열기
	function openReviewWrite_Modal(odnum) {
		window.selectedReviewRating = 0;

		$("#reviewOdnum").val(odnum);
		$("#reviewContents").val("");
		$("#reviewStarGroup .review-star").removeClass("active");
		$("#reviewRatingText").text("별점을 선택해주세요.");

		$("#reviewWriteModal").modal("show");
	}

	// 리뷰 등록
	$(document).on("click", "#reviewRegisterBtn", function() {
		const odnum = $("#reviewOdnum").val();
		const rcontents = $("#reviewContents").val().trim();

		if (!odnum) {
			alert("주문상세번호를 확인할 수 없습니다.");
			return;
		}

		if (window.selectedReviewRating === 0) {
			alert("별점을 선택해주세요.");
			return;
		}

		if (rcontents === "") {
			alert("리뷰 내용을 입력해주세요.");
			$("#reviewContents").focus();
			return;
		}

		$.ajax({
			url: "${pageContext.request.contextPath}/admin/reviewWriteJson.go",
			type: "POST",
			data: {
				odnum: odnum,
				rating: window.selectedReviewRating,
				rcontents: rcontents
			},
			dataType: "json",
			success: function(json) {
				console.log("리뷰 등록 응답:", json);

				if (Number(json.result) === 1) {
					alert("리뷰가 등록되었습니다.");
					$("#reviewWriteModal").modal("hide");
					loadOrderList(1);
				}
				else if (Number(json.result) === -1) {
					alert("이미 작성한 리뷰가 있습니다.");
				}
				else {
					alert("리뷰 등록에 실패했습니다.");
				}
			},
			error: function(request, status, error) {
				console.error("리뷰 등록 HTTP 상태:", request.status);
				console.error("리뷰 등록 응답:", request.responseText);
				console.error("리뷰 등록 오류:", error);

				alert("리뷰 등록 요청에 실패했습니다. Console을 확인해주세요.");
			}
		});
	});


	// 테흔님 작업 영역 end




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
		
	
	//리뷰 삭제 함수
	function delMyReview() {
		
		// 리뷰보기 - 체크한 리뷰 
		const reviewNo = $('input[name="reviewEditChk"]:checked').val();
		
		if(confirm("정말로 제품후기를 삭제하시겠습니까?")){
			$.ajax({
				url:"<%=ctxPath%>/member/reviewDel.go",
				method:"post",
				data:{"reviewNo":reviewNo},
				dataType:"json",
				success:function(json){
					// console.log(JSON.stringify(json));
					// {"n":1} 또는 {"n":0}
					
					if(json.n == 1) {
						alert("제품후기 삭제가 성공되었습니다.");
						showReview();
					}
					else {
						alert("제품후기 삭제가 실패했습니다.");
						showReview();
					}
				},
				error: function(request, status, error){
			         alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		       }
			});	
	   } 
		
	}	// end of function delMyReview(review_seq)--------------
	
	
	// 리뷰 수정 함수
	function editReview() {

		if($('input[name="reviewEditChk"]:checked').length == 0) {
			alert("리뷰를 선택하세요.");
			return;
		}		
				
		const checkedReview = $('input[name="reviewEditChk"]:checked');
		const $tr = checkedReview.closest('tr');
		
		const review_no = checkedReview.val();
        const content = $tr.find('.contents a').text().trim();
        const rating = $tr.find('.star').text().trim();
        const date = $tr.find('td').eq(3).text().trim();
        const writer = $tr.find('td').eq(4).text().trim();
        const thumbImg = $tr.find('.thumbnail img').attr('src');
		
		
		const modalElement = $('#editReview');
		modalElement.find('#rvwThumbnail').attr('src', thumbImg);
		modalElement.find('#rating').val(rating);
		modalElement.find('#content').val(content);
		
		/* const modalElement = document.getElementById('#editReview'); */
		
		
		
		const editReviewModal = bootstrap.Modal.getOrCreateInstance(modalElement);
		
		editReviewModal.show();	
		
		console.log( // 체크박스 value / data 속성에서
		        "review_no:" + checkedReview.val() +

		        // 화면에 표시된 값에서
		        "content:" +  $tr.find('.contents a').text().trim() +
		        "rating:"   + $tr.find('.star').text().trim()+
		        "date:"    +  $tr.find('td').eq(3).text().trim()+
		        "writer:"  +  $tr.find('td').eq(4).text().trim()+
		        "image:"   +  $tr.find('.thumbnail img').attr('alt')
		        );
	}
	
	// 리뷰 수정 썸네일 이미지 교체 시 유효성 검사	
	function thumbChk() {		
		
		const $thumb = $('img#rvwThumbnail');
	    const file = $('#rvwThumbnailChg')[0].files[0];
	    const allowed = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
	    const reset = () => {
	    	this.value = '';
	        $thumb.hide().removeAttr('src');
	    };
	
	    if (!file) {
	        reset();
	        return;
	    }
		    
	    if (!allowed.includes(file.type)) {
	        alert('jpg, png, gif, webp 파일만 첨부할 수 있습니다.');
	        reset();
	        return;
	    }
	
	    if (file.size > 10 * 1024 * 1024) {
	        alert('사진은 10MB 이하만 첨부할 수 있습니다.');
	        reset();
	        return;
	    }
	
	    const reader = new FileReader();
	    reader.onload = e => $thumb.attr('src', e.target.result).show();
	    reader.readAsDataURL(file);
	    
	}
	
	// 리뷰 수정 썸네일 이미지 삭제
	function thumbDel() {
		$('img#rvwThumbnail').hide().removeAttr('src');
		$('#rvwThumbnailChg').val('');		
		
	}
	
	// 리뷰 수정 완료 시 호출
	function editFinish() {

		const rating = $('#rating').val();
		const content = $('textarea#content').val().trim();
		const odnum = $('input[name="reviewEditChk"]:checked').val();
		const image =  $('#rvwThumbnailChg')[0].files[0];
		console.log(rating);
		console.log(content);
		console.log(odnum);
		console.log(image);

		const formData = new FormData();
		
		formData.append('rating', rating);
		formData.append('content', content);
		formData.append('odnum', odnum);
		
		if(image) {
			formData.append('image', image);
		}
		
		
		
		$.ajax({
			url:"<%=ctxPath%>/member/editFinish.go",
			method:"post",
			data: formData,
			processData:false,
			contentType:false,
			dataType:"json",
			success:function(json){
				// console.log(JSON.stringify(json));
				// {"n":1} 또는 {"n":0}
				const modalElement = $('#editReview');
				const editReviewModal = bootstrap.Modal.getOrCreateInstance(modalElement);			
				
				if(json.n == 1) {
					alert("수정되었습니다.");
					editReviewModal.hide();
					showReview();	//특정 제품의 제품후기 글들을 보여주는 함수 호출
				}
				else {
					alert("후기 수정에 실패했습니다.");
					editReviewModal.hide();
					showReview();	//특정 제품의 제품후기 글들을 보여주는 함수 호출
				}
			},
			error: function(request, status, error){
		         alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	       }
		});	
	}
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

					<div id="orderListArea">
                          주문내역을 불러오는 중입니다.
                      </div>
			      </div>
			      <div class="tab-pane fade" id="list-wish" role="tabpanel" aria-labelledby="list-wish-list">
			      
              			<jsp:include page="/WEB-INF/member/wishList.jsp" />
               
			      </div>
			      <div class="tab-pane fade" id="list-review" role="tabpanel" aria-labelledby="list-review-list">
			      	<div id="reviewArea">
			      	</div>
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