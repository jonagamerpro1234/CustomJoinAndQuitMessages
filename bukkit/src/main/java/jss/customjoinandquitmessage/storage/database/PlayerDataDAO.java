package jss.customjoinandquitmessage.storage.database;

import jss.customjoinandquitmessage.storage.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataDAO {

    private final DataBaseManager databaseManager;

    public PlayerDataDAO(DataBaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public PlayerData getPlayerData(String playerName) throws SQLException {
        String query = "SELECT * FROM PlayerData WHERE playerName = ?";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PlayerData(rs.getString("group"));
            }
        }
        return null;
    }

    public void savePlayerData(String playerName, @NotNull PlayerData data) throws SQLException {
        String query = "REPLACE INTO PlayerData (playerName, group) VALUES (?, ?)";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setString(2, data.getGroup());
            stmt.executeUpdate();
        }
    }

}
