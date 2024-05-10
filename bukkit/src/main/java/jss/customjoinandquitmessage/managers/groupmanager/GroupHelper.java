package jss.customjoinandquitmessage.managers.groupmanager;

import jss.customjoinandquitmessage.files.GroupFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class GroupHelper {

    public FileConfiguration config = new GroupFile().getConfig();

    private boolean useDefaultSystemGroup;
    private boolean isLuckperms;

    public void checkPermissionPlugins(boolean value){
         value = this.isLuckperms;
    }

    public boolean isPermissionPlugin(){
        return this.isLuckperms;
    }

    public Set<String> availableGroups(){
        return config.getKeys(false);
    }

    public String findGroup(@NotNull String name){
        return availableGroups().stream().filter(name::equalsIgnoreCase).findFirst().orElse("<red>Not found");
    }

    public Set<String> getDevKeys(){
        return config.getKeys(true);
    }

}
