package com.jfp.tack2Bsky.client;

import static com.jfp.tack2Bsky.util.Track2BskyUtil.generateRandomString;

import com.jfp.tack2Bsky.client.dto.response.SpotifyAutheticationResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpotifyClient {

  private final RestClient restClient;

  @Value("${app.spotify.accounts_url}")
  private String spotifyUrl;

  @Value("${app.spotify.client_id}")
  private String clientId;

  @Value("${app.spotify.client_secret}")
  private String clientSecret;

  @Value("${app.spotify.redirect_uri}")
  private String redirectUri;

  @Value("${app.spotify.scopes}")
  private String scope;

  public URI getAuthorizationUri() {
    log.info("GettingAuthorizationUri - Spotify");
    String state = generateRandomString(16);
    String authorizationUrl = "https://accounts.spotify.com/authorize";
    return UriComponentsBuilder.fromUriString(authorizationUrl)
        .queryParam("response_type", "code")
        .queryParam("client_id", clientId)
        .queryParam("scope", scope)
        .queryParam("redirect_uri", redirectUri)
        .queryParam("state", state)
        .build()
        .toUri();
  }

  public SpotifyAutheticationResponse exchangeCodeForToken(String code) {
    log.info("ExchangingCodeForToken - Spotify - code: {}", code);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("code", code);
    body.add("redirect_uri", redirectUri);

    ResponseEntity<SpotifyAutheticationResponse> response =
        restClient
            .post()
            .uri(spotifyUrl + "/api/token")
            .headers(headers())
            .body(body)
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleException)
            .toEntity(SpotifyAutheticationResponse.class);

    log.info("ExchangedCodeForToken - Spotify  - code: {}", code);

    return response.getBody();
  }

  private Consumer<HttpHeaders> headers() {
    return headers -> {
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      String auth =
              Base64.getEncoder()
                      .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
      headers.set("Authorization", "Basic " + auth);
    };
  }

  private void handleException(final HttpRequest request, final ClientHttpResponse response)
      throws IOException {
    log.error("Failed to authenticate with Spotify - response: {}", response);
    throw new RestClientException(
        "Failed to authenticate with Spotify...",
        new RestClientResponseException(
            "Failed to authenticate with Spotify...",
            response.getStatusCode(),
            response.getStatusText(),
            response.getHeaders(),
            null,
            null));
  }
}
