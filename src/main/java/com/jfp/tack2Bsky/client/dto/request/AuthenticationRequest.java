package com.jfp.tack2Bsky.client.dto.request;

public record AuthenticationRequest(
        String identifier,
        String password
) {
}
