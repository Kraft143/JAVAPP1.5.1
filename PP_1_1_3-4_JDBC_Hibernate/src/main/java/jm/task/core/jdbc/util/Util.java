package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    static {
        // Загрузка драйвера MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить драйвер MySQL", e);
        }
    }

    private static SessionFactory sessionFactory;

    // Метод получения подключения к БД
    public static Connection getConnection() {
        // Чтение учетных данных и адреса сервера из конфигурации или переменных окружения
        String hostName = "localhost";
        String dbName = "userdb";
        String userName = "root";
        String password = "password";

        return getConnection(hostName, dbName, userName, password);
    }

    private static Connection getConnection(String hostName, String dbName, String userName, String password) {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        try {
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            // Логгирование исключения
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/userdb");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "password");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
