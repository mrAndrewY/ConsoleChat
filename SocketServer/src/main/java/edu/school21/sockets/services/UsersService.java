package edu.school21.sockets.services;

public interface UsersService {
    String signUp(String name, String password);
    String signIn(String name, String password);
    Long getUserIdByName(String name);
}
