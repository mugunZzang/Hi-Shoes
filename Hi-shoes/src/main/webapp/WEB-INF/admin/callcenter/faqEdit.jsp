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
.faq-write-container {
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
.faq-write-box {
    width: 100%;
    min-height: 550px;
    background-color: #fff;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
    padding: 35px 40px;
}

/* 입력 테이블 */
.faq-write-table {
    width: 100%;
    border-collapse: collapse;
}

/* 행 */
.faq-write-table tr {
    border-bottom: 1px solid #eeeeee;
}

/* 마지막 행 */
.faq-write-table tr:last-child {
    border-bottom: none;
}

/* 왼쪽 제목 */
.faq-write-table th {
    width: 150px;
    padding: 18px;
    background-color: #f8f9fa;
    font-size: 15px;
    font-weight: 600;
    text-align: center;
    vertical-align: middle;
}

/* 오른쪽 입력 */
.faq-write-table td {
    padding: 15px 20px;
}

/* 공통 입력 */
.faq-input {
    width: 100%;
    max-width: 900px;
    height: 42px;
    padding: 0 12px;
    border: 1px solid #ced4da;
    border-radius: 4px;
    font-size: 14px;
    outline: none;
}

.faq-input:focus,
.faq-select:focus,
.faq-content-input:focus {
    border-color: #86b7fe;
    box-shadow:
        0 0 0 0.15rem rgba(13, 110, 253, .15);
}

/* 카테고리 */
.faq-select {
    width: 220px;
    height: 42px;
    padding: 0 12px;
    border: 1px solid #ced4da;
    border-radius: 4px;
    font-size: 14px;
    outline: none;
    background-color: #fff;
}

/* 내용 */
.faq-content-input {
    width: 100%;
    max-width: 900px;
    height: 400px;
    padding: 12px;
    resize: vertical;
    border: 1px solid #ced4da;
    border-radius: 4px;
    font-size: 14px;
    outline: none;
}

/* 버튼 영역 */
.faq-button-area {
    padding-top: 30px;
    text-align: center;
}

/* 수정 버튼 */
.faq-submit-btn {
    min-width: 120px;
    padding: 10px 25px;
    font-size: 15px;
}

/* 취소 버튼 */
.faq-cancel-btn {
    min-width: 120px;
    padding: 10px 25px;
    margin-left: 8px;
    font-size: 15px;
}

/* 모바일 */
@media (max-width: 768px) {

    .faq-write-container {
        padding: 0 3%;
    }

    .faq-write-box {
        padding: 20px;
    }

    .faq-write-table th {
        width: 90px;
        font-size: 14px;
    }

    .faq-write-table td {
        padding: 10px;
    }

    .page-title {
        font-size: 20px;
    }

}

</style>


<script type="text/javascript">

    // =========================================================
    // FAQ 수정
    // =========================================================
    function goEdit() {

        const frm = document.faqEditFrm;


        // =====================================================
        // 카테고리 검사
        // =====================================================
        if(frm.category.value === "") {

            alert("카테고리를 선택해주세요.");

            frm.category.focus();

            return;
        }


        // =====================================================
        // 제목 검사
        // =====================================================
        const subject =
            frm.fsubject.value.trim();

        if(subject === "") {

            alert("글제목을 입력해주세요.");

            frm.fsubject.focus();

            return;
        }


        // =====================================================
        // 내용 검사
        // =====================================================
        const contents =
            frm.fcontents.value.trim();

        if(contents === "") {

            alert("글내용을 입력해주세요.");

            frm.fcontents.focus();

            return;
        }


        // =====================================================
        // 수정 제출
        // =====================================================
        frm.method = "post";

        frm.submit();

    }


    // =========================================================
    // 취소
    // =========================================================
    function goBack() {

        location.href =
            "<%= ctxPath %>/admin/callcenter/callcenter.go?tab=faq";

    }

</script>


<div class="faq-write-container">


    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="page-header">

        <p class="page-title">
            FAQ 수정
        </p>


        <nav style="--bs-breadcrumb-divider: '>'; "
             aria-label="breadcrumb">

            <ol class="breadcrumb mb-0">


                <li class="breadcrumb-item">

                    <a href="<%= ctxPath %>/admin/callcenter/callcenter.go?tab=faq"
                       class="text-decoration-none">

                        Home

                    </a>

                </li>


                <li class="breadcrumb-item">

                    고객센터

                </li>


                <li class="breadcrumb-item active"
                    aria-current="page">

                    FAQ 수정

                </li>


            </ol>

        </nav>

    </div>



    <!-- FAQ 수정 영역 -->
    <div class="faq-write-box">


        <form name="faqEditFrm"
              method="post"
              action="<%= ctxPath %>/admin/callcenter/faqEdit.go">


            <!-- FAQ 번호 -->
            <input type="hidden"
                   name="fnum"
                   value="${requestScope.fdto.fnum}">


            <table class="faq-write-table">


                <tbody>


                    <!-- 카테고리 -->
                    <tr>

                        <th>
                            카테고리
                        </th>


                        <td>

                            <select name="category"
                                    class="faq-select">


                                <option value="">
                                    카테고리 선택
                                </option>


                                <option value="가입/탈퇴"
                                    ${requestScope.fdto.fcategory == '가입/탈퇴' ? 'selected' : ''}>
                                    가입/탈퇴
                                </option>


                                <option value="정보변경"
                                    ${requestScope.fdto.fcategory == '정보변경' ? 'selected' : ''}>
                                    정보변경
                                </option>


                                <option value="결제"
                                    ${requestScope.fdto.fcategory == '결제' ? 'selected' : ''}>
                                    결제
                                </option>


                                <option value="주문"
                                    ${requestScope.fdto.fcategory == '주문' ? 'selected' : ''}>
                                    주문
                                </option>


                                <option value="취소"
                                    ${requestScope.fdto.fcategory == '취소' ? 'selected' : ''}>
                                    취소
                                </option>


                                <option value="상품정보"
                                    ${requestScope.fdto.fcategory == '상품정보' ? 'selected' : ''}>
                                    상품정보
                                </option>


                                <option value="배송"
                                    ${requestScope.fdto.fcategory == '배송' ? 'selected' : ''}>
                                    배송
                                </option>


                            </select>

                        </td>

                    </tr>



                    <!-- 제목 -->
                    <tr>

                        <th>
                            글제목
                        </th>


                        <td>

                            <input type="text"
                                   name="fsubject"
                                   class="faq-input"
                                   maxlength="100"
                                   placeholder="FAQ 제목을 입력해주세요."
                                   value="${requestScope.fdto.fsubject}">

                        </td>

                    </tr>



                    <!-- 내용 -->
                    <tr>

                        <th style="vertical-align: top; padding-top: 25px;">

                            글내용

                        </th>


                        <td>

                            <textarea name="fcontents"
                                      class="faq-content-input"
                                      maxlength="1000"
                                      placeholder="FAQ 내용을 입력해주세요.">${requestScope.fdto.fcontents}</textarea>

                        </td>

                    </tr>


                </tbody>

            </table>



            <!-- 버튼 -->
            <div class="faq-button-area">


                <button type="button"
                        class="btn btn-primary faq-submit-btn"
                        onclick="goEdit()">

                    수정하기

                </button>


                <button type="button"
                        class="btn btn-secondary faq-cancel-btn"
                        onclick="goBack()">

                    취소

                </button>


            </div>


        </form>


    </div>


</div>


<jsp:include page="../adminFooter.jsp"/>