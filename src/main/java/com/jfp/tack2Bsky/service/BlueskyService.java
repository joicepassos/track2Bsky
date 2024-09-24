package com.jfp.tack2Bsky.service;

public interface BlueskyService {

  void login(String username, String password);

  void changeProfileDescription(String token, String did, String description);

  void createPost(String username, String password, String content);
}
