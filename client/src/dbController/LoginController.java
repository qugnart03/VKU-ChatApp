package dbController;

import db.DatabaseConnection;
import db.MD5;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    public static boolean Login(String username, String password) throws SQLException, ClassNotFoundException {
        String usernameHashed = new MD5().MD5Digest(username);
        System.out.println(usernameHashed);
        String passwordHased = new MD5().MD5Digest(password);
        System.out.println(passwordHased);
        String SQL = "SELECT * FROM user WHERE username=? AND password=?";
        Connection connection = DatabaseConnection.getDatabaseConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, usernameHashed);
        preparedStatement.setObject(2, passwordHased);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if(resultSet.getString(1).equals(usernameHashed) && resultSet.getString(2).equals(passwordHased)){
                return true;
            }
        }
        return false;
    }
}
