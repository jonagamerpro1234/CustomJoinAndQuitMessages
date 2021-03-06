package jss.customjoinandquitmessages.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActionbarEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Player player;
	private String title;
	
	public ActionbarEvent(Player player, String title) {
		this.player = player;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
