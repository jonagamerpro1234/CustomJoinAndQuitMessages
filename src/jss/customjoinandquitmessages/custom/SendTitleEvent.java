package jss.customjoinandquitmessages.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SendTitleEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Player player;
	private String title;
	private String subtitle;

	public SendTitleEvent(Player player, String title, String subtitle) {
		this.player = player;
		this.title = title;
		this.subtitle = subtitle;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
