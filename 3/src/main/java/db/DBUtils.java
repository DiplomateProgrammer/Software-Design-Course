package db;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    private static final String SQL_URL = "jdbc:sqlite:test.db";

    public static Statement getStatement() throws SQLException {
        return DriverManager.getConnection(SQL_URL).createStatement();
    }

    public static void initDB() throws SQLException {
        try (Statement stmt = getStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " name TEXT NOT NULL, " +
                    " price INT NOT NULL)";
            stmt.executeUpdate(sql);
        }
    }
}
