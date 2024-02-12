package by.toukach.websocketserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class WebsocketController {

  @MessageMapping("/greeting/{name}")
  public Mono<String> greetingController(@DestinationVariable("name") String name,
      Mono<String> greetingMono) {
    return greetingMono
        .doOnNext(greeting -> log.info("Received a greeting from {}: {}", name, greeting))
        .map(greeting -> "Hello to you, too, %s!".formatted(name));
  }
}
