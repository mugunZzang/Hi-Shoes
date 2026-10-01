<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            공지사항 글작성
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    공지사항
                </li>
                
                <li class="breadcrumb-item active"
                    aria-current="page">
                    글작성
                </li>

            </ol>

        </nav>

    </div>

	<div style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">
                
                <div class="col-md-12">
      <form name="noticeWriteFrm">
          <table style="width: 100%; border-collapse: separate; border-spacing: 20px 5px;">
             <tbody>
                
                <tr>
                    <td style="font-size:16pt;">글제목</td>
                    <td>
                       <input type="text" name="nsubject"  style="width: 800px; height: 40px;" maxlength="100" class="requiredInfo" />
                       <span class="error text-danger">글제목은 필수입력 사항입니다.</span>
                    </td>
                </tr>
                
                <tr>
                    <td class="align-top" style="font-size:16pt;">글내용</td>
                    <td>
                       <textarea name="contents"
                          style="width: 800px; height: 500px;"
                          maxlength="1000"></textarea>
                    </td>
                </tr>
                
                <tr>
                    <td style="font-size:16pt;" >이미지</td>
                    <td>
                       <input type="file" name="image" accept="image/*">
                    </td>
                </tr>

                <tr>
                    <td colspan="2" class="text-center">
                       <input type="button" class="btn btn-secondary btn-lg me-5" value="작성하기" onclick="goRegister()" />
                    </td>
                </tr>
                 
             </tbody>
          </table>
        
          </form>
   		</div>
     </div>
</div>


<jsp:include page="../adminFooter.jsp"/>