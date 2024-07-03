package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Messages;

import java.util.List;

public interface MessagesRepository<T> extends CrudRepository<T> {
    List<Messages> last30Messages();
}
