<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
<%
    String ctx_Path = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/member/mypage.css" />


<style type="text/css">
	/* =========================
	   상품 목록
	========================= */
	
	
	.product-list {
	
	    margin-top: 10px;
	
	    display: grid;
	
	    /*
	        1202px 전체를
	        4개의 상품 카드가 사용
	
	        카드 사이 간격 = 8px
	
	        (1202 - 8 - 8 - 8) / 4
	        = 294.5px
	    */
	    grid-template-columns: repeat(4, 1fr);
	
	    column-gap: 8px;
	
	    row-gap: 62px;
	
	    padding-top: 20px;
	
	    border-top: 2px solid #222;
	}
	
	
	/* =========================
	   상품 카드
	========================= */
	
	.product-item {
	    width: 100%;
	    position: relative;  
	    min-width: 0;
	}
	
	
	/* 상품 전체 링크 */
	.product-link {
	    display: block;
	
	    width: 100%;
	}
	
	/* 체크박스 (이미지 좌측 상단) */
	.wish-check {
	    position: absolute;
	    top: 10px;
	    left: 10px;
	    z-index: 2;            /* 이미지 위에 올라오도록 */
	    width: 20px;
	    height: 20px;
	    margin: 0;
	    cursor: pointer;
	    accent-color: #222;    /* 체크 색상 */
	}
	
	#allwishListStart {
		width: 20px;
	    height: 20px;
	    margin: 0;
	    cursor: pointer;
	    accent-color: #222;    /* 체크 색상 */
	}
	
	
	/* 상단 삭제 버튼 영역 */
	.wish-toolbar {
	    display: flex;
	    align-items: center;
	    gap: 6px;
	    margin-top: 15px;
	}
	
	.wish-toolbar label {
	    font-size: 12pt;
	    cursor: pointer;
	    line-height: 1;
    	cursor: pointer;
    	user-select: none;
	}
	
	.wish-toolbar button {
		margin-left: auto;
	    padding: 4px 14px;
	    border: 1px solid #222;
	    background: #fff;
	    cursor: pointer;
	}
	/* =========================
	   상품 이미지
	========================= */
	
	.product-image {
	    width: 100%;
	    height: 294px;
	
	    margin-bottom: 14px;
	
	    display: flex;
	
	    align-items: center;
	    justify-content: center;
	
	    background-color: #f4f4f4;
	
	    overflow: hidden;
	}
	
	
	/* 실제 이미지 */
	.product-image img {
	    width: 100%;
	    height: 100%;
	
	    object-fit: contain;
	
	    transition: transform 0.2s ease;
	}
	
	
	
	
	/* =========================
	   상품 정보
	========================= */
	
 	.wish-product-info { 
	    width: 100%;
	}
	
	
	/* =========================
	   브랜드
	========================= */
	
	.product-brand {
	    margin-bottom: 5px;
	
	    font-size: 12px;
	
	    font-weight: 700;
	
	    line-height: 1.3;
	}
	
	
	/* =========================
	   상품명
	========================= */
	
	.product-name {
	    width: 100%;
	    min-height: 17px;
	
	    display: flex;
	
	    align-items: center;
	
	    gap: 5px;
	
	    color: #666;
	
	    font-size: 11px;
	
	    line-height: 1.4;
	
	    white-space: nowrap;
	
	    overflow: hidden;
	
	    text-overflow: ellipsis;
	}
	
	
	/* 상품명 */
	
	.product-title {
	    min-width: 0;
	
	    overflow: hidden;
	
	    text-overflow: ellipsis;
	
	    white-space: nowrap;
	}
	
	
	
	
	/* =========================
	   가격
	========================= */
	
	/* 기존 가격 */
	
	.product-price del {
	    margin-right: 3px;
	
	    color: #aaa;
	
	    font-size: 10px;
	
	    text-decoration: line-through;
	}
	
	
	/* 원 */
	
	.product-price > span {
	    font-size: 10px;
	}
	
	
	/* 할인율 */
	
	.product-price em {
	    margin-left: 2px;
	
	    color: #f00000;
	
	    font-size: 10px;
	
	    font-style: normal;
	}
	
	
	
	
	
	
	/* 가격 영역 */
	.product-price {
	    min-height: 22px;
	    margin-top: 12px;
	    display: flex;
	    align-items: baseline;
	    gap: 5px;
	    white-space: nowrap;
	}
	
	/* 가격 전체의 기본 스타일 */
	.product-price strong {
	    font-size: 14px;
	    font-weight: 700;
	}
	
	/* 정가: 작은 글씨 + 취소선 */
	.product-price .ref-price {
	    display: inline-block;
	    font-size: 11px;
	    font-weight: 400;
	    color: #888;
	    text-decoration: line-through;
	    text-decoration-line: line-through;
	    text-decoration-color: #888;
	    margin-right: 3px;
	    vertical-align: baseline;
	}
	
	/* 할인가: 빨간색 + 굵은 글씨 */
	.product-price .sale-price {
	    display: inline-block;
	    font-size: 14px;
	    font-weight: 700;
	    color: #f00000;
	}
	
	/* 할인 없는 일반 가격 */
	.product-price .normal-price {
	    display: inline-block;
	    font-size: 14px;
	    font-weight: 700;
	    color: #222;
	}

	/* 서브타이틀*/
	#subtitle {
/*		border: 1px solid black;*/
		padding-top: 16px;
	}
	
</style>
<script type="text/javascript">
    let wishCurrentPage = 1;   // 현재 보고 있는 페이지

    $(function(){
        goWishList(1);   // 첫 진입

        // 페이지바 클릭 
        $(document).on("click", "#wishPageBar a.page-link", function(e){
            e.preventDefault();
            goWishList($(this).data("page"));
        });

        // 개별 체크 → 전체선택 체크박스 변경
        $(document).on("change", "input.wish-check", function(){
            const total   = $("input.wish-check").length;
            const checked = $("input.wish-check:checked").length;
            $("#allwishListStart").prop("checked", total > 0 && total === checked);
        });

        // 선택삭제
        $("#btnDeleteSelected").on("click", function(){
            if($("input.wish-check:checked").length === 0){
                alert("선택된 상품이 없습니다.");
                return;
            }
            if(!confirm("선택한 상품을 삭제하시겠습니까?")){
                return;
            }

            $.ajax({
                url: "<%= ctx_Path%>/member/wishListDelete.go",
                type: "post",
                data: $("input.wish-check:checked").serialize(),   // pnum=2&pnum=4...
                dataType: "json",
                success: function(json){
                    if(json.n > 0){
                        goWishList(wishCurrentPage);   // 현재 페이지 재조회 
                    }
                    else {
                        alert("삭제에 실패하였습니다.");
                    }
                },
                error: function(request, status, error){
                    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }
            });
        });
    }); // end of $(function(){})-------------------


    // 전체선택
    function allCheckStart() {
        const bool = $("#allwishListStart").prop("checked");
        $("input.wish-check").prop("checked", bool);
    }


    // 위시리스트 조회 (페이지 번호)
    function goWishList(pageNo) {
        $.ajax({
            url: "<%= ctx_Path%>/member/wishListJSON.go",
            type: "get",
            data: { "currentShowPageNo": pageNo },
            dataType: "json",
            success: function(json){
                if(json.isLogin === false){
                    alert("로그인이 필요한 서비스입니다.");
                    return;
                }
                wishCurrentPage = Number(json.currentShowPageNo);   
                renderWishList(json);
            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });
    }


    // 화면 그리기
    function renderWishList(json) {

        $("#allwishListStart").prop("checked", false);   // 페이지가 바뀌면 체크 상태 리셋

        const list = json.wishList;

        if(list.length === 0){
            $("#wishToolbar").hide();
            $("#wishListArea").html('<div class="product-empty"><span>상품이 없어요.</span></div>');
            $("#wishPageBar ul").empty();
            return;
        }

        $("#wishToolbar").css("display", "flex");

        let v_html = "";
        $.each(list, function(index, item){

            const pnum  = item.pnum;
            const img   = item.pimage1;
            const brand = item.brand;
            const pname = item.pname;

            const reg  = Number(item.regprice);
            const sale = Number(item.saleprice);

            let priceHtml = "";
            if(reg > sale){
                priceHtml = '<span class="ref-price">' + reg.toLocaleString("en") + '</span>'
                          + '<span class="sale-price">' + sale.toLocaleString("en") + ' 원</span>';
            }
            else {
                priceHtml = '<span class="normal-price">' + sale.toLocaleString("en") + ' 원</span>';
            }

            v_html += '<div class="product-item">'
                    +   '<input type="checkbox" class="wish-check custom_input" name="pnum" value="' + pnum + '">'
                    +   '<a href="<%= ctx_Path%>/shop/productDetail.go?pnum=' + pnum + '" class="product-link">'
                    +     '<div class="product-image">'
                    +       '<img src="<%= ctx_Path%>/images/' + img + '" alt="' + img + '" />'
                    +     '</div>'
                    +     '<div class="wish-product-info">'
                    +       '<div class="product-brand">' + brand + '</div>'
                    +       '<div class="product-name"><span class="product-title">' + pname + '</span></div>'
                    +       '<div class="product-price"><strong>' + priceHtml + '</strong></div>'
                    +     '</div>'
                    +   '</a>'
                    + '</div>';
        });

        $("#wishListArea").html(v_html);
        $("#wishPageBar ul").html(json.pageBar);
    }

</script>

<h4 id="subtitle">위시리스트</h4>

<div class="wish-toolbar" id="wishToolbar" style="display:none;">
    <input type="checkbox" id="allwishListStart" onclick="allCheckStart()">
    <label for="allwishListStart">전체선택</label>
    <button type="button" id="btnDeleteSelected"><span style="font-size: 10pt;">선택삭제</span></button>
</div>

<div class="product-list" id="wishListArea"></div>

<nav class="my-2">
    <div id="wishPageBar" style="margin-bottom:150px;">
        <ul class="pagination justify-content-center"></ul>
    </div>
</nav>
          