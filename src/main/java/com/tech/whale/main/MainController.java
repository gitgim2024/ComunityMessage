package com.tech.whale.main;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

@Controller
public class MainController {
	@Autowired
	private SpotifyApi spotifyApi;
	
    // [ 메인 페이지 이동 ]
    @RequestMapping("/main")
	public String main(HttpSession session) {
    	if (session.getAttribute("user_id") == null) {
    		return "redirect:/";
    	} else {
    		// 유저 아이디가 존재한다면 리프레시 토큰을 이용해 새로운 엑세스 토큰을 받아오는 구간
            spotifyApi.setRefreshToken((String) session.getAttribute("refreshToken"));

            AuthorizationCodeRefreshRequest refreshRequest = spotifyApi.authorizationCodeRefresh()
                    .build();

            try {
                AuthorizationCodeCredentials credentials = refreshRequest.execute();
                session.setAttribute("accessToken", credentials.getAccessToken());
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                e.printStackTrace();
            }
    		return "main/main";
    	}
	}
    // [ 세션 등록 구간 ]
	@GetMapping("/check-access-id")
	public String checkAccessId(@RequestParam Map<String, String> queryParam, HttpSession session) {
		// 세션 등록
		session.setAttribute("accessToken", queryParam.get("access_token"));
		session.setAttribute("refreshToken", queryParam.get("refresh_token"));
		session.setAttribute("user_id", queryParam.get("user_id"));
		session.setAttribute("logged_in", queryParam.get("logged_in"));
		session.setAttribute("access_id", queryParam.get("access_id"));
		
		// 관리자 번호에 따라 리다이렉트 지점 변경
		if (session.getAttribute("access_id").toString().equals("1")) {return "redirect:/admin/adminMainView";}
		else {return "redirect:main";}
	}
	
	// [ 로그아웃 기능 ]
	@GetMapping("/main/logout")
	public String logout(HttpSession session) {
		// 스프링 서버 세션 정보 초기화
		session.invalidate();
		
		// 노드 서버 정보 초기화 위해 리다이렉트
		// return "redirect:https://localhost:5500/whale/logout\\";
		// 스프링 서버 로그인 화면으로 리다이렉트
		return "redirect:/";
	}
}