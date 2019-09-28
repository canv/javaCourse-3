package lesson15.ioc.di.services;

import javassist.NotFoundException;
import lesson15.ioc.di.models.User;

import java.util.Set;

public interface UserService {
    User getByID(String id) throws NotFoundException;
    User save(User saveUser);
    Set<User> getAll();

}
