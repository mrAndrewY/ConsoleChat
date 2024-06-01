package edu.school21.sockets.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("edu.school21.sockets")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        }
    }
