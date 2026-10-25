
package jss.customjoinandquitmessage.storage;

import jss.customjoinandquitmessage.files.PlayerDataFile;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAO;
import jss.customjoinandquitmessage.utils.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;


public class PlayerDataManager {

    private final PlayerDataFile playerDataFile;
    private final PlayerDataDAO playerDataDAO;
    private final CacheManager cacheManager;
    private final boolean databaseEnabled;

    public enum DataType {
        JSON,
        DATABASE,
        BOTH
    }

    public PlayerDataManager(@NotNull PlayerDataFile playerDataFile, @NotNull PlayerDataDAO playerDataDAO, @NotNull CacheManager cacheManager, boolean databaseEnabled) {
        this.playerDataFile = Objects.requireNonNull(playerDataFile);
        this.playerDataDAO = Objects.requireNonNull(playerDataDAO);
        this.cacheManager = Objects.requireNonNull(cacheManager);
        this.databaseEnabled = databaseEnabled;
    }

    public PlayerData load(String playerName) {
        PlayerData cachedData = cacheManager.getPlayerData(playerName);

        if (cachedData != null) {
            return cachedData;
        }

        PlayerData data = null;

        if (databaseEnabled) {
            try {
                data = playerDataDAO.getPlayerData(playerName);
            } catch (SQLException exception) {
                Logger.severe(exception,"Failed to load player data from database: " + playerName);
            }
        }

        if (data == null && playerDataFile.playerDataFileExists(playerName)) {
            data = playerDataFile.loadData(playerName);
        }

        if (data != null) {
            cacheManager.setPlayerData(playerName, data);
        }

        return data;
    }

    public void save(@NotNull DataType dataType) {
        Objects.requireNonNull(dataType, "DataType cannot be null");

        Map<String, PlayerData> playerDataCache = cacheManager.getPlayerDataCache();

        switch (dataType) {
            case JSON:
                saveToJson(playerDataCache);
                break;

            case DATABASE:
                if (!databaseEnabled) {
                    Logger.warning("Cannot save player data: database is disabled.");
                    return;
                }

                saveToDatabase(playerDataCache);
                break;

            case BOTH:
                saveToJson(playerDataCache);

                if (databaseEnabled) {
                    saveToDatabase(playerDataCache);
                } else {
                    Logger.warning("Database is disabled. Player data was saved to JSON only.");
                }
                break;
        }
    }

    public void saveAll() {
        save(databaseEnabled ? DataType.DATABASE : DataType.JSON);
    }


    public void savePlayer(@NotNull String playerName, @NotNull PlayerData data, @NotNull DataType dataType) {
        Objects.requireNonNull(playerName, "Player name cannot be null");
        Objects.requireNonNull(data, "Player data cannot be null");
        Objects.requireNonNull(dataType, "DataType cannot be null");

        // Actualizar la caché.
        cacheManager.setPlayerData(playerName, data);

        switch (dataType) {
            case JSON:
                savePlayerToJson(playerName, data);
                break;

            case DATABASE:
                if (!databaseEnabled) {
                    Logger.warning("Cannot save player data: database is disabled.");
                    return;
                }
                savePlayerToDatabase(playerName, data);
                break;

            case BOTH:
                savePlayerToJson(playerName, data);

                if (databaseEnabled) {
                    savePlayerToDatabase(playerName, data);
                }
                break;
        }
    }

    private void savePlayerToJson(@NotNull String playerName, @NotNull PlayerData data) {
        try {
            playerDataFile.saveData(playerName, data);
        } catch (RuntimeException exception) {
            Logger.severe(exception, "Failed to save player data to JSON for " + playerName);
        }
    }

    private void savePlayerToDatabase(@NotNull String playerName, @NotNull PlayerData data) {
        try {
            playerDataDAO.savePlayerData(playerName, data);
        } catch (RuntimeException | SQLException exception) {
            Logger.severe(exception, "Failed to save player data to database for " + playerName);
        }
    }


    private void saveToJson(@NotNull Map<String, PlayerData> playerDataCache) {
        for (Map.Entry<String, PlayerData> entry : playerDataCache.entrySet()) {
            try {
                playerDataFile.saveData(entry.getKey(), entry.getValue());
            } catch (RuntimeException exception) {
                Logger.severe(exception, "Failed to save player data to JSON for " + entry.getKey());
            }
        }
    }

    private void saveToDatabase(@NotNull Map<String, PlayerData> playerDataCache) {
        for (Map.Entry<String, PlayerData> entry : playerDataCache.entrySet()) {
            try {
                playerDataDAO.savePlayerData(entry.getKey(),entry.getValue());
            } catch (RuntimeException | SQLException exception) {
                Logger.severe(exception,"Failed to save player data to database for "+ entry.getKey());
            }
        }
    }
}
