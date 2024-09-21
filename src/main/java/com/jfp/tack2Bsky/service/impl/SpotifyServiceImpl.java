package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.SpotifyClient;
import com.jfp.tack2Bsky.client.dto.response.CurrentlyPlayingResponse;
import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;
import com.jfp.tack2Bsky.dto.ListeningResponse;
import com.jfp.tack2Bsky.service.SpotifyService;
import com.jfp.tack2Bsky.service.SpotifyTokenService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyServiceImpl implements SpotifyService {

  private final SpotifyClient spotifyClient;
  private final SpotifyTokenService spotifyTokenService;

  private static final String REDIRECT_ERROR = "redirect:/error";
  private static final String REDIRECT_SUCCESS = "redirect:/success";

  @Override
  public void login(final HttpServletResponse httpServletResponse) throws IOException {
    log.info("Starting Spotify login process.");
    String authorizationUri = spotifyClient.getAuthorizationUri().toString();
    log.debug("Spotify authorization URI: {}", authorizationUri);
    httpServletResponse.sendRedirect(authorizationUri);
    log.info("Redirected to Spotify for login.");
  }

  @Override
  public String callback(String code, String state, String error) {
    log.info("Received callback from Spotify - code: {}, state: {}", code != null ? "present" : "null", state);

    if (error != null) {
      log.error("Error from Spotify during callback: {}", error);
      return REDIRECT_ERROR;
    }

    if (code == null) {
      log.error("Spotify callback did not include an authorization code.");
      return REDIRECT_ERROR;
    }

    return processCode(code);
  }

  private String processCode(String code) {
    try {
      log.info("Processing authorization code from Spotify.");
      SpotifyAutheticationResponse authResponse = spotifyClient.exchangeCodeForToken(code);
      log.debug("Received authentication response from Spotify: {}", authResponse);

      spotifyTokenService.saveAccessToken(authResponse);
      log.info("Spotify authentication successful.");
      return REDIRECT_SUCCESS;

    } catch (Exception e) {
      log.error("Error during Spotify authentication: {}", e.getMessage(), e);
      return REDIRECT_ERROR;
    }
  }

  @Override
  public ListeningResponse getCurrentlyPlaying() {
    log.info("Fetching currently playing track from Spotify.");
    String accessToken = spotifyTokenService.getAccessToken();

    if (accessToken == null) {
      log.error("Failed to fetch currently playing track: access token is null.");
      return null;
    }

    try {
      CurrentlyPlayingResponse currentlyPlayingResponse = spotifyClient.getCurrentlyPlaying(accessToken);
      log.info("Retrieved CurrentlyPlayingTrack from Spotify: {}", currentlyPlayingResponse);

      List<ListeningResponse.Artist> artists = currentlyPlayingResponse.item().artists().stream()
          .map(artist -> new ListeningResponse.Artist(artist.name()))
          .toList();

        return new ListeningResponse(
            currentlyPlayingResponse.item().name(),
            artists
      );

    } catch (Exception e) {
      log.error("Failed to retrieve currently playing track from Spotify: {}", e.getMessage(), e);
      return null;
    }
  }
}
