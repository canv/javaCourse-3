package lesson15.app.repositories;

import lesson15.app.models.User;

import java.util.Set;
import java.util.UUID;

public interface UserRepository {
    User findByID(UUID id);
    User save(User user);
    Set<User> findAll();
}
