package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;
import com.jfp.tack2Bsky.service.SpotifyTokenService;
import com.jfp.tack2Bsky.service.TokenStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpotifyTokenServiceImpl implements SpotifyTokenService {

  private final TokenStorageService tokenStorageService;

  public SpotifyTokenServiceImpl(TokenStorageService tokenStorageService) {
    this.tokenStorageService = tokenStorageService;
  }

  @Override
  public void saveAccessToken(final SpotifyAutheticationResponse spotifyAutheticationResponse) {
    long expirationTime =
        System.currentTimeMillis() + spotifyAutheticationResponse.expiresIn() * 1000L;

    tokenStorageService.saveTokens(
        spotifyAutheticationResponse.accessToken(),
        spotifyAutheticationResponse.refreshToken(),
        expirationTime);
  }

  @Override
  public String getAccessToken() {
    return tokenStorageService.getAccessToken();
  }

  @Override
  public String getRefreshToken() {
    return tokenStorageService.getRefreshToken();
  }
}
