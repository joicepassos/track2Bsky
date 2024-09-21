package com.jfp.tack2Bsky.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyAutheticationResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("scope") String scope,
    @JsonProperty("expires_in") int expiresIn,
    @JsonProperty("refresh_token") String refreshToken) {}
