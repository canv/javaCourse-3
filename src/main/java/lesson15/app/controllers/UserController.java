package lesson15.app.controllers;

import lesson15.app.models.User;

public interface UserController {
    String getUserByID(String id);
    String safeUser (User user);
    String getAllUsers();
}
