package com.tech.whale.login.controller;

import com.tech.whale.main.models.MainAuthorizationCode;
import com.tech.whale.main.service.MainAuthorizationCodeController;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI; // URI를 사용하기 위해 추가
import java.util.HashMap;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@RestController
public class SpotifyController {
    private final MainAuthorizationCode mainAuthorizationCode;
    private final MainAuthorizationCodeController mainAuthorizationCodeController;
    private SpotifyApi spotifyApi;

    public SpotifyController (MainAuthorizationCode mainAuthorizationCode, MainAuthorizationCodeController mainAuthorizationCodeController, SpotifyApi spotifyApi) {
        this.mainAuthorizationCode = mainAuthorizationCode;
        this.mainAuthorizationCodeController = mainAuthorizationCodeController;
        this.spotifyApi = spotifyApi;
    }

    // 리프레시 토큰을 사용하여 엑세스 토큰을 갱신하는 메서드
    @RequestMapping("/spotify/refresh_token")
    public HashMap<String, Object> refreshAccessToken(HttpSession session) {
        HashMap<String, Object> response = new HashMap<>();
        String refreshToken = (String) session.getAttribute("refreshToken");

        // 로그 추가 - 리프레시 토큰 및 클라이언트 정보 확인
        System.out.println("Refresh Token: " + refreshToken);
        System.out.println("Client ID: " + spotifyApi.getClientId());
        System.out.println("Client Secret: " + spotifyApi.getClientSecret());

        spotifyApi.setRefreshToken(refreshToken);

        AuthorizationCodeRefreshRequest refreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        try {
            AuthorizationCodeCredentials credentials = refreshRequest.execute();
            session.setAttribute("accessToken", credentials.getAccessToken());
            response.put("accessToken", credentials.getAccessToken());
            return response;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getSpotifyUserData")
    public ResponseEntity<String> getSpotifyUserData(HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");

        if (accessToken != null) {
            try {
                spotifyApi.setAccessToken(accessToken);
                // 유저 프로필을 가져오는 로직
                var userProfile = spotifyApi.getCurrentUsersProfile().build().execute();

                return ResponseEntity.ok(userProfile.toString());
            } catch (UnauthorizedException e) {
                // Access Token이 만료된 경우
                refreshAccessToken(session);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token refreshed");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user data");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not found");
        }
    }
}
