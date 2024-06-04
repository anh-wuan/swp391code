package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    // Đổi quyền truy cập của biến connection từ private thành public
    public static Connection connection = null;

    /*USE BELOW METHOD FOR YOUR DATABASE CONNECTION FOR BOTH SINGLE AND MULTILPE SQL SERVER INSTANCE(s)*/
    /*DO NOT EDIT THE BELOW METHOD, YOU MUST USE ONLY THIS ONE FOR YOUR DATABASE CONNECTION*/
    public static Connection getConnection() throws ClassNotFoundException {
        String serverName = "localhost";
        String dbName = "Timeshare";
        String portNumber = "1433";
        String userID = "sa";
        String password = "161203";

        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber
                + ";databaseName=" + dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try {
            // Sử dụng phương thức getConnection() để lấy kết nối
            if (connection == null || connection.isClosed()) {
                return DriverManager.getConnection(url, userID, password);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
