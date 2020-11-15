package hr.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartRestServices {
    public StartRestServices() {
    }

    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }
}
