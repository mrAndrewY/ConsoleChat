package edu.school21.sockets.server;
import edu.school21.sockets.services.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Component
@Setter
public class Server implements ApplicationListener<ContextRefreshedEvent> {
    private static List<ChatThreads>  enteredClients;
    private static List<ChatThreads>  chatClients;
    private static ServerSocket server;
    @Value("${server.port}")
    private int port;
  //  private String driverName;
    @Autowired
    @Qualifier("usersServiceImpl")
    private UsersService userService;
    @Autowired
    @Qualifier("chatroomServiceImpl")
    private  ChatroomService chatroomService;
    @Autowired
    @Qualifier("messagesServiceImpl")
    private  MessagesService messagesService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            try {
                server = new ServerSocket(port);
                enteredClients= Collections.synchronizedList(new ArrayList<ChatThreads>());
                chatClients= Collections.synchronizedList(new ArrayList<ChatThreads>());

                while (true) {
                    Socket newSocket = server.accept();
                    ChatThreads newThread =  new ChatThreads(newSocket, userService, chatroomService, messagesService, chatClients);
                    enteredClients.add(newThread);
                    newThread.start();
                }
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                System.out.println("closing server");
                server.close();
            }
        } catch (IOException ex1) {
            System.err.println(ex1);
        }
    }



    @Configuration
    @PropertySource("classpath:db.properties")
    public static class Config{
        @Value("${db.url}")
        private String url;
        @Value("${db.user}")
        private String user;
        @Value("${db.password}")
        private String password;
        @Value("${db.driver.name}")
        @Bean
        public HikariDataSource getJdbcImpl() {
            HikariConfig hc = new HikariConfig();
            hc.setJdbcUrl(url);
            hc.setUsername(user);
            hc.setPassword(password);
            return new HikariDataSource(hc);
        }


    }

}