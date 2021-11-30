package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.interfaces.IHook;

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
		
		if(!Settings.hook_discordsrv) {
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
	
	public HookManager getHookManager() {
		return hookManager;
	}
}
