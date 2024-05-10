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

    // Método para convertir el objeto PlayerData a formato JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Método para crear un objeto PlayerData a partir de una cadena JSON
    public static PlayerData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerData.class);
    }
}
