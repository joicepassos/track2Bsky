package com.jfp.tack2Bsky.util;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

public abstract class CheckToken {

  @Value("${app.track2bsky.internal_token}")
  private String tokenInternal;

  protected boolean isValidToken(String token) {
    return !Objects.equals(token, tokenInternal);
  }
}
