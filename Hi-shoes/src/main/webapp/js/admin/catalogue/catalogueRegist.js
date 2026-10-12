
// 제품명 중복체크 클릭 여부 알아오기 위한 용도
let b_pname_click = false;

// 입력칸이 들어 있는 영역(.col-sm-10) 안에서 오류 문구 칸을 찾는다.
// (입력칸 바로 다음 요소에 기대면 입력그룹/힌트 문구 때문에 위치가 어긋나므로 영역 기준으로 찾는다.)
function $fieldBox(elmt){
    return $(elmt).closest('.col-sm-10');
}

$(function(){
//	console.log($('#btnRegister').length );       // 1 이면 정상


    $('input[name="pname"]').on('blur', (e) =>{
        const pname = $(e.target).val().trim();
        const $err = $fieldBox(e.target).find('span.error').first();
        if(pname == ""){
            // 입력하지 않거나 공백만 입력했을 경우
            $err.show();
        } else{
            // 공백이 아닌 글자를 입력했을 경우
            $err.hide();
        }
    });  // 제품명이 포커스를 잃어버렸을 경우 이벤트 처리
	
    // ===== 카탈로그 등록하기 ===== //
    // (JSP 의 등록 버튼이 <button> 이므로 input:button 이 아니라 id 로 잡는다)
    $('#btnRegister').on('click', function(){
//        alert("카탈로그 등록 시작");
		
		
        $('span.error').hide();
        $('.positiveNumber_error, .positiveBrand_error').empty();

        // 필수 입력사항 입력 여부
        let is_infoData_OK = true;
		
		if(!b_pname_click) {
			// "중복확인" 클릭을 안 했을 경우
			alert('"중복체크"를 클릭하셔서 제품명이 중복되었는지 확인하셔야 합니다.');
		}

        // 필수 입력사항은 class 에 infoData로 만들어 놓음
        // 비어 있는 칸은 모두 오류 문구를 보여주고, 첫 번째 빈 칸에 포커스를 준다.
        let $firstEmpty = null;
        $('.infoData').each(function(index, elmt){
            const val = $(elmt).val().trim();

            if(val == ""){
                $fieldBox(elmt).find('span.error').first().show();
                is_infoData_OK = false;
                if($firstEmpty === null){
                    $firstEmpty = $(elmt);
                }
            }
        }); // end of infoData

        if($firstEmpty !== null){
            $firstEmpty.focus();
        }
		
		// 제품정가, 제품판매가, 제품구매가 는 숫자만 들어오도록 한다.
		const regExp_positiveNumber = /^[0-9]+$/;

		$('input.positiveNumber').each(function(index, elmt){

		    const value = $(elmt).val().trim();

		    if(value != "") {

		        if(!regExp_positiveNumber.test(value) || Number(value) <= 0) {

		            $fieldBox(elmt).find('.positiveNumber_error')
		                .text("0보다 큰 숫자만 입력하세요.")
		                .css({"color":"red"});

		            $(elmt).focus();

		            is_infoData_OK = false;
		            return false;

		        }
		    }
		});
		
		// 브랜드는 한글과 영어만 가능하다.
		const regExp_brand = /^[가-힣a-zA-Z]+$/;

		const $brand = $('input[name="brand"]');
		const brandName = $brand.val().trim();

		if(brandName != "") {

		    // 브랜드에 값이 입력되어 있을 경우에만 정규식 검사

		    if(!regExp_brand.test(brandName)) {

		        $fieldBox($brand).find('.positiveBrand_error')
		            .text("브랜드는 영어와 한글만 가능합니다.")
		            .css({"color":"red"});

		        $brand.focus();

		        is_infoData_OK = false;

		    }
		}
			
        if(is_infoData_OK && b_pname_click){
			const frm = document.catalogue;
			frm.submit();	
        }
    });
	
	$('#btnPnameDuplicate').on('click', function(){

		const $pname = $('input[name="pname"]');
		const pname = $pname.val().trim();
		const $err = $fieldBox($pname).find('span.error').first();

		if(pname == ""){
		    // 입력하지 않거나 공백만 입력했을 경우
		    $err.show();
		    $pname.focus();
		} else{
		    // 공백이 아닌 글자를 입력했을 경우
		    $err.hide();

		    // 서버로 보내는 값과 폼 제출 값이 같도록 앞뒤 공백을 지운 값으로 맞춘다.
		    $pname.val(pname);
			
			$.ajax({
				url:  `${ctx_Path}/admin/catalogue/pnameDuplicateCheck.go`,
				data: {"pname" : pname},
	            method : "post",     
				async : true,
				
				success : function(text){
					
					// 서버가 JSON 문자열 {"isExists":true|false} 를 돌려준다.
					// (이미 객체로 파싱돼 오는 경우도 있어 둘 다 처리)
					const json = (typeof text === "string") ? JSON.parse(text) : text;
					
					if(json.isExists){
						// 이미 존재하는 제품명이라면
						$('span#pnameDuplicateResult')
							.text(pname + " 은(는) 이미 사용 중입니다. 다른 제품명을 입력하세요.")
							.css({"color": "red"});
						b_pname_click = false;
					
					} else{
						// 존재하지 않는 제품명이라면
						$('span#pnameDuplicateResult')
							.text(pname + " 은(는) 사용 가능합니다.")
							.css({"color": "navy"});
						b_pname_click = true;
					}
				},
				error: function(request, status, error){
				    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            }
			});
		} // end of if~else()--------------------	   
	}); // end of $('#btnPnameDuplicate').on('click', function(){})---------------------

	// 제품명이 바뀌면 "중복체크"를 다시 해야 하므로 확인 상태와 결과 문구를 초기화한다.
	$('input[name="pname"]').on('change', function(){
		b_pname_click = false;
		$('span#pnameDuplicateResult').empty();
	});
		
		
}); // end of $(function(){})--------------------------
