<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Whale Join</title>
    <link rel="icon" href="static/images/main/whaleLogo.png">
    <link rel="stylesheet" href="static/css/login/styles.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
    	#message{
    		margin-bottom: 0;
	    }
	</style>
</head>
<body>
	<div class="header">
	    <div class="headerItems flexCenter">
	        <img src="static/images/login/whaleLogo.png" alt="Music Whale Logo" id="whale-logo" height="80px" onclick="location.href=`<%= request.getContextPath() %>/`">
	    </div>
	</div>
	<div class="main flexCenter">
	    <div class="loginJoin flexCenter">
	        <div class="loginJoin-container">
	            <h1 class="loginJoin-title">회원가입</h1>
	            <form id="register-form">
	                <input type="text" id="username" placeholder="아이디" required><br>
	                <input type="password" id="password" placeholder="비밀번호" required><br>
	                <input type="text" id="user_nickname" placeholder="닉네임" required><br>
	                <!-- 세션에 저장된 스포티파이 이메일을 가져와서 이메일 칸에 자동 입력 -->
	                <input type="email" id="email" placeholder="이메일" required readonly value="<%= session.getAttribute("spotifyEmail") != null ? session.getAttribute("spotifyEmail") : "" %>"><br>
	                <button type="submit">회원가입</button>
	            </form>
	            <p class="loginJoin-footer">이미 계정이 있으신가요? <a href="<%= request.getContextPath() %>/">로그인</a></p>
	            <div id="message"></div>
	        </div>
	    </div>
	</div>
	<script src="static/js/login/register.js"></script>
</body>
</html>
