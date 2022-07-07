package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;

public class AuthmeHook implements IHook{

	private HookManager hookManager;
	private boolean isEnabled;
	
	public AuthmeHook(HookManager hookManager) {
		this.hookManager = hookManager;
	}
	
	public void setup() {
		if(!Bukkit.getPluginManager().isPluginEnabled("Authme")) {
			Logger.warning("&eAuthme not enabled! - Disable Features...");
			this.isEnabled = false;
			return;
		}
		
		if(!Settings.hook_discordsrv) {
			this.isEnabled = false;
			Logger.warning("&eAuthme not enabled! - Disable Features...");
			return;
		}
		
		this.isEnabled = true;
		Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix() + "&aLoading Authme features...");
		Logger.warning("&e!!These features are still under development and may have bugs!!");
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public HookManager getHookManager() {
		return hookManager;
	}
}
