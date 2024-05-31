package edu.school21.sockets.server;
import edu.school21.sockets.repositories.ChatroomRepositoryImpl;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.services.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static ApplicationContext context;

    private static Socket clientSocket;
    private static List  enteredClients;
    private static List  chatClients;
    private static ServerSocket server;

    private int port;
    private String driverName;
    private static UsersService userService;
    private static ChatroomService chatroomService;
    private static MessagesService messagesService;
    static{
          context=new AnnotationConfigApplicationContext("edu.school21.sockets");
          userService=context.getBean(UsersServiceImpl.class);
          chatroomService=context.getBean(ChatroomServiceImpl.class);
          messagesService = context.getBean(MessagesService.class);
     }

    public Server(int port){
        this.port=port;
    }
    public void startServer() {
        try {
            try {
                server = new ServerSocket(port);
                enteredClients= Collections.synchronizedList(new ArrayList<ChatThreads>());
                chatClients= Collections.synchronizedList(new ArrayList<ChatThreads>());

                while (true) {
                    Socket newSocket = server.accept();
                    Thread newThread =  new ChatThreads(newSocket, userService, chatroomService, messagesService, chatClients);
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