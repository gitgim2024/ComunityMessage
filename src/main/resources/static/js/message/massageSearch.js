/**
 * 메세지 검색 화면
 */
$(document).ready(function() {
	$(".tab").on("click", function() {
		var tabId = $(this).data("tab"); // 클릭된 탭의 data-tab 값 가져오기

		// 현재 탭 비활성화 및 내용 숨기기
		$(".tab.now").removeClass("now");
		// 클릭된 탭 활성화
		$(this).addClass("now");

		// AJAX 호출로 JSP 화면 불러오기
		$.ajax({
			url: 'search', // AJAX 요청을 보낼 URL
			method: 'POST',
			data: { tabId: tabId }, // 필요한 데이터 (예: 어떤 탭인지)
			success: function(data) {
				$('#result').html(data); // 받아온 데이터를 #result에 삽입
				//체크박스 선택 시 버튼 나타남
			},
			error: function() {
				console.log('에러가 발생했습니다.');
			}
		});


	});

});

