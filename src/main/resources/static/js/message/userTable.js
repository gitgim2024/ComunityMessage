/**
 * 유저테이블
 */
$(document).ready(function() {
	//체크 상태에 따른 버튼 상태 변화
	//유저 검색 체크박스
	var checkboxes = document.querySelectorAll('.box');
	var confirmButton = document.getElementById('confirmButton');
	checkboxes.forEach(function(checkbox) {
		checkbox.addEventListener('change', function() {
			const checkedCount = Array.from(checkboxes).filter(x => x.checked).length;
			confirmButton.style.display = checkedCount > 0 ? 'block' : 'none'; // 하나 이상 선택되면 버튼 표시
		});

	});
	// 초기 버튼 상태 업데이트
	var initialCheckedCount = Array.from(checkboxes).filter(x => x.checked).length;
	confirmButton.style.display = initialCheckedCount > 0 ? 'block' : 'none';
	
	confirmButton.addEventListener('click', handleConfirm);


	// AJAX 요청으로 선택된 ID 송부
	function handleConfirm() {
		// 체크된 체크박스 목록 가져오기
		var checkboxes = document.querySelectorAll('.box:checked'); //선택된 체크박스 가져오기
		var selectedIds = Array.from(checkboxes).map(x => x.dataset.id); // 선택된 체크박스의 data-id 속성 값을 배열로 저장
		var tabId = $('.tab.now').data("tab");

		$.ajax({
			url: 'messageRoom',
			method: 'POST',
			data: { selectedIds: selectedIds, tabId: tabId }, // 선택된 ID 배열
			traditional: true, // 배열을 전송할 때 필요. 이 옵션을 설정하면, 배열의 요소가 일반적인 쿼리 문자열 형식으로 전송
			success: function(response) {
				// 요청 성공 시 처리
				$('.message-container').html(response); // 받아온 데이터를 #result에 삽입
			},
			error: function() {
				console.log('에러가 발생했습니다.');
			}
		});
	};
});