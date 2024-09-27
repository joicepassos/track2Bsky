package com.jfp.tack2Bsky.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyAutheticationRequest(
    @JsonProperty("grant_type") String grantType,
    @JsonProperty("token_type") String code,
    @JsonProperty("redirect_uri") String redirectUri) {}
