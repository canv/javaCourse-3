package lesson15.ioc.di.controllers;

import javassist.NotFoundException;
import lesson15.ioc.di.models.User;
import lesson15.ioc.di.services.UserService;

import java.util.stream.Collectors;

public class UserContoller {
    private UserService service;

    public String getUserByID(String id){
        try {
            return service.getByID(id).toString();
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    public String safeUser (User user){
        User saved = service.save(user);
        return saved.toString();
    }

    public String getAllUsers(){
        return service.getAll().stream()
                .map(User::toString)
                .collect(Collectors.joining(", "));
    }
}
