package jss.customjoinandquitmessage.storage.database;

import jss.customjoinandquitmessage.storage.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public interface PlayerDataDAO {

    PlayerData getPlayerData(String playerName) throws SQLException;

    void savePlayerData(String playerName, @NotNull PlayerData data) throws SQLException;

}
