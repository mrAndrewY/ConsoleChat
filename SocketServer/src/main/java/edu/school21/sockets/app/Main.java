package edu.school21.sockets.app;
import edu.school21.sockets.server.Server;


public class Main {
    public static void main(String[] args) {

        int port=0;
        if(args.length==1) {
            String []arg= args[0].split("=");
            port = Integer.parseInt(arg[1]);
        }
        Server server = new Server(port);
        server.startServer();
        }
    }
