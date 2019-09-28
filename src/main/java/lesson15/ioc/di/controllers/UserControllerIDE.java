package lesson15.ioc.di.controllers;

import javassist.NotFoundException;
import lesson15.framework.annotations.Component;
import lesson15.framework.annotations.Inject;
import lesson15.ioc.di.models.User;
import lesson15.ioc.di.services.UserService;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserControllerIDE implements UserController {

    @Inject
    private UserService service;

    @Override
    public String getUserByID(String id){
        try {
            return service.getByID(id).toString();
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @Override
    public String safeUser (User user){
        try {
            User saved = service.save(user);
            return saved.toString();
        }catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public String getAllUsers(){
        return service.getAll().stream()
                .map(User::toString)
                .collect(Collectors.joining(", "));
    }
}