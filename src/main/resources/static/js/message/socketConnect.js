var socket;
var isConnected = false;

function connect() {
	console.log(">>[JS] connect()");
	if (isConnected) return;
	var roomId = document.getElementById("roomId").value; // 방 ID 가져오기

	socket = new WebSocket("ws://localhost:9002/whale/msgroom?roomId=" + roomId); // WebSocket 서버 URL

	socket.onopen = function(event) {
		console.log("Connected to WebSocket server.");
		isConnected = true; // 연결 상태 업데이트
	};

	socket.onmessage = function(event) {
		console.log(event.data);//데이터확인
		var data = JSON.parse(event.data); // event.data를 JSON 형식으로 파싱
		
		var msguserId = data.userId; // userId 추출
		var message = data.message; // message 추출
		var msgTime = data.msgTime; // msgTime 추출
		var messagesContainer = document.querySelector(".middle");;

		// 현재 사용자 ID
		var currentUserId = userId; // 현재 사용자의 ID를 가져오는 함수

		// 메시지를 추가할 HTML 생성
		var htmlMessage = "";

		// 이미지 HTML 생성
		var imageHtml = "";
		console.log("이미지?:"+data.imagesHtml);
		if (data.imagesHtml) { // imagesHtml 키가 존재할 때만 처리
			var images = JSON.parse(data.imagesHtml);
			if (images.length > 0) {
				for (var i = 0; i < images.length; i++) {
					imageHtml += '<img src="' + images[i] + '" style="max-width: 350px; max-height: 350px; margin-right: 10px;" />';
				}
				console.log("이미지 있음: " + imageHtml);
				message = (message ? "<br>" + message : ""); // 메시지와 이미지 사이에 줄바꿈 추가
			}
		}

		if (msguserId === currentUserId) {
			// my-msg 클래스
			htmlMessage += "<div class=\"my-msg\">"
				+ "<div class=\"context\">"
				+ "<div class=\"msg-time\">" + msgTime + "</div>"
				+ "<div class=\"msg-context\">" + imageHtml + message + "</div>"
				+ "</div></div>";
		} else {
			// other-msg 클래스
			var lastMessage = messagesContainer.lastChild;//자식 요소 중 마지막 요소 반환
			var isSameUser = lastMessage && lastMessage.classList.contains('other-msg') &&
				lastMessage.querySelector('.msg-name').textContent === msguserId;

			if (!isSameUser) {
				// 새로운 사용자 블록 시작
				htmlMessage += "<div class=\"other-msg\">"
					+ "<div class=\"user-info\">"
					+ "<img class=\"msg-img\" src=\"../static/images/message/test/people.png\" />"
					+ "<div class=\"msg-name\">" + msguserId + "</div>"
					+ "</div>"; // user-info 끝
			}

			htmlMessage += "<div class=\"context\">"
				+ "<div class=\"msg-context\">" + imageHtml + message+"</div>";

			// 같은 사용자와 시간이 동일할 경우 msg-time 출력
			if (!isSameUser) {
				htmlMessage += "<div class=\"msg-time\">" + msgTime + "</div>";
			}

			htmlMessage += "</div></div>";
		}

		// 메시지 추가
		messagesContainer.innerHTML += htmlMessage;
		//스크롤 아래로 내리기
		
		scrollToBottom();
	};

	socket.onclose = function(event) {
		console.log("Disconnected from WebSocket server.");
		isConnected = false; // 연결 상태 업데이트
		console.log(event.code, event.wasClean)
		if (!event.wasClean && event.code !== 1000) {//wasClean : 정상종료 시 true, code는 1000이 정상종료
			console.log("재연결");
			setTimeout(() => this.initWebSocket(), 200);//200초 후 소켓Init함
		}
	};
	socket.onerror = function(event) {
		console.error("WebSocket error:", event);
	};
}