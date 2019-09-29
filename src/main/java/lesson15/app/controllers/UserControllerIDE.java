package lesson15.app.controllers;

import lesson15.framework.annotations.Component;
import lesson15.framework.annotations.Inject;
import lesson15.app.models.User;
import lesson15.app.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserControllerIDE implements UserController {

    @Inject
    private UserService service;

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
                .collect(Collectors.joining(", "));
    }
}