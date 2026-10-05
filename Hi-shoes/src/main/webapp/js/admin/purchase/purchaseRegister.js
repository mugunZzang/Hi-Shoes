
$(function () {
	
	// 페이지 로딩 시 사이즈 버튼 생성 (200 ~ 300, 5단위)
    let html = "";
    for (let s = 200; s <= 300; s += 5) {
		html += "<input type='radio' class='btn-check' name='size' id='size" + s + "' value='" + s + "' autocomplete='off'>" +
		        "<label class='btn btn-outline-secondary btn-sm' for='size" + s + "' style='width: 56px;'>" + s + "</label>";
    }
    $("#sizeGroup").html(html);
	
	// ======================모달창 보여주기 && 페이징 처리====================================
	$('#btncatalogueSearch').on('click', function(){

		// 검색어 초기화
		$('#catalogueSearchWord').val('');

		const catalogueModal = new bootstrap.Modal(document.getElementById('catalogueModal'));

		catalogueModal.show();

		// 전체 제품 1페이지 조회
		loadCatalogueList(1);

	});

	// 모달창에서 제품을 클릭했을 경우
	$(document).on('click', '.catalogue-item', function() {

		const pname = $(this).data('pname');

//		console.log("선택한 제품명:", pname);

		$('#catalogueName').val(pname);

		const catalogueModal =
		    bootstrap.Modal.getInstance(
		        document.getElementById('catalogueModal')
		    );

		catalogueModal.hide();
	});
	
	// 모달창에서 검색했을 경우
	$('#btncatalogueSearchSubmit').on('click', function() {

	    loadCatalogueList(1);

	});
		
	// 페이지바의 페이지 번호 클릭
	$(document).on("click", "#cataloguePageBar  a[data-page]", function(e){

		e.preventDefault();

		const pageNo = $(this).data("page");

		loadCatalogueList(pageNo);
	});
	
	// ======================모달창 보여주기 && 페이징 끝====================================

	// 추가하기 클릭
	$(document).on("click", "#btnAddItem", function () {
//		alert("발주상품 등록 시작");
		
		$('span.error').hide();

		// 필수 입력사항은 class 에 infoData로 만들어 놓음
		$('.infoData').each(function(index, elmt){
		    // input 태그들의 요소 방문하며 값 확인
		    const val = $(elmt).val().trim();

		    if(val == ""){
		        // 값이 공백인 경우
//				alert("값공백");   OK
		        $(elmt).next().show();
		        is_infoData_OK = false;
		        return false; // break
		    }
		}); // end of infoData
	}); // end of $(document).on("click", "#btnAddItem", function ()----------------
	
	// 숫자 전용 필드: 숫자 외 문자 즉시 제거
	$(document).on("input", "#purchaseQty", function () {
	    this.value = this.value.replace(/\D/g, "");
	});
});

// 제품 목록 조회
function loadCatalogueList(pageNo) {

    const CatalogueName = $('#catalogueSearchWord').val().trim();

    $.ajax({

        url: ctx_Path + '/admin/purchase/purchaseRegister.go',

        type: 'GET',

        data: {

            isAjax: 'true',
            CatalogueName: CatalogueName,
            currentShowPageNo: pageNo

        },

        dataType: 'json',

        success: function(json) {
			
//			console.log("받은 JSON:", json);
//			console.log("상품 목록:", json.catalogueList);

			let html = "";

			$.each(json.catalogueList, function(index, item) {

//			    console.log("현재 item:", item);

			    html += `
			        <tr class="catalogue-item"
			            data-pname="${item.pname}"
			            style="cursor:pointer;">
			            <td>${item.pname}</td>
			        </tr>
			    `;
			});

//			console.log("만들어진 html:", html);

			$('#catalogueTbl tbody').html(html);
			$('#cataloguePageBar').html(json.pageBar);

        },

        error: function(request, status, error) {

            console.log("code:", request.status);
            console.log("message:", request.responseText);
            console.log("error:", error);

        }

    });
}

