package jss.customjoinandquitmessage.files;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class GroupFile {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();
    private File file;
    private FileConfiguration config;

    //create groups.yml file
    public void createFile() throws IOException {
        this.file = new File(plugin.getDataFolder(), "groups.yml");

        if(!file.exists()){
            file.createNewFile();
        }
    }

    //get config for groups.yml
    public FileConfiguration get(){
        return config;
    }

    //reload groups.yml file
    public void reload(){

    }


    
}
