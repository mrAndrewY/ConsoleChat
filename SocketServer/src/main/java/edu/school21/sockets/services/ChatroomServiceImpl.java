package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.ChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

@Component
public class ChatroomServiceImpl implements ChatroomService {
private Long ChatroomId = 1L;
private ChatroomRepository<Chatroom> chRep;

@Autowired
public ChatroomServiceImpl(@Qualifier("ChatroomRepositoryImpl") ChatroomRepository<Chatroom> chRep){
    this.chRep=chRep;
}

    @Override
    public void createChatroom(Long ownerId, String chatroom, String owner) {
        chRep.save(new Chatroom(ChatroomId++, chatroom, owner));
    }


    public List chatroomsList() {
        return chRep.findAll();
    }

    @Override
    public void printChatRooms(BufferedWriter out) throws IOException {
        List<Chatroom> listChatRooms= chatroomsList();
        if (listChatRooms.isEmpty()) {
            out.write("No rooms where created for this moment");
            out.flush();
            return;
        }
        for (Chatroom room : listChatRooms) {
            out.write(room.getId() + " " + room.getName()+'\n');
            out.flush();
        }
    }

    @Override
    public boolean chatRoomExists(Long chatroomId) {
        return (chRep.findById(chatroomId).isPresent());
    }
}
