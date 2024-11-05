// public/js/find.js

$(document).ready(function () {
  $("#find-form").on("submit", function (e) {
    e.preventDefault();

    const email = $("#email").val().trim();

    // 클라이언트 측 유효성 검사 (간단한 이메일 형식 확인)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      $("#message").text("유효한 이메일 주소를 입력해주세요.");
      return;
    }

    // AJAX 요청
    $.ajax({
      url: "/whale/find/initiate",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({ email }),
      success: function (response) {
        if (response.success) {
          $("#message").text(response.message);
          // 일정 시간 후 로그인 페이지로 리디렉션
          setTimeout(() => {
            window.location.href = "/whale/";
          }, 3000);
        } else {
          $("#message").text(response.message).css("color", "red");
        }
      },
      error: function (err) {
        console.error("인증 메일 보내기 중 오류 발생:", err);
        $("#message")
          .text("인증 메일을 보내는 중 오류가 발생했습니다. 다시 시도해주세요.")
          .css("color", "red");
      },
    });
  });
});
