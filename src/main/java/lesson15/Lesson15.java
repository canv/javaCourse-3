package lesson15;

import lesson15.framework.annotations.MyFrameworkBootStart;
import lesson15.framework.core.IocDiFramework;
import lesson15.app.controllers.UserController;
import lesson15.app.models.User;

@MyFrameworkBootStart("lesson15.app")
public class Lesson15 {
    public static void main(String[] args) {
//      1) Повторить чтоб всё работало
//      2) Написать тесты c проверками исключений
//      3) Добавить @Inject для остальных
//          - в контроллере inject для конструктора
//          - в сервисе inject для сеттора

        IocDiFramework.run(Lesson15.class);
        UserController controller = IocDiFramework.getByInterface(UserController.class);
        controller.safeUser(new User("admin","root"));
        controller.safeUser(new User("guest","none"));
        controller.safeUser(new User("test","1234"));

        System.out.println(controller.getAllUsers());
    }
}