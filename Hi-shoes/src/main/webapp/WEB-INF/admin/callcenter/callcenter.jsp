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

        $(".question-row").on('click',function() {
        	
            let $row = $(this);
            let $answer = $row.next(".answer-row");

            // 현재 답변 열기 / 닫기
            $row.toggleClass("active");
            $answer.stop(true, true).slideToggle(200);

        });


	
	
	
	
	
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

<style>
    td {
        max-width: 200px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    
	    /* 문의 행 */
	.question-row {
	    cursor: pointer;
	    transition: background-color 0.2s;
	}
	
	.question-row:hover {
	    background-color: #f5f8f4;
	}
	
	/* 답글 표시 + 화살표 */
	.answer-toggle {
	    display: inline-flex;
	    align-items: center;
	    justify-content: center;
	    gap: 7px;
	    color: #315f32;
	    font-weight: 600;
	    white-space: nowrap;
	}
	
	.answer-toggle i {
	    font-size: 12px;
	    transition: transform 0.25s ease;
	}
	
	/* 펼쳐진 상태 */
	.question-row.active .answer-toggle i {
	    transform: rotate(180deg);
	}
	
	.question-row.active {
	    background-color: #f5f8f4;
	}
	
	/* 답변 행 */
	.answer-row {
	    display: none;
	}
	
	.answer-row > td {
	    padding: 10px 15px !important;
	    background-color: #f8faf7;
	}
	
	/* 답변 카드 */
	.answer-content {
	    padding: 16px 20px;
	    background-color: #ffffff;
	    border: 1px solid #e1e8df;
	    border-left: 4px solid #315f32;
	    border-radius: 6px;
	    text-align: left;
	}
	
	/* 답변 제목 */
	.answer-header {
	    display: flex;
	    align-items: center;
	    gap: 8px;
	    margin-bottom: 10px;
	    color: #315f32;
	    font-size: 14px;
	    font-weight: bold;
	}
	
	/* 답변 본문 */
	.answer-text {
	    color: #444;
	    font-size: 13px;
	    line-height: 1.7;
	    white-space: pre-wrap;
	    word-break: break-word;
	}
</style>

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
		            								class="btn btn-outline-danger"
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
        <nav class="my-5" id="pageBar">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.noticePageBar}</ul>
	   	   </div>
		</nav> 
        <div class="text-end">
        	<button type="button" class="btn btn-secondary" onclick="location.href='noticeWrite.go'">글작성</button>
        </div>
    </div>

    <!-- FAQ -->
    <div class="tab-pane fade ${tab == 'faq' ? 'show active' : ''}" id="faq">
    	<form name="faq_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="가입/탈퇴">가입/탈퇴</option>
				<option value="정보변경">정보변경</option>
				<option value="결제">결제</option>
				<option value="주문">주문</option>
				<option value="취소">취소</option>
				<option value="상품정보">상품정보</option>
				<option value="배송">배송</option>
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
		            						class="btn btn-outline-danger"
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
        <nav class="my-5" id="pageBar">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.faqPageBar}</ul>
	   	   </div>
		</nav>
        
        <div class="text-end">
        	<button type="button" class="btn btn-secondary" onclick="location.href='faqWrite.go'">글작성</button>
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
				<th style="width: 20%;">제목</th>
                <th style="width: 10%;">아이디</th>
                <th style="width: 20%;">내용</th>
                <th style="width: 20%;">작성일자</th>
                <th style="width: 10%;">답변여부</th>
            </tr>
          </thead>
            <tbody>
            	<c:if test="${not empty requestScope.questionList}">
			        <c:forEach var="question" items="${requestScope.questionList}" varStatus="status">
			        	<c:if test="${empty question.qanswer}">
				            <tr onclick="location.href='${pageContext.request.contextPath}/admin/callcenter/questionEdit.go?qnanum=${question.qnanum}'"
	    							style="cursor:pointer; ">
				                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
							    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
							    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
				                <td>${question.fk_pname}</td>
				                <td>${question.qtitle}</td>
				                <td>${question.fk_userid}</td>
				                <td>${question.qcontents}</td>
				                <td>${question.qwritedate}</td>
				                <td>답글X</td>
				            </tr>
			            </c:if>
			            <c:if test="${not empty question.qanswer}">
				            <tr class="question-row">
				                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
							    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
							    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
				                <td>${question.fk_pname}</td>
				                <td>${question.qtitle}</td>
				                <td>${question.fk_userid}</td>
				                <td>${question.qcontents}</td>
				                <td>${question.qwritedate}</td>
				                <td>
						            <span class="answer-toggle">
						                답글 <i class="fas fa-chevron-down"></i>
						            </span>
						        </td>
				            </tr>
				            <tr class="answer-row">
						        <td colspan="7">
						            <div class="answer-content">
						                <div class="answer-header">
						                    <i class="fas fa-comment-dots"></i>
						                    관리자 답변
						                </div>
						
						                <div class="answer-text">${question.qanswer}</div>
						            </div>
						        </td>
						    </tr>
			            </c:if>
			        </c:forEach>
			    </c:if>
            
	            <c:if test="${empty requestScope.questionList}">
			        <tr>
			            <td class="text-center" colspan="5">문의사항이 없습니다.</td>
			        </tr>
			    </c:if>
			    </tbody>
        </table>
        
        <nav class="my-5" id="pageBar">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.questionPageBar}</ul>
	   	   </div>
		</nav>
    </div>
</div>
</div>


<jsp:include page="../adminFooter.jsp"/>