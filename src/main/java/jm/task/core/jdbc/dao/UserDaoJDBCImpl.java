package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}
    private final Connection connection = Util.getConnection();
    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("""
                        CREATE TABLE `schema_digital_trash`.`users` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(45) NOT NULL,
                          `lastName` VARCHAR(45) NOT NULL,
                          `age` INT NOT NULL,
                          PRIMARY KEY (`id`));""");
             ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = true;
            while (resultSet.next()) {
                isExists = false;
            }
            if (isExists) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DROP TABLE users");
             ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = false;
            while (resultSet.next()) {
                isExists = true;
            }
            if (isExists) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO users"
                        + " (name, lastName, age) VALUES ('" + name
                        + "', '" + lastName + "', " + age
                        + ");");
             ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = false;
            while (resultSet.next()) {
                isExists = true;
            }
            if (isExists) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ( "DELETE FROM users WHERE id=" + id);
             ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = false;
            while (resultSet.next()) {
                isExists = true;
            }
            if (isExists) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from users");
             ResultSet resultSet1 = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = false;
            while (resultSet1.next()) {
                isExists = true;
            }
            if (isExists) {
                while (resultSet.next()) {
                    User u = new User(resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            resultSet.getByte("age"));
                    u.setId(resultSet.getLong("id"));
                    all.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("TRUNCATE users");
             ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES LIKE 'users'")) {
            boolean isExists = false;
            while (resultSet.next()) {
                isExists = true;
            }
            if (isExists) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
