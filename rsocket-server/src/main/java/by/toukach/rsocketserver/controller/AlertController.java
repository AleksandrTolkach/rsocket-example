package by.toukach.rsocketserver.controller;

import by.toukach.rsocketserver.dto.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class AlertController {

  @MessageMapping("alert")
  public Mono<Void> setAlert(Mono<Alert> alertMono) {
    return alertMono
        .doOnNext(alert -> {
          log.info("{} alert, ordered by {} at {}",
              alert.getLevel(), alert.getOrderedBy(), alert.getOrderedAt());
        })
        .thenEmpty(Mono.empty());
  }
}
