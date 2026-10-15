<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
  
<%
    String ctx_Path = request.getContextPath();
%>

<script type="text/javascript">

	function onlyOneCheck(obj) {

    const checkboxList = document.querySelectorAll('input[name="reviewEditChk"]');

    checkboxList.forEach(elmt => {
        if(elmt != obj){    // elmt != obj 은 체크박스에 체크를 하지 않은 나머지 모든 체크박스를 말한다.
            elmt.checked = false;   // 체크박스에 체크를 못하도록 한다.
        }
    });

} // end of function onlyOneCheck(obj)------------

</script>

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
				<tr>
					<th scope="row">
						<label>
							<input name="reviewEditChk" class="form-check-input" type="checkbox" onclick="onlyOneCheck(this)">&nbsp;&nbsp;1
						</label>
					</th>
					<td class="thumbnail">						
						<img src="https://placehold.co/1024x1024" alt="샘플"/>
					</td>
					<td>
						<p class="contents">
							<a href="#">너무 좋은 상품~</a>
						</p>					
					</td>
					<td class="star">★★★★★</td>
					<td>2026.10.15 13:55</td>
					<td>${(sessionScope.loginuser).userid}</td>
				</tr>	
				<tr>
					<th scope="row">
						<label>
							<input name="reviewEditChk" class="form-check-input" type="checkbox" onclick="onlyOneCheck(this)">&nbsp;&nbsp;2
						</label>
					</th>
					<td class="thumbnail">						
					</td>
					<td>
						<p class="contents">
							<a href="#">너무 구린 상품~</a>
						</p>
					</td>
					<td class="star">★</td>
					<td>2026.10.15 13:55</td>
					<td>${(sessionScope.loginuser).userid}</td>
				</tr>	
				
				<tr>
					<th scope="row">
						<label>
							<input name="reviewEditChk" class="form-check-input" type="checkbox" onclick="onlyOneCheck(this)">&nbsp;&nbsp;2
						</label>
					</th>
					<td class="thumbnail">		
						<img src="https://placehold.co/50x50" alt="샘플"/>				
					</td>
					<td>
						<p class="contents">
							<a href="#">이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.이 상품은 잘모르겠어요.</a>							
						</p>
					</td>
					<td class="star">★★★</td>
					<td>2026.10.15 13:55</td>
					<td>${(sessionScope.loginuser).userid}</td>
				</tr>
					
			</tbody>
		</table>
	</div>
</section>