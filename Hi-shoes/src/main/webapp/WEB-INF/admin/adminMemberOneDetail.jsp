<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<jsp:include page="adminHeader.jsp" />

<style type="text/css">

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
   

</style>

<script type="text/javascript">

 /*  $(function(){
	  
	  $('div#smsResult').hide();
	  
	  $('button#btnSend').on('click', function(){
		  
	    // console.log( $('input#reservedate').val() + " " + $('input#reservetime').val() );
		  // 2026-09-11 14:40
		  
		   let reservedate = $('input#reservedate').val();
		   reservedate = reservedate.split("-").join("");
		   // ["2026","09","11"] ==> "20260911"
		  
		   let reservetime = $('input#reservetime').val();
		   reservetime = reservetime.split(":").join("");
		   // ["14","40"] ==> "1440"
		   
		   const datetime = reservedate + reservetime; 
		   
		// console.log(datetime);
		  // 202609111440
		  
		   let dataObj;
		  
		   if(reservedate == "" || reservetime == "") {
			   // 문자를 바로 보내기인 경우
			   dataObj = {"mobile":"${requestScope.mdto.mobile}",
					      "smsContent":$('textarea#smsContent').val()};
		   }
		   else {
			   // 예약문자 보내기인 경우 
			   dataObj = {"mobile":"${requestScope.mdto.mobile}",
					      "smsContent":$('textarea#smsContent').val(),
					      "datetime":datetime};
		   }
		   
		  
		  // jQuery Ajax 를 사용함.
		  
		  $.ajax({
			  url:"${pageContext.request.contextPath}/member/smsSend.up",
			  method:"get",
			  data:dataObj,
			  dataType:"json",
			  success:function(json){
				// console.log(json);
				  // json 은 {group_id: 'R2GLBe2D5pTJ0yuS', success_count: 1, error_count: 0} 처럼 된다.
				  
				  if(json.success_count == 1) {
					  $('div#smsResult').html("<span style='color:red; font-weight:bold;'>문자전송이 성공되었습니다.^^</span>"); 
				  }	
				  else if(json.error_count != 0) {
					  $('div#smsResult').html("<span style='color:red; font-weight:bold;'>문자전송이 실패되었습니다.ㅜㅜ</span>");
				  }
				  
				  $('div#smsResult').show();
				  $('textarea#smsContent').val("");
				  
			  },
			  error: function(request, status, error){
				  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			  }
		  });
		  
	  });
	  
  });// end of $(function(){})-------------------------- */

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
    <h4 class="mb-0" >회원 상세</h4>
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

              <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/admin/adminMemberList.go"
                       class="text-decoration-none">
                        회원조회
                    </a>
                </li>
                
                <li class="breadcrumb-item active"
                    aria-current="page">
                    회원상세
                </li>

            </ol>

        </nav>

    </div>
<div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">
<div class="container-fluid width" style="width:70%">


	<c:if test="${empty requestScope.mdto}">
	    <div class="text-center h4 my-5">존재하지 않는 회원입니다</div>
	</c:if>
	
	<c:if test="${not empty requestScope.mdto}">
	    <p class="h3 text-center mt-5 mb-4"> >> ${requestScope.mdto.company}의 상세정보 << </p> 
	    
	    <table class="table table-bordered" style="width: 60%; margin: 0 auto;">
	       <tbody>
		       <tr>
		          <td>아이디</td>
		          <td>${requestScope.mdto.userid}</td>
		       </tr>
		       
		       <tr>
		          <td>사명</td>
		          <td>${requestScope.mdto.company}</td>
		       </tr>
		       
		       <tr>
		          <td>이메일</td>
		          <td>${requestScope.mdto.email}</td>
		       </tr>
		       
		       <tr>
		          <td>휴대폰</td>
		          <c:set var="mobile" value="${requestScope.mdto.mobile}" />
		          <td>${fn:substring(mobile, 0, 3)}-${fn:substring(mobile, 3, 7)}-${fn:substring(mobile, 7, 11)}</td> 
		       </tr>
		       
		       <tr>
		          <td>우편번호</td>
		          <td>${requestScope.mdto.postcode}</td>
		       </tr>
		       
		       <tr>
		          <td>주소</td>
		          <td>
		             ${requestScope.mdto.address}&nbsp;
		             ${requestScope.mdto.detailaddress}&nbsp;
		             ${requestScope.mdto.extraaddress}
		          </td>
		       </tr>
		       
		       <tr>
		          <td>가입일자</td>
		          <td>${requestScope.mdto.registerday}</td>
		       </tr>
		       
		       <tr>
		          <td>휴면여부</td>
		          <td>
		            <c:choose>
		              <c:when test="${requestScope.mdto.idle == '활동중'}">활동중</c:when> 
		              <c:otherwise>휴면계정</c:otherwise> 
		            </c:choose>
		          </td>
		       </tr>
		       
		       <tr>
		          <td>정지여부</td>
		          <td>
		            <c:choose>
		              <c:when test="${requestScope.mdto.ban == '정상'}">정상</c:when> 
		              <c:otherwise>정지</c:otherwise> 
		            </c:choose>
		          </td>
		       </tr>
	       </tbody>
	    </table>
	    
	    <%-- === 휴대폰 SMS(문자) 보내기 === --%>
	   <%-- <div class="border my-5 text-center" style="width: 60%; margin: 0 auto;">
		  	<p class="h5 bg-info text-white">
		  	  &gt;&gt;&nbsp;&nbsp;휴대폰 SMS(문자) 보내기 내용 입력란&nbsp;&nbsp;&lt;&lt;
		  	</p>
		  	<div class="mt-4 mb-3">
		  		<span class="bg-danger text-white" style="font-size: 14pt;">문자발송 예약일자</span>
		  		<input type="date" id="reservedate" class="mx-2" />
		  		<input type="time" id="reservetime" />
		  	</div>
		  	<div style="display: flex;">
		  	   <div style="border: solid 0px red; width: 81%; margin: auto;">
		  	      <textarea rows="4" id="smsContent" style="width: 100%;"></textarea>
		  	   </div>
		  	   <div style="border: solid 0px blue; width: 19%; margin: auto;">
		  	      <button id="btnSend" class="btn btn-secondary">문자전송</button>
		  	   </div>
		  	</div>
		  	<div id="smsResult" class="p-3"></div>
		</div> --%>
	    
	</c:if>
	
	<div class="text-center  mb-5 mt-5">
	   <button type="button" class="btn btn-secondary me-5" onclick="javascript:location.href='adminMemberList.go'">회원목록[처음으로]</button>
	   <!--<button type="button" class="btn btn-success mx-5" onclick="javascript:history.back()">회원목록[history.back()]</button>-->
	   <button type="button" class="btn btn-primary " onclick="javascript:location.href='${requestScope.referer}'">회원목록[검색된결과]</button> 
	</div>

</div>

<jsp:include page="adminFooter.jsp" />
    