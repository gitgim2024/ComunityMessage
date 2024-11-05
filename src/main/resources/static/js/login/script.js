// 리사이즈
$(document).ready(() => {
    resize();
});
$(window).resize(() => {
    resize();
});

function resize() {
    var windowHeight = $(window).height();
    var headerHeight = $(".header").height();
    $('.main').css({'height': (windowHeight - headerHeight - 1) + 'px'});
};

document.addEventListener("DOMContentLoaded", () => {
    // 사용자 로그인 여부 확인
    fetch("/whale/check-login")
        .then((response) => response.json())
        .then((data) => {
                if (!data.loggedIn) {
                    // 로그인되지 않은 경우 로그인 창 유지
                } else {
                    // 서버에서 Spotify 액세스 토큰 확인
                    fetch("/whale/spotify/refresh_token")
                        .then((response) => response.json())
                        .then((tokenData) => {
                            console.log(tokenData);
                                if (tokenData.accessToken) {
                                    // Spotify 로그인 확인 후 Spring 메인 화면으로 이동
                                    window.location.replace("/whale/main");
                                } else {
                                    // Spotify 로그인 필요
                                    window.location.replace("/whale/spotify/login");
                                }
                            }
                        );
                }
            }
        );
});