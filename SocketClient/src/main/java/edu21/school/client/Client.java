package edu21.school.client;

import java.io.*;
import java.net.Socket;



public class Client  {

    private static Socket clientSocket;
    private  BufferedReader clientInputReader;
    private  BufferedReader in;
    private  BufferedWriter out;
    private static int port;
    public Client(int port) {
        this.port=port;
    }
    public void startClient() {
        try {
            try {
                clientSocket = new Socket("localhost", port);
                clientInputReader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                Thread write = new MessagesWriter(out, clientInputReader, clientSocket);
                Thread read = new MessagesReader(in, out, clientSocket);
                write.start();
                read.start();
                while (!clientSocket.isClosed()) {
                }
            } finally {
                System.out.println("Клиент был закрыт...");

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

class MessagesWriter extends Thread {
    private final BufferedWriter out;
    private final BufferedReader clientInputReader;
    private final Socket clientSocket;
    MessagesWriter(BufferedWriter out, BufferedReader reader, Socket clientSocket) {
          this.out = out;
          this.clientInputReader = reader;
          this.clientSocket=clientSocket;
    }

    @Override
    public void run()  {
        while (!clientSocket.isClosed()){
          try {
              writeMessage();
          }catch (IOException ioe) {
              System.out.println(ioe.getMessage());
          }
        }
    }

    public void writeMessage ()throws IOException {
        String word = clientInputReader.readLine() + "\n";
        out.write(word);
        out.flush();
    }

}
class MessagesReader extends Thread {
    private  BufferedReader in;
    private  BufferedWriter out;

    private final Socket clientSocket;
    MessagesReader(BufferedReader in, BufferedWriter out,  Socket clientSocket) {
        this.in = in;
        this.out=out;
        this.clientSocket=clientSocket;
    }

    @Override
    public void run()  {
        String message;
        try {
        while ((message=in.readLine())!=null){
                System.out.println(message);
        }
            in.close();
            out.close();
            clientSocket.close();
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}