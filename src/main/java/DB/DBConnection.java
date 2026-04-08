package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/SAMSDB";
    private final String username = "root";
    private final String password = "";

    private DBConnection() throws SQLException {
        connection= DriverManager.getConnection(url,username,password);
    }
    public static DBConnection getInstance() throws SQLException {
        return dbConnection == null? dbConnection = new DBConnection():dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}