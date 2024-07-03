package edu.school21.sockets.models;

import lombok.Data;

@Data
public class Messages {
    private final long id;
    private final Long chatroomId;
    private final Long senderId;
    private final String text;
}
