package by.toukach.rsocketserver.controller;

import by.toukach.rsocketserver.dto.GratuityIn;
import by.toukach.rsocketserver.dto.GratuityOut;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
public class GratuityController {

  @MessageMapping("gratuity")
  public Flux<GratuityOut> calculate(Flux<GratuityIn> gratuityInFlux) {
    return gratuityInFlux
        .doOnNext(in -> log.info("Calculating gratuity: {}", in))
        .map(in -> {
          double percentAsDecimal = in.getPercent() / 100.0;
          BigDecimal gratuity = in.getBillTotal()
              .multiply(BigDecimal.valueOf(percentAsDecimal));
          return new GratuityOut(in.getBillTotal(), in.getPercent(), gratuity);
        });
  }
}
