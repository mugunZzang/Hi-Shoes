<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
    .paper {
        width: 794px;              /* A4 폭 */
        margin: 0 auto;
        background: #fff;
        border: 1px solid #ccc;
        padding: 50px 45px;
        font-size: 14px;
        color: #222;
    }
    .paper .doc-title { text-align: center; font-size: 28px; font-weight: 600; margin-bottom: 30px; }

    .paper table { border-collapse: collapse; width: 100%; }
    .paper th, .paper td { border: 1px solid #bbb; padding: 7px 10px; }
    .paper th { background: #f2f2f2; font-weight: 500; text-align: center; width: 90px; }

	
	.supplier-side .label-col { width: 32px; background: #f2f2f2; text-align: center; font-weight: 500; }
    .buyer-box { background: #eee; border: 1px solid #bbb; padding: 14px 18px; }
    .buyer-box .to { background: #ddd; text-align: center; font-weight: 600; padding: 8px; margin: -14px -18px 14px; }

    .items th { background: #e9e9e9; text-align: center; width: auto; }
    .items td { text-align: center; }
    .items .sum-row td { background: #e9e9e9; font-weight: 600; }
    .note-box { border: 1px solid #bbb; padding: 15px; margin-top: 25px; font-size: 13px; text-align: center; }
    
	#quotationModalBody {
	    background-color: #f5f6f6;
	    padding: 30px;
	}

    @media print {
        .no-print, nav, header, footer, #sidebarToggleIcon, #sidebarToggleBtn { display: none !important; }
        .paper { border: none; width: 100%; padding: 0; }
        #container { top: 0 !important; padding: 0 !important; }
    }
</style>


<div class="paper">

    <div class="doc-title">주문제작 견적서</div>

    <!-- 업체 정보 + 담당자 정보 -->
    <div class="row g-3 mb-4">
		<div class="col-6">
			<table class="supplier-side">
			    <tr>
			        <td class="label-col" rowspan="5">공<br>급<br>자</td>
			        <th>업체명</th><td><c:out value="${purchaseMap.supname}"/></td>
			    </tr>
			    <tr><th>대표</th><td><c:out value="${purchaseMap.ceo}"/></td></tr>
			    <tr><th>연락처</th><td><c:out value="${purchaseMap.smobile}"/></td></tr>
			    <tr><th>사업자번호</th><td><c:out value="${purchaseMap.sbusinum}"/></td></tr>
			    <tr><th>이메일</th><td><c:out value="${purchaseMap.semail}"/></td></tr>
			</table>
		</div>
		<div class="col-6">
			<div class="buyer-box h-100">
			    <div class="to"><c:out value="${purchaseMap.supname}"/> 귀하</div>
			    <div>담당자 : <c:out value="${sessionScope.loginuser.name}"/></div>  <!-- 현재 세션스코프 적용 X -->
			    <div>연락처 : <c:out value="${sessionScope.loginuser.mobile}"/></div>
			    <div>이메일 : <c:out value="${sessionScope.loginuser.email}"/></div>
			    <div>발주일 : <c:out value="${purchaseMap.purtime}"/></div>
			</div>
	    </div>
	</div>
    <p class="text-center my-4">아래와 같이 견적합니다.</p>

    <!-- 상품 목록 -->
    <table class="items">
        <colgroup>
            <col style="width: 7%;">
            <col style="width: 31%;">
            <col style="width: 10%;">
            <col style="width: 12%;">
            <col style="width: 9%;">
            <col style="width: 15%;">
            <col style="width: 16%;">
        </colgroup>
        <thead>
            <tr>
                <th>No.</th><th>품명</th><th>사이즈</th><th>색상</th>
                <th>수량</th><th>단가</th><th>합계</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="d" items="${detailList}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td><c:out value="${d.fk_pname}"/></td>
                    <td>${d.ssize}</td>
                    <td>
                        <c:choose>
                            <c:when test="${d.color == 'BLACK'}">검정</c:when>
                            <c:when test="${d.color == 'WHITE'}">흰색</c:when>
                            <c:when test="${d.color == 'SILVER'}">실버</c:when>
                            <c:when test="${d.color == 'BROWN'}">갈색</c:when>
                            <c:when test="${d.color == 'BLUE'}">파랑</c:when>
                            <c:when test="${d.color == 'YELLOW'}">노랑</c:when>
                            <c:when test="${d.color == 'RED'}">빨강</c:when>
                            <c:when test="${d.color == 'IVORY'}">아이보리</c:when>
                            <c:otherwise><c:out value="${d.color}"/></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${d.purqty}</td>
                    <td>₩<fmt:formatNumber value="${d.purdprice}" pattern="#,###"/></td>
                    <td>₩<fmt:formatNumber value="${d.amount}" pattern="#,###"/></td>
                </tr>
            </c:forEach>
            
            <c:if test="${fn:length(detailList) < 10}">
			    <c:forEach begin="${fn:length(detailList) + 1}" end="10" var="i">
			        <tr class="empty-row">
			            <td>${i}</td>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			    </c:forEach>
			</c:if>

            <tr class="sum-row">
                <td colspan="5">최종 견적가</td>
                <td colspan="2" style="font-size: 17px;">
                    ₩<fmt:formatNumber value="${total}" pattern="#,###"/>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="note-box">
        참고사항<br>
        - 본 견적서는 발주 등록 내용을 기준으로 작성되었습니다.
    </div>

</div>