package jss.customjoinandquitmessage.storage;

import com.google.gson.Gson;

public class PlayerData {

    private String group;

    public PlayerData(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // Method to convert PlayerData object to JSON format
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Method to create a Player Data object from a JSON string
    public static PlayerData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerData.class);
    }
}
