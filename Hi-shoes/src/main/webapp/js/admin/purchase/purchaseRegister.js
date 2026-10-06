
let purchaseList = [];   // 발주할 상품 목록

let cataloguePurprice = 0;

$(function () {
	
	// 페이지 로딩 시 사이즈 버튼 생성 (200 ~ 300, 5단위)
    let html = "";
	for(let s = 200; s <= 300; s += 5){
	    $('#sizeGroup').append(`
	        <div class="form-check">
	            <input class="form-check-input" type="checkbox" name="size" id="size${s}" value="${s}">
	            <label class="form-check-label" for="size${s}">${s}</label>
	        </div>`);
	}
	
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
		const price = $(this).data('price');
		
		console.log("선택한 제품 가격: ", price);
//		console.log("선택한 제품명:", pname);
		$('#catalogueName').val(pname).removeClass('is-invalid');
		$('#catalogueName').val(pname);
		$('#catalogueName').data('price', price);           // 가격은 제품명 input에 보관

		$('#purprice').val(price);   // 선택 시 값 넣기
		
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
	
	
	// ============================ 추가하기 버튼 클릭 시 발주할 상품 목록 추가 =====================================
	$('button#btnAddItem').on('click', function(){
//		alert("발주할 상품 목록 추가");

		// 값들이 전부 들어가 있는지 검사
		let isValid = true;
		
		// 이전 오류 표시 초기화
		$('infoData').removeClass('is-invalid');
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
		
		//  사이즈 검사 (라디오 버튼 기준)
		if($('#sizeGroup input:checked').length === 0){
		    $('#sizeGroup').after(
		        '<div id="sizeError" class="text-danger small mt-1">사이즈를 선택해주세요.</div>'
		    );
		    isValid = false;
		}
		
		// 5. 하나라도 비어 있으면 중단
		if(!isValid){
		    return;
		}
		
		// ==================== 여기부터 검증 통과: 목록에 추가하는 로직 ==================================
		
		const supname       = $('#supplierSelect').val();
		const catalogueName = $('#catalogueName').val();
		const color         = $('#colorSelect').val();
		const qty           = parseInt($('#purchaseQty').val());
		
		$('#sizeGroup input:checked').each(function(){

		    const item = {
		        supname:       supname,
		        catalogueName: catalogueName,
		        size:          $(this).val(),
		        color:         color,
		        qty:           qty,
		        price:         parseInt($('#purprice').val())
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

	$(document).on('change', '#sizeGroup input', function(){
	    $('#sizeError').remove();
	});
	
	// ===== 발주하기 =====
	$('#btnPurchaseSubmit').on('click', function(){

		$(this).prop('disabled', true);   // 중복 클릭 방지

		
	    $.ajax({
	        url: ctx_Path + "/admin/purchase/purchaseRegister.go",   // 실제 URL로 변경
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
	            // 성공 처리
				if(json.isSuccess == 1){
				    // 견적서 페이지로 이동 (다음 단계에서)
				    console.log("발주 성공", json.purchaseNos);
				}
				else{
				    alert("발주에 실패했습니다.");
				    $('#btnPurchaseSubmit').prop('disabled', false);
				}

	        },
	        error: function(request, status, error){
	            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
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
			
//			console.log("받은 JSON:", json);
//			console.log("상품 목록:", json.catalogueList);

			let html = "";

			$.each(json.catalogueList, function(index, item) {

//			    console.log("현재 item:", item);
//				console.log("현재 item Purprice", item.purprice);
			    html += `
			        <tr class="catalogue-item"
			            data-pname="${item.pname}"
						data-price="${item.purprice}"
			            style="cursor:pointer;">
			            <td>${item.pname} <input type="hidden" id="purprice" value=""></td>
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

// ===== 목록 렌더링 =====
function renderPurchaseList(){
	
	// 목록 유무에 따라 업체 선택 잠금/해제
	$('#supplierSelect').prop('disabled', purchaseList.length > 0);
	
    const $tbody = $('#purchaseItemTbl tbody');
    $tbody.empty();

    if(purchaseList.length === 0){
        $tbody.append(`
            <tr id="emptyRow">
                <td colspan="7" class="text-center align-middle">
                    <span style="color: red; font-weight: bold;">추가된 발주 상품이 없습니다.</span>
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
                <td class="text-center align-middle">${$('<div>').text(item.supname).html()}</td>
                <td class="text-center align-middle">${$('<div>').text(item.catalogueName).html()}</td>
                <td class="text-center align-middle">${item.size}</td>
                <td class="text-center align-middle">${item.color}</td>
                <td class="text-center align-middle">${item.qty}</td>
                <td class="text-center align-middle">${amount.toLocaleString()}</td>
                <td class="text-center align-middle">
                    <button type="button" class="btn btn-sm btn-outline-danger btnDelItem" data-idx="${idx}">삭제</button>
                </td>
            </tr>`);
    });

    $('#totalAmount').text(total.toLocaleString());
    $('#btnPurchaseSubmit').prop('disabled', false);
	
	if(purchaseList.length === 0){
	    $('#supplierSelect').prop('disabled', false);   // 비면 업체 변경 가능
	}
	else{
	    $('#supplierSelect').prop('disabled', true);    // 담긴 상품이 있으면 잠금
	}
}

// ===== 삭제 =====
$(document).on('click', '.btnDelItem', function(){
    purchaseList.splice($(this).data('idx'), 1);
    renderPurchaseList();
});

// ===== 입력 폼 초기화 =====
function resetItemForm(){
    $('#catalogueName').val('');
    $('#sizeGroup input').prop('checked', false);
    $('#colorSelect').val('');
    $('#purchaseQty').val('');
}


