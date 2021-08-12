package jss.customjoinandquitmessages.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

/**
 *
 * @author jonagamerpro1234
 * @version 1.0
 *
 */
public class EventUtils {
	
	private CustomJoinAndQuitMessages plugin;
	
	public EventUtils(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void initEvent(Listener... listeners) {
		for(Listener listener : listeners) {
			getEventManager().registerEvents(listener, plugin);
		}
	}
	
	public PluginManager getEventManager() {
		return Bukkit.getPluginManager();
	}
	
	public ConsoleCommandSender getConsoleSender() {
		return Bukkit.getConsoleSender();
	}
	
}
