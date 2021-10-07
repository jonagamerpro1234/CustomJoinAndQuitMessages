package jss.customjoinandquitmessages.json;

import java.util.List;

import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.ClickEvent.Action;

@SuppressWarnings("deprecation")
public class Json {

	private Player player;
	private String text;
	private BaseComponent[] hoverText;
	private String suggestCommand;
	private String executeCommand;
	private String openUrl;
	
	public Json(Player player, String text) {
		this.player = player;
		this.text = text;
		this.hoverText = null;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getText() {
		return text;
	}
	
	public Json setText(String text) {
		this.text = text;
		return this;
	}
	
	public Json setHover(List<String> hover) {
		this.hoverText = new BaseComponent[hover.size()];
		for(int i = 0; i < hover.size(); i++) {
			TextComponent component = new TextComponent();
			if(i == hover.size() -1) {
				component.setText(Utils.color(Utils.getVar(this.player, (String)hover.get(i))));
			}else {
				component.setText(Utils.color(Utils.getVar(this.player, (String)hover.get(i)) + "\n"));
			}
			this.hoverText[i] = component;
		}
		return this;
	}

	public Json setSuggestCommand(String suggestCommand) {
		this.suggestCommand = suggestCommand;
		return this;
	}

	public Json setExecuteCommand(String executeCommand) {
		this.executeCommand = executeCommand;
		return this;
	}
	
	public Json setOpenURL(String url) {
		this.openUrl = url;
		return this;
	}
	
	public void send() {
		TextComponent component = new TextComponent(Utils.color(this.text));
		if(this.hoverText != null) {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.hoverText));
		} 
		if(this.executeCommand != null){
			component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.executeCommand));
		}
		if(this.suggestCommand != null) {
			component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
		}
		if(this.openUrl != null) {
			component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
		}
		player.spigot().sendMessage(component);
	}
	
	public void sendToAll() {
		TextComponent component = new TextComponent(Utils.color(this.text));
		if(this.hoverText != null) {
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.hoverText));
		} 
		if(this.executeCommand != null){
			component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.executeCommand));
		}
		if(this.suggestCommand != null) {
			component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
		}
		if(this.openUrl != null) {
			component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
		}
		Utils.sendAllPlayerBaseComponent(component);
	}
}
