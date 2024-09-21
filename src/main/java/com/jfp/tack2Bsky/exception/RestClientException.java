package com.jfp.tack2Bsky.exception;

import lombok.Getter;

@Getter
public class RestClientException extends RuntimeException {

  private final int statusCode;

  public RestClientException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }
}
