<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Whale Account Find</title>
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
	    <div class="findBox flexCenter">
	        <div class="findBox-container">
	            <h1 class="findBox-title">아이디/비밀번호 찾기</h1>
	            <form id="find-form" action="<%= request.getContextPath() %>/find" method="post">
	                <input type="email" id="email" name="email" placeholder="등록된 이메일을 입력하세요" required><br>
	                <button type="submit">인증 메일 보내기</button>
	            </form>
	            <p class="loginJoin-footer">로그인 화면으로 돌아가시겠습니까? <a href="<%= request.getContextPath() %>/">로그인</a></p>
	            <div id="message"></div>
	        </div>
	    </div>
	</div>
<script src="static/js/login/script.js"></script>
<script src="static/js/login/find.js"></script>
</body>

</html>
