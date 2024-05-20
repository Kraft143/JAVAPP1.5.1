package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(255)," +
            "last_name VARCHAR(255)," +
            "age TINYINT," +
            "PRIMARY KEY (id))";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";
    private static final String INSERT_USER = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_ALL_USERS = "DELETE FROM users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        executeUpdate(CREATE_USERS_TABLE, "Таблица создана успешно.");
    }

    public void dropUsersTable() {
        executeUpdate(DROP_USERS_TABLE, "Таблица удалена успешно.");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь сохранен успешно");
        } catch (SQLException e) {
            logError("Ошибка при сохранении пользователя", e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Пользователь удален успешно");
        } catch (SQLException e) {
            logError("Ошибка при удалении пользователя", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            logError("Ошибка при получении списка пользователей", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        executeUpdate(DELETE_ALL_USERS, "Таблица очищена успешно.");
    }

    private void executeUpdate(String sql, String successMessage) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            logError("Ошибка при выполнении операции: " + sql, e);
        }
    }

    private void logError(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
