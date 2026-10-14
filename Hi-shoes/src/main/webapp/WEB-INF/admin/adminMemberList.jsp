<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="adminHeader.jsp" />

<style type="text/css">

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
   
</style>

<script type="text/javascript">

$(function(){
	if("${requestScope.searchType}" !="" && "${requestScope.searchWord}" !=""){
		$('select[name="searchType"]').val("${requestScope.searchType}");
		$('input:text[name="searchWord"]').val("${requestScope.searchWord}");
	}
	
	$('select[name="sizePerPage"]').val("${requestScope.sizePerPage}");
	
    $('input:text[name="searchWord"]').on('keyup', function(e){
        if(e.keyCode == 13){
            goSearch();
        }
    }); 
    
    //페이지당 회원명수(select 태그) 처리하기 
    //=== select 태그에 대한 이벤트는 click이 아니라 change이다. ===
    	$('select[name="sizePerPage"]').on('change',function(){
    		const frm =document.member_search_frm;
    		//frm.action ="memberList.up";
    		//frm.method ="get"; //form태그에 method를 명기하지 않으면 "get"방식이다.
    		frm.submit();
    		
    		
    	});
    
    //*** 특정회원을 클릭하면 그 회원의 상세정보를 보여주도록 한다.
     $('table#memberTbl tr.memberInfo').on('click',function(e){
    	 //alert($(e.target).parent().html());
    	 
    	 const userid=$(e.target).parent().find('.userid').text();
    	 //alert(userid);
    	 
    	 const frm = document.adminMemberOneDetail_frm;
    	 frm.userid.value=userid;
    	 <%--frm.action = "<%= request.getContextPath()%>/member/memberOneDetail.up";--%>
    	   frm.action ="${pageContext.request.contextPath}/admin/adminMemberOneDetail.go";
    	   frm.method = "post";
    	   frm.submit();
    	 
     });
    
    
});//end of function()

function goSearch() {
	
	const searchType = $('select[name="searchType"]').val();
	
	if(searchType == "") {
		alert("검색대상을 선택하세요!!");
		return; // goSearch() 함수를 종료한다.
	}
	
	const frm =document.member_search_frm;
	//frm.action ="memberList.up";
	//frm.method ="get"; //form태그에 method를 명기하지 않으면 "get"방식이다.
	frm.submit();
}//end of function goSearch()---

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
    <h4 class="mb-0" >회원 조회</h4>
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
                    회원조회
                </li>

            </ol>

        </nav>

    </div>


    <!-- 실제 페이지 작업 영역 -->
    <div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">
<div class="container-fluid width" style="width:70%">
<h2 class="text-center mb-5">회원전체 목록</h2>

<form name="member_search_frm">
  <select name="searchType">
    <option value="">검색대상</option>  
      <option value="company">사명</option>  
        <option value="userid">아이디</option>
          <option value="email">이메일</option>    
  </select>
  &nbsp;
  
  <input type="text" name="searchWord" />
  
        <%--
             form 태그내에서 데이터를 전송해야 할 input 태그가 만약에 1개 밖에 없을 경우에는
             input 태그내에 값을 넣고나서 그냥 엔터를 해버리면 submit 되어져 버린다.
             그래서 유효성 검사를 거친후 submit 을 하고 싶어도 input 태그가 만약에 1개 밖에 없을 경우라면 
             유효성검사가 있더라도 유효성검사를 거치지 않고 바로 submit 되어진다. 
             이것을 막으려면 input 태그가 2개 이상 존재하면 된다.  
             그런데 실제 화면에 보여질 input 태그는 1개 이어야 한다.
             이럴 경우 아래와 같이 해주면 된다. 
             또한 form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.   
        --%>
   <input type="text" style="display:none;" />
  <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
  <div style="margin-left: auto;">
  <span style="font-size: 12pt; font-weight: bold;">페이지당 회원명수&nbsp;-&nbsp;</span>
  <select name="sizePerPage">
         <option value="10">10명</option>
         <option value="5">5명</option>
         <option value="3">3명</option>      
      </select>
      </div>
</form>

<table class="table table-bordered table-hover" id="memberTbl">
      <thead>
          <tr>
             <th >번호</th>
             <th >아이디</th>
             <th >사명</th>
             <th >이메일</th>
             <!--<th class="table-success">성별</th>  -->
          </tr>
      </thead>
      
      <tbody>
       <c:if test="${not empty requestScope.memberList}">
         <c:forEach var="memberdto" items="${requestScope.memberList}" varStatus="status">
          <tr class="memberInfo">
          <td>${(requestScope.totalMemberCount) -(requestScope.pageNo - 1) * requestScope.sizePerPage -(status.index)}</td>
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
           <td class="userid">${memberdto.userid}</td>
           <td>${memberdto.company}</td>
           <td>${memberdto.email}</td>
           
           
          
          </tr>
         </c:forEach>
       </c:if>
       
       <c:if test="${empty requestScope.memberList}">
          <tr>
            <td colspan="5">데이터가 존재하지 않습니다.</td>
          </tr>
       </c:if>
       
      </tbody>
      
      </table>
      
      <div id="pageBar">
      <nav>
      <ul class="pagination">
      ${requestScope.pageBar}
      </ul>
      </nav>
      </div>
</div>
</div>
</div>

<form name="adminMemberOneDetail_frm">
 <input type="hidden" name="userid"/>
</form>


<jsp:include page="adminFooter.jsp" />