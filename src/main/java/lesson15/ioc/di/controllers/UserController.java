package lesson15.ioc.di.controllers;

import lesson15.ioc.di.models.User;

public interface UserController {
    String getUserByID(String id);
    String safeUser (User user);
    String getAllUsers();
}
