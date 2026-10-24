
package jss.customjoinandquitmessage.storage;

import jss.customjoinandquitmessage.files.PlayerDataFile;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDataManager {

    private final PlayerDataFile playerDataFile;
    private final PlayerDataDAO playerDataDAO;
    private final CacheManager cacheManager;
    private final Logger logger;
    private final boolean databaseEnabled;

    public enum DataType {
        JSON,
        DATABASE,
        BOTH
    }

    public PlayerDataManager(@NotNull PlayerDataFile playerDataFile, @NotNull PlayerDataDAO playerDataDAO, @NotNull CacheManager cacheManager, @NotNull Logger logger, boolean databaseEnabled) {
        this.playerDataFile = Objects.requireNonNull(playerDataFile);
        this.playerDataDAO = Objects.requireNonNull(playerDataDAO);
        this.cacheManager = Objects.requireNonNull(cacheManager);
        this.logger = Objects.requireNonNull(logger);
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
                logger.log(Level.SEVERE, "Failed to load player data from database: " + playerName, exception);
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
                    logger.warning("Cannot save player data: database is disabled.");
                    return;
                }

                saveToDatabase(playerDataCache);
                break;

            case BOTH:
                saveToJson(playerDataCache);

                if (databaseEnabled) {
                    saveToDatabase(playerDataCache);
                } else {
                    logger.warning("Database is disabled. Player data was saved to JSON only.");
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
                    logger.warning("Cannot save player data: database is disabled.");
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
            logger.log(Level.SEVERE, "Failed to save player data to JSON for " + playerName, exception);
        }
    }

    private void savePlayerToDatabase(@NotNull String playerName, @NotNull PlayerData data) {
        try {
            playerDataDAO.savePlayerData(playerName, data);
        } catch (RuntimeException | SQLException exception) {
            logger.log(Level.SEVERE, "Failed to save player data to database for " + playerName, exception);
        }
    }


    private void saveToJson(@NotNull Map<String, PlayerData> playerDataCache) {
        for (Map.Entry<String, PlayerData> entry : playerDataCache.entrySet()) {
            try {
                playerDataFile.saveData(entry.getKey(), entry.getValue());
            } catch (RuntimeException exception) {
                logger.log(Level.SEVERE, "Failed to save player data to JSON for " + entry.getKey(), exception);
            }
        }
    }

    private void saveToDatabase(@NotNull Map<String, PlayerData> playerDataCache) {
        for (Map.Entry<String, PlayerData> entry : playerDataCache.entrySet()) {
            try {
                playerDataDAO.savePlayerData(entry.getKey(),entry.getValue());
            } catch (RuntimeException | SQLException exception) {
                logger.log(Level.SEVERE,"Failed to save player data to database for "+ entry.getKey(),exception);
            }
        }
    }
}
