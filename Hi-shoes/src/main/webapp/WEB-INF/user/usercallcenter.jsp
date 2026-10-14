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

<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">


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