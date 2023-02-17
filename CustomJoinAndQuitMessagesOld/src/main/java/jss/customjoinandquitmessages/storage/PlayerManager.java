package jss.customjoinandquitmessages.storage;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class PlayerManager {

    private final PlayerData playerData;
    private final PlayerJsonStorage playerJsonStorage = new PlayerJsonStorage(CustomJoinAndQuitMessages.get());

    public PlayerManager(String name){
        playerData = playerJsonStorage.loadPlayerData(name);
    }

    public String getName(){
        return playerData.getName();
    }

    public String getGroup(){
        return playerData.getGroup();
    }

    public void setGroup(String group){
        playerData.setGroup(group);
    }

    public void savePlayerData(){
        playerJsonStorage.savePlayerData(playerData);
    }
}
