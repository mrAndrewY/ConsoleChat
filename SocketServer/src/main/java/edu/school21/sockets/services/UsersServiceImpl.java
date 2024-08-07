package edu.school21.sockets.services;

import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {
    private Long id;
    private final UsersRepository<User> ur;
    private final PasswordEncoder encoder;


    @Autowired
    public UsersServiceImpl(@Qualifier("UsersRepositoryImpl") UsersRepository<User> ur,
                            @Qualifier("encoder") PasswordEncoder encoder) {
        this.ur = ur;
        this.encoder=encoder;
    }


    @Override
    public String signUp(String name, String password) {
        String result = "Please get another name, someone get this name before";
        if (password.isEmpty()|| name.isEmpty()){
             result = "fields cannot be empty, please fill the fields";
        } else {
            Optional<User> opt = ur.findByName(name);
            if (!opt.isPresent()) {
                ur.save(new User(0L, name, encoder.encode(password)));
                result = "Success";
            }
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
