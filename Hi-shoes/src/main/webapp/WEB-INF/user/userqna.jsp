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
	
	/* 문의내역 테이블 */
.question-container {
    width: 100%;
    margin: 0 auto;
    font-family: "Noto Sans KR", sans-serif;
}

/* 테이블 기본 */
.question-container table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0;
    border: 1px solid #e1e8df;
    border-radius: 10px;
    overflow: hidden;
    background-color: #ffffff;
    font-size: 14px;
    color: #444;
}

/* 테이블 헤더 */
.question-container thead.table-primary th {
    background-color: #315f32;
    color: #ffffff;
    font-weight: 600;
    text-align: center;
    vertical-align: middle;
    padding: 15px 10px;
    border: none;
    border-bottom: 2px solid #274d28;
}

/* 테이블 내용 */
.question-container tbody td {
    padding: 13px 10px;
    vertical-align: middle;
    border-bottom: 1px solid #edf1eb;
    background-color: #ffffff;
    transition: background-color 0.2s ease;
}

/* 마지막 행의 아래쪽 테두리 */
.question-container tbody tr:last-child td {
    border-bottom: none;
}

/* 문의 행 마우스 호버 */
.question-container tbody tr:not(.answer-row):hover td {
    background-color: #f5f8f4;
}

/* 답변이 펼쳐진 문의 */
.question-container .question-row.active td {
    background-color: #edf5eb;
}

/* 답변 행 */
.question-container .answer-row > td {
    padding: 12px 15px !important;
    background-color: #f8faf7 !important;
}

/* 상품명, 제목, 내용 말줄임 */
.question-container tbody td {
    max-width: 200px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

/* 답글 표시 */
.question-container .answer-toggle {
    color: #315f32;
    font-weight: 600;
}

/* 수정 버튼 */
.question-container .qnaEditBtn {
    background-color: #315f32;
    border-color: #315f32;
    color: #ffffff;
    padding: 5px 12px;
    font-size: 13px;
    border-radius: 5px;
}

.question-container .qnaEditBtn:hover {
    background-color: #274d28;
    border-color: #274d28;
}

/* 삭제 버튼 */
.question-container .btn-outline-danger {
    padding: 5px 12px;
    font-size: 13px;
    border-radius: 5px;
}

/* 페이지바 */
#pageBar .pagination {
    gap: 5px;
}

#pageBar .page-link {
    color: #315f32;
    border: 1px solid #dce6d9;
    border-radius: 5px;
    background-color: #ffffff;
    min-width: 36px;
    text-align: center;
    transition: all 0.2s ease;
}

#pageBar .page-link:hover {
    background-color: #edf5eb;
    border-color: #315f32;
}

#pageBar .page-item.active .page-link {
    background-color: #315f32;
    border-color: #315f32;
    color: #ffffff;
}

/* 작은 화면 대응 */
@media (max-width: 768px) {
    .question-container {
        overflow-x: auto;
    }

    .question-container table {
        min-width: 750px;
    }
}
</style>

<script type="text/javascript">


$(function(){

	 
	 
	 $(document).on("click", "#pageBar a", function(e) {
		 
		    e.preventDefault(); // 페이지 이동 방지

		    let href = $(this).attr("href");
		    let match = href ? href.match(/[?&]currentShowPageNo=(\d+)/) : null;

		    // href에 페이지 번호가 있으면 사용
		    if (match) {
		        currentShowPageNo = match[1];
		    }
		    else {
		        // 현재 페이지 버튼처럼 href="#"인 경우
		        currentShowPageNo = $(this).text().trim();
		    }

		    // 숫자가 아닌 경우 요청하지 않음
		    if (!/^\d+$/.test(currentShowPageNo)) {
		        return;
		    }

		 $.ajax({
			 
			 url:"<%= ctxPath%>/user/questionListJSON.go", 
	    	 method:"post",
	    	 data: {
	    		 "currentShowPageNo" : currentShowPageNo
	            },
	         dataType:"json",
	         success:function(json){
	        	 
	        	 // console.log(JSON.stringify(json));
	        	 html = ``;
	        	 
	        	 if(json.questionList.length > 0) {
	        		 $.each(json.questionList, function(index, question) {

	        	            // 번호 계산
	        	            let num = json.totalCountOrder
	        	                    - (json.currentShowPageNo - 1) * json.sizePerPage
	        	                    - index;

	        	            if (!question.qanswer) {
	        	                // 답변이 없는 문의
	        	                html += `
	        	                    <tr>
	        	                        <td class="text-center">\${num}</td>
	        	                        <td>\${question.fk_pname}</td>
	        	                        <td>\${question.qtitle}</td>
	        	                        <td>\${question.qcontents}</td>
	        	                        <td>\${question.qwritedate}</td>
	        	                        <td class="text-center">답글X</td>
	        	                        <td class="text-center">
	        	                        <button type="button"
									        class="btn btn-success qnaEditBtn"
									        data-qnanum="\${question.qnanum}"
									        data-title="<c:out value='\${question.qtitle}'/>"
									        data-contents="<c:out value='\${question.qcontents}'/>"
									        data-islock="\${question.islock}">
										    수정
										</button>
	        	                            <button type="button"
	        	                                    class="btn btn-outline-danger"
	        	                                    onclick="goDelete(\${question.qnanum})">
	        	                                삭제
	        	                            </button>
	        	                        </td>
	        	                    </tr>`;
	        	            }
	        	            else {
	        	                // 답변이 있는 문의
	        	                html += `
	        	                    <tr class="question-row">
	        	                        <td class="text-center">\${num}</td>
	        	                        <td>\${question.fk_pname}</td>
	        	                        <td>\${question.qtitle}</td>
	        	                        <td>\${question.qcontents}</td>
	        	                        <td>\${question.qwritedate}</td>
	        	                        <td class="text-center">
	        	                            <span class="answer-toggle">
	        	                                답글 <i class="fas fa-chevron-down"></i>
	        	                            </span>
	        	                        </td>
	        	                        <td class="text-center"> 
	        	                            <button type="button"
	        	                                    class="btn btn-outline-danger"
	        	                                    onclick="event.stopPropagation(); goDelete(\${question.qnanum});">
	        	                                삭제
	        	                            </button>
	        	                        </td>
	        	                    </tr>
	        	                    <tr class="answer-row">
	        	                        <td colspan="7">
	        	                            <div class="answer-content">
	        	                                <div class="answer-header">
	        	                                    <i class="fa-solid fa-chevron-down"></i>
	        	                                    관리자 답변
	        	                                </div>
	        	                                <div class="answer-text">${question.qanswer}</div>
	        	                            </div>
	        	                        </td>
	        	                    </tr>
	        	                `;
	        	            }
	        	        });
	        	 }
	        	 else{
			        html += `<tr>
			            <td class="text-center" colspan="7">문의사항이 없습니다.</td>
			        </tr>`;
	         		}
	        	 
	        	// 새 문의 목록으로 교체
	        	 $("#questionTbody").html(html);

	        	 // 새 페이징바로 교체
	        	 $("#pageBar .pagination").html(json.questionPageBar);
	        	 
	        	 
	         },
	         error: function(request, status, error){
				    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		         }
	     });
			 
     });
	 
	 $(document).on("click", "#questionTbody tr.question-row", function(e) {

		    // 삭제 버튼 클릭 시 답글 열기 방지
		    if ($(e.target).closest("button").length > 0) {
		        return;
		    }

		    let $row = $(this);
		    let $answer = $row.next(".answer-row");

		    if ($answer.length === 0) {
		        console.log("답변 행을 찾을 수 없습니다.");
		        return;
		    }

		    if ($answer.is(":visible")) {
		        $row.removeClass("active");
		        $answer.hide();
		    }
		    else {
		        $row.addClass("active");
		        $answer.css("display", "table-row");
		    }

		});
	

	
	 // 버튼 클릭시 수정
	 
	 $(document).on("click", ".qnaEditBtn", function(e) {
		    e.preventDefault();
		    e.stopPropagation();

		    const qnanum = $(this).attr("data-qnanum");
		    const qtitle = $(this).attr("data-title");
		    const qcontents = $(this).attr("data-contents");
		    const islock = $(this).attr("data-islock");

		    $("#qnaTitle").val(qtitle);
		    $("#qnaContents").val(qcontents);
		    $(".qnd-update-modal-qnanum").val(qnanum);
		    $("#checkPublic").prop("checked", String(islock) === "비공개");

		    const modalElement = document.getElementById("qnaUpdateModal");

		    if (!modalElement) {
		        console.error("qnaUpdateModal 요소를 찾을 수 없습니다.");
		        return;
		    }

		    bootstrap.Modal.getOrCreateInstance(modalElement).show();
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
    
    function goUpdateQna() {
		
    	const modalElement = $('div#qnaUpdateModal');
		
    	const qtitle = modalElement.find("#qnaTitle").val().trim();
    	const qcontents = modalElement.find("#qnaContents").val().trim();
    	const qnanum = modalElement.find(".qnd-update-modal-qnanum").val();

    	console.log(qtitle,qcontents,qnanum);
    	// 체크박스 체크 여부
    	const checked = modalElement.find("#checkPublic").prop("checked");

    	// 체크되어 있으면 비공개, 아니면 공개
    	const islock = checked ? "비공개" : "공개";

		// 유효성 검사 살짝
		if (!qtitle || qtitle.trim() == "") {
			//console.log(title);
			alert("제목을 입력하세요.");
			return;
		}
		if (!qcontents || qcontents.trim() == "") {
			//console.log(content);
			alert("질문 내용을 입력하세요.");
			return;
		}
			
		$.ajax({
			url: "<%= ctxPath%>/shop/goUpdateQnaJSON.go",
			type: "POST",
			data: {
				"qnanum": qnanum,
				"qtitle": qtitle,
				"qcontents": qcontents,
				"islock": islock
			},
			dataType: "json",

			success: function(json) {
				if (json.n == 1) {
					alert("수정되었습니다.");
					const qnaModal = bootstrap.Modal.getOrCreateInstance(modalElement);
			        qnaModal.hide();
					
					location.reload();
				}
				else {
					alert("수정에 실패하였습니다.");
				}
			},

			error: function(request, status, error){
		    	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    }
		});
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
            <tbody id="questionTbody">
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
				                <td class="text-center">답글X</td>
				                <td class="text-center">
				                
				                <button type="button"
								        class="btn btn-success qnaEditBtn"
								        data-qnanum="${question.qnanum}"
								        data-title="<c:out value='${question.qtitle}'/>"
								        data-contents="<c:out value='${question.qcontents}'/>"
								        data-islock="${question.islock}">
								    수정
								</button>
				                <button type="button"
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
				                <td class="text-center">
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
		
	<div class="modal fade" id="qnaUpdateModal"
     tabindex="-1"
     aria-labelledby="qnaUpdateModalLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <%-- 모달 제목 --%>
            <div class="modal-header">
                <h5 class="modal-title" id="qnaWriteModalLabel">
                    상품 Q&A 수정
                </h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="닫기"></button>
            </div>

            <%-- 모달 본문 --%>
            
            
            
            <div class="modal-body">
            
	            <div class="mb-3">
		            <label for="checkPublic">
		            	비공개
		            	<input type="checkbox" id="checkPublic" checked/>
		            </label>
	            </div>
            
                <div class="mb-3">
                    <label for="qnaTitle" class="form-label">
                        제목
                    </label>
                    <input type="text"
                           id="qnaTitle"
                           class="form-control"
                           placeholder="제목">
                </div>

                <div class="mb-3">
                    <label for="qnaContents" class="form-label">
                        내용
                    </label>
                    <textarea id="qnaContents"
                              class="form-control"
                              rows="6"
                              placeholder="질문 내용"></textarea>
                </div>
            </div>

			<input class="qnd-update-modal-qnanum" type="hidden"/>

            <%-- 하단 버튼들 --%>
            <div class="modal-footer">
                <button type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">
                    취소
                </button>

                <button type="button"
                        class="btn btn-primary"
                        id="btnQnARegister"
                        onclick="goUpdateQna()">
                    수정
                </button>
            </div>

        </div>
    </div>
    </div>
    
