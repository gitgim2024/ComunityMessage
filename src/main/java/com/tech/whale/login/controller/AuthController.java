package com.tech.whale.login.controller;

import com.tech.whale.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;  // 추가된 부분
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    // 로그인 여부 확인 API
    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Boolean loggedIn = (Boolean) session.getAttribute("logged_in");
        if (loggedIn != null && loggedIn) {
            response.put("loggedIn", true);
            response.put("username", session.getAttribute("user_id"));
        } else {
            response.put("loggedIn", false);
        }
        return ResponseEntity.ok(response);
    }

    // 사용자 회원가입 시작 API
    @PostMapping("/register/initiate")
    public ResponseEntity<Map<String, Object>> initiateRegister(HttpSession session) {
        session.setAttribute("authFlow", "register");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("redirectTo", "/whale/spotify/login");
        return ResponseEntity.ok(response);
    }

    // 비밀번호 재설정 링크 이메일 발송
    @PostMapping("/find/initiate")
    public ResponseEntity<Map<String, Object>> initiateFind(@RequestBody HashMap<String, Object> map, HttpSession session) {
    	String email = map.get("email").toString();
        Map<String, Object> response = new HashMap<>();
        if (!userService.isEmailRegistered(email)) {
            response.put("success", false);
            response.put("message", "등록된 이메일이 아닙니다.");
            return ResponseEntity.ok(response);
        }
        session.setAttribute("authFlow", "find");
        userService.sendResetPasswordEmail(email);
        response.put("success", true);
        response.put("message", "비밀번호 재설정 링크가 이메일로 전송되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 비밀번호 재설정 API
    @PostMapping("/find/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();

        // requestData에서 token과 newPassword 추출
        String token = requestData.get("token");
        String newPassword = requestData.get("newPassword");

        boolean isTokenValid = userService.verifyResetToken(token);

        if (isTokenValid) {
            userService.updatePassword(token, newPassword);
            response.put("success", true);
            response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
        } else {
            response.put("success", false);
            response.put("message", "유효하지 않은 토큰입니다.");
        }

        return response;
    }
}
