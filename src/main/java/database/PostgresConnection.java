package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection implements DatabaseConnection {

    private final static String DEFAULT_CONNECTION = "localhost";
    private final static String DEFAULT_USERNAME = "postgres";
    private final static String DEFAULT_PASSWORD = "admin";
    private final static String DRIVER_NAME = "org.postgresql.Driver";

    public PostgresConnection() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Отсуствует класс драйвера подключения");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return openConnection(DEFAULT_CONNECTION, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public Connection getConnection(String username, String password) {
        return openConnection(DEFAULT_CONNECTION, username, password);
    }

    public Connection getConnection(String host, String username, String password) {
        return openConnection(host, username, password);
    }

    private Connection openConnection(String host, String username, String password) {
        try {
            return DriverManager.getConnection("jdbc:postgresql://" + host + "/postgres", username, password);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
