<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
	// Hi-shoes
%>

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" > 

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/reset.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/login.css" />

<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>

<script type="text/javascript">
	$(function(){
		
		const method = "${requestScope.method}";
		
		//console.log("~~ 확인용 method : ", method);
		/*
			~~ 확인용 method :  GET
		*/		
				
		if(method == "GET") {
			$('div#div_findResult').hide();
		}
		
		if(method == "POST") {
			$('input:text[name="company"]').val("${requestScope.company}");
			$('input:text[name="email"]').val("${requestScope.email}");
		}
		
		$('.btn-idSearch').on('click', () => {
			goFind();		
		});
		
		$('input:text[name="email"]').on('keyup', (e) => {			
			if(e.keyCode == 13) {
				goFind();	
			}		
		});
	
	}); // end of $(function()-------------
			
	// Function Declaration
	function goFind() {
		const company = $('input:text[name="company"]').val().trim();
		
		if(company == "") {
			alert("사명을 입력하세요!!");
			return;	// goFind() 함수 종료
		}
		
		const email = $('input:text[name="email"]').val();
		
		const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;		
		// 이메일 정규표현식 객체 생성	
				
		const bool = regExp_email.test(email);
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
			alert('이메일을 올바르게 입력하세요!!');
			return;	// goFind() 함수 종료
		}
		
		const frm = document.idFindFrm;
		frm.action = "<%=ctxPath%>/login/idFind.go";
		frm.method = "post";
		frm.submit();
		
	}	// end of function goFind()-------
	
	// 아이디 찾기 모달 창에 입력한 input 태그 value 값 초기화 시키기
	function func_form_reset_empty() {
		document.querySelector('form[name="idFindFrm"]').reset();
		$('div#div_findResult').empty();
	}	//function func_form_reset_empty()---------------
	
</script>


<div class="container my-3" >
	<form class="row gap-3" name="idFindFrm">
		<div class="col-12">
			  <label for="company" class="d-none">사명</label>
			  <input type="text" class="form-control mb-3 me-sm-2" name="company" id="company" placeholder="사명을 입력 해주세요.">
			
			  <label for="email" class="d-none">이메일</label>		  			    
		      <input type="text" class="form-control" name="email" id="email" placeholder="이메일을 입력해주세요.">
		</div>
	  	
	  	<div class="col-12">
	  		<button type="button" class="btn-idSearch btn btn-md btn-primary w-100">아이디 찾기</button>	
	  	</div>
	
	  
	</form>
	<div class="result-wrap" id="div_findResult">
		<span>고객님의 아이디: </span> <span>${requestScope.userid}</span>
	</div>
</div>
