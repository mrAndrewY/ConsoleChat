package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Messages;

import javax.sql.DataSource;
import java.util.List;

public interface ChatroomRepository<T> extends CrudRepository<T> {

    //public List<Messages> last30Messages () ;

}
