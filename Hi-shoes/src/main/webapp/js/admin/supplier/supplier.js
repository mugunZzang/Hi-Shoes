$(function () {

    // 검증 규칙
    const RULES = {
        supplyName:  { regex: /^[가-힣a-zA-Z()\s]{1,100}$/,      },
        bizNo:       { regex: /^\d{10}$/,                          },
        ceoName:     { regex: /^[가-힣a-zA-Z]{1,10}$/,            },
        supplyTel:   { regex: /^\d{9,11}$/,                        },
        supplyEmail: { regex: /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/ }
    };

    // 단일 필드 검증
    function validateField(id) {
        const $el = $("#" + id);
        const val = $el.val().trim();
        const ok  = RULES[id].regex.test(val);
        $el.toggleClass("is-invalid", !ok).toggleClass("is-valid", ok);
        return ok;
    }

    // 전체 검증
    function validateAll() {
        let allOk = true;
        Object.keys(RULES).forEach(id => {
            if (!validateField(id)) allOk = false;
        });
        return allOk;
    }

    // 폼 초기화
    function resetForm() {
        $("#supplyForm")[0].reset();
        $("#supplyForm .form-control").removeClass("is-invalid is-valid");
    }

    // 공급업체 등록 버튼 클릭 시
    $(document).on("click", "#supplyRegisterModal", function () {
        resetForm();
		$('#supplyModal').modal('show');
    });

    // 숫자 전용 필드: 숫자 외 문자 즉시 제거
    $(document).on("input", "#bizNo, #supplyTel", function () {
        this.value = this.value.replace(/\D/g, "");
    });

    // 실시간 검증 (한글 IME 조합 중에는 건너뜀)
    $(document).on("input", "#supplyName, #bizNo, #ceoName, #supplyTel, #supplyEmail", function (e) {
        if (e.originalEvent && e.originalEvent.isComposing) return;
        validateField(this.id);
    });
    $(document).on("compositionend", "#supplyName, #ceoName", function () {
        validateField(this.id);
    });

    // 등록 버튼 클릭
    $(document).on("click", "#btnSupplySave", function () {
        if (!validateAll()) {
            $("#supplyForm .is-invalid").first().focus();
            return;
        }

        const $btn = $(this);
        $btn.prop("disabled", true);      // 중복 등록 방지

        $.ajax({
            url: `${ctx_Path}/admin/supplier/supplierRegister.go`,
            type: "post",
			data: {
				"supplyName":  $("#supplyName").val().trim(),
				"bizNo":       $("#bizNo").val().trim(),
				"ceoName":     $("#ceoName").val().trim(),
				"supplyTel":   $("#supplyTel").val().trim(),
				"supplyEmail": $("#supplyEmail").val().trim()
			},
			dataType: "json",
            success: function (json) {
				if (json.result == 1) {
				    alert("공급업체가 등록되었습니다.");
				    location.reload();      // GET으로 목록을 다시 불러옴
				} else {
				    alert("등록에 실패했습니다.");
				    $btn.prop("disabled", false);
				}
            },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
				$btn.prop("disabled", false);
			}
        });
    });
	
	
	
	// =================== 공급업체 삭제 =================================
	$(document).on("click", ".btn-delete", function(){

	    const row = $(this).closest("tr");

	    const supname = row.find("td").eq(0).text().trim();

	    // 실수로 지우는 일을 막기 위한 확인창 (카테고리 삭제와 동일한 방식)
	    if(!confirm("'" + supname + "' 를 삭제하시겠습니까?")){
	        return;
	    }

	    $.ajax({
			url: ctx_Path + "/admin/supplier/supplierDelete.go",
			type: "post",
			data: {
				"supname": supname
			},
			dataType: "json",
			success: function(json){
				
				if(json.result == 1){
					
					alert(supname + "이 삭제되었습니다.");
					// 삭제된 행을 화면에서 제거
					row.remove();

					// 남은 행이 없으면 빈 목록 안내 표시
					if($("#supplyTbl tbody tr").length === 0){
						$("#supplyTbl tbody").html(`
						    <tr>
						        <td colspan="5" class="text-center align-middle">
						            <div class="mx-empty">
						                <i class="bi bi-inbox"></i>
						                <p>등록된 공급업체가 없습니다.</p>
						            </div>
						        </td>
						    </tr>
						`);
					}
					
				}
				
			},
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}
	    
	    });

	});
	
	// =================== 공급업체 삭제 끝 =================================
	
	
	

});  // end of $(function(){})---------------------------------------------------


// 화면에 문자열로 끼워 넣는 값 이스케이프
function escHtml(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");
}

// 공급업체 목록 조회 (필요할 때 호출: 페이지 이동 / 등록 후 갱신 등)
// 표시 모양은 adminsupplierList.jsp 의 서버 렌더링과 같게 맞춘다.
function loadSupplyList() {

    $.ajax({
        url: ctx_Path + "/admin/supplier/supplierList.go",
        method: "get",
		data: { "isAjax": "1" },
        dataType: "json",
        success: function (json) {

            let html = "";

            if (json.supplierList.length === 0) {
                html += "<tr>" +
                            "<td colspan='5' class='text-center align-middle'>" +
                                "<div class='mx-empty'>" +
                                    "<i class='bi bi-inbox'></i>" +
                                    "<p>등록된 공급업체가 없습니다.</p>" +
                                "</div>" +
                            "</td>" +
                        "</tr>";
            }
            else {
                $.each(json.supplierList, function (index, item) {
                    html += "<tr>" +
                                "<td class='text-center align-middle mx-strong'>" + escHtml(item.supplyName) + "</td>" +
                                "<td class='text-center align-middle mx-num'>" + formatBizNo(item.bizNo) + "</td>" +
                                "<td class='text-center align-middle'>" + escHtml(item.ceoName) + "</td>" +
                                "<td class='text-center align-middle mx-num'>" + formatTel(item.supplyTel) + "</td>" +
                                "<td class='text-center align-middle'>" + escHtml(item.supplyEmail) + "</td>" +
                            "</tr>";
                });
            }

            $("#supplyTbl tbody").html(html);
        },

        error: function (request, status, error) {
            alert("code: " + request.status +
                  "\nmessage: " + request.responseText +
                  "\nerror: " + error);
        }
    });
}

// 사업자등록번호 표시용 (1234567890 → 123-45-67890)
function formatBizNo(no) {
    if (!no || no.length !== 10) return no;
    return no.replace(/(\d{3})(\d{2})(\d{5})/, "$1-$2-$3");
}

// 연락처 표시용 (JSP 의 하이픈 규칙과 동일)
//   11자리 → 010-1234-5678 / 10자리 02 시작 → 02-1234-5678 / 그 외 10자리 → 031-123-4567
function formatTel(tel) {
    if (!tel) return "";
    if (tel.length === 11) return tel.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    if (tel.length === 10) {
        return tel.startsWith("02")
            ? tel.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3")
            : tel.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
    }
    return tel;
}
