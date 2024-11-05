<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Whale Login</title>
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
	    <div class="loginJoin flexCenter">
	        <div class="loginJoin-container">
	            <h1 class="loginJoin-title">로그인</h1>
	            <form action="/whale/login" id="login-form" method="post">
	                <input type="text" id="username" name="username" placeholder="아이디" required><br>
	                <input type="password" id="password" name="password" placeholder="비밀번호" required><br>
	                <button type="submit" value="whale">로그인</button><br>
	            </form>
	            <p class="loginJoin-footer">계정이 없으신가요?&nbsp;&nbsp;<button id="start-register">회원가입</button></p>
	            <p class="loginForgot-footer">계정을 잊어버리셨나요?&nbsp;&nbsp;<button id="start-find">아이디/비밀번호 찾기</button></p>
	            <div id="message"></div>
	        </div>
	    </div>
	</div>
	<script src="static/js/login/script.js"></script>
	<script>
	    $(document).ready(function() {
	        $('#start-register').on('click', function() {
	            $.ajax({
	                url: '/whale/register/initiate',
	                method: 'POST',
	                success: function(response) {
	                    if (response.success) {
	                        window.location.replace(response.redirectTo);
	                    } else {
	                        alert(response.message);
	                    }
	                },
	                error: function(err) {
	                    console.error('회원가입 시작 중 오류 발생:', err);
	                }
	            });
	        });
	
	        $('#start-find').on('click', function() {
	            window.location.href = '/whale/find';
	        });
	
	        if (`${message}` === "false") {
	            message.innerText = "아이디 또는 비밀번호가 올바르지 않습니다.";
	        }
	    });
	</script>
</body>
</html>
