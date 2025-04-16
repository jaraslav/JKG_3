package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Максим", "Максимов", (byte) 33);
        userService.saveUser("Иван", "Иванов", (byte) 26);
        userService.saveUser("Сергей", "Сергеев", (byte) 53);
        userService.saveUser("Пётр", "Петров", (byte) 62);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
