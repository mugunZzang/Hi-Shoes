

$(function(){
	
	// 초기화: 파라미터 없는 주소로 이동
	$("#btnReset").on("click", function () {
	    location.href = location.pathname;
	});

	// 검색 시 날짜 검증
	$("form[name='searchFrm']").on("submit", function (e) {
	    const start = $("input[name='startDate']").val();
	    const end = $("input[name='endDate']").val();

	    if (start && end && start > end) {
	        alert("시작일이 종료일보다 늦을 수 없습니다.");
	        e.preventDefault();
	    }
	});
	
	$('button.btnQuotation').on('click', function(){
		const purnum = $(this).data('purnum');
		
//		console.log("견적서 발주번호", purnum); 2 or  1

		// 현재 열어본 발주번호를 모달에 저장
		$('#quotationModal').data('purnum', purnum);
		
		$.ajax({
			url: ctx_Path + '/admin/purchase/purchaseEstimate.go',
			type: 'GET',
	        data: {
	            purnum: purnum,
	            isAjax: 'true'
	        },
		    success: function(response) {

//		        console.log("견적서 데이터:", response);

		        $('#quotationModalBody').html(response);

		        const quotationModal =
		            new bootstrap.Modal(
		                document.getElementById('quotationModal')
		            );

		        quotationModal.show();
		    },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}

		}); // end of ajax
	}); // end of button
	
	$(document).on('click', '#btnQuotationPrint', function() {

	    const purnum = $('#quotationModal').data('purnum');

	    window.open(
	        ctx_Path + '/admin/purchase/purchaseEstimate.go?purnum=' + purnum,
	        '_blank'
	    );

	});
	
	
	//========================== 입고처리 이벤트 ======================================
	$('button#instockUpdate').on('click', function(){
//		alert("입고처리 시작");
		
		// 클릭한 버튼의 발주번호 읽어오기
		const purnum = $(this).data('purnum');
		const instock = $(this).data('instock');
		/*
		console.log("확인용 purnum", purnum);
		확인용 purnum 2
		확인용 purnum 1
		확인용 purnum 21
		*/
		/*
		console.log("확인용 instock", instock);
		확인용 instock 미입고
		*/
		
		if(instock=="입고"){
			alert("입고된 상품은 입고처리가 불가능합니다.");
			return;  // 함수 종료
		}
		
		
		// update 하기 위한 ajax 호출
		$.ajax({
			url: ctx_Path + '/admin/purchase/purchaseStockUpdate.go',
			type: 'post',
	        data: {
	            purnum: purnum
	        },
		    success: function(result) {
				if(result <= 0){
					// 잘못된 접근 혹은 발주 상세가 존재하지 않아 변경 불가능
					console.log("변경 불가");
				} else {
					// 정상적으로 변경한 경우
//					console.log("~~~확인용 result :", result);
					location.href = location.href;
				}
		    },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}

		}); // end of ajax

	}); // end of button event
	
	
	
}); // end of function(){}----------------------------------