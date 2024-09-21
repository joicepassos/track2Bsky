package com.jfp.tack2Bsky.client;

import com.jfp.tack2Bsky.client.dto.request.BskyAuthenticationRequest;
import com.jfp.tack2Bsky.client.dto.response.BskyAuthenticationResponse;
import com.jfp.tack2Bsky.util.Track2BskyUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlueskyClient {

  private final RestClient restClient;

  @Value("${app.bluesky.url}")
  private String blueskyUrl;

  @Value("${app.bluesky.identifier}")
  private String blueskyUsername;

  @Value("${app.bluesky.password")
  private String blueskyPassword;

  public ResponseEntity<BskyAuthenticationResponse> authenticate() {
    log.info("Authenticating with Bluesky...");
    String uri = "%s/com.atproto.server.createSession".formatted(blueskyUrl);

    BskyAuthenticationRequest bskyAuthenticationRequest =
        new BskyAuthenticationRequest(blueskyUsername, blueskyPassword);
    return restClient
        .post()
        .uri(uri)
        .body(bskyAuthenticationRequest)
        .headers(Track2BskyUtil.getheaders())
        .retrieve()
        .onStatus(HttpStatusCode::isError, this::handleException)
        .toEntity(BskyAuthenticationResponse.class);
  }

  private void handleException(final HttpRequest request, final ClientHttpResponse response)
      throws IOException {
    log.error("Failed to authenticate with Bluesky...");
    throw new RestClientException(
        "Failed to authenticate with Bluesky...",
        new RestClientResponseException(
            "Failed to authenticate with Bluesky...",
            response.getStatusCode(),
            response.getStatusText(),
            response.getHeaders(),
            null,
            null));
  }
}
