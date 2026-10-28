package LAB2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // PostgreSQL connection details.
    private static final String URL =
            "jdbc:postgresql://localhost:5432/IgirePay_db";

    private static final String USER = "postgres";

    // Enter your PostgreSQL password locally.
    private static final String PASSWORD =
            "Christella@01";

    // Open a connection to PostgreSQL.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
