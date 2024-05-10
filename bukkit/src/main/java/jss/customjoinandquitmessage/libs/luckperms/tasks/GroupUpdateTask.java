package jss.customjoinandquitmessage.libs.luckperms.tasks;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.storage.PlayerData;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GroupUpdateTask extends BukkitRunnable {

    private final CustomJoinAndQuitMessage plugin;

    public GroupUpdateTask(CustomJoinAndQuitMessage plugin) {
        this.plugin = plugin;
    }

    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){

            String group;

            if(Settings.luckperms_enabled){
                group = Objects.requireNonNull(getApi().getUserManager().getUser(p.getName())).getPrimaryGroup();
            }else{
                group = "default";
            }

            if(plugin.playerDataFile.playerDataFileExists(p.getName())){
                plugin.playerDataFile.saveData(p.getName(), new PlayerData(group));
            }else{
                if(!plugin.playerDataFile.loadData(p.getName()).getGroup().equalsIgnoreCase(group)){
                    plugin.playerDataFile.saveData(p.getName(), new PlayerData(group));
                }
            }

        }
    }

    //Temp
    @Contract(pure = true)
    private @NotNull LuckPerms getApi(){
        return LuckPermsProvider.get();
    }

    public void start(int delay, int ticks, boolean isEnabled){
        if(isEnabled){
            this.runTaskTimer(plugin, delay , ticks);
        }
    }
}
