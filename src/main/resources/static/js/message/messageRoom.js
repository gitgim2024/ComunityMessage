/**
 * 메시지 방 기본 설정
 */

window.onload = function() {
	//시작 시 스크롤을 항상 아래로
	scrollToBottom();
};

// 스크롤을 항상 맨 아래로 이동시키는 함수
function scrollToBottom() {
    const messageContainer = document.querySelector('.middle');
    messageContainer.scrollTop = messageContainer.scrollHeight;
}