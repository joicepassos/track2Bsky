package com.jfp.tack2Bsky.dto;

import java.util.List;

public record ListeningResponse(String trackName, List<Artist> artists) {

  public record Artist(String name) {}
}
