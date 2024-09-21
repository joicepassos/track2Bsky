package com.jfp.tack2Bsky.client.dto.response;

import java.util.Map;

public record AuthenticationResponse(
        String accessJwt,
        String refreshJwt,
        String handle,
        String did,
        Map<String, Object> didDoc,
        String email,
        boolean emailConfirmed,
        boolean emailAuthFactor,
        boolean active,
        String status
) {}
