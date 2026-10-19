
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%>

<!--<jsp:include page="/WEB-INF/header.jsp" />-->

<style type="text/css">

    #memberOrderContainer {
    width: 100%;
    max-width: 100%;
    padding: 0;
    margin: 0;
    position: static;
}

.member-order-content {
    background-color: #ffffff;
    width: 100%;
    min-width: 0;
    min-height: 500px;
    border: 1px solid #eeeeee;
    padding: 15px;
    margin-bottom: 20px;
    box-sizing: border-box;
}

#memberOrderContainer #memberTbl {
    width: 100%;
    max-width: 100%;
    table-layout: fixed;
}

#memberOrderContainer #memberTbl th {
    font-size: 11pt;
    white-space: normal;
    overflow-wrap: anywhere;
    background-color:#E3EDE0;
}

#memberOrderContainer #memberTbl td {
    overflow-wrap: anywhere;
}
    
    

    table#memberTbl {
        width: 100%;
        max-width: 100%;
        margin: 0;
        table-layout: fixed;
    }

    table#memberTbl th {
        text-align: center;
        vertical-align: middle;
        font-size: 12pt;
        white-space: nowrap;
    }

    table#memberTbl td {
        vertical-align: middle;
        overflow-wrap: anywhere;
    }

    table#memberTbl td:first-child,
    table#memberTbl td:nth-child(2),
    table#memberTbl td:nth-child(3),
    table#memberTbl td:nth-child(5),
    table#memberTbl td:nth-child(6),
    table#memberTbl td:nth-child(7),
    table#memberTbl td:nth-child(8) {
        text-align: center;
    }

    .product-info2 {
        display: flex;
        align-items: center;
        gap: 15px;
        padding: 10px 0;
    }

    .product-image2 {
        width: 32%;
        min-width: 90px;
        flex-shrink: 0;
    }

    .product-image2 img {
        display: block;
        width: 100%;
        max-height: 140px;
        object-fit: contain;
    }

    .product-detail2 {
        flex: 1;
        min-width: 0;
        overflow-wrap: anywhere;
    }

    .product-name2 {
        display: block;
        margin-bottom: 10px;
        font-size: 11pt;
        font-weight: bold;
    }

    .product-subinfo2 {
        margin-bottom: 5px;
        color: #666666;
        font-size: 10pt;
    }

    .product-order-info2 {
        font-size: 10pt;
        line-height: 1.7;
    }

    .status-text {
        font-weight: bold;
        white-space: normal;
    }

    .review-button {
    white-space: normal;
    overflow-wrap: anywhere;
    max-width: 100%;
    padding: 3px 5px;
    font-size: 10px;
    background-color:#E3EDE0 !important;
     
   }

    .review-rating {
        color: #d99a00;
        font-size: 14pt;
    }

    .review-content {
        white-space: pre-wrap;
        overflow-wrap: anywhere;
        line-height: 1.7;
    }

    .review-date {
        color: #777777;
        font-size: 10pt;
    }
    
    .text-secondary {  
       font-size: 9pt;
    }
    
    .price-text{
     font-size: 9pt;
    }
    
    .date-text{
     font-size:9pt;
    }
    
    #orderListArea #memberTbl td {
    font-size: 13px;
     }

    div#pageBar2 {
        width: 100%;
        margin-top: 3%;
        display: flex;
    }

    div#pageBar2 > nav {
        margin: auto;
    }

    @media (max-width: 768px) {

        .product-info {
            flex-direction: column;
            align-items: flex-start;
        }

        .product-image {
            width: 100%;
            max-width: 120px;
        }

        table#memberTbl th {
            font-size: 9pt;
            white-space: normal;
        }

        .product-subinfo,
        .product-order-info {
            font-size: 9pt;
        }

    }
    
    <!--리뷰 작성 모달--> 
    
     #reviewStarGroup .review-star {
        color: #ccc;
        transition: color 0.15s;
    }

    #reviewStarGroup .review-star.active {
        color: #ffc107;
    }

    #reviewStarGroup .review-star:hover {
        color: #ffb300;
    }

</style>

<script type="text/javascript">


function openReview_Modal(odnum) {
    const body = document.getElementById("reviewModalBody");

    body.textContent = "리뷰 정보를 불러오는 중입니다.";

    $.ajax({
        url: "<%= ctxPath %>/admin/reviewInfoJson.go",
        data: { odnum: odnum },
        dataType: "json",

        success: function(json) {
            console.log("리뷰 JSON:", json);

            if (!json || !json.rcontents) {
                body.textContent = "리뷰 정보를 찾을 수 없습니다.";
                return;
            }

            // DOM 요소를 직접 생성해서 값을 넣는다.
            // 리뷰 내용에 HTML이 포함되어도 실행되지 않도록 textContent 사용
            body.replaceChildren();

            const title = document.createElement("h6");
            title.textContent = "제품명: " + (json.pname || "");
            body.appendChild(title);

            const order = document.createElement("p");
            order.textContent = "주문번호: " + (json.onum || "");
            body.appendChild(order);

            body.appendChild(document.createElement("hr"));

            const ratingDiv = document.createElement("div");
            ratingDiv.className = "mb-3";
            ratingDiv.append("별점: ");

            const ratingStrong = document.createElement("strong");
            ratingStrong.className = "review-rating";
            ratingStrong.textContent = json.rating || "";

            ratingDiv.appendChild(ratingStrong);
            ratingDiv.append(" / 5점");
            body.appendChild(ratingDiv);

            const content = document.createElement("div");
            content.className = "review-content mb-3";
            content.textContent = json.rcontents || "";
            body.appendChild(content);

            const date = document.createElement("div");
            date.className = "review-date";
            date.textContent = "작성일: " + (json.rwritedate || "");
            body.appendChild(date);

            console.log("최종 제품명:", title.textContent);
            console.log("최종 본문:", body.innerText);
        },

        error: function(request, status, error) {
            body.textContent = "리뷰 조회에 실패했습니다.";
            console.error("리뷰 조회 오류:", request.responseText);
        }
    });
}





</script>

<div class="container-fluid" id="memberOrderContainer">

    <!-- 페이지 제목 및 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0;
                margin-bottom: 15px;">

        <h4 class="mb-0">주문 조회</h4>

        <nav style="--bs-breadcrumb-divider: '>';">
            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/index.go"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active">
                    마이페이지
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    주문 내역
                </li>

            </ol>
        </nav>

    </div>


    <!-- 관리자 페이지 작업 영역 -->
  <div class="member-order-content">

        <!-- 회사명 기준 제목 -->
        <p class="h4 text-center">&raquo;&nbsp;&nbsp;회원님[${(sessionScope.loginuser).company}]의 주문내역&nbsp;&nbsp;&laquo;</p>	


        <div class="container-fluid">

            <table class="table table-bordered table-hover"
                   id="memberTbl">

                <thead class="table-light">
                    <tr>
                        <th style="width: 5%;">번호</th>
                        <th style="width: 8%;">주문번호</th>
                        <th style="width: 8%;">주문일자</th>
                        <th style="width: 26%;">상품정보</th>
                        <th style="width: 8%;">리뷰</th>
                        <th style="width: 8%;">주문수량</th>
                        <th style="width: 9%;">주문총액</th>
                        <th style="width: 9%;">배송상태</th>  
                        
                         
                    </tr>
                </thead>


                <tbody>

                    <!-- 주문내역이 없는 경우 -->
                    <c:if test="${empty requestScope.order_map_List}">
                        <tr>
                            <td colspan="8"
                                class="text-center py-5">
                                주문내역이 없습니다.
                            </td>
                        </tr>
                    </c:if>


                    <!-- 주문내역 출력 -->
                    <c:if test="${not empty requestScope.order_map_List}">

                        <c:forEach var="odrmap"
                                   items="${requestScope.order_map_List}"
                                   varStatus="status">

                            <tr>

                                <!-- 번호 -->
                               <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
                      <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
                      <td class="text-center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>


                                <!-- 주문번호 -->
                                <td>
                                    <c:out value="${odrmap.ONUM}" />
                                </td>


					      <!-- 주문일자 -->
					
							<td class="date-text" style="text-align:center;">
							
							<div>
							
							<c:out value="${odrmap.ORDERDATE.substring(0, 5)}" />
							
							</div>
							
							<div>
							
							<c:out value="${odrmap.ORDERDATE.substring(5, 10)}" />
							
							</div>
							
							</td>


                                <!-- 상품정보 -->
                                <td>

                                    <div class="product-info2">

                                        <!-- 상품 이미지 -->
                                        <div class="product-image2">

                                          <img
                                             src="<%= ctxPath %>/images/<c:out value='${odrmap.PIMAGE1}'/>"
                                                
                                                 />

                                        </div>


                                        <!-- 상품명 및 주문정보 -->
                                        <div class="product-detail2" >

                                            <strong class="product-name2">
                                                <c:out value="${odrmap.PNAME}" />
                                            </strong>

                                            <div class="product-order-info2">
                                                제품번호:
                                                <c:out value="${odrmap.SNUM}" />
                                            </div>
                                            
                                             <div class="product-order-info2" style="text-decoration: line-through;">
												    정가:
												    <fmt:formatNumber value="${odrmap.REGPRICE}" pattern="#,##0" />원
												</div>
                                            
                                             <div class="product-order-info2" >
												    판매가:
												    <fmt:formatNumber value="${odrmap.SALEPRICE}" pattern="#,##0" />원
												</div>
												
												<div class="product-order-info2">
                                                 사이즈:
                                                <c:out value="${odrmap.SSIZE}" /> 
                                                 
                                            </div>
                                            
                                          <div class="product-order-info2">
                                                색상:
                                                <c:out value="${odrmap.COLOR}" />
                                            </div>


                                           <!-- <div class="product-order-info ">
                                                수량:
                                                <c:out value="${odrmap.OQTY}" />개
                                                · 주문금액:

                                                <strong>
                                                    <fmt:formatNumber
                                                        value="${odrmap.OQTY * odrmap.SALEPRICE}"
                                                        pattern="#,##0" />원
                                                </strong>
                                            </div>-->

                                        </div>

                                    </div>

                                </td>
                                
                               
								<!-- 리뷰 -->
								<td class="text-center">
								    <c:choose>
								
								        <%-- 리뷰가 이미 존재하는 경우 --%>
								        <c:when test="${not empty odrmap.RCONTENTS}">
								            <button type="button"
								                    class="btn btn-outline-dark btn-sm review-button"
								                    onclick="openReview_Modal('${odrmap.ODNUM}')"
								                    data-bs-toggle="modal"
								                    data-bs-target="#reviewModal">
								                리뷰 보기
								            </button>
								        </c:when>
								
								        <%-- 리뷰가 없고 출고 완료인 경우 --%>
								        <c:when test="${odrmap.ORDERSTATUS eq '출고 완료'}">
								            <button type="button"
								                    class="btn btn-outline-success btn-sm review-button"
								                    onclick="openReviewWrite_Modal('${odrmap.ODNUM}', '${odrmap.ONUM}')"
								                    data-bs-toggle="modal"
								                    data-bs-target="#reviewWriteModal">
								                리뷰 작성
								            </button>
								        </c:when>
								
								        <%-- 리뷰가 없고 출고 완료가 아닌 경우 --%>
								        <c:otherwise>
								            <span class="text-secondary small">리뷰 없음</span>
								        </c:otherwise>
								
								    </c:choose>
								</td>


                                <!-- 주문수량 -->
                                <td>
                                    <c:out value="${odrmap.OQTY}" />개
                                </td>


                                <!-- 주문총액 -->
                                <td class="price-text">
                                    <fmt:formatNumber
                                        value="${odrmap.OQTY * odrmap.SALEPRICE}"
                                        pattern="#,##0" />원
                                </td>


                                <!-- 배송상태 -->
                                <td>

                                    <c:choose>

                                        <c:when test="${odrmap.ORDERSTATUS == '접수'}">
                                            <span class="status-text"
                                                  style="color: blue;">
                                                결제 완료
                                            </span>
                                        </c:when>

                                        <c:when test="${odrmap.ORDERSTATUS == '상품 준비 중'}">
                                            <span class="status-text"
                                                  style="color: #336600;">
                                                상품 준비 중
                                            </span>
                                        </c:when>

                                        <c:when test="${odrmap.ORDERSTATUS == '출고 완료'}">
                                            <span class="status-text"
                                                  style="color: red;">
                                                배송 완료
                                            </span>
                                        </c:when>

                                        <c:otherwise>
                                            <span class="status-text">
                                                <c:out value="${odrmap.ORDERSTATUS}" />
                                            </span>
                                        </c:otherwise>

                                    </c:choose>

                                </td>


                             

                            </tr>

								<c:if test="${not empty odrmap.RCONTENTS}">

								      
								

                                

                            </c:if>
                           
                          

                        </c:forEach>

                    </c:if>

                </tbody>

            </table>    
                           <!-- 리뷰 보기 모달 -->
                          <div class="modal fade" id="reviewModal"
								     tabindex="-1" aria-labelledby="reviewModalLabel"
								     aria-hidden="true">
								
								    <div class="modal-dialog modal-dialog-centered">
								        <div class="modal-content">
								
								            <div class="modal-header">
								                <h5 class="modal-title" id="reviewModalLabel">
								                    작성한 리뷰
								                </h5>
								
								                <button type="button" class="btn-close"
								                        data-bs-dismiss="modal"
								                        aria-label="닫기"></button>
								            </div>
								
								            <div class="modal-body" id="reviewModalBody">
								            </div>
								
								            <div class="modal-footer">
								                <button type="button" class="btn btn-secondary"
								                        data-bs-dismiss="modal">
								                    닫기
								                </button>
								            </div>
								
								        </div>
								    </div>
								</div>
								
								
								
								
<!-- 리뷰 작성 모달 -->
<div class="modal fade" id="reviewWriteModal" tabindex="-1"
     aria-labelledby="reviewWriteModalLabel" aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title" id="reviewWriteModalLabel">
                    리뷰 작성
                </h5>
                <button type="button" class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="닫기"></button>
            </div>

            <div class="modal-body">

                <!-- 주문상세번호 -->
                <input type="hidden" id="reviewOdnum">

                <!-- 별점 -->
                <div class="mb-3">
                    <label class="form-label fw-bold">별점</label>

                    <div id="reviewStarGroup"
                         class="d-flex gap-2 fs-3"
                         style="cursor: pointer;">

                        <span class="review-star" data-rating="1">★</span>
                        <span class="review-star" data-rating="2">★</span>
                        <span class="review-star" data-rating="3">★</span>
                        <span class="review-star" data-rating="4">★</span>
                        <span class="review-star" data-rating="5">★</span>

                    </div>

                    <small id="reviewRatingText"
                           class="text-muted">별점을 선택해주세요.</small>
                </div>

                <!-- 리뷰 내용 -->
                <div class="mb-3">
                    <label for="reviewContents"
                           class="form-label fw-bold">
                        리뷰 내용
                    </label>

                    <textarea id="reviewContents"
                              class="form-control"
                              rows="5"
                              maxlength="300"
                              placeholder="상품에 대한 리뷰를 작성해주세요."></textarea>

                    <div class="text-end mt-1">
                        <small class="text-muted">
                            최대 300자
                        </small>
                    </div>
                </div>

            </div>

            <div class="modal-footer">
                <button type="button"
                        class="btn btn-outline-secondary"
                        data-bs-dismiss="modal">
                    취소
                </button>

                <button type="button"
                        class="btn btn-success"
                        id="reviewRegisterBtn">
                    리뷰 등록
                </button>
            </div>

        </div>
    </div>
</div>
								
								
								
								
								
								
								
								
								
								
								
								
					        </div>

			
			        <!-- 페이지바 -->
			        <div id="pageBar2">
			
			            <nav>
			                <ul class="pagination">
			                    ${requestScope.pageBar2}
			                </ul>
			            </nav>
			
			        </div>
			
			    </div>
			
			  </div>


<!--<jsp:include page="/WEB-INF/footer.jsp" />-->
