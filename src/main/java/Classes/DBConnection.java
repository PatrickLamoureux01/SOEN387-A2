package Classes;
import java.sql.*;

public class DBConnection {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3308/";
    static final String DB_NAME = "test";
    // Database credentials
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "root";
    static Connection conn = null;

    public static Connection getConnection() {
        try{
            Class.forName(JDBC_DRIVER); //Register JDBC driver
//Open a connection
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database",e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found",e);
        }
    }

    public static void closeConnection() throws SQLException{
//Close connection
        if(conn!=null) conn.close();
    }

}
