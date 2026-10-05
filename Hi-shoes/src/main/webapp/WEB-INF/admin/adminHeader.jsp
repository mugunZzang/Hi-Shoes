<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--
==========================================================================
 adminHeader.jsp : 관리자 공통 헤더 + 사이드바 (드롭다운 방식)
==========================================================================
 [새 관리자 페이지를 만들 때]
   1) 페이지 맨 위에서 include 한다.
        <jsp:include page="adminHeader.jsp" />
   2) 본문 최상위 div 에 반드시 id="container" 를 쓴다.
      -> 사이드바가 열리고 닫힐 때 이 요소가 옆으로 밀린다. 없으면 본문이 사이드바에 가려진다.
   3) 새 페이지 링크는 아래 사이드바의 해당 대메뉴(menu-sub) 안에 <a> 로 추가한다.
   4) 사이드바를 숨길 페이지(관리자 메인 등)는 isAdminMainPage 처럼 조건을 추가한다.

 [CSS 변수] <style> 상단 :root
   --sidebar-w : 사이드바 너비 / --push-w : 열릴 때 본문이 밀리는 거리
   사이드바 너비를 바꾸면 --push-w 도 함께 조정한다. (--nav-h 는 JS 자동 계산)

 [상태 저장] 열림/닫힘은 localStorage("adminSidebarOpen")에 저장되어 페이지 이동 후에도 유지된다.
==========================================================================
--%>

<%
    String ctxPath = request.getContextPath();

	String currentUri = (String) request.getAttribute("jakarta.servlet.forward.request_uri");
	if(currentUri == null) {
	    currentUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	}
	if(currentUri == null) {
	    currentUri = request.getRequestURI();
	}

	/*
		[현재 메뉴 판별] : 사이드바에서 어떤 드롭다운을 자동으로 펼칠지 결정한다.
		페이지의 요청 주소(URL)에 메뉴 키워드가 "/키워드" 형태로 포함되면 해당 메뉴가 펼쳐진다.
		  /admin/member/memberList.go -> "member" -> 회원관리 펼침
		  /admin/orderList.go         -> "order"  -> 주문관리 펼침
		  /admin/catalogueXxx.go      -> "catalogue" -> 발주관리 펼침 (메뉴키는 purchase)
		위에서부터 검사해 처음 맞는 것에서 멈춘다.
		  예) /admin/ordermemberList.go 는 order 로 판별되므로,
		      member 로 하고 싶다면 /admin/member/ordermemberList.go 처럼 폴더명을 앞에 둔다.
		대소문자를 구분하므로 키워드는 소문자로 쓴다.
		판별이 안 되어도 사이드바는 정상 표시되고, 드롭다운이 모두 닫힌 채로 시작할 뿐이다.
	*/

	String adminMenu = "";

	if(currentUri.contains("/member")) {
	    adminMenu = "member";
	}
	else if(currentUri.contains("/order")) {
	    adminMenu = "order";
	}
	else if(currentUri.contains("/product")) {
	    adminMenu = "product";
	}
	else if(currentUri.contains("/supplier")) {
	    adminMenu = "supplier";
	}
	else if(currentUri.contains("/catalogue")) {
	    adminMenu = "purchase";
	}
	else if(currentUri.contains("/customer")) {
	    adminMenu = "customer";
	}
	else if(currentUri.contains("/chart")) {
	    adminMenu = "chart";
	}

	boolean isAdminMainPage  = currentUri.contains("/admin/adminMain");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%-- Bootstrap CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-5.3.8-dist/css/bootstrap.min.css" >
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">

<%-- Font Awesome 6 Icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<%-- Optional JavaScript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-4.0.0.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-5.3.8-dist/js/bootstrap.bundle.min.js" ></script>

<style type="text/css">
	* {
		font-family: "Noto Sans KR", sans-serif;
		  font-optical-sizing: auto;
		  font-weight: 400;
		  font-style: normal;
	}

/* =========================================
   관리자 사이드바 (변수)
   ========================================= */
:root {
    --nav-h: 90px;
    --sidebar-w: 250px;
    --push-w: 124px;          /* 본문이 밀리는 거리 (조절 가능) */
    --sidebar-speed: 0.4s;
    --sidebar-ease: cubic-bezier(0.4, 0, 0.2, 1);
    --sidebar-head-h: 50px;      /* 버튼이 들어가는 행 높이 */
}

/* =========================================
   사이드바 본체
   ========================================= */
.admin-sidebar {
    position: fixed;
    top: var(--nav-h);
    left: 0;
    height: calc(100% - var(--nav-h));
    width: var(--sidebar-w);
    background-color: #ffffff;
    border-right: 1px solid #dddddd;
    z-index: 1020;
    overflow-y: auto;
    transform: translateX(-100%);                    /* 기본: 숨김 */
    will-change: transform;
}

body.sidebar-open .admin-sidebar {
    transform: translateX(0);                        /* 열림 */
}

/* 사이드바 맨 위 버튼 행 */
.admin-sidebar .sidebar-head {
    height: var(--sidebar-head-h);
    border-bottom: 1px solid #eeeeee;
}

.admin-sidebar a {
    display: block;
    padding: 12px 25px;
    color: #333333;
    text-decoration: none;
    transition: background-color 0.2s, color 0.2s;
}

.admin-sidebar a:hover {
    background-color: #f2f2f2;
    color: #315B48;
}

.admin-sidebar a.active {
    background-color: #e8f0ec;
    color: #315B48;
    font-weight: 600;
    border-left: 4px solid #315B48;
}

/* =========================================
   드롭다운 메뉴 (대메뉴 버튼 + 하위 메뉴)
   ========================================= */
/* 대메뉴 버튼 */
.admin-sidebar .menu-group-btn {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 14px 25px;
    border: none;
    background: transparent;
    color: #315B48;
    font-weight: 600;
    text-align: left;
    cursor: pointer;
    transition: background-color 0.2s;
}

.admin-sidebar .menu-group-btn:hover {
    background-color: #f2f2f2;
}

/* 펼침 상태 표시 */
.admin-sidebar .menu-group-btn[aria-expanded="true"] {
    background-color: #f7faf8;
}

/* 화살표 회전 */
.admin-sidebar .menu-group-btn .menu-arrow {
    font-size: 12px;
    transition: transform 0.25s ease;
}

.admin-sidebar .menu-group-btn[aria-expanded="true"] .menu-arrow {
    transform: rotate(180deg);
}

/* 하위 메뉴: 들여쓰기 */
.admin-sidebar .menu-sub a {
    padding: 10px 25px 10px 40px;
    font-size: 14px;
    color: #555555;
}

.admin-sidebar .menu-sub a.active {
    color: #315B48;
    padding-left: 36px;              /* active 의 border-left 4px 만큼 보정 */
}

/* 하위 메뉴 없는 단일 메뉴 (대메뉴 버튼과 같은 모양) */
.admin-sidebar a.menu-single {
    padding: 14px 25px;
    color: #315B48;
    font-weight: 600;
}

.admin-sidebar a.menu-single.active {
    padding-left: 21px;              /* active 의 border-left 4px 만큼 보정 */
}

/* =========================================
   사이드바 토글 버튼
   ========================================= */
.sidebar-toggle-btn {
    position: fixed;
    top: calc(var(--nav-h) + 5px);          /* 행 높이 50px 중 세로 가운데 */
    left: 10px;
    z-index: 1040;
    margin: 0;
    padding: 0;

    width: 40px;
    height: 40px;

    display: flex;
    align-items: center;
    justify-content: center;

    background-color: #ffffff;
    border: 1px solid #dddddd;
    border-radius: 50%;

    color: #315B48;
    font-size: 22px;
    cursor: pointer;

    will-change: transform;
}

.sidebar-toggle-btn:hover {
    color: #244638;
    background-color: #f2f2f2;
}

/* 열리면 버튼이 사이드바 안쪽 오른쪽 위로 이동 */
body.sidebar-open .sidebar-toggle-btn {
    transform: translateX(calc(var(--sidebar-w) - 50px));
    border-color: transparent;
    background-color: transparent;
}

/* =========================================
   본문: 폭은 그대로, 위치만 오른쪽으로 push
   ========================================= */
body {
    overflow-x: hidden;                              /* 밀렸을 때 가로 스크롤 방지 */
}

body.sidebar-open #container {
    transform: translateX(var(--push-w));
}

/* =========================================
   애니메이션 (첫 로딩 이후부터, 모두 같은 시간/곡선)
   ========================================= */
body.sidebar-ready .admin-sidebar,
body.sidebar-ready .sidebar-toggle-btn,
body.sidebar-ready #container {
    transition: transform var(--sidebar-speed) var(--sidebar-ease);
}

/* =========================================
   작은 화면: 본문은 밀지 않고 사이드바가 위에 덮임
   ========================================= */
@media (max-width: 991.98px) {
    body.sidebar-open #container {
        transform: none;
    }
}
</style>

<script type="text/javascript">
	function setNavHeight() {
	    const nav = document.querySelector("nav.navbar.fixed-top");
	    if (!nav) return;
	    const rect = nav.getBoundingClientRect();
	    // navbar 아래 끝 위치(= 위쪽 margin 포함)를 기준으로 삼음
	    document.documentElement.style.setProperty("--nav-h", Math.ceil(rect.bottom) + "px");
	}
	setNavHeight();
	window.addEventListener("load", setNavHeight);
	window.addEventListener("resize", setNavHeight);
</script>

</head>
 <body style="background-color: #f2f2f2;">
<% if(!isAdminMainPage) { %>

    <script>
        // 깜빡임 방지: 화면이 그려지기 전에 저장된 상태를 먼저 적용
        (function () {
            if (localStorage.getItem("adminSidebarOpen") !== "N") {
                document.body.classList.add("sidebar-open");
            }
        })();
    </script>

    <!-- 사이드바 토글 버튼 -->
    <button type="button"
            id="sidebarToggleBtn"
            class="sidebar-toggle-btn"
            onclick="toggleAdminSidebar()"
            title="메뉴 열기/닫기">
        <i id="sidebarToggleIcon" class="fa-solid fa-chevron-right"></i>
    </button>

    <!-- 사이드바 -->
    <div id="adminSidebar" class="admin-sidebar">

	    <!-- 버튼이 들어가는 행 (버튼 자체는 fixed로 이 위에 겹쳐 표시됨) -->
	    <div class="sidebar-head d-flex align-items-center" style="padding-left: 25px;">
		    <span class="fw-semibold" style="color:#315B48;">전체메뉴</span>
		</div>

       <%--
          [사이드바 메뉴 추가 방법]
          메뉴는 두 종류다. 하위 메뉴가 있으면 "드롭다운 대메뉴", 없으면 "단일 메뉴"를 쓴다.

          ▶ 링크 추가 (드롭다운 안) : 해당 대메뉴의 <div class="menu-sub"> 안에 <a> 를 한 줄 추가한다.
               <a href="<%= ctxPath %>/admin/xxx.go"
                  class="<%= currentUri.contains("xxx") ? "active" : "" %>">메뉴이름</a>
               contains() 안에는 해당 페이지 URL 에만 있는 고유한 단어를 쓴다. (현재 페이지 강조용)

          ▶ 드롭다운 대메뉴 추가 : 아래 "드롭다운 틀"을 복사해 id, 메뉴키, 이름, 링크를 바꾼다.
               - 버튼의 data-bs-target 과 collapse div 의 id 는 같아야 한다.
               - 메뉴키는 상단 adminMenu 판별의 값과 같아야 한다.
                 새 메뉴키가 필요하면 상단 판별 블록에 else if 를 추가한다.

          ▶ 단일 메뉴 추가 : 아래 "단일 메뉴 틀"을 복사해 메뉴키, 링크, 이름을 바꾼다.
        --%>

        <%-- ▼ 드롭다운 틀 (복사용)
        <div class="menu-group">
            <button type="button" class="menu-group-btn"
                    data-bs-toggle="collapse" data-bs-target="#menuXxx"
                    aria-expanded="<%= "메뉴키".equals(adminMenu) %>">
                대메뉴 이름 <i class="fa-solid fa-chevron-down menu-arrow"></i>
            </button>
            <div id="menuXxx" class="collapse menu-sub <%= "메뉴키".equals(adminMenu) ? "show" : "" %>">
                <a href="<%= ctxPath %>/admin/xxx.go">하위 메뉴</a>
            </div>
        </div>
        --%>

        <%-- ▼ 단일 메뉴 틀 (복사용)
        <a href="<%= ctxPath %>/admin/xxx.go"
           class="menu-single <%= "메뉴키".equals(adminMenu) ? "active" : "" %>">메뉴 이름</a>
        --%>

        <%-- 회원조회 : 단일 메뉴 (URL 키워드: /member) --%>
        <a href="<%= ctxPath %>/admin/memberList.go"
           class="menu-single <%= "member".equals(adminMenu) ? "active" : "" %>">회원조회</a>

        <%-- 주문관리 : 드롭다운 (URL 키워드: /order) --%>
        <div class="menu-group">
            <button type="button" class="menu-group-btn"
                    data-bs-toggle="collapse" data-bs-target="#menuOrder"
                    aria-expanded="<%= "order".equals(adminMenu) %>">
                주문관리 <i class="fa-solid fa-chevron-down menu-arrow"></i>
            </button>
            <div id="menuOrder" class="collapse menu-sub <%= "order".equals(adminMenu) ? "show" : "" %>">
                <a href="<%= ctxPath %>/admin/orderList.go"
                   class="<%= currentUri.contains("orderList") ? "active" : "" %>">주문 목록</a>
                <a href="<%= ctxPath %>/admin/orderList.go"
                   class="<%= currentUri.contains("orderList") ? "active" : "" %>">주문 등록</a>
                <a href="<%= ctxPath %>/admin/orderDelivery.go"
                   class="<%= currentUri.contains("orderDelivery") ? "active" : "" %>">배송 관리</a>
            </div>
        </div>

        <%-- 상품관리 : 드롭다운 (URL 키워드: /product) ※ 하위 메뉴/URL 은 예시, 실제 값으로 교체 --%>
        <div class="menu-group">
            <button type="button" class="menu-group-btn"
                    data-bs-toggle="collapse" data-bs-target="#menuProduct"
                    aria-expanded="<%= "product".equals(adminMenu) %>">
                상품관리 <i class="fa-solid fa-chevron-down menu-arrow"></i>
            </button>
            <div id="menuProduct" class="collapse menu-sub <%= "product".equals(adminMenu) ? "show" : "" %>">
                <a href="<%= ctxPath %>/admin/productList.go"
                   class="<%= currentUri.contains("productList") ? "active" : "" %>">상품 목록</a>
                <a href="<%= ctxPath %>/admin/productRegister.go"
                   class="<%= currentUri.contains("productRegister") ? "active" : "" %>">상품 등록</a>
            </div>
        </div>

        <%-- 공급관리 : 드롭다운 (URL 키워드: /supply) ※ 하위 메뉴/URL 은 예시, 실제 값으로 교체 --%>
        <div class="menu-group">
            <button type="button" class="menu-group-btn"
                    data-bs-toggle="collapse" data-bs-target="#menuSupply"
                    aria-expanded="<%= "supplier".equals(adminMenu) %>">
                공급업체 관리 <i class="fa-solid fa-chevron-down menu-arrow"></i>
            </button>
            <div id="menuSupply" class="collapse menu-sub <%= "supplier".equals(adminMenu) ? "show" : "" %>">
                <a href="<%= ctxPath %>/admin/supplier/supplierList.go"
                   class="<%= currentUri.contains("supplyList") ? "active" : "" %>">공급업체 목록</a>
            </div>
        </div>

        <%-- 발주관리 : 드롭다운 (URL 키워드: /catalogue, 메뉴키: purchase) --%>
        <div class="menu-group">
            <button type="button" class="menu-group-btn"
                    data-bs-toggle="collapse" data-bs-target="#menuPurchase"
                    aria-expanded="<%= "purchase".equals(adminMenu) %>">
                발주관리 <i class="fa-solid fa-chevron-down menu-arrow"></i>
            </button>
            <div id="menuPurchase" class="collapse menu-sub <%= "purchase".equals(adminMenu) ? "show" : "" %>">
                <a href="<%= ctxPath %>/admin/catalogueList.go"
                   class="<%= currentUri.contains("catalogueList") ? "active" : "" %>">카탈로그 목록</a>
                <a href="<%= ctxPath %>/admin/catalogueRegister.go"
                   class="<%= currentUri.contains("catalogueRegister") ? "active" : "" %>">카탈로그 등록</a>
                <a href="<%= ctxPath %>/admin/purchaseList.go"
                   class="<%= currentUri.contains("purchaseList") ? "active" : "" %>">발주 목록</a>
                <a href="<%= ctxPath %>/admin/purchaseRegister.go"
                   class="<%= currentUri.contains("purchaseRegister") ? "active" : "" %>">발주 등록</a>     
            </div>
        </div>

        <%-- 고객센터 : 단일 메뉴 (URL 키워드: /customer) ※ URL 은 예시, 실제 값으로 교체 --%>
        <a href="<%= ctxPath %>/admin/customerList.go"
           class="menu-single <%= "customer".equals(adminMenu) ? "active" : "" %>">고객센터</a>

        <%-- 차트관리 : 단일 메뉴 (URL 키워드: /chart) ※ URL 은 예시, 실제 값으로 교체 --%>
        <a href="<%= ctxPath %>/admin/chartMain.go"
           class="menu-single <%= "chart".equals(adminMenu) ? "active" : "" %>">차트관리</a>

    </div>

    <script>
        function toggleAdminSidebar() {
            const isOpen = document.body.classList.toggle("sidebar-open");
            localStorage.setItem("adminSidebarOpen", isOpen ? "Y" : "N");
            updateSidebarIcon(isOpen);
        }

        function updateSidebarIcon(isOpen) {
            const icon = document.getElementById("sidebarToggleIcon");
            icon.className = isOpen ? "fa-solid fa-chevron-left"
                                    : "fa-solid fa-chevron-right";
        }

        // 초기 아이콘 상태 반영 + 이후부터 애니메이션 활성화
        updateSidebarIcon(document.body.classList.contains("sidebar-open"));
        window.addEventListener("load", function () {
            document.body.classList.add("sidebar-ready");
        });
    </script>

<% } %>

 	<!-- 상단 네비게이션 시작 -->
	<nav class="navbar navbar-expand-lg bg-white fixed-top py-2 shadow-sm my-2"
	     style="border-bottom: 1px solid #cccccc;
	            border-radius: 0 0 6px 6px;">


	    <!-- Brand/logo -->
	    <a class="navbar-brand"
	       href="<%= ctxPath %>/index.up"
	       style="margin-right: 1%;
	              padding-left: 5%;">

	        <img src="<%= ctxPath %>/images/Logo.svg" />

	    </a>


	    <!-- 모바일 Navbar 버튼 -->
	    <button class="navbar-toggler"
	            type="button"
	            data-bs-toggle="collapse"
	            data-bs-target="#collapsibleNavbar">

	        <span class="navbar-toggler-icon"></span>

	    </button>


	    <!-- Navbar 메뉴 -->
	    <div class="collapse navbar-collapse"
	         id="collapsibleNavbar">

	        <ul class="nav"
	            style="font-size: 16pt;">

	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    회원조회

	                </a>
	            </li>


	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    주문관리

	                </a>
	            </li>


	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    상품관리

	                </a>
	            </li>


	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    공급관리

	                </a>
	            </li>


	            <!-- 발주관리 -->
	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/admin/catalogueRegister.go">

	                    발주관리

	                </a>
	            </li>


	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    고객센터

	                </a>
	            </li>


	            <li class="nav-item">
	                <a class="nav-link menufont_size text-black"
	                   href="<%= ctxPath %>/shop/mallHomeMore.up">

	                    차트관리

	                </a>
	            </li>

	        </ul>


	        <!-- 관리자 정보 -->
	        <div class="ms-auto d-flex align-items-center"
	             style="padding:0% 5%;">

	            <span style="font-size:12pt;">
	                관리자 로그인중..
	            </span>

	            &nbsp;&nbsp;

	            <i class="fa-solid fa-circle-user fa-2x"
	               style="color:#315B48;">
	            </i>

	            &nbsp;&nbsp;&nbsp;&nbsp;

	            <a class="text-decoration-none text-secondary"
	               href="<%= ctxPath%>/login/logout.up"
	               title="로그아웃">

	                <i class="fa-solid fa-right-from-bracket fa-2x"
	                   style="color:#315B48;">
	                </i>

	            </a>

	        </div>

	    </div>

	</nav>
   <!-- 상단 네비게이션 끝 -->