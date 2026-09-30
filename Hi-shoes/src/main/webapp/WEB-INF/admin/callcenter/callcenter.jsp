<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../adminHeader.jsp"/>

<div class="container-fluid" id="container" style="position: relative; top:90px; padding: 0% 7%; ">
    <div class="d-flex justify-content-between align-items-center my-5">
        <p class="mb-2 fs-3">고객센터</p>

        <nav style="--bs-breadcrumb-divider: '>';">
            <ol class="breadcrumb justify-content-end mb-0">
                <li class="breadcrumb-item">
                    <a href="#">Home</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    고객센터
                </li>
            </ol>
        </nav>
    </div>
<div>

<ul class="nav nav-tabs nav-fill">
        <li class="nav-item">
          <a class="nav-link" data-bs-toggle="tab" href="#notice">공지사항</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-bs-toggle="tab" href="#faq">FAQ</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-bs-toggle="tab" href="#question">문의사항</a>
        </li>
</ul>


<div class="tab-content">

    <!-- 공지사항 -->
    <div class="tab-pane fade show active" id="notice">
        <table class="table">
            <tr>
                <th>글번호</th>
                <th>글제목</th>
                <th>글내용</th>
                <th>작성일</th>
            </tr>
            <tr>
                <td>1</td>
                <td>공지사항입니다.</td>
                <td>2026-09-29</td>
            </tr>
        </table>
    </div>

    <!-- FAQ -->
    <div class="tab-pane fade" id="faq">
        <table class="table">
            <tr>
                <th>번호</th>
                <th>질문</th>
            </tr>
            <tr>
                <td>1</td>
                <td>자주 묻는 질문입니다.</td>
            </tr>
        </table>
    </div>

    <!-- 문의사항 -->
    <div class="tab-pane fade" id="question">
        <table class="table">
            <tr>
                <th>번호</th>
                <th>문의내용</th>
                <th>답변상태</th>
            </tr>
            <tr>
                <td>1</td>
                <td>문의사항입니다.</td>
                <td>답변완료</td>
            </tr>
        </table>
    </div>
</div>


<jsp:include page="../adminFooter.jsp"/>