package jss.customjoinandquitmessages.json;

import java.util.List;

import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setHover(List<String> hover) {
		this.hoverText = new BaseComponent[hover.size()];
		for(int i = 0; i < hover.size(); i++) {
			TextComponent component = new TextComponent();
			if(i == hover.size() -1) {
				component.setText(Utils.hexcolor(Utils.getVar(this.player, (String)hover.get(i))));
			}else {
				component.setText(Utils.hexcolor(Utils.getVar(this.player, (String)hover.get(i)) + "\n"));
			}
			this.hoverText[i] = component;
		}
	}

	public void setSuggestCommand(String suggestCommand) {
		this.suggestCommand = suggestCommand;
	}

	public void setExecuteCommand(String executeCommand) {
		this.executeCommand = executeCommand;
	}
	
	public void setOpenURL(String url) {
		this.openUrl = url;
	}
	
	public void send() {
		TextComponent component = new TextComponent(Utils.hexcolor(this.text));
		if(this.hoverText != null) {
			component.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, this.hoverText));
		} 
		if(this.executeCommand != null){
			component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, this.executeCommand));
		}
		if(this.suggestCommand != null) {
			component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
		}
		if(this.openUrl != null) {
			component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
		}
		player.spigot().sendMessage(component);
	}
	
	public void sendToAll() {
		TextComponent component = new TextComponent(Utils.hexcolor(this.text));
		if(this.hoverText != null) {
			component.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, this.hoverText));
		} 
		if(this.executeCommand != null){
			component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, this.executeCommand));
		}
		if(this.suggestCommand != null) {
			component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
		}
		if(this.openUrl != null) {
			component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
		}
		Utils.sendAllPlayerBaseComponent(component);
	}
}
