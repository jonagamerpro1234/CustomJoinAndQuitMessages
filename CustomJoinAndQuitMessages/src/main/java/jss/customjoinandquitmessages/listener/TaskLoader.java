package jss.customjoinandquitmessages.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.LuckPermsHook;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;

public class TaskLoader {
	
	private CustomJoinAndQuitMessages plugin;
	private int taskGroupId;
	
	public TaskLoader(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}



	public void onUpdateGroup() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		taskGroupId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if(Settings.hook_luckperms_autoUpdate_group) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						PlayerManager playerManager = new PlayerManager();
						if(!playerManager.getGroup(p).equalsIgnoreCase(LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup())) {
							playerManager.setGroup(p, LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup());
						}else {
							Logger.debug("&eThe player already has the same group!");
						}
					}
				}else {
					scheduler.cancelTask(taskGroupId);
				}
			}
		}, 0L, 600L);
	}
	
}
