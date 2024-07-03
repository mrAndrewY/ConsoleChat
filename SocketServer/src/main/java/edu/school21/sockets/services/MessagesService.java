package edu.school21.sockets.services;

import java.io.BufferedWriter;
import java.io.IOException;

public interface MessagesService  {
public void printLast30Messages(BufferedWriter out) throws IOException;
public void saveMessage(long id, Long chatNumber, Long userId, String text);

}
