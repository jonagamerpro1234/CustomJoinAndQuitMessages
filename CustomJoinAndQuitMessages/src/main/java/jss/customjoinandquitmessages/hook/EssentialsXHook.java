package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util; 
import jss.customjoinandquitmessages.utils.interfaces.IHook;

public class EssentialsXHook implements IHook{

	private HookManager hookManager;
	private boolean isEnabled;
	private Essentials essentials;
	
	public EssentialsXHook(HookManager hookManager) {
		this.hookManager = hookManager;
	}
	
	public void setup() {
		if(!Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
			Logger.warning("&eEssentials not enabled! - Disable Features...");
			this.isEnabled = false;
			return;
		}
		
		if(!Settings.hook_essentials) {
			this.isEnabled = false;
			Logger.warning("&eEssentials not enabled! - Disable Features...");
			return;
		}
		
		this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		this.isEnabled = true;
		Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix() + "&aLoading Essentials features...");	
		Logger.warning("&e!!These features are still under development and may have bugs!!");
	}
	
	public boolean isVanish(Player player) {
		return essentials.getUser(player).isVanished();
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public HookManager getHookManager() {
		return hookManager;
	}
}
