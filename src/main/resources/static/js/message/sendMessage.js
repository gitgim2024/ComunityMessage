$(document).ready(function() {
connect();
var formData = new FormData();
const maxSize = 2 * 1024 * 1024; // 최대 2MB
const allowedTypes = ['image/jpeg', 'image/png', 'image/gif']; // 허용되는 이미지 형식
window.sendMessage = function() {
	console.log(">>[JS] sendMessage()")
	connect();
	var message = document.getElementById("messageInput").value;
	var result = document.getElementById("result");
	//글자 갯수 1000자 제한
	if (message.length > 1000) {
		console.log("글자수 : " + message.length);
		result.innerHTML = '글자수는 1000자를 초과할 수 없습니다.';
		return;
	}

	uploadImage().then(data => {//이미지와 메시지 함께 송부
		if (message || data.length > 0) { // 사용자 ID와 메시지나 이미지 첨부가가 있는 경우
			if (data.length > 0) {//첨부된 이미지가 하나라도 있는지 확인
				// 이미지 id들
				console.log("데이터오는지 확인 : " + data);
			}
			var finalMessage = {
				userId: userId,
				message: message,
				imageids:JSON.stringify(data)
			};
			socket.send(JSON.stringify(finalMessage));//메시지 전송
		} else {
			console.log("입력 된 메시지가 없습니다.");
		}
	});
	// 파일 입력 초기화
	formData = new FormData();
	document.getElementById("messageInput").value = ""; // 입력창 비우기
	document.getElementById("file").value = '';
	document.getElementById('imagePreview').innerHTML = '';//미리보기초기화
	result.innerHTML = '';
}

// 이미지 업로드 처리 함수
// 파일을 업로드할 때는 일반적으로 HTTP POST 요청을 사용. 이미지 업로드 후 서버가 응답하는 데이터를 받아 처리하는 로직이 필요하기 때문에 AJAX 사용.
function uploadImage() {
	console.log(">>[JS] uploadImage()")

	return fetch('api/images/upload', {//파일을 서버의 upload페이지에 보내서 응답받음
		method: 'POST',
		body: formData
	})
		.then(response => response.json())//결과값을 json형식에 담음
		.then(data => {
			console.log("업로드 성공:", data);
			return data;//json형식으로 온 결과값을 data에담아 return함.
		})
		.catch(error => {
			console.error("이미지 업로드 실패:", error);
			return [];
		});
}

//파일미리보기 및 전송파일 셋팅
window.previewImage = function(event) {
	console.log(">>[JS] previewImage()")
	
	var imagePreview = document.getElementById('imagePreview');
	var files = event.target.files;

	if (files.length > 5) {
		alert("이미지는 최대 5개까지 첨부가 가능합니다.");
		return;
	}

	for (var i = 0; i < files.length; i++) {
		var file = files[i];

		// 파일 크기 체크
		if (file.size > maxSize) {
			alert("파일 크기는 2MB를 넘을 수 없습니다.");
			continue;
		}
		// 파일 형식 체크
		if (!allowedTypes.includes(file.type)) {
			alert("허용되지 않는 파일 형식입니다. JPEG, PNG 또는 GIF만 가능합니다.");
			continue;
		}

		// 모든 체크를 통과하면 FormData에 파일 추가
		formData.append('file', file);
	}
	//채팅입력창에 표시
	imagePreview.innerHTML = '';
	for (var i = 0; i < formData.getAll('file').length; i++) {
		var file = formData.getAll('file')[i];

		var reader = new FileReader();

		reader.readAsDataURL(file); // 이미지 파일을 데이터 URL로 읽기
		reader.onload = function(e) {//FileReader 객체에서 사용하는 이벤트 핸들러로, 파일 읽기가 완료되었을 때 실행되는 함수
			var newImg = document.createElement('img');
			newImg.src = e.target.result;
			newImg.style.maxWidth = '50px';
			newImg.style.marginRight = '10px';
			imagePreview.appendChild(newImg); // 새 이미지 추가
		}
	}
}
});