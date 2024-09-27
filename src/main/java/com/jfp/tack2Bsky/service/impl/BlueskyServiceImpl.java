package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.client.BlueskyClient;
import com.jfp.tack2Bsky.client.dto.response.BskyAuthenticationResponse;
import com.jfp.tack2Bsky.dto.TokenCredentials;
import com.jfp.tack2Bsky.service.BlueskyService;
import com.jfp.tack2Bsky.service.BlueskyTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlueskyServiceImpl implements BlueskyService {

  private final BlueskyClient blueskyClient;
  private final BlueskyTokenService blueskyTokenService;

  public void login(final String username, final String password) {
    log.info("StartingBlueskyLoginProcess - User: {}", username);
    BskyAuthenticationResponse bskyAuthenticationResponse =
        blueskyClient.authenticate(username, password);
    blueskyTokenService.saveAccessToken(bskyAuthenticationResponse);
    log.info("BlueskyLoginFinished - User: {}", username);
  }

  @Override
  public void changeProfileDescription(String username, String password, String description) {
    log.info("ChangingProfileDescriptionBluesky - User: {}", username);
    blueskyClient.updateProfileDescription(username, password, description);
    log.info("ProfileDescriptionChangedBluesky - User: {}", username);
  }

  //TODO:: Implement mensal post creation with spotify listening history analysis
  @Override
  public void createPost(String content, String username, String password) {
    TokenCredentials tokenCredentials = blueskyTokenService.getAccessToken();
    log.info("CreatingPostBluesky - User: {}", tokenCredentials.did());
    blueskyClient.createPost(username, password ,content);
    log.info("PostCreatedBluesky - User: {}", tokenCredentials.did());
  }
}
