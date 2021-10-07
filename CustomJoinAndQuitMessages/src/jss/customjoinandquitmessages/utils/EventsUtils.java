package jss.customjoinandquitmessages.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class EventsUtils {
	
	private CustomJoinAndQuitMessages plugin;
	
	public EventsUtils(CustomJoinAndQuitMessages plugin) {
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
	
	public static ConsoleCommandSender getStaticConsoleSender() {
		return Bukkit.getConsoleSender();
	}
}
