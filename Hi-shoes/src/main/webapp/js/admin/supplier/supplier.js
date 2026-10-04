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
        const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("supplyModal"));
        modal.show();
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
/*
		console.log("~~~ 확인용 supplyName:", $("#supplyName").val().trim());
		console.log("~~~ 확인용 bizNo:", $("#bizNo").val().trim());
		console.log("~~~ 확인용 ceoName:",  $("#ceoName").val().trim());
		console.log("~~~ 확인용 supplyTel:", $("#supplyTel").val().trim());
		console.log("~~~ 확인용 supplyEmail:", $("#supplyEmail").val().trim());
		~~~ 확인용 supplyName: 테스트
		~~~ 확인용 bizNo: 1239128421
		~~~ 확인용 ceoName: 테스트
		~~~ 확인용 supplyTel: 91283712983
		~~~ 확인용 supplyEmail: test@companby.com
*/	
        $.ajax({
            url: `${ctx_Path}/admin/supplier/supplierRegister.go`  ,    
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
				}
            },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}
        });
    });

	
	
});  // end of $(function(){})---------------------------------------------------



// 공급업체 목록 조회 (최초 로딩 / 페이지 이동 / 등록 후 갱신 공용)
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
                                "<span style='color:red; font-weight:bold;'>등록된 공급업체가 없습니다.</span>" +
                            "</td>" +
                        "</tr>";
            }
            else {
                $.each(json.supplierList, function (index, item) {
					
					console.log("~~~ 확인용 item.supplyName: ", item.supplyName);
					console.log("~~~ 확인용 formatBizNo(item.bizNo): ", formatBizNo(item.bizNo));
					console.log("~~~ 확인용 item.ceoName: ", item.ceoName);
					console.log("~~~ 확인용 item.supplyTel: ", item.supplyTel);
					console.log("~~~ 확인용 item.supplyEmail: ", item.supplyEmail);
                    html += "<tr>" +
                                "<td class='text-center align-middle'>" + item.supplyName + "</td>" +
                                "<td class='text-center align-middle'>" + formatBizNo(item.bizNo) + "</td>" +
                                "<td class='text-center align-middle'>" + item.ceoName + "</td>" +
                                "<td class='text-center align-middle'>" + item.supplyTel + "</td>" +
                                "<td class='text-center align-middle'>" + item.supplyEmail + "</td>" +
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