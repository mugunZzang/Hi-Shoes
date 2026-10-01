<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>



<jsp:include page="../../header.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/kimkc/showMall.css" >

<div id="container">

	
		
		
	<div id="contents" >
	
		
			<div id="category_nav" >
				<ul id="category_nav_list" >
					<li>cate1</li>
					<li>cate2</li>
					<li>cate3</li>
					<li>cate3</li>
					<li>cate3</li>
				</ul>
			</div>
			
		
			<div id="search_keyword">
				<form>
					<li>
					   <label class="title" for="userid">색상</label>
					   <input class="input_keyword" name="userid" id="userid" type="text" size="20" maxlength="20" autocomplete="off" autofocus />
				   </li>
				   <li>
					   <label class="title" for="userid">사이즈</label>
					   <input class="input_keyword" name="userid" id="userid" type="text" size="20" maxlength="20" autocomplete="off" autofocus />
				   </li>
				   <li>
					   <label class="title" for="userid">가격</label>
					   <input class="input_keyword" name="userid" id="userid" type="text" size="20" maxlength="20" autocomplete="off" autofocus />
				   </li>
				   <li>
					   <label class="title" for="userid">브랜드</label>
					   <input class="input_keyword" name="userid" id="userid" type="text" size="20" maxlength="20" autocomplete="off" autofocus />
				   </li>
				   <li>
					   <label class="title" for="userid">제품명</label>
					   <input class="input_keyword" name="userid" id="userid" type="text" size="20" maxlength="20" autocomplete="off" autofocus />
				   </li>
				</form>
			</div>
		 
			<div id="prodList" class="text-center row">
				
				
				<div class="col-lg-3 col-md-6">
					<div class="card">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
						  	<p class="card-title">Card title</p>
						  	<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						
						
						  	<a href="#" class="card-link stretched-link" style="display: none;">Card link</a>
						  	
						</div>
					</div>
				</div>
				
				<div class="col-lg-3 col-md-6">
					<div class="card">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
						  	<p class="card-title">Card title</p>
						  	<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						
						
						  	<a href="#" class="card-link stretched-link" style="display: none;">Card link</a>
						  	
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="card">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
						  	<p class="card-title">Card title</p>
						  	<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						
						
						  	<a href="#" class="card-link stretched-link" style="display: none;">Card link</a>
						  	
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="card">
						<img src="..." class="card-img-top" alt="...">
						<div class="card-body">
						  	<p class="card-title">Card title</p>
						  	<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						
						
						  	<a href="#" class="card-link stretched-link" style="display: none;">Card link</a>
						  	
						</div>
					</div>
				</div>
				
			</div>
		
			<nav class="my-5">
	       		<div style='display:flex; width:80%; margin: 0 auto;'>
	   	     		<ul class="pagination" style='margin:auto;'></ul>
	   	   		</div>
			</nav>
		</div>
			
	
	<%-- 한 행에 4개씩만 보여주기 --%>
	
</div>

<jsp:include page="../../footer.jsp" />