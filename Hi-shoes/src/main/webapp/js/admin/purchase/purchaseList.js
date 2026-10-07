

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
	
}); // end of function(){}----------------------------------