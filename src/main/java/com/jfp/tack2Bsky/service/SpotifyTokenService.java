package com.jfp.tack2Bsky.service;

import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;

public interface SpotifyTokenService {

  void saveAccessToken(SpotifyAutheticationResponse spotifyAutheticationResponse);

  String getAccessToken();

  String getRefreshToken();
}
