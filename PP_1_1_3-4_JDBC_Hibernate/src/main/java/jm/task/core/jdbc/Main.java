package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

/**
 * Алгоритм работы приложения:
 * В методе main класса Main должны происходить следующие операции:
 * <p>
 * Создание таблицы User(ов)
 * Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем — name добавлен в базу данных)
 * Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
 * Очистка таблицы User(ов)
 * Удаление таблицы
 */
public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Peter", "Great", (byte) 30);
        userService.saveUser("Ivan", "Sidorov", (byte) 25);
        userService.saveUser("Samir", "Aslanov", (byte) 26);
        userService.saveUser("Max", "Pain", (byte) 50);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
