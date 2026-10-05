<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String ctxPath = request.getContextPath();    
%>
<jsp:include page="../header.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/login.css" />

<section class="bodycont container-fluid d-flex center justify-content-center align-items-center">
	<div class="center-cont container-sm my-5">
		<div class="title-wrap">
			<h2 class="section-tit">로그인</h2>
		</div>
		<div class="cont">
			<form class="row g-3 align-items-center">
			  <label for="inlineFormInputName2" class="d-none">아이디</label>
			  <input type="text" class="form-control mb-2 me-sm-2" id="inlineFormInputName2" placeholder="아이디를 입력 해주세요.">
			
			  <label for="inlineFormInputGroupUsername2" class="d-none">비밀번호</label>		  			    
		      <input type="text" class="form-control" id="inlineFormInputGroupUsername2" placeholder="비밀번호를 입력해주세요.">
			
			  <div class="form-check mb-2 me-sm-2">
			    <input class="form-check-input" type="checkbox" id="inlineFormCheck">
			    <label class="form-check-label" for="inlineFormCheck">
			      아이디 저장
			    </label>
			  </div>
			
			  <button type="submit" class="btn btn-lg btn-primary mb-4">로그인</button>
			</form>
			
			<section class="other-service mx-auto container text-center px-0">
				<ul class="row mx-auto px-0 text-cneter d-flex justify-content-center">
					<li class="col-3">
						<a href="#" data-bs-toggle="modal" data-bs-target="#userIdfind">아이디 찾기</a>	
					</li>
					<li class="col-3">
						<a href="#" data-bs-toggle="modal" data-bs-target="#passwdFind">비밀번호 찾기</a>	
					</li>
					<li class="col-3">
						<a href="#">회원가입</a>	
					</li>
				</ul>				
			</section>	
		</div>		
	</div>
</section>

<%-- ****** 아이디 찾기 Modal 시작 ****** --%>
<%-- <div class="modal fade" id="userIdfind"> --%> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%> 
  <div class="modal fade" id="userIdfind" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">아이디 찾기</h4>
          <button type="button" class="btn-close idFindClose" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="idFind">
          	<iframe id="iframe_idFind" style="border: none; width: 100%; height: 200px;" src="<%= ctxPath%>/login/idFind.go"> 
          	</iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger idFindClose" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div>
<%-- ****** 아이디 찾기 Modal 끝 ****** --%>	


<%-- ****** 비밀번호 찾기 Modal 시작 ****** --%>
  <div class="modal fade" id="passwdFind" data-bs-backdrop="static" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">비밀번호 찾기</h4>
          <button type="button" class="btn-close passwdFindClose" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="pwFind">
          	<iframe style="border: none; width: 100%; height: 300px;" src="<%= ctxPath%>/login/pwdFind.go">  
          	</iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger passwdFindClose" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div> 
<%-- ****** 비밀번호 찾기 Modal 끝 ****** --%>	

<jsp:include page="../footer.jsp" />
