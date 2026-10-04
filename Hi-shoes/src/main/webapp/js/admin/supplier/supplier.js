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
            success: function () {
                alert("공급업체가 등록되었습니다.");
                bootstrap.Modal.getInstance(document.getElementById("supplyModal")).hide();
                // 목록 갱신 함수가 있다면 여기서 호출 (예: loadSupplyList();)
            },
			error: function(request, status, error){
				alert("code: " + request.status +"\nmessage: " + request.responseText +"\nerror: " + error);
			}
        });
    });

});