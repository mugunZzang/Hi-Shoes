<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
%>   

	<section class="footer-banner container-fluid">
		<div class="wrapper">
			<section class="cs-texts">
				<span>고객센터</span>
				<span>1588-9667</span>
				<span>월~금 09:00 ~ 12:00 / 13:00 ~ 18:00 (공휴일 휴무)</span>
			</section>
			<section>
				<a href="#" class="link-btn1">챗봇 상담 </a>
			</section>
		</div>
	</section>
	<footer class="container-fluid">
		<div class="wrapper">
			<section>
				<img alt="푸터 로고" src="<%= ctxPath%>/images/Footer-Logo.svg">
			</section>
			<section class="address-wrap">
				<address>
					<span>
						<a href="#">이용약관</a>
					</span>
					<span>
						<a href="#" class="fw-bold">개인정보처리방침</a>
					</span>
					<p>
						상호명  |  대표이사: 육무군  |  주소: 서울시 월드컵북로 21 풍성빌딩 2층  |  이메일: aaa@bbb.com 
					</p>					
					<p>
						COPYRIGHT © 상호명 ALL RIGHTS RESERVED
					</p>
				</address>
			</section>
			<section class="social-wrap">
				<a href="#">
					<img alt="인스타그램" src="<%= ctxPath %>/images/instagram-icon.svg">
				</a>
				<a href="#">
					<img alt="페이스북" src="<%= ctxPath %>/images/facebook-icon.svg">
				</a>
			</section>			
		</div>
	</footer>

</body>
</html>