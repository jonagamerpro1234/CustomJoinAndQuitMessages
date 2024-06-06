package jss.customjoinandquitmessage.managers.groupmanager.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Join implements IChatFormat{

    private String group;
    private final FileConfiguration config;

    public Join(String group,FileConfiguration config){
        this.config = config;
        this.group = group;
    }

    public boolean isMainEnabled() {
        return false;
    }

    public String getMainFormat() {
        return null;
    }

    public int MainDelay() {
        return 0;
    }

    public boolean isWelcome() {
        return false;
    }

    public List<String> getWelcome() {
        return null;
    }

    public boolean isTitle() {
        return false;
    }

    public String getTitle() {
        return null;
    }

    public String getSubTitle() {
        return null;
    }

    public int getFadeIn() {
        return 0;
    }

    public int getStay() {
        return 0;
    }

    public int getFadeOut() {
        return 0;
    }

    public boolean isActionbar() {
        return false;
    }

    public String getActionbar() {
        return null;
    }

    public boolean isSound() {
        return false;
    }

    public String getNameSound() {
        return null;
    }

    public boolean isSendToAllSound() {
        return false;
    }

    public int getSoundVolume() {
        return 0;
    }

    public int getSoundPitch() {
        return 0;
    }

}
