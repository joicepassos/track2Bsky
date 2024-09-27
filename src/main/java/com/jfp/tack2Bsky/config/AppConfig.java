package com.jfp.tack2Bsky.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

  @Bean
  RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }

  @Bean
  OpenAPI openApi() {
    var server = new Server();
    String context = "/track2bsky";
    String url = "http://localhost";
    String port = "8080";
    server.setUrl(url + ":" + port + context);
    server.setDescription("local");
    String version = "v1.0";
    String title = "Track2Bsky";
    return new OpenAPI().info(new Info().title(title).version(version)).servers(List.of(server));
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
