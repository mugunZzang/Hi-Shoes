<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctx_Path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>

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



</script>

<style>
    td {
        max-width: 200px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    /* ===============================
   전체 고객센터 영역
================================ */
#container {
    font-family: "Noto Sans KR", sans-serif;
    color: #444;
}

#container .tab-content {
    background-color: #fff;
    border: 1px solid #e1e8df !important;
    border-top: none !important;
    border-radius: 0 0 10px 10px;
    padding: 30px !important;
    box-shadow: 0 4px 15px rgba(49, 95, 50, 0.04);
}

/* ===============================
   공지사항 / FAQ 탭
================================ */
#container .nav-tabs {
    border-bottom: 2px solid #315f32;
}

#container .nav-tabs .nav-link {
    color: #666;
    font-weight: 500;
    padding: 14px 20px;
    border: 1px solid transparent;
    border-radius: 8px 8px 0 0;
    transition: all 0.2s ease;
}

#container .nav-tabs .nav-link:hover {
    color: #315f32;
    background-color: #f5f8f4;
}

#container .nav-tabs .nav-link.active {
    color: #fff;
    background-color: #315f32;
    border-color: #315f32;
    font-weight: 600;
}

/* ===============================
   검색 폼
================================ */
#container .tab-pane form {
    display: flex;
    align-items: center;
    gap: 10px;
    flex-wrap: wrap;
    margin-bottom: 22px;
}

#container .tab-pane form select,
#container .tab-pane form input[type="text"] {
    height: 40px;
    padding: 8px 12px;
    border: 1px solid #dce5d9;
    border-radius: 6px;
    background-color: #fff;
    color: #444;
    outline: none;
    transition: border-color 0.2s, box-shadow 0.2s;
}

#container .tab-pane form select {
    min-width: 125px;
}

#container .tab-pane form input[type="text"] {
    flex: 1;
    min-width: 150px;
}

#container .tab-pane form select:focus,
#container .tab-pane form input[type="text"]:focus {
    border-color: #638b63;
    box-shadow: 0 0 0 3px rgba(49, 95, 50, 0.1);
}

/* 검색 버튼 */
#container .tab-pane form .btn {
    height: 40px;
    padding: 0 22px;
    border: 1px solid #315f32;
    border-radius: 6px;
    background-color: #315f32;
    color: #fff;
    font-size: 14px;
    font-weight: 500;
    transition: background-color 0.2s ease;
}

#container .tab-pane form .btn:hover {
    background-color: #274d28;
    border-color: #274d28;
}

/* ===============================
   공지사항 / FAQ 테이블
================================ */
#container .tab-pane table {
    width: 100%;
    margin-bottom: 0;
    border: 1px solid #e1e8df;
    border-radius: 8px;
    border-collapse: separate;
    border-spacing: 0;
    overflow: hidden;
    table-layout: fixed;
    font-size: 14px;
    background-color: #fff;
}

/* 테이블 헤더 */
#container .tab-pane table thead.table-primary th {
    background-color: #315f32;
    color: #fff;
    text-align: center;
    vertical-align: middle;
    font-weight: 600;
    padding: 15px 12px;
    border: none;
    border-bottom: 2px solid #274d28;
}

/* 테이블 본문 */
#container .tab-pane table tbody td {
    max-width: none;
    padding: 14px 12px;
    vertical-align: middle;
    text-align: center;
    color: #444;
    background-color: #fff;
    border-bottom: 1px solid #edf1eb;
    border-right: none;
    line-height: 1.6;
}

/* 제목 및 내용 말줄임 */
#container .tab-pane table tbody td:nth-child(2),
#container .tab-pane table tbody td:nth-child(3) {
    text-align: left;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

/* 글번호 */
#container .tab-pane table tbody td:first-child {
    color: #777;
    font-size: 13px;
}

/* 마지막 행 테두리 */
#container .tab-pane table tbody tr:last-child td {
    border-bottom: none;
}

/* 행 호버 효과 */
#container .tab-pane table tbody tr.noticeInfo,
#container .tab-pane table tbody tr.faqInfo {
    cursor: pointer;
    transition: background-color 0.2s ease;
}

#container .tab-pane table tbody tr.noticeInfo:hover td,
#container .tab-pane table tbody tr.faqInfo:hover td {
    background-color: #f2f7f0;
    color: #315f32;
}

/* 빈 목록 메시지 */
#container .tab-pane table tbody td[colspan] {
    text-align: center;
    padding: 35px 12px;
    color: #888;
    background-color: #fcfdfb;
}

/* ===============================
   페이지바
================================ */
#container .tab-pane #pageBar {
    margin-top: 28px !important;
    margin-bottom: 15px !important;
}

#container .tab-pane #pageBar .pagination {
    gap: 5px;
    flex-wrap: wrap;
}

#container .tab-pane #pageBar .page-link {
    min-width: 36px;
    padding: 8px 12px;
    color: #315f32;
    text-align: center;
    background-color: #fff;
    border: 1px solid #dce6d9;
    border-radius: 5px;
    transition: all 0.2s ease;
}

#container .tab-pane #pageBar .page-link:hover {
    color: #274d28;
    background-color: #edf5eb;
    border-color: #8cab87;
}

#container .tab-pane #pageBar .page-item.active .page-link {
    color: #fff;
    background-color: #315f32;
    border-color: #315f32;
    font-weight: 600;
}

/* ===============================
   반응형
================================ */
@media (max-width: 768px) {
    #container {
        padding: 100px 15px 40px !important;
    }

    #container .tab-content {
        padding: 15px !important;
    }

    #container .tab-pane table {
        min-width: 600px;
    }

    #container .tab-pane {
        overflow-x: auto;
    }

    #container .tab-pane form input[type="text"] {
        min-width: 120px;
    }
}
</style>

<div class="container-fluid"
     id="container"
     style="top: 90px;
            padding: 7% 7%;">


<ul class="nav nav-tabs nav-fill">
        <li class="nav-item">
          <a class="nav-link ${tab == 'notice' ? 'active' : ''}" href="usercallcenter.go?tab=notice">공지사항</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${tab == 'faq' ? 'active' : ''}" href="usercallcenter.go?tab=faq">FAQ</a>
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
            </tr>
          </thead>
          	<tbody>
            	<c:if test="${not empty requestScope.noticeList}">
			        <c:forEach var="notice" items="${noticeList}" varStatus="status">
			            <tr class="noticeInfo" onclick="location.href='<%= ctx_Path %>/user/noticeDetail.go?nnum=${notice.nnum}'"
    							style="cursor:pointer;">
			                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
			                <td>${notice.nsubject}</td>
			                <td>${notice.ncontents}</td>
			                <td>${notice.nwritedate}</td>
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
            </tr>
          </thead>
			 <tbody>
            	<c:if test="${not empty requestScope.faqList}">
            	
			        <c:forEach var="faq" items="${requestScope.faqList}" varStatus="status">
			            <tr class="faqInfo" onclick="location.href='<%= ctx_Path%>/user/faqDetail.go?fnum=${faq.fnum}'"
    							style="cursor:pointer;">
			                <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
			                <td>${faq.fsubject}</td>
			                <td>${faq.fcontents}</td>
			                <td>${faq.fcategory}</td>
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
        
    </div> 	

    </div>
</div>


<jsp:include page="../footer.jsp"/>    