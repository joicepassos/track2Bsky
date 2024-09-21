package com.jfp.tack2Bsky.service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SpotifyService {

  void login(HttpServletResponse httpServletResponse) throws IOException;

  String callback(String code, String state, String error);
}
