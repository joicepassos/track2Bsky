package com.jfp.tack2Bsky.client;

import com.jfp.tack2Bsky.client.dto.request.AuthenticationRequest;
import com.jfp.tack2Bsky.client.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;

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


    public ResponseEntity<AuthenticationResponse> authenticate() {
        log.info("Authenticating with Bluesky...");
        String uri  = "%s/com.atproto.server.createSession".formatted(blueskyUrl);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                blueskyUsername,
                blueskyPassword
        );
        return restClient
                .post()
                .uri(uri)
                .body(authenticationRequest)
                .body(getheaders())
                .retrieve()
                .onStatus(HttpStatusCode::isError,this::handleException)
                .toEntity(AuthenticationResponse.class);
    }

    private Consumer<HttpHeaders> getheaders() {
        return httpHeaders -> {
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        };
    }

    private void handleException(final HttpRequest request, final ClientHttpResponse response) throws IOException {
        log.error("Failed to authenticate with Bluesky...");
        throw new RestClientException(
                "Failed to authenticate with Bluesky...",
                new RestClientResponseException(
                        "Failed to authenticate with Bluesky...",
                        response.getStatusCode(),
                        response.getStatusText(),
                        response.getHeaders(),
                        null,
                        null
                )
        );
    }

}
