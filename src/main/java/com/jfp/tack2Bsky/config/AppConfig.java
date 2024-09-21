package com.jfp.tack2Bsky.config;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

  @Bean
  RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }

  private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
        Collections.singletonList(APPLICATION_FORM_URLENCODED));
    return mappingJackson2HttpMessageConverter;
  }

  @Bean
  OpenAPI openApi() {
    var server = new Server();
    String context = "/track2Bsky";
    String url = "http://localhost";
    String port = "8887";
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
