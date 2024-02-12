package by.toukach.websocketclient;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Slf4j
@Configuration
public class WebsocketClientConfiguration {

  @Bean
  public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
    return args -> {
      RSocketRequester requester =
          requesterBuilder.websocket(URI.create("http://localhost:8080/rsocket"));

      requester
          .route("/greeting/{name}", "Alex")
          .data("Hello RSocket!")
          .retrieveMono(String.class)
          .subscribe(response -> log.info("Got a response: {}", response));
    };
  }
}
