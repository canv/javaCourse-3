package lesson15.framework;

import lesson15.framework.core.IocDiFramework;
import lesson15.app.controllers.UserController;
import lesson15.app.models.User;

public class Lesson15 {
//  1) Повторить чтоб всё работало
//  2) Написать тесты c проверками исключений
//  3) Добавить @Inject для остальных
//      - в контроллере inject для конструктора
//      - в сервисе inject для сеттора

    public static void main(String[] args) {
        IocDiFramework.init("lesson15.app");
        UserController controller = IocDiFramework.getByInterface(UserController.class);
        controller.safeUser(new User("admin","root"));
        controller.safeUser(new User("guest","1234"));
        controller.safeUser(new User("test","tset"));

        System.out.println(controller.getAllUsers());
      //  System.out.println(IocDiFramework.iocContainer.values().toString());
    }
}