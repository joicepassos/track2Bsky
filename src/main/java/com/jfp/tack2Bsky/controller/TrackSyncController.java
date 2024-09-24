package com.jfp.tack2Bsky.controller;

import com.jfp.tack2Bsky.service.TrackSyncService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackSyncController {

  private final TrackSyncService trackSyncService;

  @PostMapping("/sync")
  public ResponseEntity<Void> syncTracks(
      @RequestParam(value = "username") final String username,
      @RequestParam(value = "password") final String password) {
    trackSyncService.syncTracks(username, password);
    return ResponseEntity.ok().build();
  }
}
