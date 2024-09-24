package com.jfp.tack2Bsky.service.impl;

import com.jfp.tack2Bsky.dto.ListeningResponse;
import com.jfp.tack2Bsky.service.BlueskyService;
import com.jfp.tack2Bsky.service.SpotifyService;
import com.jfp.tack2Bsky.service.TrackSyncService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackSyncServiceImpl implements TrackSyncService {

  private final BlueskyService blueskyService;
  private final SpotifyService spotifyService;

  @Override
  public void syncTracks(final String username, final String password) {
    blueskyService.login(username, password);
    ListeningResponse listeningResponse = spotifyService.getCurrentlyPlaying();
    log.info("Syncing tracks from Spotify to Bluesky.");
    String description = getTrackDescription(listeningResponse);
    blueskyService.changeProfileDescription(
        username, password, description);
  }

  private String getTrackDescription(ListeningResponse listeningResponse) {
    return String.format(
        "Ouvindo %s -  %s",
        listeningResponse.trackName(),
        listeningResponse.artists().stream()
            .map(ListeningResponse.Artist::name)
            .collect(Collectors.joining(", ")));
  }
}
