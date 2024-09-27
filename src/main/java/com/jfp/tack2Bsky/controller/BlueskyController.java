package com.jfp.tack2Bsky.controller;

import static com.jfp.tack2Bsky.util.Track2BskyUtil.TOKEN_INTERNAL;

import com.jfp.tack2Bsky.service.BlueskyService;
import com.jfp.tack2Bsky.util.CheckToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify")
public class BlueskyController extends CheckToken {

  private final BlueskyService blueskyService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(
      @RequestHeader(TOKEN_INTERNAL) String token,
      @RequestParam(value = "username") final String username,
      @RequestParam(value = "password") final String password) {
    if (isValidToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    blueskyService.login(username, password);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/create-post")
  public ResponseEntity<Void> createPost(
      @RequestHeader(TOKEN_INTERNAL) String token,
      @RequestParam(value = "content") final String content,
      @RequestParam(value = "username") final String username,
      @RequestParam(value = "password") final String password) {
    if (isValidToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    blueskyService.createPost(content, username, password);
    return ResponseEntity.ok().build();
  }
}
