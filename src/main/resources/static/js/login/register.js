// public/js/register.js

$(document).ready(function() {
  // 리사이즈
  resize();
  $(window).resize(resize);

  function resize() {
    var windowHeight = $(window).height();
    var headerHeight = $(".header").height();
    $('.main').css({'height': (windowHeight - headerHeight - 1) + 'px'});
  }

  // 회원가입 폼 제출 처리
  $('#register-form').on('submit', function(e) {
    e.preventDefault();

    const username = $('#username').val().trim();
    const password = $('#password').val().trim();
    const email = $('#email').val();
    const nickname = $('#user_nickname').val().trim();

    // 클라이언트 측 유효성 검사 (간단한 예시)
    if (!username || !nickname || !password || !email) {
      $('#message').text('모든 필드를 입력해주세요.');
      return;
    }

    $.ajax({
      url: '/whale/register/complete',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({ username, password, email, nickname }),
      success: function(response) {
        if (response.success) {
          alert(response.message);
          window.location.replace(response.redirectTo);
        } else {
          $('#message').text(response.message);
        }
      },
      error: function(err) {
        console.error('회원가입 중 오류 발생:', err);
        $('#message').text('서버 오류로 인해 회원가입에 실패했습니다.');
      }
    });
  });
});
