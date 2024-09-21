package com.jfp.tack2Bsky.util;

import java.util.Collections;
import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Track2BskyUtil {

  public static Consumer<HttpHeaders> getheaders() {
    return httpHeaders -> {
      httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    };
  }

  public static String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int index = (int) (characters.length() * Math.random());
      stringBuilder.append(characters.charAt(index));
    }
    return stringBuilder.toString();
  }
}
