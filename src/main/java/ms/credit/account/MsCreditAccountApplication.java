package ms.credit.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsCreditAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsCreditAccountApplication.class, args);
  }

}
