<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style type="text/css">

    /* 문의사항 영역에만 폰트 적용 */
    .question-container {
        font-family: "Noto Sans KR", sans-serif;
        font-optical-sizing: auto;
        font-weight: 400;
        font-style: normal;
    }

    .question-container * {
        font-family: inherit;
    }

    /* 문의사항 테이블의 일반 셀 */
    .question-container table.table td {
        max-width: 200px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    /* 문의 행 */
    .question-container .question-row {
        cursor: pointer;
        transition: background-color 0.2s;
    }

    .question-container .question-row:hover {
        background-color: #f5f8f4;
    }

    /* 답글 표시 + 화살표 */
    .question-container .answer-toggle {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 7px;
        color: #315f32;
        font-weight: 600;
        white-space: nowrap;
    }

    .question-container .answer-toggle i {
        font-size: 12px;
        transition: transform 0.25s ease;
    }

    /* 펼쳐진 상태 */
    .question-container .question-row.active .answer-toggle i {
        transform: rotate(180deg);
    }

    .question-container .question-row.active {
        background-color: #f5f8f4;
    }

    /* 답변 행: 처음에는 숨김 */
    .question-container .answer-row {
        display: none;
    }

    /* 답변 셀: 전체 너비 사용 */
    .question-container table.table > tbody > tr.answer-row > td {
        max-width: none !important;
        width: auto;
        white-space: normal !important;
        overflow: visible !important;
        text-overflow: clip !important;
        padding: 10px 15px !important;
        background-color: #f8faf7;
    }

    /* 답변 카드 */
    .question-container .answer-content {
        display: block;
        width: 100%;
        box-sizing: border-box;
        padding: 16px 20px;
        background-color: #ffffff;
        border: 1px solid #e1e8df;
        border-left: 4px solid #315f32;
        border-radius: 6px;
        text-align: left;
    }

    /* 답변 제목 */
    .question-container .answer-header {
        display: flex;
        align-items: center;
        gap: 8px;
        margin-bottom: 10px;
        color: #315f32;
        font-size: 14px;
        font-weight: bold;
    }

    /* 답변 본문 */
    .question-container .answer-text {
        color: #444;
        font-size: 13px;
        line-height: 1.7;
        white-space: pre-wrap;
        word-break: break-word;
    }

</style>

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

function goSearch(frm) {

    const searchType = $(frm).find('select[name="searchType"]').val();

    if(searchType == "") {
        alert("검색대상을 선택하세요!!");
        return;
    }

    frm.submit();
}// end of function goSearch(frm, type)

</script>

<div class="question-container">
    	<form name="question_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="pname">판매상품명</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form)">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 10%;">판매품명</th>
				<th style="width: 20%;">제목</th>
                <th style="width: 20%;">내용</th>
                <th style="width: 20%;">작성일자</th>
                <th style="width: 10%;">답변여부</th>
            </tr>
          </thead>
            <tbody>
            	<c:if test="${not empty requestScope.questionList}">
			        <c:forEach var="question" items="${requestScope.questionList}" varStatus="status">
			        	<c:if test="${empty question.qanswer}">
				            <tr>
				                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
							    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
							    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
				                <td>${question.fk_pname}</td>
				                <td>${question.qtitle}</td>
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
				                <td>${question.qcontents}</td>
				                <td>${question.qwritedate}</td>
				                <td>
						            <span class="answer-toggle">
						                답글 <i class="fas fa-chevron-down"></i>
						            </span>
						        </td>
				            </tr>
				            <tr class="answer-row">
						        <td colspan="6">
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
			            <td class="text-center" colspan="6">문의사항이 없습니다.</td>
			        </tr>
			    </c:if>
        </table>
        
        <nav class="my-5" id="pageBar">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.questionPageBar}</ul>
	   	   </div>
		</nav>
    </div>
