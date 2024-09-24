package com.jfp.tack2Bsky.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record CurrentlyPlayingResponse(
    @JsonProperty("device") Device device,
    @JsonProperty("repeat_state") String repeatState,
    @JsonProperty("shuffle_state") boolean shuffleState,
    @JsonProperty("context") Context context,
    @JsonProperty("timestamp") long timestamp,
    @JsonProperty("progress_ms") long progressMs,
    @JsonProperty("is_playing") boolean isPlaying,
    @JsonProperty("item") Item item,
    @JsonProperty("currently_playing_type") String currentlyPlayingType,
    @JsonProperty("actions") Actions actions) {

  public record Device(
      @JsonProperty("id") String id,
      @JsonProperty("is_active") boolean isActive,
      @JsonProperty("is_private_session") boolean isPrivateSession,
      @JsonProperty("is_restricted") boolean isRestricted,
      @JsonProperty("name") String name,
      @JsonProperty("type") String type,
      @JsonProperty("volume_percent") int volumePercent,
      @JsonProperty("supports_volume") boolean supportsVolume) {}

  public record Context(
      @JsonProperty("type") String type,
      @JsonProperty("href") String href,
      @JsonProperty("external_urls") ExternalUrls externalUrls,
      @JsonProperty("uri") String uri) {
    public record ExternalUrls(@JsonProperty("spotify") String spotify) {}
  }

  public record Item(
      @JsonProperty("album") Album album,
      @JsonProperty("artists") List<Artist> artists,
      @JsonProperty("available_markets") List<String> availableMarkets,
      @JsonProperty("disc_number") int discNumber,
      @JsonProperty("duration_ms") long durationMs,
      @JsonProperty("explicit") boolean explicit,
      @JsonProperty("external_ids") ExternalIds externalIds,
      @JsonProperty("external_urls") ExternalUrls externalUrls,
      @JsonProperty("href") String href,
      @JsonProperty("id") String id,
      @JsonProperty("is_playable") boolean isPlayable,
      @JsonProperty("linked_from") LinkedFrom linkedFrom,
      @JsonProperty("restrictions") Restrictions restrictions,
      @JsonProperty("name") String name,
      @JsonProperty("popularity") int popularity,
      @JsonProperty("preview_url") String previewUrl,
      @JsonProperty("track_number") int trackNumber,
      @JsonProperty("type") String type,
      @JsonProperty("uri") String uri,
      @JsonProperty("is_local") boolean isLocal) {

    public record Album(
        @JsonProperty("album_type") String albumType,
        @JsonProperty("total_tracks") int totalTracks,
        @JsonProperty("available_markets") List<String> availableMarkets,
        @JsonProperty("external_urls") ExternalUrls externalUrls,
        @JsonProperty("href") String href,
        @JsonProperty("id") String id,
        @JsonProperty("images") List<Image> images,
        @JsonProperty("name") String name,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("release_date_precision") String releaseDatePrecision,
        @JsonProperty("restrictions") Restrictions restrictions,
        @JsonProperty("type") String type,
        @JsonProperty("uri") String uri,
        @JsonProperty("artists") List<Artist> artists) {

      public record Image(
          @JsonProperty("url") String url,
          @JsonProperty("height") int height,
          @JsonProperty("width") int width) {}

      public record Restrictions(@JsonProperty("reason") String reason) {}

      public record Artist(
          @JsonProperty("external_urls") ExternalUrls externalUrls,
          @JsonProperty("href") String href,
          @JsonProperty("id") String id,
          @JsonProperty("name") String name,
          @JsonProperty("type") String type,
          @JsonProperty("uri") String uri) {}
    }

    public record ExternalIds(
        @JsonProperty("isrc") String isrc,
        @JsonProperty("ean") String ean,
        @JsonProperty("upc") String upc) {}

    public record ExternalUrls(@JsonProperty("spotify") String spotify) {}

    public record LinkedFrom() {}

    public record Restrictions(@JsonProperty("reason") String reason) {}

    public record Artist(
        @JsonProperty("external_urls") ExternalUrls externalUrls,
        @JsonProperty("href") String href,
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("uri") String uri) {}
  }

  public record Actions(
      @JsonProperty("interrupting_playback") boolean interruptingPlayback,
      @JsonProperty("pausing") boolean pausing,
      @JsonProperty("resuming") boolean resuming,
      @JsonProperty("seeking") boolean seeking,
      @JsonProperty("skipping_next") boolean skippingNext,
      @JsonProperty("skipping_prev") boolean skippingPrev,
      @JsonProperty("toggling_repeat_context") boolean togglingRepeatContext,
      @JsonProperty("toggling_shuffle") boolean togglingShuffle,
      @JsonProperty("toggling_repeat_track") boolean togglingRepeatTrack,
      @JsonProperty("transferring_playback") boolean transferringPlayback,
      @JsonProperty("disallows") Map<String, Boolean> disallows) {}
}
