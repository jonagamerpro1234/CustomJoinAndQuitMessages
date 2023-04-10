package jss.customjoinandquitmessages.manager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GroupManager {

    private final FileConfiguration config = CustomJoinAndQuitMessages.get().getGroupsFile().getConfig();

    @Contract(" -> new")
    public static @NotNull GroupManager get() {
        return new GroupManager();
    }

    public Set<String> getGroupList() {
        return config.getKeys(false);
    }

    public boolean existsGroup(String group) {
        return getGroupList().contains(group);
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
            isHover = config.getBoolean(group + ".First-Join.Enabled");
        }
        return isHover;
    }

    public boolean isHover(String group) {
        boolean isHover = false;
        if (existsGroup(group)) {
            isHover = config.getBoolean(group + ".HoverEvent.Enabled");
        }
        return isHover;
    }

    public List<String> getHover(String group) {
        if (existsGroup(group)) {
            return config.getStringList(group + ".HoverEvent.Hover");
        }
        return new ArrayList<>();
    }

    public boolean isClick(String group) {
        boolean isClick = false;
        if (existsGroup(group)) {
            isClick = config.getBoolean(group + ".ClickEvent.Enabled");
        }
        return isClick;
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

    public boolean isTitle(String group){
        boolean isTitle = false;
        if (existsGroup(group)) {
            isTitle = config.getBoolean(group + ".Title.Enabled");
        }
        return isTitle;
    }

    public String getTitle(String group){
        if(existsGroup(group)){
            return config.getString(group + ".Title.Title");
        }
        return null;
    }

    public String getSubTitle(String group){
        if(existsGroup(group)){
            return config.getString(group + ".Title.SubTitle");
        }
        return null;
    }

    public int getFadeIn(String group){
        if(existsGroup(group)){
            return config.getInt(group + ".Title.FadeIn");
        }
        return 0;
    }

    public int getStay(String group){
        if(existsGroup(group)){
            return config.getInt(group + ".Title.Stay");
        }
        return 0;
    }

    public int getFadeOut(String group){
        if(existsGroup(group)){
            return config.getInt(group + ".Title.FadeOut");
        }
        return 0;
    }

    public boolean isActionbar(String group){
        boolean isActionBar = false;
        if(existsGroup(group)) {
            isActionBar = config.getBoolean(group + ".Actionbar.Enabled");
        }
        return isActionBar;
    }

    public String getActionbar(String group){
        if(existsGroup(group)) {
            return config.getString(group + ".Actionbar.Text");
        }
        return null;
    }

    public boolean isSound(String group) {
        boolean isSound = false;
        if(existsGroup(group)) {
            isSound = config.getBoolean(group + "Sound.Enabled");
        }
        return isSound;
    }

    public boolean isSoundAll(String group){
        boolean isSoundAll = false;
        if(existsGroup(group)){
            isSoundAll = config.getBoolean(group + "Sound.Send-To-All");
        }
        return isSoundAll;
    }

    public String getSound(String group){
        if(existsGroup(group)) {
            return config.getString(group + ".Sound.Name");
        }
        return null;
    }

    public int getVolume(String group){
        if(existsGroup(group)){
            return config.getInt(group + ".Sound.Volume");
        }
        return 1;
    }

    public float getPitch(String group){
        if(existsGroup(group)){
            return Float.parseFloat(Objects.requireNonNull(config.getString(group + ".Sound.Pitch")));
        }
        return 1.0f;
    }

}
