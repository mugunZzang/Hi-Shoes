<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../adminHeader.jsp"/>

<script type="text/javascript">







function goSearch(frm, type) {

    const searchType = $(frm).find('select[name="searchType"]').val();

    if(searchType == "") {
        alert("검색대상을 선택하세요!!");
        return;
    }

    // 어떤 탭에서 검색했는지 전달
    $(frm).append('<input type="hidden" name="tab" value="' + type + '">');

    frm.submit();
}// end of function goSearch(frm, type)
</script>

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

<div class="tab-content" style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">

    <!-- 공지사항 -->
    <div class="tab-pane fade show active" id="notice">
    	<form name="notice_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="subject">글제목</option>
				<option value="content">글내용</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form,'notice')">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 20%;">글제목</th>
                <th style="width: 50%;">글내용</th>
                <th style="width: 20%;">작성일</th>
            </tr>
          </thead>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
            </tr>
        </table>
        <div class="text-end">
        	<button type="button" style="border-radius: 10px; font-size: 16px; padding: 6px 10px;">글작성</button>
        </div>
    </div>

    <!-- FAQ -->
    <div class="tab-pane fade" id="faq">
    	<form name="faq_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="subject">글제목</option>
				<option value="content">글내용</option>
				<option value="category">카테고리</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form,'faq')">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 20%;">글제목</th>
                <th style="width: 50%;">글내용</th>
                <th style="width: 20%;">카테고리</th>
            </tr>
          </thead>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
            </tr>
        </table>
        
        <div class="text-end">
        	<button type="button" style="border-radius: 10px; font-size: 16px; padding: 6px 10px;">글작성</button>
        </div>
        
    </div>

    <!-- 문의사항 -->
    <div class="tab-pane fade" id="question">
    	<form name="question_search_frm">
			<select name="searchType">
				<option value="">검색대상</option>
				<option value="pname">판매상품명</option>
				<option value="userid">아이디</option>
			</select>
			&nbsp;
			
			<input type="text" name="searchWord"/>
			
			<button type="button" class="btn btn-secondary" onclick="goSearch(this.form,'qestion')">검색</button>
		</form>
        <table class="table" style="width: 100%;">
          <thead class="table-primary">
            <tr>
                <th style="width: 10%;">글번호</th>
                <th style="width: 10%;">판매상품명</th>
                <th style="width: 10%;">아이디</th>
                <th style="width: 50%;">내용</th>
                <th style="width: 20%;">작성일자</th>
            </tr>
          </thead>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
                <td>5</td>
            </tr>
        </table>
    </div>
</div>


<jsp:include page="../adminFooter.jsp"/>