<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="../static/css/message/messageRoom.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
			<div class="other-msg">
				<div class="user-info">
					<img class="msg-img" src="../static/images/message/test/people.png" />
					<!-- 메시지 출력점  -->
					<div class="msg-name">내 친구</div>
				</div>
				<div class="context">
					<div class="msg-context">식사하셨나요?ddddddddddddddddddddddddddddddddddd</div>
				</div>
				<div class="context">
					<div class="msg-context">대dddddddddddddddddddddd답</div>
					<div class="msg-time">오후 3:40</div>
				</div>
			</div>
			<div class="my-msg">
				<div class="context">
					<div class="msg-time">오후 13:40</div>
					<div class="msg-context">ㅇㅇ</div>
				</div>
				<div class="context">
					<div class="msg-time">오후 3:41</div>
					<div class="msg-context">너dddddddddddddddddddddddddddddddddddddddddd는?</div>
				</div>
			</div>
		</div>
		<div class="bottom">
			<textarea class="textarea"></textarea>
			<div type="submit" class="send-button">
				<img src="../static/images/message/send.svg" alt="전송" />
			</div>
			<input type="submit" id="emoji_btn" onchange="previewImage(event)"
				style='display: none;'> <img class="emoticon"
				src="../static/images/message/emoji.svg"
				onclick='document.all.emoji_btn.click();' /> <input type="file"
				id="file" accept="image/png, image/jpeg" multiple
				onchange="previewImage(event)" style='display: none;'> <img
				class="file" src="../static/images/message/picture.svg"
				onclick='document.all.file.click();' />
		</div>

	</div>
</body>
</html>