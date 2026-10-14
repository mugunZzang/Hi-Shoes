<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	// 컨텍스트 패스명(context path name)을 알아오고자 한다.
	String ctxPath = request.getContextPath();
	// ctxPath ==> /JSPServletBegin   
%>    
    
<jsp:include page="adminHeader.jsp" />

	<div class="container py-5">
		<p class="h2 text-danger">장애발생</p>
		<p class="h4 text-primary mt-3">빠른 복구를 위해서 최선을 다하겠습니다</p>
	</div>
	
<jsp:include page="adminFooter.jsp" />		


