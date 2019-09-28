package lesson15.ioc.di.repositories;

import lesson15.ioc.di.models.User;

import java.util.Set;
import java.util.UUID;

public interface UserRepository {
    User findByID(UUID id);
    User save(User user);
    Set<User> findAll();
}
