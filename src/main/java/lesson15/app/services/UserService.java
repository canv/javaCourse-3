package lesson15.app.services;

import lesson15.app.models.User;

import java.util.Set;

public interface UserService {
    User getByID(String id);
    User save(User saveUser);
    Set<User> getAll();

}
