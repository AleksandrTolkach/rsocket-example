package by.toukach.rsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsocketClientApplication {

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(RsocketClientApplication.class, args);

    Thread.sleep(10000);
  }
}
