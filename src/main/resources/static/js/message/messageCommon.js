/**
 * 공통으로 사용할 기능
 */
function goBack() { //수정필요
	window.location.replace("home");
}

$(document).ready(function() {
	$("#searchInput").on("click", function() {
		// AJAX 호출로 JSP 화면 불러오기
		$.ajax({
			url: 'messageSearch', // 검색 결과를 보여줄 JSP 파일
			method: 'POST',
			success: function(data) {
				$('#content').html(data); // 받아온 데이터를 #content에 삽입
			},
			error: function() {
				console.log('에러가 발생했습니다.');
			}
		});
	});
});



