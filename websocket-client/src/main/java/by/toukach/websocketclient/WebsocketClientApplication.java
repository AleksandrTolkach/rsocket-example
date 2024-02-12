package by.toukach.websocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketClientApplication {

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(WebsocketClientApplication.class, args);
    Thread.sleep(1000L);
  }
}
