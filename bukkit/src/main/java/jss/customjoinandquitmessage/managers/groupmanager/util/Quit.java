package jss.customjoinandquitmessage.managers.groupmanager.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Quit {

    private String group;
    private final FileConfiguration config;

    public Quit(String group,FileConfiguration config){
        this.config = config;
        this.group = group;
    }

}
