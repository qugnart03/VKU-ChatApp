package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseConnection {
    private Connection connection;
    
    private static DatabaseConnection databaseConnection;
    
    private DatabaseConnection()throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/db_chatapp?useTimezone=true&useSSL=false&useTimezone=true&serverTimezone=UTC","root","");
    }
    public Connection getConnection(){
        return connection;
    }
    public static DatabaseConnection getDatabaseConnection()throws ClassNotFoundException,SQLException{
        if(databaseConnection == null){
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }
}
