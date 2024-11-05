package com.tech.whale.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Configuration
public class AppConfig {
	@Value("${spotify.id}")
    private String SpotifyId;
	@Value("${spotify.secret}")
    private String SpotifySecret;
	@Value("${spotify.redirect-url}")
    private String SpotifyRedirectUrl;
	
	
	// [ Spotify API 빈 생성 ]
	@Bean
	public SpotifyApi spotifyApi() {
		return new SpotifyApi.Builder().setClientId(SpotifyId)
									   .setClientSecret(SpotifySecret)
									   .setRedirectUri(SpotifyHttpManager.makeUri(SpotifyRedirectUrl))
									   .build();
	}
}
