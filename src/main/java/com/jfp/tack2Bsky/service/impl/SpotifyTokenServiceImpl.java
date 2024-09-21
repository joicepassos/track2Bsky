package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;
import com.jfp.tack2Bsky.service.SpotifyTokenService;
import java.io.FileOutputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpotifyTokenServiceImpl implements SpotifyTokenService {

  private static final String TOKENS_FILE = "spotify_tokens.properties";

  // TODO::Save tokens with firebase or other cloud storage

  @Override
  public void saveAccessToken(final SpotifyAutheticationResponse spotifyAutheticationResponse) {
    long expirationTime = System.currentTimeMillis() + spotifyAutheticationResponse.expiresIn() * 1000L;

    Properties properties = new Properties();
    properties.setProperty("accessToken", spotifyAutheticationResponse.accessToken());
    properties.setProperty("refreshToken", spotifyAutheticationResponse.refreshToken());
    properties.setProperty("expirationTime", String.valueOf(expirationTime));

    try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENS_FILE)) {
      properties.store(fileOutputStream, "Spotify tokens");
    } catch (Exception e) {
      log.error("Failed to save tokens to file", e);
      throw new RuntimeException("Failed to save tokens to file", e);
    }
  }

  @Override
  public void refreshAccessToken(final SpotifyAutheticationResponse spotifyAutheticationResponse) {
    log.info("Refreshing access token...");
    Properties properties = new Properties();
    try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENS_FILE)) {
      properties.setProperty("accessToken", spotifyAutheticationResponse.accessToken());
      properties.setProperty("refreshToken", spotifyAutheticationResponse.refreshToken());
      properties.store(fileOutputStream, "Spotify tokens");
    } catch (Exception e) {
      log.error("Failed to refresh access token", e);
      throw new RuntimeException("Failed to refresh access token", e);
    }
  }
}
