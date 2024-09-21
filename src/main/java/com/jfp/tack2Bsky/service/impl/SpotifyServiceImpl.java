package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.SpotifyClient;
import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;
import com.jfp.tack2Bsky.service.SpotifyService;
import com.jfp.tack2Bsky.service.SpotifyTokenService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyServiceImpl implements SpotifyService {

  private final SpotifyClient spotifyClient;
  private final SpotifyTokenService spotifyTokenService;
  private static final String REDIRECT_STRING = "redirect:/";
  private static final String REDIRECT_ERROR = REDIRECT_STRING + "error";
  private static final String REDIRECT_SUCCESS = REDIRECT_STRING + "success";

  @Override
  public void login(final HttpServletResponse httpServletResponse) throws IOException {
    log.info("Logging in with Spotify...");
    spotifyClient.getAuthorizationUri();
    httpServletResponse.sendRedirect(spotifyClient.getAuthorizationUri().toString());
  }

  @Override
  public String callback(String code, String state, String error) {
    log.info("Callback received from Spotify - code: {}, state: {}, error: {}", code, state, error);

    if (error != null) {
      log.error("Error received from Spotify: {}", error);
      return REDIRECT_ERROR;
    }

    if (code == null) {
      log.error("Code is null");
      return REDIRECT_ERROR;
    }

    try {
      log.info("Code received from Spotify: {}", code);
      SpotifyAutheticationResponse spotifyAutheticationResponse =
          spotifyClient.exchangeCodeForToken(code);
      spotifyTokenService.saveAccessToken(spotifyAutheticationResponse);
      log.info("Successfully authenticated with Spotify");
      return REDIRECT_SUCCESS;

    } catch (Exception e) {
      log.error("Failed to authenticate with Spotify: {}", e.getMessage());
      return REDIRECT_ERROR;
    }
  }
}
