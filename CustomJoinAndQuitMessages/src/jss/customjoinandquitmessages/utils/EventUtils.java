package jss.customjoinandquitmessages.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class EventUtils {
	
	private CustomJoinAndQuitMessages plugin;
	
	public EventUtils(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public  void addEventList(Listener listener) {
		getEventManager().registerEvents(listener, plugin);
	}
	
	public PluginManager getEventManager() {
		return Bukkit.getPluginManager();
	}
	
	public ConsoleCommandSender getConsoleSender() {
		return Bukkit.getConsoleSender();
	}
	
}
