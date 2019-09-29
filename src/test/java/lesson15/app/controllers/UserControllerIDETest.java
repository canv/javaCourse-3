package lesson15.app.controllers;

import lesson15.app.models.User;
import lesson15.app.repositories.UserRepository;
import lesson15.app.repositories.UserRepositorySafeToMap;
import lesson15.app.services.UserService;
import lesson15.app.services.UserServiceFirstImplements;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserControllerIDETest {
    private UserRepository userRepository = new UserRepositorySafeToMap();
    private UserService userService = new UserServiceFirstImplements(userRepository);
    private UserController userController = new UserControllerIDE(userService);

    @Test
    public void userNavigateTest() {

        UUID uuid = UUID.randomUUID();
        User userID = new User(uuid, "userID", "passID");
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        User user3 = new User("user3", "pass3");

        Set<User> users = new HashSet<>();
        users.add(userID);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        String usersStr = users.stream()
                .map(User::toString)
                .collect(Collectors.joining(", "));

        System.out.println(usersStr);

        userController.safeUser(userID);
        userController.safeUser(user1);
        userController.safeUser(user2);
        userController.safeUser(user3);

        assertThat(userController.getUserByID(uuid.toString()), is(userID.toString()));
        assertThat(usersStr, is(userController.getAllUsers()));
    }
}