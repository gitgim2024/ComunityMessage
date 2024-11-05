// public/js/reset.js

$(document).ready(function() {
  $('#reset-form').on('submit', function(e) {
    e.preventDefault();

    const newPassword = $('#newPassword').val();
    const confirmPassword = $('#confirmPassword').val();

    // 비밀번호 확인
    if (newPassword !== confirmPassword) {
      $('#message').text('비밀번호가 일치하지 않습니다.').css('color', 'red');
      return;
    }

    // // 비밀번호 강도 검사 (예시: 최소 8자, 하나 이상의 숫자 및 특수 문자 포함)
    // const passwordRegex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$/;
    // if (!passwordRegex.test(newPassword)) {
    //   $('#message').text('비밀번호는 최소 8자 이상이어야 하며, 숫자와 특수 문자를 포함해야 합니다.').css('color', 'red');
    //   return;
    // }

    // URL에서 토큰 추출
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (!token) {
      $('#message').text('유효하지 않은 요청입니다.').css('color', 'red');
      return;
    }

    // AJAX 요청
    $.ajax({
      url: '/whale/find/reset-password',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({ token, newPassword }),
      success: function(response) {
        if (response.success) {
          $('#message').text(response.message);
          // 일정 시간 후 로그인 페이지로 리디렉션
          setTimeout(() => {
            window.location.href = '/whale/';
          }, 3000);
        } else {
          $('#message').text(response.message).css('color', 'red');
        }
      },
      error: function(err) {
        console.error('비밀번호 재설정 중 오류 발생:', err);
        console.log('에러 응답 내용:', err.responseText); // 응답 텍스트 확인
        console.log('에러 상태 코드:', err.status);      // HTTP 상태 코드 확인
        console.log('에러 상세 내용:', err);             // 전체 에러 객체 출력
        $('#message').text('비밀번호를 재설정하는 중 오류가 발생했습니다. 다시 시도해주세요.').css('color', 'red');
      }
    });
  });
});
