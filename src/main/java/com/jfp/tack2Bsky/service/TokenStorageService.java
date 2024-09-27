package com.jfp.tack2Bsky.service;

public interface TokenStorageService {
  void saveTokens(String accessToken, String refreshToken, long expirationTime);

  String getAccessToken();

  String getRefreshToken();
}
