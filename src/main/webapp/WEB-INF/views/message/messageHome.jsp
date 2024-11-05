<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="../static/css/message/messageHome.css">
<script type="text/javascript"
	src="../static/js/message/messageCommon.js"></script>

<meta charset="UTF-8" />
<title>Message Home</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: 0;
}

body {
	min-height: 100vh;
}
</style>
<script>
		//리사이즈
		$(document).ready(() => {resize();});
		$(window).resize(() => {resize();});
		function resize() {
			var windowHeight = $(window).height();
			$('.message-container').css({'height': (windowHeight-3)+'px'});
		};
	</script>
</head>
<body>
	<div class="message-container">
			<div id="search">
				<button type="button" class="beforePage" onclick="goBack()">
					<img class="barBtns" src="../static/images/message/arrow.png"
						alt="이전" />
				</button>
				<input id="searchInput" name="searchInput" type="text"
					placeholder="검색" />
				<button type="submit" id="searchBtn">
					<img class="barBtns" src="../static/images/message/search.png"
						alt="검색" />
				</button>
			</div>
			<div id="content">
				<span id="title">메시지</span>
				<jsp:include page="messageTable.jsp" />

			</div>
		</div>
</body>
</html>