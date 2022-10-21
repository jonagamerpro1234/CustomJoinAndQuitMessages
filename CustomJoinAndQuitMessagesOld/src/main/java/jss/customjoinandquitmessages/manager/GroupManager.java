package jss.customjoinandquitmessages.manager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger;
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
        if(group == null){
            Logger.error("&cGroup could not be found: &e" + group);
            return false;
        }
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
        boolean isclick = false;
        if (existsGroup(group)) {
            isclick = config.getBoolean(group + ".ClickEvent.Enabled");
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

    public String getType(String group){
        if (existsGroup(group)) {
            return config.getString(group + ".Type");
        }
        return null;
    }

    public boolean isTitle(String group){
        boolean istitle = false;
        if (existsGroup(group)) {
            istitle = config.getBoolean(group + ".Title.Enabled");
        }
        return istitle;
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
        boolean isactionbar = false;
        if(existsGroup(group)) {
            isactionbar = config.getBoolean(group + ".Actionbar.Enabled");
        }
        return isactionbar;
    }

    public String getActionbar(String group){
        if(existsGroup(group)) {
            return config.getString(group + ".Actionbar.Text");
        }
        return null;
    }

    public boolean isSound(String group) {
        boolean issound = false;
        if(existsGroup(group)) {
            issound = config.getBoolean(group + "Sound.Enabled");
        }
        return issound;
    }

    public boolean isSoundAll(String group){
        boolean issoundall = false;
        if(existsGroup(group)){
            issoundall = config.getBoolean(group + "Sound.Send-To-All");
        }
        return issoundall;
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
