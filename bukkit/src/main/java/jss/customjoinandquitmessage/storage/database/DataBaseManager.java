package jss.customjoinandquitmessage.storage.database;

import jss.customjoinandquitmessage.utils.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;
    private DataBaseManager instance;

    //test singleton
    public DataBaseManager get(String host, int port, String database, String user, String password) {
        if (instance == null) {
            instance = new DataBaseManager(host, port, database, user, password);
        }
        return instance;
    }

    public DataBaseManager(String host, int port, String database, String user, String password){
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true";
        this.user = user;
        this.password = password != null ? password : "";
    }

    public boolean connect() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url, user, password);
            Logger.success("Connected to database");
            return  true;
        }
        Logger.warning("Database is already connected!");
        return false;
    }

    public void disconnect() throws SQLException {
         if(connection != null && !connection.isClosed()){
            connection.close();
         }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }

        return connection;
    }

    public void createTable() throws SQLException {
        String createTableSQL =
                "CREATE TABLE IF NOT EXISTS PlayerData (" +
                "playerName VARCHAR(16) NOT NULL PRIMARY KEY," +
                "playerGroup VARCHAR(32) NOT NULL)";
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }


}
