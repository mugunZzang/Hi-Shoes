$(function(){	
	$('button#btnSubmit').on('click', function(){
		goLogin_Cookies(); // 로그인 시도한다.(아이디 저장은 Cookie를 사용함)
		//goLogin_localStorage(); // 로그인 시도한다.(아이디 저장은 localStorage를 사용함)
	});
	
	$('input#loginPwd').on('keyup', function(e){
		
		if(e.keyCode == 13) {	// 암호입력란에 엔터를 했을 경우
			goLogin_Cookies();	// 로그인 시도한다(아이디저장은 Cookie를 사용함).
			//goLogin_localStorage(); // 로그인 시도한다.(아이디 저장은 localStorage를 사용함)
		}
	});
});	// end of $('button#btnSubmit').on('click', function()----------------


// Function Declaration
// ==== 로그인 처리 함수 (아이디저장은 Cookie를 사용함) ==== //
function goLogin_Cookies() {
	
	
	if( $('input#loginUserid').val().trim() == "" ) {
		alert('아이디를 입력하세요!!');
		$('input#loginUserid').val("").focus();
		return; // goLogin_Cookies() 함수 종료
	}
	
	if( $('input#loginPwd').val().trim() == "" ) {
		alert('암호를 입력하세요!!');
		$('input#loginPwd').val("").focus();
		return; // goLogin_Cookies() 함수 종료
	}
	
	const frm = document.loginFrm;
	frm.submit();
	
}	// end of function goLogin_Cookies()----------

// ==== 로그인 처리 함수 (아이디저장은 localStorage를 사용함) ==== //
function goLogin_localStorage() {
	
	
	if( $('input#loginUserid').val().trim() == "" ) {
		alert('아이디를 입력하세요!!');
		$('input#loginUserid').val("").focus();
		return; // goLogin_localStorage() 함수 종료
	}
	
	if( $('input#loginPwd').val().trim() == "" ) {
		alert('암호를 입력하세요!!');
		$('input#loginPwd').val("").focus();
		return; // goLogin_localStorage() 함수 종료
	}
	
	if($('input:checkbox[id="saveid"]').prop("checked")) {
		//alert('아이디저장 체크를 하셨네요.');
		localStorage.setItem('saveid', $('input#loginUserid').val());
	}
	else {
		//alert('아이디저장 체크를 해제 하셨네요.');
		localStorage.removeItem('saveid');
	}
	
	
	const frm = document.loginFrm;
	frm.submit();
	
}	// end of function goLogin_Cookies()----------


// === 코인충전 결제금액 선택하기(실제로 카드 결제) === // 
function goCoinPurchaseTypeChoice(userid, ctx_Path) {
	// 코인충전 결제금액 선택하기 팝업창 띄우기
	const url = `${ctx_Path}/member/coinPurchaseTypeChoice.up?userid=${userid}`;
	
	// 너비 650, 높이 570인 팝업창을 화면 가운데 위치시키기
	const width = 650;
	const height = 570;
	
	const left = Math.ceil((window.screen.width - width) / 2);
				
	const top = Math.ceil((window.screen.height - height) / 2);
	
	window.open(url, "coinPurchaseTypeChoice", 
				`left=${left}, top=${top}, width=${width}, height=${height}`);
}	// end of function goCoinPurchaseTypeChoice(userid, ctx_Path)-------------------

// === 포트원 결제를 해주는 함수 === //
function goCoinPurchaseEnd(ctxPath, coinmoney, userid) {
	//alert(`확인용 부모창의 함수 호출함. \n결제금액: ${coinmoney}원, 사용자id: ${userid}`);
	
	// >>> 포트원 결제 팝업창 띄우기 <<< //
	// 너비 1000, 높이 600 인 팝업창을 화면 가운데 위치시키기
	
	const url = `${ctxPath}/member/coinPurchaseEnd.up?coinmoney=${coinmoney}&userid=${userid}`;
	
	const width = 1000;
	const height = 600;
	
	const left = Math.ceil((window.screen.width - width) / 2);				
	const top = Math.ceil((window.screen.height - height) / 2);
	
	window.open(url, "coinPurchaseEnd", 
					`left=${left}, top=${top}, width=${width}, height=${height}`);
}	// end of function goCoinPurchaseEnd(ctxPath, coinmoney, userid)------------

// ==== DB 상의 tbl_member 테이블에 해당 사용자의 코인액 및 포인트를 증가(update)시켜 주는 함수 ==== //
function goCoinUpdate(ctxPath, userid, coinmoney){
	//console.log(`~~ 확인용 userid : ${userid}, coinmoney: ${coinmoney}원`);
	
	// === jQuery 를 사용한 Ajax === //		
	$.ajax({
		url: `${ctxPath}/member/coinUpdateLoginUser.up`, 
		data: {"userid" : userid,
			   "coinmoney" : coinmoney}, // data 속성은 http://localhost:9090/MyMVC/member/coinUpdateLoginUser.up 로 전송 해야 할 데이터를 말한다.
		//type: "post", 	  //  type : "post",    // jQuery 4.x 이하 모든 버전에서 사용가능함. type 을 생략하면 type : "get" 이다.
     	method : "post",  // jQuery 4.x 버전에서만 사용가능함. jQuery 3.x 이하 버전에서는 사용불가함. method 를 생략하면 method : "get" 이다.
		
		async: true,	  // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
		                  // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
				
	    dataType: "json", // http://localhost:9090/MyMVC/member/coinUpdateLoginUser.up 에서 응답해주는 결과물의 타입은 json이다.
						  		  
		success: function(json) {		
			
			//console.log("json => ", json);			
			//  {loc: '/MyMVC/index.up', message: '육무군님의 300,000원 결제가 완료되었습니다.', n: 1} 
			
			alert(json.message);
			location.href=json.loc;
			
			
		},
		error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
	});
}; // end of function goCoinUpdate(ctxPath, userid, coinmoney)----------

// === 나의 정보 수정하기 === //
function goEditMyInfo(userid ,ctx_Path) {
	// 나의 정보 수정하기 팝업창 띄우기
	const url = `${ctx_Path}/member/memberEdit.up?userid=${userid}`;
		
	const width = 700;
	const height = 700;
	
	const left = Math.ceil((window.screen.width - width) / 2);				
	const top = Math.ceil((window.screen.height - height) / 2);
	
	window.open(url, "myInfoEdit", 
					`left=${left}, top=${top}, width=${width}, height=${height}`);
					
}	// end of function goEditMyInfo(userid ,ctx_Path)-----------------
