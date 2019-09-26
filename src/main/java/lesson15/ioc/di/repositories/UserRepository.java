package lesson15.ioc.di.repositories;

import lesson15.ioc.di.models.User;

import java.util.*;

public class UserRepository {
    private final Map<UUID, User> uuidUserMap;

    public UserRepository() {
        this.uuidUserMap = new HashMap<>();
    }

    public User findByID(UUID id){
        return  uuidUserMap.get(id);
    }

    public User save(User user){
        uuidUserMap.put(user.getId(),user);
        return user;
    }

    public Set<User> findAll(){
        return new HashSet<>(uuidUserMap.values());
    }
}
