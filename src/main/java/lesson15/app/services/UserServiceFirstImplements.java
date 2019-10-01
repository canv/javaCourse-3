package lesson15.app.services;

import com.google.common.base.Strings;
import lesson15.app.repositories.UserRepository;
import lesson15.app.exceptions.*;
import lesson15.app.models.User;
import lesson15.framework.annotations.*;
import lombok.*;

import java.util.*;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceFirstImplements implements UserService {

    @Inject
    private UserRepository repository;

    @Override
    public User getByID(String id) {
        if (Strings.isNullOrEmpty(id))
            throw new IllegalIDException("Null can't be applied!");

        UUID uuid = UUID.fromString(id);
        User foundUser = repository.findByID(uuid);

        if (Objects.isNull(foundUser))
            throw new UserNotFoundException("User is not found!");
        return foundUser;
    }

    @Override
    public User save(User saveUser) {
        if (Strings.isNullOrEmpty(saveUser.getUsername()))
            throw new IllegalUsernameException("Wrong user name!");
        if (Strings.isNullOrEmpty(saveUser.getMd5Password()))
            throw new IllegalPasswordException("Wrong password!");


        return repository.save(saveUser);
    }

    @Override
    public Set<User> getAll(){
        return repository.findAll();
    }
}
