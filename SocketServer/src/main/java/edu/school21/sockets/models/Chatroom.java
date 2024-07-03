package edu.school21.sockets.models;

import lombok.Data;

@Data
public class Chatroom {
    private final Long id;
    private final String name;
    private final String owner;
}
