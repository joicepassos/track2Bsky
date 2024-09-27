package com.jfp.tack2Bsky.client;

import com.jfp.tack2Bsky.client.dto.response.BskyAuthenticationResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlueskyClient {

  private static final String AUTHENTICATION_URI =
      "https://bsky.social/xrpc/com.atproto.server.createSession";

  private final RestClient restClient;

  @Value("${app.bluesky.url}")
  private String blueskyUrl;

  private String tokenJwt;
  private String did;

  public BskyAuthenticationResponse authenticate(final String username, final String password) {
    log.info("Authenticating with Bluesky...");
    Map<String, String> credentials = Map.of("identifier", username, "password", password);

    BskyAuthenticationResponse response =
        postRequest(AUTHENTICATION_URI, credentials, null, BskyAuthenticationResponse.class);
    tokenJwt = response.accessJwt();
    did = response.did();

    return response;
  }

  public void updateProfileDescription(String username, String password, String description) {
    String uri = blueskyUrl + "/com.atproto.repo.putRecord";
    authenticate(username, password);

    Map<String, Object> currentProfile =
        (Map<String, Object>) getCurrentProfile(tokenJwt, did).get("value");

    log.info("Updating profile description on Bluesky - Current Profile: {}", currentProfile);

    String currentDescription = currentProfile.get("description").toString();
    if (currentDescription.equals(description)) {
      description = currentDescription;
    } else {
      if (currentDescription.contains("Listening")) {
        int index = currentDescription.indexOf("Listening");
        currentDescription = currentDescription.substring(0, index);
      }
      description = currentDescription + " " + description;
    }

    Map<String, Object> record = new HashMap<>();
    record.put("avatar", currentProfile.get("avatar"));
    record.put("banner", currentProfile.get("banner"));
    record.put("description", description);
    record.put("displayName", currentProfile.get("displayName"));

    log.info("Updating profile description on Bluesky - New Profile: {}", record);

    Map<String, Object> profileUpdate = new HashMap<>();
    profileUpdate.put("collection", "app.bsky.actor.profile");
    profileUpdate.put("repo", did);
    profileUpdate.put("rkey", "self");
    profileUpdate.put("record", record);

    postRequest(uri, profileUpdate, tokenJwt, String.class);
  }

  private Map getCurrentProfile(String token, String did) {
    String uri =
        blueskyUrl
            + "/com.atproto.repo.getRecord?collection=app.bsky.actor.profile&repo="
            + did
            + "&rkey=self";

    return restClient
        .get()
        .uri(uri)
        .headers(headersWithBearer(token))
        .retrieve()
        .onStatus(HttpStatusCode::isError, this::handleException)
        .toEntity(Map.class)
        .getBody();
  }

  private <T> T postRequest(String uri, Object body, String token, Class<T> responseType) {
    return restClient
        .post()
        .uri(uri)
        .body(body)
        .headers(headersWithBearer(token))
        .retrieve()
        .onStatus(HttpStatusCode::isError, this::handleException)
        .toEntity(responseType)
        .getBody();
  }

  public void createPost(String username, String password, String content) {

    String uri = blueskyUrl + "/com.atproto.repo.createRecord";

    authenticate(username, password);

    log.info("Creating post on Bluesky...");

    Map<String, Object> record = new HashMap<>();
    record.put("text", content);
    record.put("createdAt", OffsetDateTime.now().toString());

    Map<String, Object> postBody = new HashMap<>();
    postBody.put("collection", "app.bsky.feed.post");
    postBody.put("repo", did);
    postBody.put("record", record);

    log.info("Creating post on Bluesky - URI: {}", uri + " - Body: " + postBody);
    postRequest(uri, postBody, tokenJwt, String.class);
  }

  private static Consumer<HttpHeaders> headersWithBearer(String token) {
    return httpHeaders -> {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      if (token != null) {
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
      }
    };
  }

  private void handleException(final HttpRequest request, final ClientHttpResponse response)
      throws IOException {
    log.error("Failed to process the request on Bluesky...");
    throw new RestClientException(
        "Failed to process the request on Bluesky. Status: " + response.getStatusCode(),
        new RestClientResponseException(
            "Failed to process the request on Bluesky.",
            response.getStatusCode(),
            response.getStatusText(),
            response.getHeaders(),
            response.getBody().readAllBytes(),
            null));
  }
}
