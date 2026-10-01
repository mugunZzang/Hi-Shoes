
// 제품명 중복체크 클릭 여부 알아오기 위한 용도
let b_pname_click = false;

$(function(){

    $('input[name="pname"]').on('blur', (e) =>{
        const pname = $(e.target).val().trim();
        if(pname == ""){
            // 입력하지 않거나 공백만 입력했을 경우
            $(e.target).parent().find('span.error').show();
        } else{
            // 공백이 아닌 글자를 입력했을 경우
            $(e.target).parent().find('span.error').hide();
        }
    });  // 제품명이 포커스를 잃어버렸을 경우 이벤트 처리
	
    // ===== 카탈로그 등록하기 ===== //
    $('input:button[id="btnRegister"]').on('click', function(){
        //alert("카탈로그 등록 시작");
        
        $('span.error').hide();

        // 필수 입력사항 입력 여부
        let is_infoData_OK = true;
		
		if(!b_pname_click) {
			// "중복확인" 클릭을 안 했을 경우
			alert('"중복확인"를 클릭하셔서 제품명이 중복되었는지 확인하셔야 합니다.');
		}

        // 필수 입력사항은 class 에 infoData로 만들어 놓음
        $('.infoData').each(function(index, elmt){
            // input 태그들의 요소 방문하며 값 확인
            const val = $(elmt).val().trim();

            if(val == ""){
                // 값이 공백인 경우
                $(elmt).next().show();
                is_infoData_OK = false;
                return false; // break
            }
        }); // end of infoData
		
		// 제품정가, 제품판매가, 제품구매가 는 숫자만 들어오도록 한다.
		const regExp_positiveNumber = /^[0-9]+$/;

		$('input.positiveNumber').each(function(index, elmt){

		    const value = $(elmt).val().trim();

		    if(value != "") {

		        if(!regExp_positiveNumber.test(value)) {

		            $(elmt).next().next()
		                .text("0보다 큰 수로 입력하세요.")
		                .css({"color":"red"});

		            $(elmt).val("").focus();

		            is_infoData_OK = false;
		            return false;

		        } else {

		            $(elmt).next().next().empty();

		        }
		    }
		});
		
		// 브랜드는 한글과 영어만 가능하다.
		const regExp_brand = /^[가-힣a-zA-Z]+$/;

		const brandName = $('input[name="brand"]').val().trim();

		if(brandName != "") {

		    // 브랜드에 값이 입력되어 있을 경우에만 정규식 검사

		    if(!regExp_brand.test(brandName)) {

		        $('input[name="brand"]').next().next()
		            .text("브랜드는 영어와 한글만 가능합니다.")
		            .css({"color":"red"});

		        $('input[name="brand"]').focus();

		        is_infoData_OK = false;

		    } else {

		        $('input[name="brand"]').next().next().empty();

		    }
		}
			
        if(is_infoData_OK && b_pname_click){
			const frm = document.catalogue;
			frm.submit();	
        } else{
//            alert("전송실패");
        }
    });
	
	$('input:button[id="btnPnameDuplicate"]').on('click', function(){
		// 아이디 데이터를 POST 로 보내야 하기에 form 태그 필요함
		if($('input:text[name="pname"]').val().trim() == ""){
		    // 입력하지 않거나 공백만 입력했을 경우
		    $('input:text[name="pname"]').parent().parent().find('span.error').show();
		} else{
		    // 공백이 아닌 글자를 입력했을 경우
		    $('input:text[name="pname"]').parent().parent().find('span.error').hide();
			
			// === jQuery 를 사용한 Ajax 첫번째 방법 === // 
			$.ajax({
				url:  `${ctx_Path}/admin/catalogue/pnameDuplicateCheck.go`,
				data: {"pname" : $('input:text[name="pname"]').val()},
	            method : "post",     
			
				async : true,     // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
	                               // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
				
				success : function(text){
					
					// text 는 idDuplicateCheck.up 을 통해 웹브라우저에 가져온 결과물인 "{"isExists":true}" 또는 "{"isExists":false}" 로 되어지는 string 타입의 결과물이다. 
					console.log(text);
					console.log("~~~text 의 데이터타입 : ", typeof(text));
					/*				
					{"isExists":true}
	
					memberRegister.js:393 ~~~text 의 데이터타입 :  string
					*/
					
					const json = JSON.parse(text);
					// JSON.parse(text); 은 JSON.parse("{"isExists":true}"); 또는 JSON.parse("{"isExists":false}"); 와 같은 것인데
	                // 그 결과물은 {"isExists":true} 또는 {"isExists":false} 와 같은 문자열을 자바스크립트 객체로 변환해주는 것이다. 
	                // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.
					
	//				console.log("json => ", json);
	//				console.log("json 의 데이터타입=> ", typeof(json));
					/*
					json =>  {isExists: true}isExists: true[[Prototype]]: Object
					memberRegister.js:401 json 의 데이터타입=>  object
					*/
					
					if(json.isExists){
						// 이미 아이디가 존재한다면
						$('span#pnameDuplicateResult').html($('input:text[name="pname"]').val()+" 은 이미 사용중이므로 제품명을 입력하세요.").css({"color": "red"});
						$('input#userid').val("");	
					
					} else{
						// 아이디가 존재하지 않는다면				
						$('span#pnameDuplicateResult').html($('input:text[name="pname"]').val()+" 은 사용가능합니다.").css({"color": "navy"});
						b_pname_click = true;
					}
				},
				error: function(request, status, error){
				                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            }
				});
			} // end of if~else()--------------------	   
		}); // end of $('input:button[id="btnPnameDuplicate"]').on('click', function(){})---------------------

		//* 제품명값이 변경되면 가입하기 버튼을 클릭시 "제품명중복확인" 을 클릭했는지 클릭안했는지를 알아보기위한 용도 초기화 시키기 
		$('input:text[name="pname"]').on('change', function(){
			b_pname_click = false;
		});
		
		
}); // end of $(function(){})--------------------------

