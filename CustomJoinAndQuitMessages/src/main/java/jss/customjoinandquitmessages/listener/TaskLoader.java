package jss.customjoinandquitmessages.listener;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.HookManager;
import jss.customjoinandquitmessages.hook.LuckPermsHook;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class TaskLoader {

    private CustomJoinAndQuitMessages plugin;
    private int taskGroupId;

    public TaskLoader(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public void onUpdateGroup() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskGroupId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                LuckPermsHook luckPermsHook = HookManager.get().getLuckPermsHook();

                if(luckPermsHook.isEnabled()){
                    if (Settings.hook_luckperms_autoUpdate_group) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            PlayerManager playerManager = new PlayerManager();
                            if (!playerManager.getGroup(p).equalsIgnoreCase(LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup())) {
                                playerManager.setGroup(p, LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup());
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
            }
        }, 200L, Settings.hook_luckperms_autoUpdate_group_tick);
    }

}
