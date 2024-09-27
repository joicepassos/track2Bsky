package com.jfp.tack2Bsky.controller;

import static com.jfp.tack2Bsky.util.Track2BskyUtil.TOKEN_INTERNAL;

import com.jfp.tack2Bsky.service.TrackSyncSchedulerService;
import com.jfp.tack2Bsky.service.TrackSyncService;
import com.jfp.tack2Bsky.util.CheckToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackSyncController extends CheckToken {

  private final TrackSyncService trackSyncService;
  private final TrackSyncSchedulerService trackSyncSchedulerService;

  @PostMapping("/sync")
  public ResponseEntity<Void> syncTracks(
      @RequestParam(value = TOKEN_INTERNAL) final String token,
      @RequestParam(value = "username") final String username,
      @RequestParam(value = "password") final String password) {
    if (isValidToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    trackSyncService.syncTracks(username, password);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sync-periodically")
  public ResponseEntity<Void> syncTracksPeriodically(
      @RequestParam(value = TOKEN_INTERNAL) final String token) {
    if (isValidToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    trackSyncSchedulerService.syncTracksPeriodically();
    return ResponseEntity.ok().build();
  }
}
