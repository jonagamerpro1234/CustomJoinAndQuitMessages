package jss.customjoinandquitmessages.events;

import org.bukkit.event.Listener;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventsUtils;

public class JoinListener implements Listener {

	private CustomJoinAndQuitMessages plugin;
	private EventsUtils eventsUtils = new EventsUtils(plugin);
	
	public JoinListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventsUtils.getEventManager().registerEvents(this, plugin);
		
	}
	
	
	
}
