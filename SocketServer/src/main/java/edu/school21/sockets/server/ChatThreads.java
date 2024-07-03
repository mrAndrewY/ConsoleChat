package edu.school21.sockets.server;

import edu.school21.sockets.services.ChatroomService;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;

import java.io.*;
import java.net.Socket;

import java.util.List;



public class ChatThreads extends Thread {
    private final Socket threadSocket;
    private final UsersService usersService;
    private final ChatroomService chatroomService;
    private final MessagesService messagesService;
    private long chatNumber;
    private  BufferedReader in;
    private  BufferedWriter out;
    String userName;
    private final List<ChatThreads> chatClients;
    public ChatThreads(Socket threadSocket, UsersService us, ChatroomService chS, MessagesService mService, List<ChatThreads> chatClients) {
        this.threadSocket=threadSocket;
        usersService=us;
        this.chatClients=chatClients;
        this.chatroomService=chS;
        messagesService=mService;
        try {
            in = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(threadSocket.getOutputStream()));

        }catch (IOException ioException){
            ioException.getStackTrace();
        }
    }
    @Override
    public void run()   {
      entryFunction();
    }

    public void entryFunction() {
          try{
            out.write("Hello from server!\n");
            out.flush();
            while(true) {
                out.write("enter option:\n 1. signIn\n 2. signUp\n 3. Exit\n");
                out.flush();
                String res = in.readLine();
                if (res!=null&&res.equals("1")) {
                    if(signInCommand()){
                        userPersonalRoom ();
                    }
                }
                if (res!=null&&res.equals("2")) {
                    signUpCommand();
                }

                if (res==null||res.equals("3")) {
                    threadSocket.close();
                    in.close();
                    out.close();
                }
            }
        }catch (IOException ioException){
            ioException.getStackTrace();
        }
    }

    public boolean signUpCommand()throws IOException{
        out.write("Enter username:\n");
        out.flush();
        System.out.println("entered SignUp");
        String userName = in.readLine();
        out.write("Enter password: \n");
        out.flush();
        String password = in.readLine();
        String message=usersService.signUp(userName, password);
        out.write(message+'\n');
        out.flush();
        return message.equals("Success");
    }
    public boolean signInCommand()throws IOException{
        out.write("Enter username:\n");
        out.flush();
        System.out.println("entered SignIn");
        String userName = in.readLine();
        this.userName = userName;
        out.write("Enter password: \n");
        out.flush();
        String password = in.readLine();
        String message = usersService.signIn(userName, password);
        out.write(message+"\n");
        out.flush();
        return message.equals("Success");
    }

    public void chatTalking(List<ChatThreads> chatClients) throws IOException {
        chatClients.add(this);
        messagesService.printLast30Messages(out);
        while (true) {
            String message = in.readLine();
            if (message==null) {
                chatClients.remove(this);
             break;
            }
            messagesService.saveMessage(0L, chatNumber, usersService.getUserIdByName(userName),userName+": "+message);
            for (ChatThreads participant : chatClients) {
                if (participant != this&&participant.chatNumber==chatNumber) {
                    participant.out.write(userName+": "+message + '\n');
                    participant.out.flush();
                }
            }
            if (message.equals("Exit")) {
                out.write("You have left the chat" + '\n');
                out.flush();
                chatClients.remove(this);
                break;
            }
        }

    }
    public void choosingRoom() throws IOException {
        while (true) {
            out.write("Rooms:\n");
            out.flush();
            chatroomService.printChatRooms(out);
            out.write("\n0.\tExit:\n");
            out.flush();
            out.write("Please choose the number of the chat\n");
            out.flush();
            String message = in.readLine();
            if (message.equals("0")) break;
            boolean numberIsCorrect=true;
            Long chatroomNumber=null;
            try {
                chatroomNumber = Long.parseLong(message);
            } catch(NumberFormatException nfe) {
                numberIsCorrect=false;
                out.write("Please enter the number!");
            }
            if (numberIsCorrect) {
                if (chatroomService.chatRoomExists(chatroomNumber)) {
                    chatNumber = chatroomNumber;
                    chatTalking(chatClients);
                }else{
                    out.write("This chat does not exists\n");
                    out.flush();
                }
            }
        }
    }

    void userPersonalRoom () throws IOException{
        while (true) {
            out.write("1.\tCreate room:\n2.\tChoose room:\n3.\tExit:\n");
            out.flush();
            String decision = in.readLine();
            if (decision!=null&&decision.equals("1")) {
                out.write("Please choose the name of the new room\n");
                out.flush();
                String newRoomName = in.readLine();
                if (!newRoomName.equals("")){
                    chatroomService.createChatroom(usersService.getUserIdByName(userName), newRoomName, userName);
                    out.write("Chatroom " +newRoomName+" created!\n");
                    out.flush();
                }else{
                    out.write("Chatroom name should not be empty\n");
                    out.flush();
                }
            }
            if (decision!=null&&decision.equals("2")) {
               choosingRoom();
            }
            if (decision==null||decision.equals("3")) {
                break;
            }
            }
        }

    }



