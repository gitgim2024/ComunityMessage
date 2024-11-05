package com.tech.whale.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tech.whale.login.service.UserService;
import com.tech.whale.main.models.MainAuthorizationCode;
import com.tech.whale.main.service.MainAuthorizationCodeController;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final MainAuthorizationCode mainAuthorizationCode;
    private final MainAuthorizationCodeController mainAuthorizationCodeController;

    public LoginController (MainAuthorizationCode mainAuthorizationCode, MainAuthorizationCodeController mainAuthorizationCodeController) {
        this.mainAuthorizationCode = mainAuthorizationCode;
        this.mainAuthorizationCodeController = mainAuthorizationCodeController;
    }

    @Autowired
    private SpotifyApi spotifyApi;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String loginHome() {
        return "login/login";
    }

    // 사용자 로그인 API
    @PostMapping("/login")
    public String postLogin(HttpSession session, HttpServletRequest request, Model model) { // HttpServletRequest 추가
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        System.out.println("username: " + username + " password: " + password);

        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            session.setAttribute("logged_in", true);  // 새로운 세션 생성
            session.setAttribute("user_id", username);
            userService.checkAccessIdLogin(username, session);
            // 여기서 리디렉션 경로 수정
            return "redirect:/spotify/login";
        } else {
            model.addAttribute("message",false);
            return "login/login";
        }
    }

    @RequestMapping("/register")
    public String loginRegister() {
        return "login/register";
    }

    @RequestMapping("/find")
    public String loginFind() {
        return "login/find";
    }

    @RequestMapping("/reset-password")
    public String loginResetPassword() {
        return "login/reset-password";
    }

    // [ Java를 활용한 Spotify 인증 방식 ]
    @RequestMapping("/spotify/login")
    public String login() {
        URI uri = mainAuthorizationCode.getAuthorizationUri();
        return "redirect:" + uri.toString();
    }

    @RequestMapping("/spotify/callback")
    public String callback(HttpServletRequest req, HttpSession session) {
    	
    	try {
    		// 기존 로그인 로직
            mainAuthorizationCodeController.execute(req, session);

            // 스포티파이 API를 통해 인증된 사용자 정보 가져오기
            String[] spotifyInfo = getSpotifyInfo(req, session);

            // 유저 정보를 세션에 저장
            session.setAttribute("spotifyEmail", spotifyInfo[0]);
            session.setAttribute("spotifyId", spotifyInfo[1]);

            // 세션 값에 따라 리디렉션 처리
            String isRegistered = (String) session.getAttribute("authFlow");
            if ("register".equals(isRegistered)) {
                return "redirect:/register";
            } else if (session.getAttribute("access_id").toString().equals("1")) {
            	return "redirect:/admin/adminMainView";
            } else {
                return "redirect:/main";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
    }

    // 스포티파이 API를 사용해서 유저 정보 가져오는 메서드
    public String[] getSpotifyInfo(HttpServletRequest req, HttpSession session) {
        // 스포티파이에서 토큰을 얻는 과정
        String accessToken = (String) session.getAttribute("accessToken");

        // 로그 추가 - 액세스 토큰 확인
        System.out.println("Access Token: " + accessToken);

        // AccessToken을 사용해 스포티파이 API 호출
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        try {
            // 스포티파이 API로 사용자 프로필 정보 요청
            GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
            User user = getCurrentUsersProfileRequest.execute();
            String email = user.getEmail();  // 사용자 이메일 가져오기
            String spotifyId = user.getId();  // 사용자 이메일 가져오기

            return new String[] {email, spotifyId};

        } catch (SpotifyWebApiException | IOException | ParseException e) {
            // 엑세스 토큰이 만료된 경우, 리프레시 토큰을 사용해 새로운 엑세스 토큰을 발급받고 다시 시도
            accessToken = (String) session.getAttribute("accessToken");
            if (accessToken != null) {
                try {
                    spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
                    GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
                    User user = getCurrentUsersProfileRequest.execute();
                    String email = user.getEmail();  // 사용자 이메일 가져오기
                    String spotifyId = user.getId();  // 사용자 이메일 가져오기

                    return new String[] {email, spotifyId};
                } catch (IOException | SpotifyWebApiException | ParseException ex) {
                    ex.printStackTrace();
                    return null;
                }
            } else {
                e.printStackTrace();
                return null;
            }
        }
    }
}
