package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class LuckPermsHook implements IHook{

	private HookManager hookManager;
	private boolean isEnabled;
	
	public LuckPermsHook(HookManager hookManager) {
		this.hookManager = hookManager;
	}
	
	public void setup() {
		if(!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
			Logger.warning("&eLuckPerms not enabled! - Disable Features...");
			this.isEnabled = false;
			return;
		}
		
		if(!Settings.hook_luckperms) {
			this.isEnabled = false;
			Logger.warning("&eLuckPerms not enabled! - Disable Features...");
			return;
		}
		
		this.isEnabled = true;
		Utils.sendColorMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&aLoading LuckPerms features...");	
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public boolean isGroup(Player player, String name){
		LuckPerms api = LuckPermsProvider.get();
		String group = api.getUserManager().getUser(player.getName()).getPrimaryGroup();
		boolean a = false;
		if(name.equals(group)) {
			a = true;
		}
		return a;
	}

	public String getGroup(Player player){
		if(player != null) {
			Logger.debug("N");
		}
		LuckPerms api = LuckPermsProvider.get();
		String group = api.getUserManager().getUser(player.getName()).getPrimaryGroup();
		return group;
	}
	
	public HookManager getHookManager() {
		return hookManager;
	}
}
