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

        assertThat(userID.toString(),is(userController.safeUser(userID)));
        assertThat(user1.toString(),is(userController.safeUser(user1)));
        assertThat(user2.toString(),is(userController.safeUser(user2)));
        assertThat(user3.toString(),is(userController.safeUser(user3)));

        assertThat(userController.getUserByID(uuid.toString()), is(userID.toString()));
        assertThat(userController.getAllUsers().contains(userID.toString()), is(true));
        assertThat(userController.getAllUsers().contains(user1.toString()), is(true));
        assertThat(userController.getAllUsers().contains(user2.toString()), is(true));
        assertThat(userController.getAllUsers().contains(user3.toString()), is(true));
    }
}