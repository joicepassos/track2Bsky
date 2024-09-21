package com.jfp.tack2Bsky.service;

import com.jfp.tack2Bsky.client.dto.response.CurrentlyPlayingResponse;
import com.jfp.tack2Bsky.dto.ListeningResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SpotifyService {

  void login(HttpServletResponse httpServletResponse) throws IOException;

  String callback(String code, String state, String error);

  ListeningResponse getCurrentlyPlaying();
}
