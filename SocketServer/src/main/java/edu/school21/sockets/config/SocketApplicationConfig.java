package edu.school21.sockets.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan
public class SocketApplicationConfig {
    @Bean("encoder")
    public PasswordEncoder getIncoder(){
        return new BCryptPasswordEncoder();
    }
        }



