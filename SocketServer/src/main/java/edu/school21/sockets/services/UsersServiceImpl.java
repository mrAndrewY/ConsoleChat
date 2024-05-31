package edu.school21.sockets.services;

import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private Long id;
    private UsersRepository<User> ur;

    private PasswordEncoder encoder;


    @Autowired
    public UsersServiceImpl(@Qualifier("UsersRepositoryImpl") UsersRepository<User> ur,
                            @Qualifier("encoder") PasswordEncoder encoder) {
        this.ur = ur;
        this.encoder=encoder;
    }


    @Override
    public String signUp(String name, String password) {
        if (password.equals("")) password="0000";
        String result = "Please get another name, someone get this name before";
        Optional<User> opt = ur.findByName(name);
        if(!opt.isPresent()){
            ur.save(new User(0L, name, encoder.encode(password)));
            result = "Success";
        }
        return result;

    }

    @Override
    public String signIn(String name, String password) {
        Optional<User> opt = ur.findByName(name);
        String result = "User not found";
        if(opt.isPresent()){
           if (encoder.matches(password, opt.get().getPassword())) {
               result ="Success";
               System.out.println(result);
           } else {
               result = "Non correct password";
               System.out.println(result);
           }
        }
        return result;
    }

    @Override
    public Long getUserIdByName(String name) {
        return ur.findByName(name).get().getId();
    }
}
