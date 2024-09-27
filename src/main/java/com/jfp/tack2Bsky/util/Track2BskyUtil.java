package com.jfp.tack2Bsky.util;

public class Track2BskyUtil {

  public static final String TOKEN_INTERNAL = "x-internal-token";

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
