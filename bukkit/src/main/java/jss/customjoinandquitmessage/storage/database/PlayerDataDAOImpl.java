package jss.customjoinandquitmessage.storage.database;

import jss.customjoinandquitmessage.storage.PlayerData;
import jss.customjoinandquitmessage.utils.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataDAOImpl implements PlayerDataDAO {

    private final DataBaseManager databaseManager;

    public PlayerDataDAOImpl(DataBaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public PlayerData getPlayerData(String playerName) throws SQLException {
        String query = "SELECT playerGroup FROM PlayerData WHERE playerName = ?";
        Logger.debug("Loading player data for: " + playerName);

        Connection conn = databaseManager.getConnection();
        Logger.debug("Connection obtained for player data lookup: " + playerName);
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String playerGroup = rs.getString("playerGroup");

                    Logger.debug("Player data found: " + playerName + " | Group: " + playerGroup);
                    return new PlayerData(playerGroup);
                }
            }
        } catch (SQLException e) {
            Logger.severe(e, "Failed to load player data for " + playerName);
            throw e;
        }

        Logger.debug("No player data found for: " + playerName);
        return null;
    }

    @Override
    public void savePlayerData(String playerName, @NotNull PlayerData data) throws SQLException {
        String query = "REPLACE INTO PlayerData (playerName, playerGroup) VALUES (?, ?)";
        Logger.debug("Saving player data: " + playerName + " | Group: " + data.getGroup());

        Connection conn = databaseManager.getConnection();
        Logger.debug("Connection obtained for player data save: " + playerName);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setString(2, data.getGroup());

            int affectedRows = stmt.executeUpdate();
            Logger.debug("Player data saved: " + playerName + " | Affected rows: " + affectedRows);
        } catch (SQLException e) {
            Logger.severe(e, "Failed to save player data for " + playerName);
            throw e;
        }
    }
}
