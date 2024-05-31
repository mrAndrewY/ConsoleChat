package edu21.school.client.app;

import edu21.school.client.Client;

public class Main {
    public static void main(String[] args) {
        int port=0;
        if(args.length==1) {
            String []arg= args[0].split("=");
            port = Integer.parseInt(arg[1]);
        }
        Client client = new Client(port);
        client.startClient();
    }
}

