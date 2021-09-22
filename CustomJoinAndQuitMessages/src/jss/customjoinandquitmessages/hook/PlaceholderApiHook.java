package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventsUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Logger.Level;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.interfaces.LoaderHook;

public class PlaceholderApiHook implements LoaderHook{
	
	private CustomJoinAndQuitMessages plugin;
	private HooksManager hooksManager;
	private Logger logger = new Logger(plugin);
	private EventsUtils EventsUtils = new EventsUtils(plugin);
	private boolean isEnabled;
	
	public PlaceholderApiHook(HooksManager hooksManager) {
		this.hooksManager = hooksManager;
	}

	public void load() {
		if(!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			this.isEnabled = false;
			logger.Log(Level.WARNING, "placeholderapi not enabled! - Disable Features...");
			return;
		}
		
		this.isEnabled = true;
		Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + " " + "&aLoading placeholderapi features...");
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}

	public HooksManager getHooksManager() {
		return hooksManager;
	}

}
