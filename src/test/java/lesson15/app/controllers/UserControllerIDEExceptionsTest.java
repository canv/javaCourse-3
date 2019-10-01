package lesson15.app.controllers;

import lesson15.app.exceptions.*;
import lesson15.app.models.User;
import lesson15.app.repositories.*;
import lesson15.app.services.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class UserControllerIDEExceptionsTest {
    private UserRepository userRepository = new UserRepositorySafeToMap();
    private UserService userService = new UserServiceFirstImplements(userRepository);
    private UserController userController = new UserControllerIDE(userService);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void emptyUserIDGettingTest() {
        thrown.expect(IllegalIDException.class);
        userController.getUserByID("");
    }
    @Test
    public void nullUserIDGettingTest() {
        thrown.expect(IllegalIDException.class);
        userController.getUserByID(null);
    }
    @Test
    public void uncorrectedUserIDGettingTest() {
        thrown.expect(IllegalArgumentException.class);
        userController.getUserByID("33");
    }

    @Test
    public void nullUsernameSafeTest() {
        thrown.expect(IllegalUsernameException.class);
        userController.safeUser(new User(null, "11"));
    }
    @Test
    public void emptyUsernameSafeTest() {
        thrown.expect(IllegalUsernameException.class);
        userController.safeUser(new User("", "11"));
    }

    @Test
    public void nullPasswordSafeTest() {
        thrown.expect(IllegalPasswordException.class);
        userController.safeUser(new User("11", null));
    }
    @Test
    public void emptyPasswordSafeTest() {
        thrown.expect(IllegalPasswordException.class);
        userController.safeUser(new User("11", ""));
    }
}