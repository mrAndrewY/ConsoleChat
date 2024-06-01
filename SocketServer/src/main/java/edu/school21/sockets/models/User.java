package edu.school21.sockets.models;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    public User(Long id, String name,  String password) {
        this.id=id;
        this.name=name;
        this.password=password;
    }
}
