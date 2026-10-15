//$(document).ready(function(){});
//$(function(){});

<<<<<<< HEAD
let b_idcheck_click = false;
// "아이디 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도

let b_emailcheck_click = false;
// "이메일 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도

let b_zipcodeSearch_click = false;
// "우편번호찾기" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도

$(() => {
	
	$('p.error').hide();
//	$('input:text[id="name"]').focus();
//	또는
	$('input#pwd').focus();	
	
//	$('input#name').blur(() => { alert('name에 있던 포커스를 잃어버렸습니다-1.'); });
//	$('input#name').bind('blur', () => {alert('name에 있던 포커스를 잃어버렸습니다-2.');});
//	$('input#name').on('blur', () => {alert('name에 있던 포커스를 잃어버렸습니다-3.');});
	
		
	$('input#pwd').on("blur", (e) => {
				
		const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
		// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 개체 생성
		
		const bool = regExp_pwd.test($(e.target).val());
		
		if(!bool) {
			// 암호가 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 비밀번호가 정규표현식에 맞는 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 pwd 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#pwdcheck').on("blur", (e) => {				
				
		if($('input#pwd').val() != $(e.target).val()) {
			// 비밀번호가 비밀번호 확인값과 값이 다른 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);
			
			$('input#pwd').prop("disabled", false).val("").focus();
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 비밀번호와 비밀번호 확인 값이 같은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 pwdcheck 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#email').on("blur", (e) => {				
					
		const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;		
		// 이메일 정규표현식 객체 생성	
				
		const bool = regExp_email.test($(e.target).val());
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 이메일이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#hp2').on("blur", (e) => {				
						
		const regExp_hp2 = /^[1-9][0-9]{3}$/;		
		// 휴대폰 국번 정규표현식 객체 생성	
				
		const bool = regExp_hp2.test($(e.target).val());
		
		if(!bool) {
			// 휴대폰 국번이 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 휴대폰 국번이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 hp2 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#hp3').on("blur", (e) => {				
							
		//const regExp_hp3 = /^[1-9]{4}$/;		
		// 또는
		const regExp_hp3 = /^\d{4}$/;		
		// 휴대폰 국번 마지막 4자리 정규표현식 객체 생성	
				
		const bool = regExp_hp3.test($(e.target).val());
		
		if(!bool) {
			// 휴대폰 마지막 4자리가 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 이메일이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 hp3 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#postcode').on("keyup", (e) => {				
		
		alert('우편번호 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 postcode 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	$('input#address').on("keyup", (e) => {				
			
		alert('주소 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 address 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	$('input#extraAddress').on("keyup", (e) => {				
			
		alert('참고항목 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 extraAddress 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	// === "우편번호찾기"를 클릭했을 때 이벤트 처리하기 === //
	$('#zipcodeSearch').on('click', function(){
		
		b_zipcodeSearch_click = true;
		
	    new daum.Postcode({
	    oncomplete: function(data) {
	        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	        let addr = ''; // 주소 변수
	        let extraAddr = ''; // 참고항목 변수

	        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	            addr = data.roadAddress;
	        } else { // 사용자가 지번 주소를 선택했을 경우(J)
	            addr = data.jibunAddress;
	        }

	        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	        if(data.userSelectedType === 'R'){
	            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                extraAddr += data.bname;
	            }
	            // 건물명이 있고, 공동주택일 경우 추가한다.
	            if(data.buildingName !== '' && data.apartment === 'Y'){
	                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	            }
	            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	            if(extraAddr !== ''){
	                extraAddr = ' (' + extraAddr + ')';
	            }
	            // 조합된 참고항목을 해당 필드에 넣는다.
	            document.getElementById("extraAddress").value = extraAddr;
	        
	        } else {
	            document.getElementById("extraAddress").value = '';
	        }

	        // 우편번호와 주소 정보를 해당 필드에 넣는다.
	        document.getElementById('postcode').value = data.zonecode;
	        document.getElementById("address").value = addr;
	        // 커서를 상세주소 필드로 이동한다.
	        document.getElementById("detailAddress").focus();
	    	}
		}).open();
	
		// 우편번호를 읽기 전용(readonly)로 만들기
		$('input#postcode').attr("readonly", true);
		
		// 주소를 읽기 전용(readonly)로 만들기
		$('input#address').attr("readonly", true);
		
		// 참고항목을 읽기 전용(readonly)로 만들기
		$('input#extraAddress').attr("readonly", true);
		
	});	//end of $('img#zipcodeSearch').on('click', function() ------------	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	
	// "사업자등록번호중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$('#busiNumcheck').on('click', function(){
		b_busiNumcheck_click = true;
		// "사업자등록번호 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도
		
		//입력하고자 하는 사업자등록번호가 데이터베이스 테이블에 존재하는지, 존재하지 않는 지 알아와야한다.(JAVA가 하는일임.. 그런데 여기서 어떻게 처리하지? 바로 AJAX가 필요해지는 순간임.)
		/*
           Ajax (Asynchronous JavaScript and XML)란?                         
          ==> 이름만 보면 알 수 있듯이 '비동기 방식의 자바스크립트와 XML' 로서     
              Asynchronous JavaScript + XML 인 것이다.
              한마디로 말하면, Ajax 란? Client 와 Server 간에 XML 데이터를 JavaScript 를 사용하여 비동기 통신으로 주고 받는 기술이다.
              하지만 요즘에는 데이터 전송을 위한 데이터 포맷방법으로 XML 을 사용하기 보다는 JSON(Javascript Standard Object Notation) 을 더 많이 사용한다. 
              참고로 HTML은 데이터 표현을 위한 포맷방법이다.
              그리고, 비동기식이란 어떤 하나의 웹페이지에서 여러가지 서로 다른 다양한 일처리가 개별적으로 발생한다는 뜻으로서, 
              어떤 하나의 웹페이지에서 서버와 통신하는 그 일처리가 발생하는 동안 일처리가 마무리 되기전에 또 다른 작업을 할 수 있다는 의미이다.
        */
	   
		// === jQuery 를 사용한 Ajax 첫번째 방법 === //
		$.ajax({
			url: "busiNumDuplicateCheck.go", 
			data: {"busiNum" : $('input#busiNum').val()},	
			// data 속성은 http://localhost:9090/MyMVC/member/idDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
			type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
         	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
			
			async: true,	  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
							  
			success: function(text) {
				//console.log("text => ", text);
				//text 는 idDuplicateCheck.up 을 통해 웹브라우저에서 가져온 결과물인 "{"isExists":true}" 또는 "{"isExists":false}" 로 되어지는 string 타입의 결과물이다. 
				//console.log("text의 데이터 타입 => ", typeof text);
				//text의 데이터 타입 =>  string
				
				const json = JSON.parse(text);
				// JSON.parse(text); 은 JSON.parse("{"isExists":true}"); 또는 JSON.parse("{"isExists":false}"); 와 같은 것인데
                // 그 결과물은 {"isExists":true} 또는 {"isExists":false} 와 같은 문자열을 자바스크립트 객체로 변환해주는 것이다. 
                // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.
				
				//console.log("json => ", json);
				//json => {isExists: true}
				//json => {isExists: false}
				
				//console.log("json의 데이터 타입 => ", typeof json);
				//json의 데이터 타입 =>  object
				
				if(json.isExists){
					// 입력한 busiNum가 이미 사용 중이라면
					$('#busiNumcheckResult').html($('input#busiNum').val() + " 은 이미 사용중이므로 다른 사업자등록번호를 입력하세요.").css({"color":"red"});
					$('input#busiNum').val("");
				}
				else {
					// 입력한 busiNum가 존재하지 않는 경우라면
					$('#busiNumcheckResult').html($('input#busiNum').val() + " 은 사용가능 합니다.").css({"color":"#87A922"});
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});		
		
	});
	// "사업자등록번호중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	// "아이디중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$('#idcheck').on('click', function(){
		b_idcheck_click = true;
		// "아이디 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도
		
		//입력하고자 하는 아이디가 데이터베이스 테이블에 존재하는지, 존재하지 않는 지 알아와야한다.(JAVA가 하는일임.. 그런데 여기서 어떻게 처리하지? 바로 AJAX가 필요해지는 순간임.)
		/*
           Ajax (Asynchronous JavaScript and XML)란?                         
          ==> 이름만 보면 알 수 있듯이 '비동기 방식의 자바스크립트와 XML' 로서     
              Asynchronous JavaScript + XML 인 것이다.
              한마디로 말하면, Ajax 란? Client 와 Server 간에 XML 데이터를 JavaScript 를 사용하여 비동기 통신으로 주고 받는 기술이다.
              하지만 요즘에는 데이터 전송을 위한 데이터 포맷방법으로 XML 을 사용하기 보다는 JSON(Javascript Standard Object Notation) 을 더 많이 사용한다. 
              참고로 HTML은 데이터 표현을 위한 포맷방법이다.
              그리고, 비동기식이란 어떤 하나의 웹페이지에서 여러가지 서로 다른 다양한 일처리가 개별적으로 발생한다는 뜻으로서, 
              어떤 하나의 웹페이지에서 서버와 통신하는 그 일처리가 발생하는 동안 일처리가 마무리 되기전에 또 다른 작업을 할 수 있다는 의미이다.
        */
	   
		// === jQuery 를 사용한 Ajax 첫번째 방법 === //
		$.ajax({
			url: "idDuplicateCheck.go", 
			data: {"userid" : $('input#userid').val()},	
			// data 속성은 http://localhost:9090/MyMVC/member/idDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
			type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
         	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
			
			async: true,	  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
							  
			success: function(text) {
				//console.log("text => ", text);
				//text 는 idDuplicateCheck.up 을 통해 웹브라우저에서 가져온 결과물인 "{"isExists":true}" 또는 "{"isExists":false}" 로 되어지는 string 타입의 결과물이다. 
				//console.log("text의 데이터 타입 => ", typeof text);
				//text의 데이터 타입 =>  string
				
				const json = JSON.parse(text);
				// JSON.parse(text); 은 JSON.parse("{"isExists":true}"); 또는 JSON.parse("{"isExists":false}"); 와 같은 것인데
                // 그 결과물은 {"isExists":true} 또는 {"isExists":false} 와 같은 문자열을 자바스크립트 객체로 변환해주는 것이다. 
                // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.
				
				//console.log("json => ", json);
				//json => {isExists: true}
				//json => {isExists: false}
				
				//console.log("json의 데이터 타입 => ", typeof json);
				//json의 데이터 타입 =>  object
				
				if(json.isExists){
					// 입력한 userid가 이미 사용 중이라면
					$('#idcheckResult').html($('input#userid').val() + " 은 이미 사용중이므로 다른 아이디를 입력하세요.").css({"color":"red"});
					$('input#userid').val("");
				}
				else {
					// 입력한 userid가 존재하지 않는 경우라면
					$('#idcheckResult').html($('input#userid').val() + " 은 사용가능 합니다.").css({"color":"#87A922"});
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});		
		
	});
	// "아이디중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$('#emailcheck').on('click', function(){		
		b_emailcheck_click = true;
		// "이메일 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도
		
		// === jQuery 를 사용한 Ajax 두번째 방법 === //		
		$.ajax({
			url: "emailDuplicateCheck.go", 
			data: {"email" : $('input#email').val()},	
			// data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
			type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
         	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
			
			async: true,	  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
					
		    dataType: "json", // emailDuplicateCheck.up 에서 응답해주는 결과물의 타입은 json이다.
							  		  
			success: function(json) {
				//console.log("json => ", json);
				//json => {isExists: true}
				//json => {isExists: false}
				//json 는 idDuplicateCheck.up 을 통해 웹브라우저에서 가져온 결과물인 "{"isExists":true}" 또는 "{"isExists":false}" 로 되어지는 object 타입의 결과물이다. 
				
				//console.log("json의 데이터 타입 => ", typeof json);
				//json의 데이터 타입 =>  object		
				
				console.log("json => ", json);
				//json => {isExists: true}
				//json => {isExists: false}
				
				console.log("json의 데이터 타입 => ", typeof json);
				//json의 데이터 타입 =>  object
				
				if(json.isExists){
					// 입력한 userid가 이미 사용 중이라면
					$('#emailCheckResult').html($('input#email').val() + " 은 이미 사용중이므로 다른 이메일을 입력하세요.").css({"color":"red"});
					$('input[name="email"]').val("");
				}
				else {
					// 입력한 email가 존재하지 않는 경우라면
					$('#emailCheckResult').html($('input#email').val() + " 은 사용가능 합니다.").css({"color":"#87A922"});
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	});
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	// 아이디 값이 변경되면 가입하기 버튼을 클릭 시 "아이디중복확인" 을 클릭했는지 안했는지 알아보기 //	
	$('input#userid').on('change', function(){
		b_idcheck_click = false;
	});
	
	// 이메일 값이 변경되면 가입하기 버튼을 클릭 시 "이메일중복확인" 을 클릭했는지 안했는지 알아보기 //
	$('input#email').on('change', function(){
		b_emailcheck_click = false;
	});
		
});	// end of $(() => -----------



//Function Declaration
// "수정하기" 버튼 클릭 시 호출되는 함수
function goEdit() {
	
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	let b_requiredInfo = true;
	
	const requiredInfo_list = document.querySelectorAll('input.requiredInfo');
	
	for(let elmt of requiredInfo_list) {		
		const data = elmt.value.trim();
		if(data == "") {
			alert('*표시된 필수입력사항은 모두 입력하셔야 합니다.-1');
			b_requiredInfo = false;
			
			break;
		}
	}	// end of for--------------------
	
	// 또는
	/*
	$('input.requiredInfo').each(function(index, elmt){
		const data = $(elmt).val().trim();
		
		if(data == "") {
			alert('*표시된 필수입력사항은 모두 입력하셔야 합니다.-2');
			b_requiredInfo = false;
			return false;	//break;와 같은 뜻
		}
	});
	*/
	
	if(!b_requiredInfo) {
		return;		// goEdit() 함수를 종료한다.
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //
	
			
	// *** "이메일중복확인"를 클릭했는지 검사하기 시작 *** //	
	if(!b_emailcheck_click) {
		//"이메일중복확인"를 클릭을 안 했을 경우
		alert('이메일중복확인을 클릭하셔야 합니다.');
		return;	//goRegister() 함수를 종료한다.
	}		
	// *** "이메일중복확인"를 클릭했는지 검사하기 끝 *** //
	
	// *** "우편번호찾기"를 클릭했는지 검사하기 시작 *** //	
	if(!b_zipcodeSearch_click) {
		//"우편번호찾기"를 클릭을 안 했을 경우
		alert('"우편번호찾기"를 클릭하셔서 우편번호를 입력하셔야 합니다.');
		return;	//goRegister() 함수를 종료한다.
	}	
	// *** "우편번호찾기"를 클릭했는지 검사하기 끝 *** //	
	
	// *** 우편번호 및 주소에 값을 입력햇는지 검사하기 시작 *** //
	const postcode = $('input#postcode').val().trim();
	const address = $('input#address').val().trim();
	const detailAddress = $('input#detailAddress').val().trim();
	
	if(postcode == "" || address == "" || detailAddress == "") {
		alert('우편번호 및 주소를 입력하셔야 합니다.');
		return;	//goEdit() 함수를 종료한다.
	}
	
	// *** 우편번호 및 주소에 값을 입력햇는지 검사하기 끝 *** //
	
	// 변경한 비밀번호가 현재 사용 중인 비밀번호와 같은 것인지 다른 것인지 판단을 해 주어야 한다.
	let isNewPwd = false;
		
	// === jQuery 를 사용한 Ajax 방법 === //		
	$.ajax({
		url: "duplicatePwdCheck.go", 
		data: {"new_pwd" : $('input:password[name="pwd"]').val(),
			   "userid" : $('input:hidden[name="userid"]').val()},	
		// data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
		//type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
     	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
		
		async: false,	  // !!! 반드시 동기방식 이어야 한다. !!! 
						  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
		                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
				
	    dataType: "json", // emailDuplicateCheck.up 에서 응답해주는 결과물의 타입은 json이다.
						  		  
		success: function(json) {			
			
			if(json.isUsed){
				// 입력한 새료운 비밀번호가 현재 비밀번호와 같다면
				$('#duplicate_pwd').html("현재 사용 중인 비밀번호로 비밀번호 변경은 불가합니다.");
				isNewPwd = false;
			}
			else {
				// 입력한 비밀번호가 새로운 것이라면
				isNewPwd = true;
			}
		},
		error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
	});		
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	
	
	// 변경한 비밀번호가 새로운 비밀번호 일 경우
	if(isNewPwd){
		//alert("새로운 비밀번호 이므로 DB에 사용자 정보를 수정하러 간다.");
		
		const frm = document.editFrm;
		frm.action = "memberEditEnd.go";
		frm.method = "post";
		frm.submit();
=======
let b_emailcheck_click = false;
// "이메일 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도

let b_zipcodeSearch_click = false;
// "우편번호찾기" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도

$(() => {
	
	$('p.error').hide();
//	$('input:text[id="name"]').focus();
//	또는
	$('input#pwd').focus();	
	
//	$('input#name').blur(() => { alert('name에 있던 포커스를 잃어버렸습니다-1.'); });
//	$('input#name').bind('blur', () => {alert('name에 있던 포커스를 잃어버렸습니다-2.');});
//	$('input#name').on('blur', () => {alert('name에 있던 포커스를 잃어버렸습니다-3.');});
	
		
	$('input#pwd').on("blur", (e) => {
		
		$('#duplicate_pwd').html("").css("display", "none");		
		
		const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
		// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 개체 생성
		
		const bool = regExp_pwd.test($(e.target).val());
		
		if(!bool) {
			// 암호가 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 비밀번호가 정규표현식에 맞는 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 pwd 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#pwdcheck').on("blur", (e) => {				
				
		if($('input#pwd').val() != $(e.target).val()) {
			// 비밀번호가 비밀번호 확인값과 값이 다른 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);
			
			$('input#pwd').prop("disabled", false).val("").focus();
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 비밀번호와 비밀번호 확인 값이 같은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 pwdcheck 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#email').on("blur", (e) => {				
					
		const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;		
		// 이메일 정규표현식 객체 생성	
				
		const bool = regExp_email.test($(e.target).val());
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 이메일이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#hp2').on("blur", (e) => {				
						
		const regExp_hp2 = /^[1-9][0-9]{3}$/;		
		// 휴대폰 국번 정규표현식 객체 생성	
				
		const bool = regExp_hp2.test($(e.target).val());
		
		if(!bool) {
			// 휴대폰 국번이 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 휴대폰 국번이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 hp2 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#hp3').on("blur", (e) => {				
							
		//const regExp_hp3 = /^[1-9]{4}$/;		
		// 또는
		const regExp_hp3 = /^\d{4}$/;		
		// 휴대폰 국번 마지막 4자리 정규표현식 객체 생성	
				
		const bool = regExp_hp3.test($(e.target).val());
		
		if(!bool) {
			// 휴대폰 마지막 4자리가 정규표현식에 위배된 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);			
			$(e.target).prop("disabled", false).val("").focus();
			
			$(e.target).parent().parent().find('p.error').show();			
		}
		else {
			// 이메일이 정규표현식에 위배되지 않은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			$(e.target).parent().parent().find('p.error').hide();	
		}
	});	// 아이디가 hp3 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$('input#postcode').on("keyup", (e) => {				
		
		alert('우편번호 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 postcode 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	$('input#address').on("keyup", (e) => {				
			
		alert('주소 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 address 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	$('input#extraAddress').on("keyup", (e) => {				
			
		alert('참고항목 입력은 직접 입력은 불가하고\n우편번호 찾기를 통해 입력가능합니다.');			
		$(e.target).val("");	
		
	});	// 아이디가 extraAddress 인 것에 키보드를 눌렀다가 손을 뗄 경우
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	// === "우편번호찾기"를 클릭했을 때 이벤트 처리하기 === //
	$('#zipcodeSearch').on('click', function(){
		
		b_zipcodeSearch_click = true;
		
	    new daum.Postcode({
	    oncomplete: function(data) {
	        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	        let addr = ''; // 주소 변수
	        let extraAddr = ''; // 참고항목 변수

	        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	            addr = data.roadAddress;
	        } else { // 사용자가 지번 주소를 선택했을 경우(J)
	            addr = data.jibunAddress;
	        }

	        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	        if(data.userSelectedType === 'R'){
	            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                extraAddr += data.bname;
	            }
	            // 건물명이 있고, 공동주택일 경우 추가한다.
	            if(data.buildingName !== '' && data.apartment === 'Y'){
	                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	            }
	            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	            if(extraAddr !== ''){
	                extraAddr = ' (' + extraAddr + ')';
	            }
	            // 조합된 참고항목을 해당 필드에 넣는다.
	            document.getElementById("extraAddress").value = extraAddr;
	        
	        } else {
	            document.getElementById("extraAddress").value = '';
	        }

	        // 우편번호와 주소 정보를 해당 필드에 넣는다.
	        document.getElementById('postcode').value = data.zonecode;
	        document.getElementById("address").value = addr;
	        // 커서를 상세주소 필드로 이동한다.
	        document.getElementById("detailAddress").focus();
	    	}
		}).open();
	
		// 우편번호를 읽기 전용(readonly)로 만들기
		$('input#postcode').attr("readonly", true);
		
		// 주소를 읽기 전용(readonly)로 만들기
		$('input#address').attr("readonly", true);
		
		// 참고항목을 읽기 전용(readonly)로 만들기
		$('input#extraAddress').attr("readonly", true);
		
	});	//end of $('img#zipcodeSearch').on('click', function() ------------	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$('#emailcheck').on('click', function(){
		
		if ($('input#email').val().trim() == "") {
									
			$('form[name="editFrm"] :input').prop("disabled", true);
			$('input#email').prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$('input#email').parent().parent().find('p.error').show();	
			
		}else {			
			b_emailcheck_click = true;
			// "이메일 중복확인" 를 클릭했는지 클릭을 안햇는지 여부를 알아오기 위한 용도
			
			// === jQuery 를 사용한 Ajax 두번째 방법 === //		
			$.ajax({
				url: "emailDuplicateCheck.go", 
				data: {"email" : $('input#email').val()},	
				// data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
				type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
	         	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
				
				async: true,	  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
				                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
						
			    dataType: "json", // emailDuplicateCheck.up 에서 응답해주는 결과물의 타입은 json이다.
								  		  
				success: function(json) {
					//console.log("json => ", json);
					//json => {isExists: true}
					//json => {isExists: false}
					//json 는 idDuplicateCheck.up 을 통해 웹브라우저에서 가져온 결과물인 "{"isExists":true}" 또는 "{"isExists":false}" 로 되어지는 object 타입의 결과물이다. 
					
					//console.log("json의 데이터 타입 => ", typeof json);
					//json의 데이터 타입 =>  object		
					
					console.log("json => ", json);
					//json => {isExists: true}
					//json => {isExists: false}
					
					console.log("json의 데이터 타입 => ", typeof json);
					//json의 데이터 타입 =>  object
					
					if(json.isExists){
						// 입력한 userid가 이미 사용 중이라면
						$('#emailCheckResult').html($('input#email').val() + " 은 이미 사용중이므로 다른 이메일을 입력하세요.").css({"color":"red"});
						$('input[name="email"]').val("");
					}
					else {
						// 입력한 email가 존재하지 않는 경우라면
						$('#emailCheckResult').html($('input#email').val() + " 은 사용가능 합니다.").css({"color":"#87A922"});
					}
				},
				error: function(request, status, error){
	                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            }
			});
		}
	});
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	
	// 이메일 값이 변경되면 가입하기 버튼을 클릭 시 "이메일중복확인" 을 클릭했는지 안했는지 알아보기 //
	$('input#email').on('change', function(){
		b_emailcheck_click = false;
	});
		
});	// end of $(() => -----------



//Function Declaration
// "수정하기" 버튼 클릭 시 호출되는 함수
function goEdit() {
	
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	let b_requiredInfo = true;
	
	const requiredInfo_list = document.querySelectorAll('input.requiredInfo');
	
	for(let elmt of requiredInfo_list) {		
		const data = elmt.value.trim();
		if(data == "") {
			alert('* 표시된 필수입력사항은 모두 입력하셔야 합니다.');
			b_requiredInfo = false;
			
			break;
		}
	}	// end of for--------------------
	
	// 또는
	/*
	$('input.requiredInfo').each(function(index, elmt){
		const data = $(elmt).val().trim();
		
		if(data == "") {
			alert('*표시된 필수입력사항은 모두 입력하셔야 합니다.-2');
			b_requiredInfo = false;
			return false;	//break;와 같은 뜻
		}
	});
	*/
	
	if(!b_requiredInfo) {
		return;		// goEdit() 함수를 종료한다.
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //
	
			
	// *** "이메일중복확인"를 클릭했는지 검사하기 시작 *** //	
	if(!b_emailcheck_click) {
		//"이메일중복확인"를 클릭을 안 했을 경우
		alert('이메일중복확인을 클릭하셔야 합니다.');
		return;	//goRegister() 함수를 종료한다.
	}		
	// *** "이메일중복확인"를 클릭했는지 검사하기 끝 *** //
	
	// *** "우편번호찾기"를 클릭했는지 검사하기 시작 *** //	
	if(!b_zipcodeSearch_click) {
		//"우편번호찾기"를 클릭을 안 했을 경우
		alert('"우편번호찾기"를 클릭하셔서 우편번호를 입력하셔야 합니다.');
		return;	//goRegister() 함수를 종료한다.
	}	
	// *** "우편번호찾기"를 클릭했는지 검사하기 끝 *** //	
	
	// *** 우편번호 및 주소에 값을 입력햇는지 검사하기 시작 *** //
	const postcode = $('input#postcode').val().trim();
	const address = $('input#address').val().trim();
	const detailAddress = $('input#detailAddress').val().trim();
	
	if(postcode == "" || address == "" || detailAddress == "") {
		alert('우편번호 및 주소를 입력하셔야 합니다.');
		return;	//goEdit() 함수를 종료한다.
	}
	
	// *** 우편번호 및 주소에 값을 입력햇는지 검사하기 끝 *** //
	
	// 변경한 비밀번호가 현재 사용 중인 비밀번호와 같은 것인지 다른 것인지 판단을 해 주어야 한다.
	let isNewPwd = false;
		
	// === jQuery 를 사용한 Ajax 방법 === //		
	$.ajax({
		url: "duplicatePwdCheck.go", 
		data: {"new_pwd" : $('.duplChk input:password[name="pwd"]').val(),
			   "userid" : $('.duplChk input[name="userid"]').val()},	
		// data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송 해야 할 데이터를 말한다.
		//type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
     	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
		
		async: false,	  // !!! 반드시 동기방식 이어야 한다. !!! 
						  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
		                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
				
	    dataType: "json", // emailDuplicateCheck.up 에서 응답해주는 결과물의 타입은 json이다.
						  		  
		success: function(json) {			
			
			if(json.isUsed){
				// 입력한 새료운 비밀번호가 현재 비밀번호와 같다면
				$('#duplicate_pwd').html("현재 사용 중인 비밀번호로 비밀번호 변경은 불가합니다.").css("display", "block");
				isNewPwd = false;
			}
			else {
				// 입력한 비밀번호가 새로운 것이라면
				$('#duplicate_pwd').html("").css("display", "none");
				isNewPwd = true;
			}
		},
		error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
	});		
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	
	
	// 변경한 비밀번호가 새로운 비밀번호 일 경우
	if(isNewPwd){
		//alert("새로운 비밀번호 이므로 DB에 사용자 정보를 수정하러 간다.");
		
		if($('input#pwd').val() != $('input#pwdcheck').val()) {
			// 비밀번호가 비밀번호 확인값과 값이 다른 경우
			/*   
	            >>>> .prop() 와 .attr() 의 차이 <<<<            
	                  .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함. 
	                  .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
	         */
			$('form[name="editFrm"] :input').prop("disabled", true);
			
			$('input#pwd').prop("disabled", false).val("").focus();
			$('input#pwdcheck').prop("disabled", false).val("").focus();
			
			//$(e.target).next().next().next().show();
			// 또는 
			$('input#pwdcheck').parent().parent().find('p.error').show();			
		}
		else {

			// 비밀번호와 비밀번호 확인 값이 같은 경우
			$('form[name="editFrm"] :input').prop("disabled", false);	
			
			//$(e.target).next().next().next().hide();
			// 또는	
			$('input#pwdcheck').parent().parent().find('p.error').hide();
			
			const frm = document.editFrm;
			frm.action = "memberEditEnd.go";
			frm.method = "post";
			frm.submit();
		}
>>>>>>> refs/heads/yukmg
	}
	
	
};	// end of function goRegister()----------