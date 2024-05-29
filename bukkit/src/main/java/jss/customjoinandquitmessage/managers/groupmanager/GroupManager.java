package jss.customjoinandquitmessage.managers.groupmanager;

import org.bukkit.configuration.ConfigurationSection;

public class GroupManager extends GroupHelper {

    public String getFormatMessage(String group, String section){
        if (availableGroups().contains(group)) {
            return config.getString(group + "." + section +".Format");
        }else{

            return "N/A";
        }
    }

}
