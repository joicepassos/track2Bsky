package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.service.TrackSyncSchedulerService;
import com.jfp.tack2Bsky.service.TrackSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackSyncScheduler implements TrackSyncSchedulerService {

  @Value("${app.bluesky.identifier}")
  private String username;

  @Value("${app.bluesky.password}")
  private String password;

  private final TrackSyncService trackSyncService;

  @Scheduled(fixedRate = 180000)
  public void syncTracksPeriodically() {
    log.info("Starting scheduled track sync...");
    trackSyncService.syncTracks(username, password);
    log.info("Scheduled track sync completed.");
  }
}
