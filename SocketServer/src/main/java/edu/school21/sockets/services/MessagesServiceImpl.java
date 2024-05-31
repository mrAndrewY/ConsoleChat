package edu.school21.sockets.services;

import edu.school21.sockets.models.Messages;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

@Component
public class MessagesServiceImpl implements   MessagesService{
    private final MessagesRepository<Messages> messagesRep;
    @Autowired
    public MessagesServiceImpl(@Qualifier("MessageRepositoryImpl") MessagesRepository<Messages> mRep){
        messagesRep=mRep;
    }
    @Override
    public void printLast30Messages(BufferedWriter out)throws IOException {
        List<Messages> list=messagesRep.last30Messages();
        for(Messages message : list) {
            out.write(message.getText()+'\n');
            out.flush();
        }
    }

    @Override
    public void saveMessage(long id, Long chatNumber, Long userId, String text) {
        messagesRep.save(new Messages(id, chatNumber, userId, text));
    }
}
