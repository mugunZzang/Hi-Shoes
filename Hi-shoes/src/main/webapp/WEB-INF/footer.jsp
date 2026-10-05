<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>

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

<%
    // 본인의 DB 정보에 맞게 수정하세요.
    String driver = "oracle.jdbc.driver.OracleDriver"; // MySQL 기준 (오라클은 oracle.jdbc.driver.OracleDriver)
    String url = "jdbc:oracle:thin:@211.238.142.54:1521/XEPDB1";
    String user = "SEMI_ORAUSER";
    String password = "bclass";

    Connection conn = null;

    try {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
        out.println("<h2>🎉 DB 연결 성공! 🎉</h2>");
    } catch (Exception e) {
        out.println("<h2>❌ DB 연결 실패 ❌</h2>");
        out.println("<pre>");
        e.printStackTrace(new java.io.PrintWriter(out));
        out.println("</pre>");
    } finally {
        if(conn != null) try { conn.close(); } catch(Exception e) {}
    }
%>

</body>
</html>