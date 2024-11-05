package com.tech.whale.main.models;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import javax.servlet.http.HttpSession;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Repository;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@Repository
public class MainAuthorizationCode {
    private final SpotifyApi spotifyApi;
    private String code;

    public MainAuthorizationCode(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SpotifyApi getSpotifyapi() {
        return spotifyApi;
    }

    public URI getAuthorizationUri() {
        return authorizationCodeUri_Async();
    }

    public URI authorizationCodeUri_Async() {
        try {
            final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().state("x4xkmn9pu3j6ukrs8n")
                    .scope("ugc-image-upload,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,"
                            + "app-remote-control,streaming,playlist-read-private,playlist-read-collaborative,playlist-modify-private,"
                            + "playlist-modify-public,user-follow-modify,user-follow-read,user-read-playback-position,user-top-read,"
                            + "user-read-recently-played,user-library-modify,user-library-read,user-read-email,user-read-private")
                    .show_dialog(true)
                    .build();

            return authorizationCodeUriRequest.executeAsync().join();
        } catch (Exception e) {
            throw new RuntimeException("Error during Spotify authorization", e);
        }
    }

    public void authorizationCode_Async(String code, HttpSession session) {
        try {
            final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest.executeAsync();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
            session.setAttribute("accessToken", authorizationCodeCredentials.getAccessToken());
            session.setAttribute("refreshToken", authorizationCodeCredentials.getRefreshToken());
            session.setAttribute("tokenType", authorizationCodeCredentials.getTokenType());
            session.setAttribute("expiresIn", authorizationCodeCredentials.getExpiresIn());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
}
