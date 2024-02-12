package by.toukach.rsocketclient.client;

import by.toukach.rsocketclient.dto.Alert;
import by.toukach.rsocketclient.dto.Alert.Level;
import by.toukach.rsocketclient.dto.GratuityIn;
import by.toukach.rsocketclient.dto.GratuityOut;
import by.toukach.rsocketclient.dto.StockQuote;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class RsocketClientConfiguration {

  @Bean
  public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
    return args -> {
      RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

      tcp
          .route("greeting/{name}", "Alex")
          .data("Hello RSocket!")
          .retrieveMono(String.class)
          .subscribe(response -> log.info("Got a response: {}", response));
    };
  }

  @Bean
  public ApplicationRunner stockQuoteRunner(RSocketRequester.Builder requesterBuilder) {
    return args -> {
      RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

      tcp
          .route("stock/{symbol}", "XYZ")
          .retrieveFlux(StockQuote.class)
          .doOnNext(stockQuote ->
              log.info("Price of {} : {} (at {})",
                  stockQuote.getSymbol(), stockQuote.getPrice(), stockQuote.getTimestamp()))
          .subscribe();
    };
  }

  @Bean
  public ApplicationRunner alertRunner(RSocketRequester.Builder requesterBuilder) {
    return args -> {
      RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

      tcp
          .route("alert")
          .data(new Alert(Level.RED, "Craig", Instant.now()))
          .send()
          .subscribe();
      log.info("Alert sent");
    };
  }

  @Bean
  public ApplicationRunner gratuityRunner(RSocketRequester.Builder requesterBuilder) {
    return args -> {
      RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

      Flux<GratuityIn> gratuityInFlux =
          Flux.fromIterable(List.of(
              new GratuityIn(BigDecimal.valueOf(35.50), 18),
              new GratuityIn(BigDecimal.valueOf(10.00), 15),
              new GratuityIn(BigDecimal.valueOf(23.25), 20),
              new GratuityIn(BigDecimal.valueOf(52.75), 18),
              new GratuityIn(BigDecimal.valueOf(80.00), 15)
          ));

      tcp
          .route("gratuity")
          .data(gratuityInFlux)
          .retrieveFlux(GratuityOut.class)
          .subscribe(out -> {
            log.info("{} % gratuity on {} is {} ", out.getPercent(), out.getBillTotal(),
                out.getGratuity());
          });
      log.info("Alert sent");
    };
  }
}
