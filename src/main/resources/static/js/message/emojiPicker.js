const script = import.meta.url;
console.log(">>[JS]"+script);
import { EmojiButton } from 'https://cdn.jsdelivr.net/npm/@joeattardi/emoji-button@4.6.4/dist/index.min.js';
console.log("로드확인 : " + typeof EmojiButton); // "function" 이면 정상적으로 로드됨

const picker = new EmojiButton({
	/*옵션입력*/
});

picker.on('emoji', emoji => {
	document.getElementById("messageInput").value += emoji.emoji;
});



function emojiPicker() {
	var button = document.querySelector('#emoji_btn');
    console.log("호출완료");
	picker.togglePicker(button);
	document.querySelector('.emoji-picker.light').parentNode.style = "position: absolute; right: 0; bottom: 0; z-index:9999";
}

// 전역으로 함수 등록
window.emojiPicker = emojiPicker;