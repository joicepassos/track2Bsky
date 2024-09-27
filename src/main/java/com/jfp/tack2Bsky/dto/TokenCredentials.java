package com.jfp.tack2Bsky.dto;

public record TokenCredentials(String token, String refreshToken, String did, String tokenType) {}
