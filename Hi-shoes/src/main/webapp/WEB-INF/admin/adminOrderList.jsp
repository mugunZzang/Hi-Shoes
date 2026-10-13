<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String ctxPath = request.getContextPath();
    //     /MyMVC     
%>

<jsp:include page="adminHeader.jsp" />

<style type="text/css" >
form[name="member_search_frm"] {
width: 100%;
margin: 0 0 3% 0;
display: flex;
align-items: center;
flex-wrap: wrap;
}

table#memberTbl {
width: 100%;
max-width: 100%;
margin: 0;
}
   
   table#memberTbl {
	    width: 100%;
	    margin: 0;
	}
   
   table#memberTbl th {
      text-align: center;
      font-size: 14pt;
   }
   
   table#memberTbl tr.memberInfo:hover {
      cursor: pointer;
   }
   
   table#memberTbl td:first-child {
      text-align: right;
   }
   
   table#memberTbl td:nth-of-type(2),
   table#memberTbl td:nth-of-type(3),
   table#memberTbl td:last-child {
      text-align: center;
   }
   
	 form[name="member_search_frm"] {
	    border: solid 0px red;
	    width: 100%;
	    margin: 0 0 3% 0;
	    display: flex;
        align-items: center;
	}
	
	form[name="member_search_frm"] button.btn-secondary {
	    margin-left: 2%;
	    margin-right: 0;
	}
   
	   div#pageBar {
	    width: 100%;
	    margin: 3% 0 0 0;
	    display: flex;
	}
   
   div#pageBar > nav {
      margin: auto;
   }
   
   
   
   /* 모달관련 CSS 시작 */
   table#personInfoTbl {
		border: 0px solid gray;
		border-collapse: collapse;
		width: 90%;
		margin-top: 3%;
	}
	
	table#personInfoTbl tr {
		line-height: 200%;
	}
	
	table#personInfoTbl td {
		border: 0px solid gray;
		border-collapse: collapse;
		font-size: 14pt;
	}
	
	table#personInfoTbl td.title {
		text-align: justify;
		font-size: 14pt;
		font-weight: bold;
	}
	/* 모달관련 CSS 끝 */
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/myshop/categoryListJSON.js"></script>

<script type="text/javascript">
   $(function(){
	   
	   $("input#btnPreparingProduct").on('click',function(){
			// 배송하기 버튼 클릭시
			
			const chkPreparingProduct_length = $("input:checkbox[class='chkPreparingProduct custom_input']:checked").length;
			// 배송하기 체크박스에 체크가 되어진 개수
			// class 가 복합으로 이루어진 경우 class='chkPrepaingProduct custom_input' 와 같이 해야하지 class=chkDeliverStart 으로 하면 안된다.
			
			const chkDeliverEnd_length = $("input:checkbox[class='chkDeliverEnd custom_input']:checked").length;
			// 배송완료 체크박스에 체크가 되어진 개수
			// class 가 복합으로 이루어진 경우 class='chkDeliverEnd custom_input' 와 같이 해야하지 class=chkDeliverStart 으로 하면 안된다.
		
			
			if(chkPrepaingProduct_length == 0) {
				alert("먼저 하나이상의 준비할 제품을 선택하셔야 합니다.");
				return; // 종료 
			}
			
			if(chkDeliverEnd_length > 0) {
				alert("배송하기만 선택하셔야 합니다.");
				return; // 종료 
			}
			
			$("input.custom_input").prop("disabled", true); 
			// 배송하기 및 배송완료 관련 모든 input 태그 비활성화 시키기
			
			$("input:checkbox[class='chkPreparingProduct custom_input']:checked").prop("disabled", false);
			// 체크되어진 배송하기 체크박스(제품번호값을 가지고 있음)만 활성화 시키기
			
			$("input:checkbox[class='chkPreparingProduct custom_input']:checked").next().next().prop("disabled", false);
			// 체크되어진 배송하기의 주문코드(전표)만 활성화 시키기
			
			const frm = document.frmDeliver;
			frm.method = "post";
			frm.action = "<%= ctxPath%>/admin/adminPreparingProduct.up";
			frm.submit();

		});// end of $("input#btnDeliverStart").click()------------------
		
		
		$("input#btnDeliverEnd").click(function(){
			// 배송완료 버튼 클릭시
			
			const chkPreparingProduct_length = $("input:checkbox[class='chkPreparingProduct custom_input']:checked").length;
			// 배송하기 체크박스에 체크가 되어진 개수
			// class 가 복합으로 이루어진 경우 class='chkDeliverStart custom_input' 와 같이 해야하지 class=chkDeliverStart 으로 하면 안된다.
			
			const chkDeliverEnd_length = $("input:checkbox[class='chkDeliverEnd custom_input']:checked").length;
			// 배송완료 체크박스에 체크가 되어진 개수
			// class 가 복합으로 이루어진 경우 class='chkDeliverEnd custom_input' 와 같이 해야하지 class=chkDeliverStart 으로 하면 안된다.
		
			
			if(chkDeliverEnd_length == 0) {
				alert("먼저 하나이상의 배송을 완료할 제품을 선택하셔야 합니다.");
				return; // 종료 
			}
			
			if(chkPreparingProduct_length > 0) {
				alert("배송완료만 선택하셔야 합니다.");
				return; // 종료 
			}
			
			$("input.custom_input").prop("disabled", true); 
			// 배송하기 및 배송완료 관련 모든 input 태그 비활성화 시키기
			
			$("input:checkbox[class='chkPreparingProduct custom_input']:checked").prop("disabled", false);
			// 체크되어진 배송준비중 체크박스(제품번호값을 가지고 있음)만 활성화 시키기
			
			$("input:checkbox[class='chkDeliverEnd custom_input']:checked").next().next().prop("disabled", false);
			// 체크되어진 출고완료의 주문코드(전표)만 활성화 시키기
			
			const frm = document.frmDeliver;
			frm.method = "post";
			frm.action = "<%= ctxPath%>/admin/adminDeliverEnd.up";
			frm.submit();	
			
		});// end of $("input#btnDeliverEnd").click()------------------
	   
   });// end of $(function(){})---------------
   
   
   // Function Declaration
   
   function openMember_Modal(onum){
	   
	   $.ajax({
			url:"<%= ctxPath%>/admin/adminOnumMemberInfoJson.go",
			data:{"onum":onum},
			dataType:"json",
			success:function(json){
				console.log(JSON.stringify(json));
			/*
				{"birthday":"1971-01-27","registerday":"2026-04-29","detailaddress":"1004동 9004호","address":"경기 군포시 오금로 15-17","gender":"1","mobile":"01055530959","postcode":"15864","userid":"seoyh","point":14425,"extraaddress":" (금정동)","name":"서영학","email":"hanmailrg@naver.com","coin":4913000}
			*/
				
				let v_html = `<table id="personInfoTbl">
					<tr>
					  <td class="title">▷ 아이디</td>
					  <td>\${json.userid}</td>
					</tr>
								
					<tr>
					  <td class="title">▷ 사명</td>
					  <td>\${json.company}</td>
					</tr>
								
					<tr>
					  <td class="title">▷ 이메일</td>
					  <td>\${json.email}</td>
					</tr>
					
					<tr>
					  <td class="title">▷ 연락처</td>
					  <td>\${json.mobile}</td>
					</tr>
					
					<tr>
					  <td class="title">▷ 우편번호</td>
					  <td>\${json.postcode}</td>
					</tr>
					
					<tr>
					  <td class="title">▷ 주소</td>
					  <td>\${json.address}<br>\${json.detailaddress}&nbsp;\${json.extraaddress}</td>
					</tr>
				</table>`;
			
				$("div#onum_owner_modal-body").html(v_html);
			},
			error: function(request, status, error){
			   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
	   });	
   }// end of function openMember_Modal(odrcode)--------------
   
   function allCheckStart() {
		$("input.custom_input").prop("disabled", false); // 모든 배송하기 및 배송완료 input 태그 활성화 시키기
		
		const bool = $("input#allCheckPreparingProduct").is(':checked');
		$("input.chkPreparingProduct").prop('checked', bool);
	}// end of function allCheckBoxStart()------------
	
	
	function allCheckEnd() {
		$("input.custom_input").prop("disabled", false); // 모든 배송하기 및 배송완료 input 태그 활성화 시키기
		
		const bool = $("input#allCheckDeliverEnd").is(':checked');
		$("input.chkDeliverEnd").prop('checked', bool);
	}// end of function allCheckBoxEnd()-----------
   
   
</script>

<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">
    <h4 class="mb-0" >주문목록</h4>
        <p class="mb-0 fs-4 fw-semibold">
          
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/admin/adminMain.go"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    주문관리
                </li>
                
                <li class="breadcrumb-item active"
                    aria-current="page">
                    주문목록
                </li>

            </ol>

        </nav>

    </div>


    <!-- 실제 페이지 작업 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;
                
                margin-bottom: 150px;">



<div class="container-fluid" style="border: solid 0px red">
<h2 class="text-center mb-5">주문내역 전체 목록</h2>
	
	<div>
		<form name="frmDeliver">
			<table class="table table-bordered table-hover" id="memberTbl">
				
			  <c:if test='${userid ne "admin"}'>
				<tr>	
					<th colspan="7" style="text-align: right; border: none;"> 
						<input type="checkbox" id="allCheckPreparingProduct" onclick="allCheckStart()">&nbsp;<label for="allCheckPreparingProduct"><span style="color: green; font-weight: bold; font-size: 9pt;">전체선택(상품 준비 중)</span></label>&nbsp;
						<input type="button" name="btnPreparingProduct" id="btnPreparingProduct" value="상품 준비 중" class="btn btn-outline-success btn-sm me-3" />
						
						<input type="checkbox" id="allCheckDeliverEnd" onclick="allCheckEnd()">&nbsp;<label for="allCheckDeliverEnd"><span style="color: red; font-weight: bold; font-size: 9pt;">전체선택(출고완료)</span></label>&nbsp;
						<input type="button" name="btnDeliverEnd" id="btnDeliverEnd" value="출고완료" class="btn btn-outline-danger btn-sm" />
					</th>
				</tr>
			  </c:if>
					<thead>
			    <tr>
					<th width="8%" style="text-align: center;">번호</th>
					<!-- <th class="table-success" width="8%" style="text-align: center;">이름</th> -->
					<th  width="12%" style="text-align: center;">주문번호</th>
					<th  width="10%" style="text-align: center;">주문일자</th>
					<th  width="36%" style="text-align: center;">제품정보</th> <%-- 제품번호,제품명,제품이미지1,판매정가,판매세일가,포인트 --%> 
					<th  width="7%"  style="text-align: center;">주문수량</th>
					<th  width="10%" style="text-align: center;">주문총액</th>   
					<!--<th width="7%"  style="text-align: center;">총포인트</th> -->
					<th width="10%" style="text-align: center;">배송상태</th>
			    </tr>
			    </thead>
				<c:if test="${empty requestScope.order_map_List}" > 
				  <tr>
					  <td colspan="8" align="center">
					  <span style="color: black; font-weight: bold;">주문내역이 없습니다.</span>
				  </td>
				  </tr>
				</c:if>
			
				<%-- ============================================ --%>
				<c:if test="${not empty requestScope.order_map_List}">
					<c:forEach var="odrmap" items="${requestScope.order_map_List}" varStatus="status">
						<%--
							 varStatus 는 반복문의 상태정보를 알려주는 애트리뷰트이다.
							 status.index : 0 부터 시작한다.
							 status.count : 반복문 횟수를 알려주는 것이다.
						 --%>
						<tr>
						    <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> 
						    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%> 
						    <td align="center">${ (requestScope.totalCountOrder) - (currentShowPageNo -1) * (requestScope.sizePerPage) - (status.index) }</td>
						    <%-- >>> 페이징 처리시 보여주는 순번 공식 <<<
								   데이터개수 - (페이지번호 - 1) * 1페이지당보여줄개수 - 인덱스번호 => 순번 
								
								   <예제>
								   데이터개수 : 12
								   1페이지당보여줄개수 : 5
								
								   ==> 1 페이지       
								   12 - (1-1) * 5 - 0  => 12
								   12 - (1-1) * 5 - 1  => 11
								   12 - (1-1) * 5 - 2  => 10
								   12 - (1-1) * 5 - 3  =>  9
								   12 - (1-1) * 5 - 4  =>  8
								
								   ==> 2 페이지
								   12 - (2-1) * 5 - 0  =>  7
								   12 - (2-1) * 5 - 1  =>  6
								   12 - (2-1) * 5 - 2  =>  5
								   12 - (2-1) * 5 - 3  =>  4
								   12 - (2-1) * 5 - 4  =>  3
								
								   ==> 3 페이지
								   12 - (3-1) * 5 - 0  =>  2
								   12 - (3-1) * 5 - 1  =>  1 
							  --%>
						    <td align="center"> <%-- 주문코드(전표) 출력하기. 
							      만약에 관리자로 들어와서 주문내역을 볼 경우 해당 주문코드(전표)를 클릭하면 
							      주문코드(전표)를 소유한 회원정보를 조회하도록 한다.  --%>
							      <c:if test='${userid ne "admin"}'>
							         <%-- 주문코드(전표) 소유주를 모달로 띄우기 --%>
							         <span style="cursor: pointer;" data-bs-toggle="modal" data-bs-target="#onum_owner_modal_view" onclick="openMember_Modal('${odrmap.ONUM}')">${odrmap.ONUM}</span> 
							     </c:if>
							      
							      <!--<c:if test='${userid ne "admin"}'>
							         ${odrmap.ONUM}
							      </c:if>-->
							</td>
							
							<td align="center"> <%-- 주문일자 --%>
								<small>${odrmap.ORDERDATE}</small>
							</td>
							
							<td>  <%-- === 제품정보 넣기 === --%>
								 <div style="display: flex; padding-top: 10px; justify-content: space-between;">
								    <%-- flex 아이템들은 width의 기본값은 내용물 만큼 잡히고 height 값들은 동일하게 설정되어 inline 형태로 보여진다.
								         justify-content: space-between; 은 flex 아이템들 사이에 간격을 균일하게 만들어 주는 것이다.
								                 이것이 없으면  flex 아이템들은 간격없이 죽~~붙어서 나오게 된다. 
								    --%>
								    <div style="width: 44%;">
								       <img src="<%= ctxPath%>/images/${odrmap.PIMAGE1}" width="100%" />  <%-- 제품이미지1 --%>
								    </div>
								    <div style="width: 54%;">
								        <ul class="list-unstyled">
									       <li>제품번호 : ${odrmap.SNUM}</li> <%-- 제품번호 --%>
									       <li>제품명 : ${odrmap.PNAME}</li>    <%-- 제품명 --%> 
									       <li>정&nbsp;가 : <span style="text-decoration: line-through;"><fmt:formatNumber value="${odrmap.REGPRICE}" pattern="###,###" /></span> 원</li>   <%-- 제품개당 판매정가 --%> 
									       <li>판매가 : <span class="text-danger font-weight-bold"><fmt:formatNumber value="${odrmap.SALEPRICE}" pattern="###,###" /></span> 원</li> <%-- 제품개당 판매세일가 --%>
									      <!--  <li>포인트 : <span class="text-danger font-weight-bold"><fmt:formatNumber value="${odrmap.POINT}" pattern="###,###" /></span> POINT</li>  <%-- 제품개당 포인트 --%> -->
									    </ul>
								    </div>
								 </div>
							</td>
							
							<td align="center">    <%-- 수량 --%>
								 ${odrmap.OQTY} 개
							</td>
							
							<td align="center">    <%-- 금액 --%>
							     <c:set var="oqty" value="${odrmap.OQTY}" />
							     <c:set var="saleprice" value="${odrmap.SALEPRICE}" />
							     <c:set var="ordertotal" value="${oqty * saleprice}" />
							     
							     <fmt:formatNumber value="${ordertotal}" pattern="###,###" /> 원 
							</td>
							
							<!--  <td align="center">    <%-- 포인트 --%>
							     <c:set var="point" value="${odrmap.POINT}" />
							     <c:set var="totalpoint" value="${su * point}" />
								 <fmt:formatNumber value="${totalpoint}" pattern="###,###" /> P
							</td>-->
							
							<td align="center"> <%-- 배송상태 --%>
								<c:if test='${userid ne "admin"}'>
								   <c:choose>
								      <c:when test="${odrmap.ORDERSTATUS == '접수'}">
								         <span style="color: blue; font-weight: bold; font-size: 12pt;">접수</span><br>
								      </c:when>
								      <c:when test="${odrmap.ORDERSTATUS == '상품 준비 중'}">
								         <span style="color: green; font-weight: bold; font-size: 12pt;">상품 준비 중</span><br>
								      </c:when>
								     
								      <c:when test="${odrmap.ORDERSTATUS == '출고 완료'}">
								         <span style="color: red; font-weight: bold; font-size: 12pt;">출고 완료</span><br>
								      </c:when>
								   </c:choose>
								</c:if>
								
								<c:if test='${userid ne "admin"}'>
									<c:if test="${odrmap.ORDERSTATUS == '접수'}">
										<input type="checkbox" class="chkPreparingProduct custom_input" name="snum" id="chkPreparingProduct${status.index}" value="${odrmap.SNUM}">&nbsp;
										<label for="chkPreparingProduct${status.index}">상품 준비 중</label> 
								  <%--  <input type="text"   class="onumPreparingProduct custom_input" name="onum" value="${odrmap.ONUM}" /> --%>
							            <input type="hidden" class="onumPreparingProduct custom_input" name="onum" value="${odrmap.ONUM}" />  
									</c:if>
									<br/>
									<c:if test="${odrmap.ORDERSTATUS == '상품 준비 중'}">
										<input type="checkbox" class="chkDeliverEnd custom_input" name="snum" id="chkDeliverEnd${status.index}" value="${odrmap.SNUM}">&nbsp;
										<label for="chkDeliverEnd${status.index}">출고 완료</label>
								  <%--  <input type="text"   class="onumDeliverEnd custom_input" name="onum" value="${odrmap.ONUM}" /> --%> 
							            <input type="hidden" class="onumDeliverEnd custom_input" name="onum" value="${odrmap.ONUM}" /> 
									</c:if>
								</c:if>
							</td>
						</tr>
					    </c:forEach>
					</c:if>
			</table>
		</form>  
	</div> 
	 
	<%-- === 페이지바 === --%>
	 <div id="pageBar">
      <nav>
      <ul class="pagination">
      ${requestScope.pageBar}
      </ul>
      </nav>
      </div>
 
</div>
</div>

<%-- ****** 주문코드(전표) 소유주 보여주기 Modal 시작 ****** --%>
  <div class="modal fade" id="onum_owner_modal_view" tabindex="-1"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%> 
                                                   <%-- tabindex="-1"은 모달을 열었을 때 모달에 포커스를 강제로 주도록 하는 속성으로, 접근성과 사용자 경험을 개선하기 위해 Bootstrap에서는 기본적으로 넣어둔다.  --%>
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">주문자 정보 보기</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="onum_owner_modal-body">
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
<%-- ****** 주문코드(전표) 소유주 보여주기 Modal 끝 ****** --%>

<jsp:include page="adminFooter.jsp" />
  