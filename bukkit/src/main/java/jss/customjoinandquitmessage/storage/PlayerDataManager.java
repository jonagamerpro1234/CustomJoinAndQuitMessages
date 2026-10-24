
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

    private void saveToJson(@NotNull Map<String, PlayerData> playerDataCache) {
        for (Map.Entry<String, PlayerData> entry : playerDataCache.entrySet()) {
            try {
                playerDataFile.saveData(entry.getKey(), entry.getValue());
            } catch (RuntimeException exception) {
                logger.log(Level.SEVERE, "Failed to save player data to JSON for "+ entry.getKey(), exception
                );
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
