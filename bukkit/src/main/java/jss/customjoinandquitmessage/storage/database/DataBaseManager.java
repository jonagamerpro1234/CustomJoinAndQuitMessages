package jss.customjoinandquitmessage.storage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public DataBaseManager(String host, int port, String database, String user, String password){
        this.url = "jdbc:mysql://" + host + port + "/" + database;
        this.user = user;
        this.password = password != null ? password : "";
    }

    public void connect() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url, user, password);
        }
    }

    public void disconnect() throws SQLException {
         if(connection != null && !connection.isClosed()){
            connection.close();
         }
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS PlayerData (" +
                "playerName VARCHAR(16) NOT NULL PRIMARY KEY," +
                "playerGroup VARCHAR(32) NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }


}
