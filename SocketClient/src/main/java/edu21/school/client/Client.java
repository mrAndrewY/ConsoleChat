package edu21.school.client;


import java.io.*;
import java.net.Socket;


public class Client {

    private Socket clientSocket;
    private BufferedReader clientInputReader;
    private BufferedReader in;
    private BufferedWriter out;
    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public void startClient() {
        try {
            try {
                clientSocket = new Socket("localhost", port);
                clientInputReader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                Thread write = new Thread(() -> {
                    while (!clientSocket.isClosed()) {
                        try {
                            writeMessage();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }
                    }
                });

                Thread read = new Thread (()->{
                    String message;
                    try {
                        while ((message = in.readLine()) != null) {
                            System.out.println(message);
                        }
                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                });
                write.start();
                read.start();
                while (!clientSocket.isClosed()) {
                }
            } finally {
                System.out.println("Клиент был закрыт...");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeMessage() throws IOException {
        String word = clientInputReader.readLine() + "\n";
        out.write(word);
        out.flush();
    }
}

