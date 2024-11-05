<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Whale Accont Password Reset</title>
    <link rel="icon" href="static/images/main/whaleLogo.png">
    <link rel="stylesheet" href="static/css/login/styles.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="header">
	    <div class="headerItems flexCenter">
	        <img src="static/images/login/whaleLogo.png" alt="Music Whale Logo" id="whale-logo" height="80px" onclick="location.href=`<%= request.getContextPath() %>/`">
	    </div>
	</div>
	<div class="main flexCenter">
		<div class="resetBox flexCenter">
			<div class="resetBox-container">
				<h1 class="resetBox-title">비밀번호 재설정</h1>
				<form id="reset-form">
					<input type="password" id="newPassword" placeholder="새 비밀번호" required><br>
					<input type="password" id="confirmPassword" placeholder="비밀번호 확인" required><br>
					<button type="submit">비밀번호 변경</button>
				</form>
				<div id="message"></div>
			</div>
		</div>
	</div>
<script src="static/js/login/script.js"></script>
<script src="static/js/login/reset.js"></script>
</body>

</html>
