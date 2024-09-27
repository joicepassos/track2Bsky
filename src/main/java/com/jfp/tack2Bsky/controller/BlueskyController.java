package com.jfp.tack2Bsky.controller;

import com.jfp.tack2Bsky.service.BlueskyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify")
public class BlueskyController {

  private final BlueskyService blueskyService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(
      @RequestParam(value = "username") final String username,
      @RequestParam(value = "password") final String password) {
    blueskyService.login(username, password);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/create-post")
  public ResponseEntity<Void> createPost(
          @RequestParam(value = "content" ) final String content,
          @RequestParam(value = "username") final String username,
            @RequestParam(value = "password") final String password) {
    blueskyService.createPost(content, username, password);
    return ResponseEntity.ok().build();
  }
}
