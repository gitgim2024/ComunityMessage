<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	var userId = '${sessionScope.user_id}'; // 세션에서 user_id 읽기
</script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/message/messageRoom.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/message/messageRoom.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/message/socketConnect.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/message/sendMessage.js"></script>
<meta charset="UTF-8">
</head>
<body>
	<input type="hidden" id="roomId" value="${roomID }" />
	<div class="talk-container">
		<div class="top">
			<div class="top-left">
				<button type="button" class="beforePage" onclick="goBack()">
					<img class="barBtns" src="../static/images/message/arrow.png"
						alt="이전" />
				</button>
				<img class="room-img" src="../static/images/message/test/people.png" />
				<div class="profile-name">${ids }</div>
			</div>
			<div class="top-right">
				<img class="barBtns" src="../static/images/message/search.png"
					alt="검색" /> <img class="barBtns"
					src="../static/images/message/plus.svg" alt="검색" />
			</div>
		</div>

		<div class="middle">
			<c:forEach var="msg" items="${roomMsgs}">
				<c:choose>
					<c:when test="${msg.user_id == sessionScope.user_id}">
						<!-- 현재 사용자와 일치하는 경우 -->
						<div class="my-msg">
							<div class="context">
								<div class="msg-time">${fn:substring(msg.message_create_date, 11, 16)}</div>
								<div class="msg-context">${msg.message_context}</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<!-- 다른 사용자 -->
						<c:if
							test="${fn:contains(msg.message_context, previousUserId) == false}">
							<div class="other-msg ${msg.user_id}">
								<div class="user-info">
									<img class="msg-img"
										src="../static/images/message/test/people.png" />
									<div class="msg-name">${msg.user_id}</div>
								</div>
								<div class="context">
									<div class="msg-context">${msg.message_context}</div>
									<div class="msg-time">${msg.message_create_date}</div>
								</div>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:set var="previousUserId" value="${msg.user_id}" />
				<!-- 이전 사용자 ID를 설정 -->
			</c:forEach>

		</div>
		<div class="bottom">
			<!-- 이미지 미리보기 -->
			<div id="imagePreview" style="display: flex; margin-bottom: 10px;"></div>
			<textarea class="textarea" id="messageInput"></textarea>
			<div type="button" class="send-button" onclick="sendMessage()">
				<img src="../static/images/message/send.svg" alt="전송" />
			</div>
			<input type="button" id="emoji_btn" style='display: none;'
				onclick="emojiPicker();"> <img class="emoticon"
				src="../static/images/message/emoji.svg"
				onclick='document.all.emoji_btn.click();' /> <input type="file"
				id="file" accept="image/png, image/jpeg" multiple
				onchange="previewImage(event)" style='display: none;'> <img
				class="file" src="../static/images/message/picture.svg"
				onclick='document.all.file.click();' /> <label id="result"></label>
		</div>

	</div>
</body>
</html>