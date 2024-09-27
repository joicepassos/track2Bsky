package com.jfp.tack2Bsky.service;

import com.jfp.tack2Bsky.client.dto.response.BskyAuthenticationResponse;
import com.jfp.tack2Bsky.dto.TokenCredentials;

public interface BlueskyTokenService {

  void saveAccessToken(BskyAuthenticationResponse bskyAuthenticationResponse);

  TokenCredentials getAccessToken();
}
