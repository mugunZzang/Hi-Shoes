<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Bootstrap CSS --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>

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

<script type="text/javascript">


$(function(){

	 $(".question-row").on("click", function() {

	        let $row = $(this);
	        let $answer = $row.next(".answer-row");
	        let $content = $answer.find(".answer-content");

	        if ($answer.is(":visible")) {

	            $row.removeClass("active");

	            $content.stop(true, true).slideUp(200, function() {
	                $answer.hide();
	            });

	        } else {

	            $row.addClass("active");

	            // 답변 행을 테이블 행으로 표시
	            $answer.css("display", "table-row");

	            // 답변 카드만 애니메이션 적용
	            $content.stop(true, true).slideDown(200);
	        }

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

function goDelete(num) {
    if (confirm("삭제하시겠습니까?")) {
        // 실제 삭제 처리할 코드
        $.ajax({
        		 url:"<%= ctxPath%>/user/delete2.go", 
            	 method:"post",
            	 data: {
                     "num": num
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

<div class="question-container">
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">번호</th>
                <th style="width: 20%;">판매상품명</th>
				<th style="width: 10%;">제목</th>
                <th style="width: 20%;">내용</th>
                <th style="width: 10%;">작성일자</th>
                <th style="width: 15%;">답변여부</th>
                <th style="width: 10%; text-align:center;"></th>
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
				                <td class="text-center"><button type="button"
		            						class="btn btn-outline-danger"
		           						onclick="event.stopPropagation(); goDelete(${question.qnanum});">
		        					삭제
		    						</button></td>
				                
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
						        <td class="text-center"><button type="button"
		            						class="btn btn-outline-danger"
		           						onclick="event.stopPropagation(); goDelete(${question.qnanum});">
		        					삭제
		    						</button></td>
				            </tr>
				            <tr class="answer-row">
						        <td colspan="6">
						            <div class="answer-content">
						                <div class="answer-header">
						                    <i class="fa-solid fa-chevron-down"></i>
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
			            <td class="text-center" colspan="7">문의사항이 없습니다.</td>
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
