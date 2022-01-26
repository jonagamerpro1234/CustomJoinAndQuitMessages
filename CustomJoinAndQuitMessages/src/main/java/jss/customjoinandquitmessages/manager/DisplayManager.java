package jss.customjoinandquitmessages.manager;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.json.Json;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;

public class DisplayManager {

	private Player player;
	private final FileConfiguration config = CustomJoinAndQuitMessages.getInstance().getConfigFile().getConfig();
	
	public DisplayManager(Player player) {
		this.player = player;
	}

	public void showFirstJoinMessage() {
		if(!Settings.firstjoin) {
			Logger.warning("&e[showFirstJoinMessage] &b-> &7This feature is disabled and you will not be able to see the preview"); 
			return;
		}
		
		String text = "";

		
		if (Settings.is_Group_Display) {

		} else {
			text = Settings.join_message_first;
		}
		
		boolean isNormalType = Settings.join_type.equalsIgnoreCase("normal");
		boolean isModifyType = Settings.join_type.equalsIgnoreCase("modify");
		
		Json json = new Json(player, Utils.color(Utils.getVar(player, text)));
	
		if(isNormalType) {
			json.sendToAll();
			return;
		}else if(isModifyType) {
			
			boolean isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
			boolean isClick = config.getString("Join.ClickEvent.Enabled").equals("true");
			
			List<String> Hover_Text = config.getStringList("Join.HoverEvent.Hover");

			String isClick_Mode = config.getString("Join.ClickEvent.Mode");
			String Action_Command = config.getString("Join.ClickEvent.Actions.Command");
			String Action_Url = config.getString("Join.ClickEvent.Actions.Url");
			String Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
			
			if (isHover) {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.setHover(Hover_Text).sendToAll();
				}
			} else {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.sendToAll();
				}
			}
		}
	}
	
	public void showJoinMessage() {
		if(!Settings.join) {
			Logger.warning("&e[showJoinMessage] &b-> &7This feature is disabled and you will not be able to see the preview"); 
			return;
		}
		String text = "";

		if (Settings.is_Group_Display) {

		} else {
			text = Settings.join_message;
		}
		
		boolean isNormalType = Settings.join_type.equalsIgnoreCase("normal");
		boolean isModifyType = Settings.join_type.equalsIgnoreCase("modify");
		
		Json json = new Json(player, Utils.color(Utils.getVar(player, text)));
			
		if(isNormalType) {
			json.sendToAll();
			return;
		}else if(isModifyType) {
			
			boolean isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
			boolean isClick = config.getString("Join.ClickEvent.Enabled").equals("true");
			
			List<String> Hover_Text = config.getStringList("Join.HoverEvent.Hover");

			String isClick_Mode = config.getString("Join.ClickEvent.Mode");
			String Action_Command = config.getString("Join.ClickEvent.Actions.Command");
			String Action_Url = config.getString("Join.ClickEvent.Actions.Url");
			String Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
			
			if (isHover) {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.setHover(Hover_Text).sendToAll();
				}
			} else {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.sendToAll();
				}
			}
		}
	}

	public void showQuitMessage() {
		if(!Settings.quit) {
			Logger.warning("&e[showQuitMessage] &b-> &7This feature is disabled and you will not be able to see the preview"); 
			return;
		}
		
		String text = "";

		if (Settings.is_Group_Display) {

		} else {
			text = Settings.quit_message;
		}
		
		boolean isNormalType = Settings.quit_type.equalsIgnoreCase("normal");
		boolean isModifyType = Settings.quit_type.equalsIgnoreCase("modify");
		
		Json json = new Json(player, Utils.color(Utils.getVar(player, text)));
		
		if(isNormalType) {
			json.sendToAll();
			return;
		}else if(isModifyType) {
			
			boolean isHover = config.getString("Quit.HoverEvent.Enabled").equals("true");
			boolean isClick = config.getString("Quit.ClickEvent.Enabled").equals("true");
			
			List<String> Hover_Text = config.getStringList("Quit.HoverEvent.Hover");

			String isClick_Mode = config.getString("Quit.ClickEvent.Mode");
			String Action_Command = config.getString("Quit.ClickEvent.Actions.Command");
			String Action_Url = config.getString("Quit.ClickEvent.Actions.Url");
			String Action_Suggest = config.getString("Quit.ClickEvent.Actions.Suggest-Command");
			
			if (isHover) {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.setHover(Hover_Text).sendToAll();
				}
			} else {
				if (isClick) {
					if (isClick_Mode.equalsIgnoreCase("command")) {
						json.setExecuteCommand(Action_Command).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("url")) {
						json.setOpenURL(Action_Url).sendToAll();
					} else if (isClick_Mode.equalsIgnoreCase("suggest")) {
						json.setSuggestCommand(Action_Suggest).sendToAll();
					}
				} else {
					json.sendToAll();
				}
			}
		}
	}
	
	public void showWelcomeMessage() {
		if(!Settings.welcome) {
			Logger.warning("&e[showWelcomeMessage] &b-> &7This feature is disabled and you will not be able to see the preview"); 
			return;
		}
		
		Settings.list_welcome.forEach( (text) -> {
			Utils.sendColorMessage(player, Utils.getVar(player, text));
		});
	}
	
	
}
