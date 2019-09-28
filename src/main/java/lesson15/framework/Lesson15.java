package lesson15.framework;

import lesson15.framework.core.IocDiFramework;
import lesson15.ioc.di.controllers.UserController;
import lesson15.ioc.di.controllers.UserControllerIDE;
import lesson15.ioc.di.models.User;
import lesson15.ioc.di.repositories.UserRepository;
import lesson15.ioc.di.repositories.UserRepositorySafeToMap;
import lesson15.ioc.di.services.UserService;
import lesson15.ioc.di.services.UserServiceFirstImplements;

import java.util.UUID;

public class Lesson15 {
// 1) Написать тесты к каждому слою
//      - так же проверить исключения
//      - добавить зависимости apache, junit4, hamcrest, lombok
//      - написать тесты к проекту

    public static void main(String[] args) {
        IocDiFramework.init("lesson.ioc.di");
        UserController controller = IocDiFramework.getByInterface(UserController.class);
        controller.safeUser(new User("user3","pass3"));
        System.out.println(controller.getAllUsers());

       // forTests();
    }

    private static void forTests() {
        UserController userController = prepareUserController();

        User user1 = new User("user1","pass1");
        String saveUser1 = userController.safeUser(user1);
        System.out.println(saveUser1);

        UUID id = UUID.randomUUID();

        userController.safeUser(new User(id, "user2", "pass2"));
        System.out.println(userController.getUserByID(id.toString()));
        System.out.println(userController.getAllUsers());
    }

    private static UserController prepareUserController() {
        UserRepository userRepository = new UserRepositorySafeToMap();
        UserService userService = new UserServiceFirstImplements(userRepository);
        return new UserControllerIDE(userService);
    }
}