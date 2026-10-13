$(function () {

	
    $("#btnSubmit").on("click", function () {

        const pwd = $("#loginPwd").val().trim();

        if (!pwd) {
            $("#errorMessage").text("비밀번호를 입력해주세요.");
            $("#loginPwd").focus();
            return;
        }

        $.ajax({
            url: $("#loginFrm").attr("action"),
            method: "POST",
            dataType: "json",
            data: {
                "pwd": pwd
            },

            success: function (json) {

                if (json.n == 1) {

										
                    // 비밀번호 일치: 인증 화면 숨기기
					$("#authenticationSection").hide().attr("style", "display: none !important;");

                    // 개인정보 수정 화면 표시
                    $("#memberEditSection").show();

                    $("#errorMessage").text("");
                    $("#loginPwd").val("");

                } else {

					
                    // 비밀번호 불일치
                    $("#errorMessage").text(
                        json.message || "비밀번호가 일치하지 않습니다."
                    );

                    $("#loginPwd").val("").focus();
                }
            },

            error: function (request, status, error) {
/*                if (xhr.status === 401) {
                    $("#errorMessage").text("로그인이 필요합니다.");
                } else {
                    $("#errorMessage").text(
                        "인증 처리 중 오류가 발생했습니다. 다시 시도해주세요."
                    );
                }
*/				
				console.error("===== AJAX 요청 실패 =====");
				console.error("HTTP 상태 코드:", request.status);
				console.error("status:", status);
				console.error("error:", error);
				console.error("서버 응답 내용:", request.responseText);
				
            }
        });

    });

    // Enter 키로도 인증 실행
    $("#loginPwd").on("keydown", function (e) {
        if (e.key === "Enter") {
            e.preventDefault();
            $("#btnSubmit").click();
        }
    });

});