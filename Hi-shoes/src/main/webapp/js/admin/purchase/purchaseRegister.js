
let purchaseList = [];   // 발주할 상품 목록

$(function () {
	
	// 페이지 로딩 시 사이즈 칩 생성 (200 ~ 300, 5단위)
	// - 체크박스는 화면에서 숨기고 칩(label)을 눌러 선택한다. (#sizeGroup input:checked 로 읽는 코드는 그대로 동작)
	for(let s = 200; s <= 300; s += 5){
	    $('#sizeGroup').append(`
	        <label class="mx-chip" for="size${s}">
	            <input type="checkbox" name="size" id="size${s}" value="${s}">
	            <span>${s}</span>
	        </label>`);
	}
	
	// ======================모달창 보여주기 && 페이징 처리====================================
	$('#btncatalogueSearch').on('click', function(){

		// 검색어 초기화
		$('#catalogueSearchWord').val('');

		$('#catalogueModal').modal('show');

		// 전체 제품 1페이지 조회
		loadCatalogueList(1);

	});

	// 모달창에서 제품을 클릭했을 경우
	$(document).on('click', '.catalogue-item', function() {

		const pname = $(this).data('pname');
		const price = $(this).data('price');
		
		$('#catalogueName').val(pname).removeClass('is-invalid');
		$('#catalogueName').data('price', price);           // 구매가는 제품명 input에 보관 (추가하기 때 여기서 읽는다)

		$('#catalogueModal').modal('hide');
		
	});
	
	// 모달창에서 검색했을 경우
	$('#btncatalogueSearchSubmit').on('click', function() {

	    loadCatalogueList(1);

	});
		
	// 모달창에서 검색어 입력 후 엔터
	$('input#catalogueSearchWord').on('keyup', function(event) {
		if (event.keyCode === 13) {
			loadCatalogueList(1);
		}

	});
	// 페이지바의 페이지 번호 클릭
	$(document).on("click", "#cataloguePageBar  a[data-page]", function(e){

		e.preventDefault();

		const pageNo = $(this).data("page");

		loadCatalogueList(pageNo);
	});
	
	// ======================모달창 보여주기 && 페이징 끝====================================

	// 숫자 전용 필드: 숫자 외 문자 즉시 제거
	$(document).on("input", "#purchaseQty", function () {
	    this.value = this.value.replace(/\D/g, "");
	});

	// 사이즈 칩: 선택 상태 표시 + 오류 문구 제거
	$(document).on('change', '#sizeGroup input', function(){
	    $(this).closest('.mx-chip').toggleClass('is-selected', this.checked);
	    $('#sizeError').remove();
	});
	
	
	// ============================ 추가하기 버튼 클릭 시 발주할 상품 목록 추가 =====================================
	
	
	$('button#btnAddItem').on('click', function(){

		// 값들이 전부 들어가 있는지 검사
		let isValid = true;
		
		// 이전 오류 표시 초기화
		$('.infoData').removeClass('is-invalid');
		$('#sizeError').remove();
		
		// 업체명, 제품명, 색상, 수량 검사
		$('.infoData').each(function(){
			if($(this).val().trim() === ""){
			     $(this).addClass('is-invalid');
			     isValid = false;
			 }
		});
		
		//수량 숫자 검사 (1 이상)
	    const qtyCheck = $('#purchaseQty').val().trim();
	    if(qtyCheck !== "" && (!/^\d+$/.test(qtyCheck) || parseInt(qtyCheck) < 1)){
	        $('#purchaseQty').addClass('is-invalid');
	        isValid = false;
	    }
		
		//  사이즈 검사 (체크된 칩이 하나라도 있어야 함)
		if($('#sizeGroup input:checked').length === 0){
		    $('#sizeGroup').after(
		        '<div id="sizeError" class="text-danger small mt-1">사이즈를 선택해주세요.</div>'
		    );
		    isValid = false;
		}
		
		// 하나라도 비어 있으면 중단
		if(!isValid){
		    return;
		}

		// 선택한 제품의 구매가 (제품 검색 모달에서 선택할 때 저장해 둔 값)
		const price = parseInt($('#catalogueName').data('price'));
		if(isNaN(price)){
		    alert("제품을 다시 선택해주세요. (구매가를 확인할 수 없습니다.)");
		    return;
		}
		
		// ==================== 여기부터 검증 통과: 목록에 추가하는 로직 ==================================
		
		const supname       = $('#supplierSelect').val();
		const catalogueName = $('#catalogueName').val();
		const color         = $('#colorSelect').val();
		const colorLabel    = $('#colorSelect option:selected').text();   // 화면 표시용 (검정, 흰색 ...)
		const qty           = parseInt($('#purchaseQty').val());
		
		$('#sizeGroup input:checked').each(function(){

		    const item = {
		        supname:       supname,
		        catalogueName: catalogueName,
		        size:          $(this).val(),
		        color:         color,
		        colorLabel:    colorLabel,
		        qty:           qty,
		        price:         price
		    };

		    // 같은 상품이면 수량 합치기
		    const exist = purchaseList.find(p =>
		        p.supname === item.supname &&
		        p.catalogueName === item.catalogueName &&
		        p.size === item.size &&
		        p.color === item.color
		    );

		    if(exist){
		        exist.qty += item.qty;
		    }
		    else{
		        purchaseList.push(item);
		    }
		});


		renderPurchaseList();
		resetItemForm();
	});
	
	$(document).on('input change', '.infoData', function(){
	    $(this).removeClass('is-invalid');
	});
	
	// ===== 발주하기 =====
	$('#btnPurchaseSubmit').on('click', function(){

		$(this).prop('disabled', true);   // 중복 클릭 방지

		
	    $.ajax({
	        url: ctx_Path + "/admin/purchase/purchaseRegister.go",
	        type: "post",
			data: {
				"supname":                $('#supplierSelect').val(),
				"str_catalogueName_join": purchaseList.map(p => p.catalogueName).join(','),
				"str_size_join":          purchaseList.map(p => p.size).join(','),
				"str_color_join":         purchaseList.map(p => p.color).join(','),
				"str_qty_join":           purchaseList.map(p => p.qty).join(','),
				"str_price_join":         purchaseList.map(p => p.price).join(',')
			},
	        dataType: "json",
	        success: function(json){
				if(json.isSuccess == 1){
				    // 견적서 페이지로 이동
					location.replace(ctx_Path + "/admin/purchase/purchaseEstimate.go?purnum=" + json.purnum);
				}
				else{
				    alert("발주에 실패했습니다.");
				    $('#btnPurchaseSubmit').prop('disabled', false);
				}

	        },
	        error: function(request, status, error){
	            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
				// 다른 이유로 실패했을 경우 버튼 활성화
				$('#btnPurchaseSubmit').prop('disabled', false);
	        }
	    });
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

			let html = "";

			if(!json.catalogueList || json.catalogueList.length === 0){
			    html += `
			        <tr>
			            <td class="text-center align-middle">
			                <div class="mx-empty">
			                    <i class="bi bi-inbox"></i>
			                    <p>검색된 제품이 없습니다.</p>
			                </div>
			            </td>
			        </tr>`;
			}
			else {
				$.each(json.catalogueList, function(index, item) {

				    html += `
				        <tr class="catalogue-item"
				            data-pname="${item.pname}"
							data-price="${item.purprice}"
				            style="cursor:pointer;">
				            <td>
				                ${item.pname}
				                <span class="mx-hint ms-2">구매가 ${Number(item.purprice).toLocaleString()}원</span>
				            </td>
				        </tr>
				    `;
				});
			}

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

// ===== 목록 렌더링 =====
function renderPurchaseList(){
	
    const $tbody = $('#purchaseItemTbl tbody');
    $tbody.empty();

    // 담긴 상품이 있으면 업체 선택 잠금, 비면 해제
    $('#supplierSelect').prop('disabled', purchaseList.length > 0);

    if(purchaseList.length === 0){
        $tbody.append(`
            <tr id="emptyRow">
                <td colspan="7" class="text-center align-middle">
                    <div class="mx-empty">
                        <i class="bi bi-inbox"></i>
                        <p>추가된 발주 상품이 없습니다.</p>
                    </div>
                </td>
            </tr>`);
        $('#totalAmount').text('0');
        $('#btnPurchaseSubmit').prop('disabled', true);
        return;
    }

    let total = 0;

    purchaseList.forEach(function(item, idx){
        const amount = item.price * item.qty;
        total += amount;

        $tbody.append(`
            <tr>
                <td class="text-center align-middle">${item.supname}</td>
                <td class="text-center align-middle">${item.catalogueName}</td>
                <td class="text-center align-middle">${item.size}</td>
                <td class="text-center align-middle">${item.colorLabel || item.color}</td>
                <td class="text-center align-middle">${item.qty}</td>
                <td class="text-center align-middle">${amount.toLocaleString()}원</td>
                <td class="text-center align-middle">
                    <button type="button" class="btn btn-sm btn-outline-danger btnDelItem" data-idx="${idx}">
                        <i class="bi bi-trash"></i> 삭제
                    </button>
                </td>
            </tr>`);
    });

    $('#totalAmount').text(total.toLocaleString());
    $('#btnPurchaseSubmit').prop('disabled', false);
}

// ===== 삭제 =====
$(document).on('click', '.btnDelItem', function(){
    purchaseList.splice($(this).data('idx'), 1);
    renderPurchaseList();
});

// ===== 입력 폼 초기화 =====
function resetItemForm(){
    $('#catalogueName').val('').removeData('price');
    $('#sizeGroup input').prop('checked', false);
    $('#sizeGroup .mx-chip').removeClass('is-selected');
    $('#colorSelect').val('');
    $('#purchaseQty').val('');
}
