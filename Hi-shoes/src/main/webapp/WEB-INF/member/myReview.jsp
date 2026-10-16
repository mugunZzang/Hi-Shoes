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
			<button type="button" class="btn btn-sm btn-danger me-1" onClick="editReview()">수정</button>
			<button type="button" class="btn btn-sm btn-info" onClick='delMyReview()'>삭제</button>
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
									<input name="reviewEditChk" class="form-check-input" type="checkbox" onclick="onlyOneCheck(this)" value="${reviewmap.review_no}">
										&nbsp;&nbsp;${ (requestScope.totalCountReview) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }
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

<%-- 리뷰 수정 모달 --%>
<div class="modal fade" id="editReview" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">리뷰 수정</h4>
          <button type="button" class="btn-close idFindClose" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body p-3">
        	<div class="mb-3">
        		<label for="image" class="label label-default">썸네일: </label>
        		<div class="thumbbox text-center">
        			<img src="" id="rvwThumbnail" style="width:120px; height:120px; object-fit:contain;">
        		</div>    
        		<div class="row p-2 g-2 d-flex justify-content-between align-items-center">
        			<div class="col-8 m-0">
        				<input type="file" id="rvwThumbnailChg" onchange="thumbChk()" style="cursor:pointer;">
        			</div>
        			<div class="col-2 m-0">
        				<button type="button" class="btn btn-danger" onclick="thumbDel()">삭제</button>
        			</div>       			
        			
        		</div>  		
        		
        	</div>        	
        	<div class="mb-3">
        		<label for="content" class="label label-default">내용: </label>
        		<textarea id="content" class="form-control" rows="3"></textarea>
        	</div>
        	<div class="">
        		<label for="rating" class="label label-default">별점: </label>
        		<select id="rating">
        			<option value="5">★★★★★</option>
        			<option value="4">★★★★</option>
        			<option value="3">★★★</option>
        			<option value="2">★★</option>
        			<option value="1">★</option>
        		</select>
        	</div>        	        
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
        	<button type="button" class="btn btn-info" onclick="editFinish()">수정</button>
          	<button type="button" class="btn btn-danger idFindClose" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div>