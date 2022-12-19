package jss.customjoinandquitmessages.listener;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.manager.HookManager;
import jss.customjoinandquitmessages.hook.LuckPermsHook;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class TaskLoader {

    private final CustomJoinAndQuitMessages plugin;
    private int taskGroupId;

    public TaskLoader(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public void onUpdateGroup() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskGroupId = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
            LuckPermsHook luckPermsHook = HookManager.get().getLuckPermsHook();

            if(Settings.c_type.equalsIgnoreCase("group")){
                if (luckPermsHook.isEnabled() && Settings.hook_luckperms_autoUpdate_group) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        PlayerManager playerManager = new PlayerManager();
                        if (!playerManager.getGroup(p).equalsIgnoreCase(Objects.requireNonNull(LuckPermsHook.getApi().getUserManager().getUser(p.getName())).getPrimaryGroup())) {
                            playerManager.setGroup(p, Objects.requireNonNull(LuckPermsHook.getApi().getUserManager().getUser(p.getName())).getPrimaryGroup());
                        } else {
                            Logger.debug("&eThe player already has the same group!");
                        }
                    }
                } else {
                    scheduler.cancelTask(taskGroupId);
                }
            }else{
                scheduler.cancelTask(taskGroupId);
            }
        }, 200L, Settings.hook_luckperms_autoUpdate_group_tick);
    }

}
