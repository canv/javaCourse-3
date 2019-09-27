package lesson15.ioc.di.services;

import com.google.common.base.Strings;
import javassist.NotFoundException;
import lesson15.ioc.di.exceptions.WrongArgumentException;
import lesson15.ioc.di.models.User;
import lesson15.ioc.di.repositories.UserRepository;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getByID(String id) throws NotFoundException {
        if (Strings.isNullOrEmpty(id))
            throw new WrongArgumentException("Null can't be applied!");
        UUID uuid = UUID.fromString(id);
        User foundUser = repository.findByID(uuid);
        if (Objects.isNull(foundUser))
            throw new NotFoundException("User is not found!");
        return foundUser;
    }

    public User save(User saveUser) {
        if (Strings.isNullOrEmpty(saveUser.getUsername()))
            throw new IllegalArgumentException();
        if (Strings.isNullOrEmpty(saveUser.getMd5Password()))
            throw new IllegalArgumentException();

        return repository.save(saveUser);
    }

    public Set<User> getAll(){
        return repository.findAll();
    }
}
