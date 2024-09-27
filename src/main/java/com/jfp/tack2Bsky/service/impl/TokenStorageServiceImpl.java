package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.service.TokenStorageService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenStorageServiceImpl implements TokenStorageService {

  private static final String TOKENS_FILE = "spotify_tokens.properties";

  @Override
  public void saveTokens(String accessToken, String refreshToken, long expirationTime) {
    Properties properties = new Properties();
    properties.setProperty("accessToken", accessToken);
    properties.setProperty("refreshToken", refreshToken);
    properties.setProperty("expirationTime", String.valueOf(expirationTime));

    try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENS_FILE)) {
      properties.store(fileOutputStream, "Spotify tokens");
    } catch (Exception e) {
      log.error("Failed to save tokens to file", e);
      throw new RuntimeException("Failed to save tokens to file", e);
    }
  }

  @Override
  public String getAccessToken() {
    Properties properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(TOKENS_FILE)) {
      properties.load(fileInputStream);
      return properties.getProperty("accessToken");
    } catch (Exception e) {
      log.error("Failed to get access token", e);
      throw new RuntimeException("Failed to get access token", e);
    }
  }

  @Override
  public String getRefreshToken() {
    Properties properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(TOKENS_FILE)) {
      properties.load(fileInputStream);
      return properties.getProperty("refreshToken");
    } catch (Exception e) {
      log.error("Failed to get refresh token", e);
      throw new RuntimeException("Failed to get refresh token", e);
    }
  }
}
