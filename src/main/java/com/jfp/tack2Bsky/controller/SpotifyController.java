package com.jfp.tack2Bsky.controller;

import static com.jfp.tack2Bsky.util.Track2BskyUtil.TOKEN_INTERNAL;

import com.jfp.tack2Bsky.dto.ListeningResponse;
import com.jfp.tack2Bsky.service.SpotifyService;
import com.jfp.tack2Bsky.util.CheckToken;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify")
public class SpotifyController extends CheckToken {

  private final SpotifyService spotifyService;

  @GetMapping("/login")
  public void login(HttpServletResponse response) throws IOException {
    spotifyService.login(response);
  }

  @GetMapping("/callback")
  public String callback(
      @RequestParam(value = "code") final String code,
      @RequestParam(value = "state") final String state,
      @RequestParam(value = "error", required = false) final String error) {
    return spotifyService.callback(code, state, error);
  }

  @GetMapping("/currently-playing")
  public ResponseEntity<ListeningResponse> getCurrentlyPlaying(
      @RequestParam(value = TOKEN_INTERNAL) final String token) {
    if (isValidToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(spotifyService.getCurrentlyPlaying());
  }
}
