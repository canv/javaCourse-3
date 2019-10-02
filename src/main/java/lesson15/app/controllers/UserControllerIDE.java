package lesson15.app.controllers;

import lesson15.framework.annotations.*;
import lesson15.app.models.*;
import lesson15.app.services.*;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Component(3)
@NoArgsConstructor
public class UserControllerIDE implements UserController {
    private UserService service;

    @Inject
    UserControllerIDE(UserService service) {
        this.service = service;
    }

    @Override
    public String getUserByID(String id) {
        return service.getByID(id).toString();
    }

    @Override
    public String safeUser(User user) {
        User saved = service.save(user);
        return saved.toString();
    }

    @Override
    public String getAllUsers() {
        return service.getAll().stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }
}
