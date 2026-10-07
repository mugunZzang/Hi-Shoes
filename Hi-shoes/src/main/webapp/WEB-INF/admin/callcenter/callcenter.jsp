<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctx_Path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../adminHeader.jsp"/>

<script type="text/javascript">

$(function(){
	
	
	
	
	
	
	
}); // end of $(function(){

function goSearch(frm, type) {

    const searchType = $(frm).find('select[name="searchType"]').val();

    if(searchType == "") {
        alert("검색대상을 선택하세요!!");
        return;
    }

    // 어떤 탭에서 검색했는지 전달
    $(frm).append('<input type="hidden" name="tab" value="' + type + '">');

    frm.submit();
}// end of function goSearch(frm, type)

function goDelete(num, type) {
    if (confirm("삭제하시겠습니까?")) {
        // 실제 삭제 처리할 코드
        $.ajax({
        		url:"<%= ctx_Path %>/admin/callcenter/delete.go", 
            	 method:"post",
            	 data: {
                     "num": num,
                     "type": type
                    },
                 dataType:"json",
                 success:function(json){
                	 if (json.result == 1) {
                         alert("삭제되었습니다.");

                         
                         location.reload();
                     }
                     else {
                         alert("삭제에 실패했습니다.");
                     }
                 },
                 error: function(request, status, error){
  				    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
  		         }
             });
    } else {
        // 취소를 눌렀을 때
        return;
    }
}

</script>

<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold">
            고객센터
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    고객센터
                </li>

            </ol>

        </nav>

    </div>

<ul class="nav nav-tabs nav-fill">
        <li class="nav-item">
          <a class="nav-link ${tab == 'notice' ? 'active' : ''}" href="callcenter.go?tab=notice">공지사항</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${tab == 'faq' ? 'active' : ''}" href="callcenter.go?tab=faq">FAQ</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${tab == 'question' ? 'active' : ''}" href="callcenter.go?tab=question">문의사항</a>
        </li>
</ul>

<div class="tab-content" style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">

    <!-- 공지사항 -->
    <div class="tab-pane fade ${tab == 'notice' ? 'show active' : ''}" id="notice">
    	<form name="notice_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="subject">글제목</option>
				<option value="content">글내용</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form,'notice')">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 20%;">글제목</th>
                <th style="width: 50%;">글내용</th>
                <th style="width: 10%;">작성일</th>
                <th style="width: 10%; text-align:center;">삭제유무</th>
            </tr>
          </thead>
          	<tbody>
            	<c:if test="${not empty requestScope.noticeList}">
			        <c:forEach var="notice" items="${noticeList}" varStatus="status">
			            <tr class="noticeInfo" onclick="location.href='${pageContext.request.contextPath}/admin/callcenter/noticeEdit.go?nnum=${notice.nnum}'"
    							style="cursor:pointer;">
			                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
			                <td>${notice.nsubject}</td>
			                <td>${notice.ncontents}</td>
			                <td>${notice.nwritedate}</td>
			                <td class="text-center"><button type="button"
		            						class="btn btn-danger"
		           						onclick="event.stopPropagation(); goDelete(${notice.nnum}, 'notice'); " >
		        					삭제
		    						</button></td>
			            </tr>
			        </c:forEach>
			    </c:if>
            
	            <c:if test="${empty requestScope.noticeList}">
			        <tr>
			            <td class="text-center" colspan="3">공지사항이 없습니다.</td>
			        </tr>
			    </c:if>
		    </tbody>
        </table>
        <nav class="my-5">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.noticePageBar}</ul>
	   	   </div>
		</nav> 
        <div class="text-end">
        	<button type="button" style="border-radius: 10px; font-size: 16px; padding: 6px 10px;" onclick="location.href='noticeWrite.go'">글작성</button>
        </div>
    </div>

    <!-- FAQ -->
    <div class="tab-pane fade ${tab == 'faq' ? 'show active' : ''}" id="faq">
    	<form name="faq_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="register">가입/탈퇴</option>
				<option value="change">정보변경</option>
				<option value="pay">결제</option>
				<option value="order">주문</option>
				<option value="cancle">취소</option>
				<option value="pinfo">상품정보</option>
				<option value="delivery">배송</option>
			</select>
			&nbsp;
			<button type="button"
            class="btn btn-secondary"
            onclick="goSearch(this.form, 'faq')">
        		검색
    		</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 20%;">글제목</th>
                <th style="width: 50%;">글내용</th>
                <th style="width: 10%;">카테고리</th>
                <th style="width: 10%; text-align:center;">삭제유무</th>
            </tr>
          </thead>
			 <tbody>
            	<c:if test="${not empty requestScope.faqList}">
            	
			        <c:forEach var="faq" items="${requestScope.faqList}" varStatus="status">
			            <tr class="faqInfo" onclick="location.href='${pageContext.request.contextPath}/admin/callcenter/faqEdit.go?fnum=${faq.fnum}'"
    							style="cursor:pointer;">
			                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
			                <td>${faq.fsubject}</td>
			                <td>${faq.fcontents}</td>
			                <td>${faq.fcategory}</td>
			                <td class="text-center"><button type="button"
		            						class="btn btn-danger"
		           						onclick="goDelete(${faq.fnum}, 'faq'); event.stopPropagation();">
		        					삭제
		    						</button></td>
			            </tr>
			        </c:forEach>
			    </c:if>
            
	            <c:if test="${empty requestScope.faqList}">
			        <tr>
			            <td class="text-center" colspan="4">faq가 없습니다.</td>
			        </tr>
			    </c:if>
		    </tbody>
        </table>
        <nav class="my-5">
        
        
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.faqPageBar}</ul>
	   	   </div>
		</nav>
        
        <div class="text-end">
        	<button type="button" style="border-radius: 10px; font-size: 16px; padding: 6px 10px;" onclick="location.href='faqWrite.go'">글작성</button>
        </div>
        
    </div> 	

    <!-- 문의사항 -->
    <div class="tab-pane fade ${tab == 'question' ? 'show active' : ''}" id="question">
    	<form name="question_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="pname">판매상품명</option>
				<option value="userid">아이디</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form,'question')">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 10%;">판매상품명</th>
                <th style="width: 10%;">아이디</th>
                <th style="width: 50%;">내용</th>
                <th style="width: 20%;">작성일자</th>
            </tr>
          </thead>
            <tbody>
            	<c:if test="${not empty requestScope.questionList}">
			        <c:forEach var="question" items="${requestScope.questionList}" varStatus="status">
			            <tr onclick="location.href='questionEdit.go?qnanum=${question.qnanum}'"
    							style="cursor:pointer;">
			                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
			                <td>${question.fk_pname}</td>
			                <td>${question.fk_userid}</td>
			                <td>${question.qcontents}</td>
			                <td>${question.qwritedate}</td>
			            </tr>
			        </c:forEach>
			    </c:if>
            
	            <c:if test="${empty requestScope.questionList}">
			        <tr>
			            <td class="text-center" colspan="5">문의사항이 없습니다.</td>
			        </tr>
			    </c:if>
        </table>
        
        <nav class="my-5">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.questionPageBar}</ul>
	   	   </div>
		</nav>
    </div>
</div>
</div>


<jsp:include page="../adminFooter.jsp"/>