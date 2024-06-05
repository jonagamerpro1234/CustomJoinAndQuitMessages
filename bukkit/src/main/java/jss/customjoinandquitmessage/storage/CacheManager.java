package jss.customjoinandquitmessage.storage;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private final Map<String, PlayerData> playerDataCache;

    public CacheManager() {
        this.playerDataCache = new HashMap<>();
    }

    public Map<String, PlayerData> getPlayerDataCache() {
        return playerDataCache;
    }

    public PlayerData getPlayerData(String playerName) {
        return playerDataCache.get(playerName);
    }

    public void setPlayerData(String playerName, PlayerData playerData) {
        playerDataCache.put(playerName, playerData);
    }

    public void removePlayerData(String playerName) {
        playerDataCache.remove(playerName);
    }

    public boolean isCached(String playerName) {
        return playerDataCache.containsKey(playerName);
    }

}
