<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
<%
    String ctx_Path = request.getContextPath();
%>

<section class="myReview">
	<div class="tit-wrap container-fluid">
		<h3 class="section-tit">리뷰 보기</h3>
	</div>
	<div class="reviewTbl p-3">
		<div class="btn-wrap text-end my-2">
			<button type="button" class="btn btn-sm btn-danger me-1">수정</button>
			<button type="button" class="btn btn-sm btn-info">삭제</button>
		</div>
		
		<table class="table">		
			<thead>
				<tr>
					<th scope="col" bgcolor="#1D1D1D">번호</th>
					<th scope="col" bgcolor="#1D1D1D">이미지</th>
					<th scope="col" bgcolor="#1D1D1D">내용</th>
					<th scope="col" bgcolor="#1D1D1D">별점</th>
					<th scope="col" bgcolor="#1D1D1D">작성일</th>
					<th scope="col" bgcolor="#1D1D1D">작성자</th>
				</tr>				
			</thead>
			<tbody>
			
				<c:if test="${empty requestScope.review_map_List}" > 
				  <tr>
					  <td colspan="8" align="center">
					  <span style="color: red; font-weight: bold;">작성한 리뷰가 없습니다.</span>
				  </tr>
				</c:if>
				
				<c:if test="${not empty requestScope.review_map_List}">				
					<c:forEach var="reviewmap" items="${requestScope.review_map_List}" varStatus="status">
					
						<tr>
							<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
							<th scope="row">
								<label>
									<input name="reviewEditChk" class="form-check-input" type="checkbox" onclick="onlyOneCheck(this)">&nbsp;&nbsp;${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }
								</label>
							</th>
							<td class="thumbnail">						
								<img src="<%= ctx_Path%>/images/${reviewmap.review_image}" alt="${reviewmap.review_image}"/>
							</td>
							<td>
								<p class="contents">
									<a href="#">${reviewmap.review_content}</a>
								</p>					
							</td>
							<td class="star">${reviewmap.review_rating}</td>
							<td>${reviewmap.review_date}</td>
							<td>${reviewmap.writer}</td>
						</tr>
						
					</c:forEach>
				</c:if>				
					
			</tbody>
		</table>
		
		
		
		<%-- === 페이지바 === --%>
		<nav class="my-5">
	       <div style='display:flex; width:80%; margin: 0 auto;'>
	   	     <ul class="pagination" style='margin:auto;'>${requestScope.pageBar}</ul>
	   	   </div>
		</nav> 
	</div>
</section>