package sample.Database;

import java.sql.*;

public class DatabaseHandler extends Config {
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    public void singUpUser(User user) throws SQLException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_NICK + ","
                + Const.USERS_PASSWORD + "," + Const.USER_EMAIL + ")"
                + "VALUES(?,?,?)";

        try {
            PreparedStatement prst = getDbConnection().prepareStatement(insert);
            prst.setString(1, user.getUsername());
            prst.setString(2, user.getPassword());
            prst.setString(3, user.getEmail());
            prst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public ResultSet checkUser(User user) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_NICK
                + "=? OR " + Const.USER_EMAIL + " =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getEmail());
        return prSt.executeQuery();
    }

}