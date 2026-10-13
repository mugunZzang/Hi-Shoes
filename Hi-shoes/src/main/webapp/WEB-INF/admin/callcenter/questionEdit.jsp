<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>
<jsp:include page="../adminHeader.jsp"/>

<script type="text/javascript">


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

        <p class="mb-0 fs-4 fw-semibold">
            문의사항 답글작성
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        HOME
                    </a>
                </li>

                <li class="breadcrumb-item">
                    <a href="<%= ctxPath %>/admin/callcenter/callcenter.go"
                       class="text-decoration-none">
                        고객센터
                    </a>
                </li>
                
                 <li class="breadcrumb-item active">
                    문의사항 답글작성
                </li>

            </ol>

        </nav>

    </div>


    <!-- 문의 내용 -->
    <div class="card">

        <div class="card-header">
            문의 내용
        </div>

        <div class="card-body">

            <table class="table">

                <tr>
                    <th style="width:15%;">작성자</th>
                    <td>${requestScope.question.fk_userid}</td>
                </tr>

                <tr>
                    <th>판매상품</th>
                    <td>${requestScope.question.fk_pname}</td>
                </tr>

                <tr>
                    <th>작성일</th>
                    <td>${requestScope.question.qwritedate}</td>
                </tr>

                <tr>
                    <th>문의내용</th>
                    <td style="white-space:pre-wrap;">
                        ${requestScope.question.qcontents}
                    </td>
                </tr>

            </table>

        </div>
   </div>

    


    <!-- 관리자 답글 -->
    <div class="card mt-4">

        <div class="card-header">
            관리자 답글
        </div>

        <div class="card-body">

            <form method="post">

                <!-- 어떤 문의에 답변하는지 구분 -->
                <input type="hidden"
                       name="qnum"
                       value="${requestScope.question.qnanum}">

                <textarea name="qanswer"
			          class="form-control"
			          rows="7"
			          placeholder="답변 내용을 입력해주세요."
			          required></textarea>

                <div class="text-end mt-3">

                    <button type="submit"
                            class="btn btn-secondary">
                        답글 등록
                    </button>

                    <button type="button"
                            class="btn btn-outline-danger"
                            onclick="history.back();">
                        목록으로
                    </button>

                </div>

            </form>

        </div>

    </div>

</div>



<jsp:include page="../adminFooter.jsp"/>