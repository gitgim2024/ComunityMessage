/**
 * messageHome
 */
var isChange = false;
$(document).ready(function() {
	// 공통 AJAX 호출 함수
	function moveMsgSearch() {
		var searchInput = $('#searchInput').val(); // 검색어 가져오기
		var tabId = $('.tab.now').data("tab");//탭 가져오기
		console.log(">>>>>>>>입력값 : "+searchInput);
		var roomList = $('#roomList').val();
		$.ajax({
			url: 'messageSearch', // 검색 결과를 보여줄 JSP 파일
			method: 'POST',
			data: {
				list: roomList, // 숨겨진 필드의 값을 포함
				searchInput : searchInput,
				tabId : tabId
			},
			success: function(data) {
				$('#content').html(data); // 받아온 데이터를 #content에 삽입
			},
			error: function() {
				console.log('에러가 발생했습니다.');
			}
		});
	}
	function moveSearch() {
		var searchInput = $('#searchInput').val(); // 검색어 가져오기
		var tabId = $('.tab.now').data("tab");//탭 가져오기
		console.log(">>>>>>>>입력값 : "+searchInput);
		$.ajax({
			url: 'search', // 검색 결과를 보여줄 JSP 파일
			method: 'POST',
			data: {
				searchInput : searchInput,
				tabId : tabId
			},
			success: function(data) {
				$('#result').html(data); // 받아온 데이터를 #content에 삽입
			},
			error: function() {
				console.log('에러가 발생했습니다.');
			}
		});
	}
	$("#searchInput").on("click", function() {
		if (isChange == false) {
			moveMsgSearch();
			isChange = true;
		}
		
	});


	$("#searchBtn").on("click", function() {
		if (isChange == false) {
			moveMsgSearch();
			isChange = true;
		} else {
			moveSearch();
		}
	});
});



