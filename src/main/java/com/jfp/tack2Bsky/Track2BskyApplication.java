package com.jfp.tack2Bsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Track2BskyApplication {

  public static void main(String[] args) {
    SpringApplication.run(Track2BskyApplication.class, args);
  }
}
