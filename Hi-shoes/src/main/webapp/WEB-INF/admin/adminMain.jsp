<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="adminHeader.jsp" />

<style type="text/css">

.dashboard-status-title {
background-color: #E3EDE0;
color: #24476a;
font-weight: bold;
padding: 10px;
border-radius: 4px 4px 0 0;
}


.nav-link {
    background-color: transparent;
    color: #333;
}


.nav-link.active {
    background-color: #E3EDE0 !important;
    color: #336600;
}


/* 재고현황 테이블 크기 튀어나감 방지 css */

.stock-table {

table-layout: fixed;

font-size: 12px;

}

.stock-table th,

.stock-table td {

padding: 4px 2px;

line-height: 1.1;

vertical-align: middle;

overflow-wrap: anywhere;

}



 </style>


<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold">
          
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

               <%-- <li class="breadcrumb-item active"
                    aria-current="page">
                    Library
                </li>--%> 

            </ol>

        </nav>

    </div>

   

      <!-- 실제 페이지 작업 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">

        <!-- 실제 관리자 페이지 내용 -->
		 <!-- ================= 메인페이지 첫 번째 줄 ================= -->
<div class="row">

    <!-- 주문/출고 현황 -->
    <div class="col-md-6">

        <h5 class="mb-3">주문/출고 현황</h5>

        <div class="row">

            <!-- 주문건 -->
            <div class="col-4">
                <div class="card border-0 shadow-lg text-center p-0"
                     style="height: 290px;">

                    <div class ="dashboard-status-title">주문건</div>

                    <div class="mt-5">
                        <span style="font-size: 28px;"> ${requestScope.orderCount}건</span>
                    </div>

                </div>
            </div>


            <!-- 승인건 -->
            <div class="col-4">
                <div class="card border-0 shadow-lg text-center p-0"
                     style="height: 290px;">

                    <div class ="dashboard-status-title">승인건</div>

                    <div class="mt-5">
                        <span style="font-size: 28px;">${requestScope.approveCount}건</span>
                    </div>

                </div>
            </div>


            <!-- 출고건 -->
            <div class="col-4">
                <div class="card border-0 shadow-lg text-center p-0"
                     style="height: 290px;">

                    <div class ="dashboard-status-title">출고건</div>

                    <div class="mt-5">
                        <span style="font-size: 28px;">${requestScope.deliveryCount}건</span>
                    </div>

                </div>
            </div>

        </div>

    </div>


    <!-- 공지사항 -->
    <div class="col-md-6">
<h5 class="mb-3">공지 및 문의사항</h5>

<div class="card border-0 shadow-lg"
     style="height: 290px;">

    <!-- 공지사항의 탭 메뉴 -->
    <ul class="nav nav-tabs" role="tablist">

        <li class="nav-item" role="presentation">
            <button class="nav-link active"
                   
                    data-bs-toggle="tab"
                    data-bs-target="#notice"
                    type="button">
                공지사항
            </button>
        </li>

       <!--  <li class="nav-item" role="presentation">
            <button class="nav-link"
                    data-bs-toggle="tab"
                    data-bs-target="#qna"
                    type="button">
                Q&A
            </button>
        </li> -->

        <li class="nav-item" role="presentation">
            <button class="nav-link"
                   
                    data-bs-toggle="tab"
                    data-bs-target="#qna"
                    type="button">
                문의사항
            </button>
        </li>

    </ul>


    <!-- 탭 내용 -->
    <div class="tab-content p-3">


        <!-- ================= 공지사항 ================= -->
        <div class="tab-pane fade show active"
             id="notice">

            <ul class="list-unstyled mb-0">
		     <c:forEach var="notice" items="${requestScope.noticeList}">
		    <li class="mb-2">
		        <span>|전체공지|</span>
		        ${notice["NSUBJECT"]}
		        <small class="float-end">${notice["NWRITEDATE"]}</small>
		    </li>
		  </c:forEach>

                

            </ul>

        </div>


      


        <!-- ================= 문의사항 ================= -->
        <div class="tab-pane fade"
             id="qna">

            <ul class="list-unstyled mb-0">

                 <c:forEach var="qna" items="${requestScope.qnaList}">
		    <li class="mb-2">
		        <span>|문의사항|</span>
		        ${qna["QTITLE"]}
		        <small class="float-end">${qna["QWRITEDATE"]}</small>
		    </li>
		  </c:forEach>

            </ul>

        </div>

    </div>

</div>
</div>

</div>


<!-- ================= 메인페이지 두 번째 줄 ================= -->
<div class="row mt-4">

    <!-- 당일 매출 현황 -->
    <div class="col-md-6">

        <h5 class="mb-3">당일 매출 현황</h5>

        <div class="card border-0 shadow-lg p-3 "
             style="height: 290px;">
            

            <table class="table table-bordered text-center my-auto" >
            

                <thead>
                    <tr>
                        <th class="table-success"></th>
                        <th colspan="3" class="table-success">판매</th>
                        
                    </tr>

                    <tr>
                        <th class="table-success"></th>
                        <th class="table-success">건수</th>
                        <th class="table-success">수량</th>
                        <th class="table-success">금액</th>
                        
                    </tr>
                </thead>

                <tbody>
                    <tr>
                        <td>금일</td>
                        <td>${requestScope.todayDeliveryCount}건</td>
                        <td>${requestScope.todaySalesCount}개</td>
                        <td>${requestScope.todaySalesSum}원</td>
                    </tr>
                </tbody>

            </table>

        </div>

    </div>


    <!-- 재고현황 -->
    <div class="col-md-6">

        <h5 class="mb-3">재고현황</h5>

        <div class="card border-0 shadow-lg"
             style="height: 290px;">
             
			<table class="table table-bordered text-center mx-auto my-auto stock-table"

            style="width:95%;">
            
			<thead>
			    <tr>
			        <th class="table-success">제품명</th>
			        <th class="table-success">현재고</th>
			        <th class="table-success">재고상태</th>
			        <th class="table-success">사이즈</th>
			        <th class="table-success">색상</th>
			    </tr>
			</thead>
			
			 <tbody>
			      <c:forEach var="stock" items="${requestScope.stockList}">
					    <tr>
					        <td>${stock.PNAME}</td>
					        <td>${stock.SQTY}개</td>
					        <td>${stock.STOCKSTATUS}</td>
					        <td>${stock.SSIZE}</td>
					        <td>${stock.COLOR}</td>
					    </tr>
					</c:forEach>
									    
				    
			   
				
			 </tbody>
			
			</table>
            

        </div>

    </div>

</div>

    </div>

</div>


<jsp:include page="adminFooter.jsp" />
    
   
