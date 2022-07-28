package jss.customjoinandquitmessages.manager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupManager {

    private final FileConfiguration config = CustomJoinAndQuitMessages.get().getGroupsFile().getConfig();

    public static GroupManager get() {
        return new GroupManager();
    }

    public Set<String> getGroupList() {
        Set<String> sections = config.getKeys(false);
        return sections;
    }

    public boolean existsGroup(String group) {
        return config.contains(group);
    }

    public String getGroup(String group) {
        if (existsGroup(group)) {
            return config.getString(group);
        }
        return null;
    }

    public String getJoin(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".Join");
        }
        return null;
    }

    public String getQuit(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".Quit");
        }
        return null;
    }

    public String getFirstJoin(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".First-Join.Text");
        }
        return null;
    }

    public boolean isFirstJoin(String group) {
        boolean isHover = false;
        if (existsGroup(group)) {
            isHover = config.getString(group + ".First-Join.Enabled").equals("true");
        }
        return isHover;
    }

    public boolean isHover(String group) {
        boolean isHover = false;
        if (existsGroup(group)) {
            isHover = config.getString(group + ".HoverEvent.Enabled").equals("true");
        }
        return isHover;
    }

    public List<String> getHover(String group) {
        if (existsGroup(group)) {
            return config.getStringList(group + ".HoverEvent.Hover");
        }
        return new ArrayList<String>();
    }

    public boolean isClick(String group) {
        boolean isclick = false;
        if (existsGroup(group)) {
            isclick = config.getString(group + ".ClickEvent.Enabled").equals("true");
        }
        return isclick;
    }

    public String getClickMode(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".ClickEvent.Mode");
        }
        return null;
    }

    public String getClickCommand(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".ClickEvent.Actions.Command");
        }
        return null;
    }

    public String getClickUrl(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".ClickEvent.Actions.Url");
        }
        return null;
    }

    public String getClickSuggestCommand(String group) {
        if (existsGroup(group)) {
            return config.getString(group + ".ClickEvent.Actions.Suggest-Command");
        }
        return null;
    }


}
