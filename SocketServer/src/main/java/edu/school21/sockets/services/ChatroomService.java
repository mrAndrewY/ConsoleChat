package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public interface ChatroomService {
    public void createChatroom(Long ownerId, String chatroom, String owner);
    public List<Chatroom> chatroomsList();
    public void printChatRooms (BufferedWriter out) throws IOException;
    public boolean chatRoomExists(Long chatroomId);

}
