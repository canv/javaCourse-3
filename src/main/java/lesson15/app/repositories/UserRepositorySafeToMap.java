package lesson15.app.repositories;

import lesson15.app.models.User;
import lesson15.framework.annotations.Component;

import java.util.*;

@Component
public class UserRepositorySafeToMap implements UserRepository {

    private final Map<UUID, User> uuidUserMap;

    public UserRepositorySafeToMap() {
        this.uuidUserMap = new HashMap<>();
    }

    @Override
    public User findByID(UUID id){
        return  uuidUserMap.get(id);
    }

    @Override
    public User save(User user){
        uuidUserMap.put(user.getId(),user);
        return user;
    }

    @Override
    public Set<User> findAll(){
        return new HashSet<>(uuidUserMap.values());
    }
}