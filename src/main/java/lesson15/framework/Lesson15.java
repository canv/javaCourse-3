package lesson15.framework;

import lesson15.framework.core.IocDiFramework;
import lesson15.app.controllers.UserController;
import lesson15.app.models.User;

public class Lesson15 {
//  Написать тесты c проверками исключений
    public static void main(String[] args) {
        IocDiFramework.init("lesson15.app");
        UserController controller = IocDiFramework.getByInterface(UserController.class);
        controller.safeUser(new User("admin","root"));
        System.out.println(controller.getAllUsers());
    }
}