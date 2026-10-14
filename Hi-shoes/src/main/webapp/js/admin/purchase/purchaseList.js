
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
	
	// ========================== 견적서 모달 ======================================
	$('button.btnQuotation').on('click', function(){
		const purnum = $(this).data('purnum');

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

		        $('#quotationModalBody').html(response);

		        // 버튼을 누를 때마다 새로 만들지 않고 기존 모달을 재사용
				$('#quotationModal').modal('show');

		    },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}

		}); // end of ajax
	}); // end of button
	
	// 인쇄/PDF 저장: 모달에 열려 있는 견적서를 바로 인쇄한다.
	// (quotationContent.jsp 의 @media print 가 모달 바깥 화면은 숨기고 견적서만 출력한다)
	$(document).on('click', '#btnQuotationPrint', function() {
	      const purnum = $('#quotationModal').data('purnum');
	      window.open(ctx_Path + '/admin/purchase/purchaseEstimate.go?purnum=' + purnum, '_blank');
	});
	
	
	//========================== 입고처리 이벤트 ======================================
	// 반복문 안의 버튼이므로 id 가 아니라 class(btnInstockUpdate)로 잡는다.
	$('button.btnInstockUpdate').on('click', function(){
		
		// 클릭한 버튼의 발주번호 읽어오기
		const purnum = $(this).data('purnum');
		const instock = $(this).data('instock');   // 예: 미입고
		
		if(instock=="입고"){
			alert("입고된 상품은 입고처리가 불가능합니다.");
			return;  // 함수 종료
		}

		if(!confirm("발주번호 " + purnum + " 을(를) 입고 처리하시겠습니까?")){
			return;
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
					alert("입고처리에 실패했습니다. (이미 처리되었거나 발주 상세가 없습니다.)");
				} else {
					// 정상적으로 변경한 경우: 같은 검색조건/탭 그대로 새로고침
					location.reload();
				}
		    },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}

		}); // end of ajax

	}); // end of button event
	
	
	
}); // end of function(){}----------------------------------
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
	

