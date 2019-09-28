package lesson15.ioc.di.services;

import com.google.common.base.Strings;
import javassist.NotFoundException;
import lesson15.framework.annotations.Component;
import lesson15.ioc.di.exceptions.WrongArgumentException;
import lesson15.ioc.di.models.User;
import lesson15.ioc.di.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserServiceFirstImplements implements UserService {
    private UserRepository repository;

    @Override
    public User getByID(String id) throws NotFoundException {
        if (Strings.isNullOrEmpty(id))
            throw new WrongArgumentException("Null can't be applied!");
        UUID uuid = UUID.fromString(id);
        User foundUser = repository.findByID(uuid);
        if (Objects.isNull(foundUser))
            throw new NotFoundException("User is not found!");
        return foundUser;
    }

    @Override
    public User save(User saveUser) {
        if (Strings.isNullOrEmpty(saveUser.getUsername()))
            throw new IllegalArgumentException();
        if (Strings.isNullOrEmpty(saveUser.getMd5Password()))
            throw new IllegalArgumentException();

        return repository.save(saveUser);
    }

    @Override
    public Set<User> getAll(){
        return repository.findAll();
    }
}
