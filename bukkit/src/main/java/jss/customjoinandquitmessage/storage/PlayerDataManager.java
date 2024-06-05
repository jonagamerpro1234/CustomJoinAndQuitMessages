package jss.customjoinandquitmessage.storage;

import jss.customjoinandquitmessage.files.PlayerDataFile;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAO;
import org.jetbrains.annotations.NotNull;

public class PlayerDataManager {

    private PlayerDataFile playerDataFile;
    private PlayerDataDAO playerDataDAO;
    private CacheManager cacheManager;


    public enum typeData {
        Json,DataBase
    }


    public void save(@NotNull typeData typeData){

    }

    public void saveAll(){

    }

}
