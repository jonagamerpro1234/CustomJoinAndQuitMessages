package jss.customjoinandquitmessage.managers.groupmanager.util;

import org.bukkit.configuration.file.FileConfiguration;

public class FirstJoin {

    private String group;
    private final FileConfiguration config;

    public FirstJoin(String group,FileConfiguration config){
        this.config = config;
        this.group = group;
    }

}
