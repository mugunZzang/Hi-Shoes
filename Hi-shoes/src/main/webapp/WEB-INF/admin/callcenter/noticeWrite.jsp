<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../adminHeader.jsp"/>


<style>

/* 전체 페이지 */
.notice-write-container {
    position: relative;
    top: 90px;
    padding: 0 7%;
    padding-bottom: 100px;
}


/* 페이지 제목 영역 */
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    padding: 20px 0;
    margin-bottom: 15px;
}

.page-title {
    margin: 0;
    font-size: 24px;
    font-weight: 600;
}


/* 글작성 박스 */
.notice-write-box {
    background-color: #fff;

    width: 100%;

    border: 1px solid #e5e5e5;
    border-radius: 4px;

    padding: 35px 40px;

    min-height: 550px;
}


/* 입력 테이블 */
.notice-write-table {
    width: 100%;
    border-collapse: collapse;
}


/* 각 행 */
.notice-write-table tr {
    border-bottom: 1px solid #eeeeee;
}


/* 마지막 행 */
.notice-write-table tr:last-child {
    border-bottom: none;
}


/* 제목 */
.notice-write-table th {
    width: 150px;

    background-color: #f8f9fa;

    padding: 18px;

    font-size: 15px;
    font-weight: 600;

    text-align: center;

    vertical-align: middle;
}


/* 입력 영역 */
.notice-write-table td {
    padding: 15px 20px;
}


/* 제목 input */
.notice-title-input {
    width: 100%;
    max-width: 900px;

    height: 42px;

    border: 1px solid #ced4da;
    border-radius: 4px;

    padding: 0 12px;

    font-size: 14px;

    outline: none;
}

.notice-title-input:focus {
    border-color: #86b7fe;
    box-shadow: 0 0 0 0.15rem rgba(13, 110, 253, .15);
}


/* 내용 textarea */
.notice-content-input {
    width: 100%;
    max-width: 900px;

    height: 400px;

    resize: vertical;

    border: 1px solid #ced4da;
    border-radius: 4px;

    padding: 12px;

    font-size: 14px;

    outline: none;
}

.notice-content-input:focus {
    border-color: #86b7fe;
    box-shadow: 0 0 0 0.15rem rgba(13, 110, 253, .15);
}


/* 파일 */
.notice-file-input {
    font-size: 14px;
}


/* 버튼 영역 */
.notice-button-area {
    text-align: center;

    padding-top: 30px;
}


/* 작성 버튼 */
.notice-submit-btn {
    min-width: 120px;

    padding: 10px 25px;

    font-size: 15px;
}


/* 취소 버튼 */
.notice-cancel-btn {
    min-width: 120px;

    padding: 10px 25px;

    margin-left: 8px;

    font-size: 15px;
}


/* 모바일 */
@media (max-width: 768px) {

    .notice-write-container {
        padding: 0 3%;
    }

    .notice-write-box {
        padding: 20px;
    }

    .notice-write-table th {
        width: 90px;
        font-size: 14px;
    }

    .notice-write-table td {
        padding: 10px;
    }

    .page-title {
        font-size: 20px;
    }

}

</style>


<script type="text/javascript">

    function goWriter() {

        const frm = document.noticeWriteFrm;

        // 제목 검사
        const subject = frm.nsubject.value.trim();

        if(subject === "") {

            alert("글제목을 입력해주세요.");

            frm.nsubject.focus();

            return;
        }


        // 내용 검사
        const contents = frm.ncontents.value.trim();

        if(contents === "") {

            alert("글내용을 입력해주세요.");

            frm.ncontents.focus();

            return;
        }


        // 제출
        frm.submit();
    }


    // 취소
    function goBack() {

        location.href = "<%= ctxPath %>/admin/callcenter/callcenter.go";

    }

</script>


<div class="notice-write-container">


    <!-- 페이지 제목 + 브레드크럼 -->

    <div class="page-header">

        <p class="page-title">
            공지사항 글작성
        </p>


        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="<%= ctxPath %>/admin/callcenter/callcenter.go"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item">
                    고객센터
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    공지사항 글작성
                </li>

            </ol>

        </nav>

    </div>


    <!-- 글작성 영역 -->

    <div class="notice-write-box">


        <form name="noticeWriteFrm"
              method="post"
              action=""
              enctype="multipart/form-data">


            <table class="notice-write-table">

                <tbody>


                    <!-- 제목 -->

                    <tr>

                        <th>
                            글제목
                        </th>

                        <td>

                            <input type="text"
                                   name="nsubject"
                                   class="notice-title-input"
                                   maxlength="100"
                                   placeholder="공지사항 제목을 입력해주세요.">

                        </td>

                    </tr>


                    <!-- 내용 -->

                    <tr>

                        <th style="vertical-align: top; padding-top: 25px;">
                            글내용
                        </th>

                        <td>

                            <textarea name="ncontents"
                                      class="notice-content-input"
                                      maxlength="1000"
                                      placeholder="공지사항 내용을 입력해주세요."></textarea>

                        </td>

                    </tr>


                    <!-- 이미지 -->

                    <tr>

                        <th>
                            이미지
                        </th>

                        <td>

                            <input type="file"
                                   name="image"
                                   class="notice-file-input"
                                   accept="image/*">

                        </td>

                    </tr>


                </tbody>

            </table>


            <!-- 버튼 -->

            <div class="notice-button-area">

                <button type="button"
                        class="btn btn-secondary notice-submit-btn"
                        onclick="goWriter()">

                    작성하기

                </button>


                <button type="button"
                        class="btn btn-outline-danger notice-cancel-btn"
                        onclick="goBack()">

                    취소

                </button>

            </div>


        </form>

    </div>


</div>


<jsp:include page="../adminFooter.jsp"/>