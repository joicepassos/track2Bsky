package com.jfp.tack2Bsky.controller;

import com.jfp.tack2Bsky.service.SpotifyService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify")
public class SpotifyController {

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
}
