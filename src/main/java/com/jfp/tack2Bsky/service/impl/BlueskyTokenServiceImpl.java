package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.dto.response.BskyAuthenticationResponse;
import com.jfp.tack2Bsky.dto.TokenCredentials;
import com.jfp.tack2Bsky.service.BlueskyTokenService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlueskyTokenServiceImpl implements BlueskyTokenService {

  private static final String TOKENS_FILE = "bluesky_tokens.properties";

  // TODO::Save tokens with firebase or other cloud storage

  @Override
  public void saveAccessToken(final BskyAuthenticationResponse bskyAuthResponse) {
    Properties properties = new Properties();
    properties.setProperty("accessToken", bskyAuthResponse.accessJwt());
    properties.setProperty("refreshToken", bskyAuthResponse.refreshJwt());
    properties.setProperty("did", bskyAuthResponse.did());

    try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENS_FILE)) {
      properties.store(fileOutputStream, "Bluesky tokens");
    } catch (Exception e) {
      log.error("Failed to save tokens to file", e);
      throw new RuntimeException("Failed to save tokens to file", e);
    }
  }

  @Override
  public void refreshAccessToken(final BskyAuthenticationResponse bskyAuthResponse) {
    log.info("Refreshing access token...");
    Properties properties = new Properties();
    try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENS_FILE)) {
      properties.setProperty("accessToken", bskyAuthResponse.accessJwt());
      properties.setProperty("refreshToken", bskyAuthResponse.refreshJwt());
      properties.store(fileOutputStream, "Bluesky tokens");
    } catch (Exception e) {
      log.error("Failed to refresh access token", e);
      throw new RuntimeException("Failed to refresh access token", e);
    }
  }

  @Override
  public TokenCredentials getAccessToken() {
    Properties properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(TOKENS_FILE)) {
      properties.load(fileInputStream);

      TokenCredentials tokenCredentials =
          new TokenCredentials(
              properties.getProperty("accessToken"),
              properties.getProperty("refreshToken"),
              properties.getProperty("did"),
              "bluesky");

      return tokenCredentials;
    } catch (Exception e) {
      log.error("Failed to get access token", e);
      throw new RuntimeException("Failed to get access token", e);
    }
  }
}
