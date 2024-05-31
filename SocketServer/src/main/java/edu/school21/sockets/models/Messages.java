package edu.school21.sockets.models;

public class Messages {
    private long id;
    private Long chatroomId;
    private Long senderId;
    private String text;


    public Messages(long id, Long chatroomId, Long senderId, String text) {
        this.id=id;
        this.chatroomId=chatroomId;
        this.senderId=senderId;
        this.text=text;
    }

    public Long getId() {
        return id;
    }

    public Long getChatroomId() {
        return chatroomId;
    }

    public Long getSenderId() {
        return senderId;
    }
    public String getText(){
        return text;
    }
}
