package jss.customjoinandquitmessages.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.json.Json;
import jss.customjoinandquitmessages.utils.EventsUtils;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class JoinListener implements Listener {

	private CustomJoinAndQuitMessages plugin;
	private EventsUtils eventsUtils = new EventsUtils(plugin);
	
	public JoinListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventsUtils.getEventManager().registerEvents(this, plugin);
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		Player p = e.getPlayer();
		
		boolean isDefault = config.getString("Config.Type").equalsIgnoreCase("default");
		boolean isNormal = config.getString("Config.Type").equalsIgnoreCase("normal");
		boolean isGroup = config.getString("Config.Type").equalsIgnoreCase("group");
		boolean isEnabled = config.getString("Join.Enabled").equals("true");
		boolean isNone = config.getString("Config.Type").equalsIgnoreCase("none");
		boolean isUpdate = config.getString("Config.Update").equals("true");
		boolean isWelcome = config.getString("Welcome.Enabled").equals("true");
		
		if(isWelcome) {
			
			List<String> List_Text = config.getStringList("Welcome.Text");
			
			for(int i = 0; i < List_Text.size(); i++) {
				String text = (String) List_Text.get(i);
				text = Utils.color(text);
				text = Utils.getVar(p, text);
				Utils.sendColorMessage(text);
			}
			
		}
		
		if(isEnabled) {
			if(isDefault) {
				return;
			}else if(isNormal) {
				e.setJoinMessage(null);
						
				boolean isNormalType = config.getString("Join.Type").equalsIgnoreCase("normal");
				boolean isModifyType = config.getString("Join.Type").equalsIgnoreCase("modify");
				
				String text = config.getString("Join.Text");
				
				text = Utils.color(text);
				text = Utils.getVar(p, text);
				
				Json json = new Json(p, text);
				
				if(isNormalType) {
					json.sendToAll();
					return;
				}else if(isModifyType) {
					
					boolean isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
					boolean isClick = config.getString("Join.ClickEvent.Enabled").equals("true");
					boolean isTitle = config.getString("Join.Title.Enabled").equals("true");
					boolean isSound = config.getString("Join.Sound.Enabled").equals("true");
					boolean isActionBar = config.getString("Join.ActionBar").equals("true");
					boolean isSoundAll = config.getString("Join.Sound.Send-To-All").equals("true");
					
					List<String> Hover_Text = config.getStringList("Join.HoverEvent.Hover");
					
					String isClick_Mode = config.getString("Join.ClickEvent.Mode");
					String Action_Command = config.getString("Join.ClickEvent.Actions.Command");
					String Action_Url = config.getString("Join.ClickEvent.Actions.Url");
					String Action_Suggest= config.getString("Join.ClickEvent.Actions.Suggest-Command");
					String Title_Text = config.getString("Join.Title.Title");
					String SubTitle_Text = config.getString("Join.Title.SubTitle");
					String Actionbar_Text = config.getString("Join.ActionBar.Text");
					String Sound_Name = config.getString("Join.Sound.Name");
					
					int FadeIn = config.getInt("Join.Title.FadeIn");
					int Stay = config.getInt("Join.Title.Stay");
					int FadeOut = config.getInt("Join.Title.FadeOut");
					int Sound_Volume = config.getInt("Join.Sound.Volume");
					
					float Sound_Pitch = Float.valueOf(config.getString("Join.Sound.Pitch"));
					
					if(isHover) {
						if(isClick) {
							if(isClick_Mode.equalsIgnoreCase("command")) {
								json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("url")) {
								json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
								json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
							}
						}else {
							json.setHover(Hover_Text).sendToAll();
						}
					}else{
						if(isClick) {
							if(isClick_Mode.equalsIgnoreCase("command")) {
								json.setExecuteCommand(Action_Command).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("url")) {
								json.setOpenURL(Action_Url).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
								json.setSuggestCommand(Action_Suggest).sendToAll();
							}
						}else {
							json.sendToAll();
						}
					}
					
					if(isTitle) {
						Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)), Utils.color(Utils.getVar(p, SubTitle_Text)));
					}
					
					if(isActionBar) {
						ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
					}
					
					if(isSound) {
						 if(isSoundAll) {
							 Location location = p.getLocation();
							 p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
						 }else {
							 for(Player pp : Bukkit.getOnlinePlayers()) {
								 Location location = p.getLocation();
								 pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch); 
							 }
						 }
					}
				}
				
				return;
			}else if(isGroup) {
				e.setJoinMessage(null);
				
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
					boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");
					
					String text = config.getString("Groups." + key + ".Join-Text");
					String isPermission = config.getString("Groups." + key + ".Permission");
					
					text = Utils.color(text);
					text = Utils.getVar(p, text);
					
					Json json = new Json(p, text);
					
					if(isNormalType) {
						
						if(p.hasPermission(isPermission));
						
						json.sendToAll();
						return;
					}else if(isModifyType) {
						
						boolean isHover = config.getString("Groups." + key + ".HoverEvent.Enabled").equals("true");
						boolean isClick = config.getString("Groups." + key + ".ClickEvent.Enabled").equals("true");
						boolean isTitle = config.getString("Groups." + key + ".Title.Enabled").equals("true");
						boolean isSound = config.getString("Groups." + key + ".Sound.Enabled").equals("true");
						boolean isActionBar = config.getString("Groups." + key + ".ActionBar").equals("true");
						boolean isSoundAll = config.getString("Groups." + key + ".Sound.Send-To-All").equals("true");
						
						List<String> Hover_Text = config.getStringList("Groups." + key + ".HoverEvent.Hover");
						
						String isClick_Mode = config.getString("Groups." + key + ".ClickEvent.Mode");
						String Action_Command = config.getString("Groups." + key + ".ClickEvent.Actions.Command");
						String Action_Url = config.getString("Groups." + key + ".ClickEvent.Actions.Url");
						String Action_Suggest= config.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
						String Title_Text = config.getString("Groups." + key + ".Title.Title");
						String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
						String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
						String Sound_Name = config.getString("Groups." + key + ".Sound.Name");
						
						int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
						int Stay = config.getInt("Groups." + key + ".Title.Stay");
						int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
						int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");
						
						float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));
						
						if(p.hasPermission(isPermission));
						
						if(isHover) {
							if(isClick) {
								if(isClick_Mode.equalsIgnoreCase("command")) {
									json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("url")) {
									json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
									json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
								}
							}else {
								json.setHover(Hover_Text).sendToAll();
							}
						}else{
							if(isClick) {
								if(isClick_Mode.equalsIgnoreCase("command")) {
									json.setExecuteCommand(Action_Command).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("url")) {
									json.setOpenURL(Action_Url).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
									json.setSuggestCommand(Action_Suggest).sendToAll();
								}
							}else {
								json.sendToAll();
							}
						}
						
						if(isTitle) {
							Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)), Utils.color(Utils.getVar(p, SubTitle_Text)));
						}
						
						if(isActionBar) {
							ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
						}
						
						if(isSound) {
							 if(isSoundAll) {
								 Location location = p.getLocation();
								 p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
							 }else {
								 for(Player pp : Bukkit.getOnlinePlayers()) {
									 Location location = p.getLocation();
									 pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch); 
								 }
							 }
						}
					}
					
				}
				return;
			}else if(isNone) {
				e.setJoinMessage(null);
				return;
			}
		}
		
		if(isUpdate) {
			if(p.isOp() || p.hasPermission("Cjm.Update.Notify")) {
				new UpdateChecker(CustomJoinAndQuitMessages.getPlugin(), UpdateSettings.ID).getUpdateVersion(version ->{
					if(!CustomJoinAndQuitMessages.getPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
	                    TextComponent component = new TextComponent(Utils.color(Utils.getPrefixPlayer() + " &aThere is a new version available for download"));
	                    component.setClickEvent(new ClickEvent(Action.OPEN_URL, UpdateSettings.URL_PlUGIN[0]));
	                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.color("&6Click on this message to copy the link")).create()));
	                    p.spigot().sendMessage(component);
					}
				});
			}
		}
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		Player p = e.getPlayer();
		
		boolean isDefault = config.getString("Config.Type").equalsIgnoreCase("default");
		boolean isNormal = config.getString("Config.Type").equalsIgnoreCase("normal");
		boolean isGroup = config.getString("Config.Type").equalsIgnoreCase("group");
		boolean isEnabled = config.getString("Quit.Enabled").equals("true");
		boolean isNone = config.getString("Config.Type").equalsIgnoreCase("none");
		
		if(isEnabled) {
			if(isDefault) {
				return;
			}else if(isNormal) {
				e.setQuitMessage(null);
						
				boolean isNormalType = config.getString("Quit.Type").equalsIgnoreCase("normal");
				boolean isModifyType = config.getString("Quit.Type").equalsIgnoreCase("modify");
				
				String text = config.getString("Quit.Text");
				
				text = Utils.color(text);
				text = Utils.getVar(p, text);
				
				Json json = new Json(p, text);
				
				if(isNormalType) {
					json.sendToAll();
					return;
				}else if(isModifyType) {
					
					boolean isHover = config.getString("Quit.HoverEvent.Enabled").equals("true");
					boolean isClick = config.getString("Quit.ClickEvent.Enabled").equals("true");
					//boolean isTitle = config.getString("Quit.Title.Enabled").equals("true");
					boolean isSound = config.getString("Quit.Sound.Enabled").equals("true");
					//boolean isActionBar = config.getString("Quit.ActionBar").equals("true");
					boolean isSoundAll = config.getString("Quit.Sound.Send-To-All").equals("true");
					
					List<String> Hover_Text = config.getStringList("Quit.HoverEvent.Hover");
					
					String isClick_Mode = config.getString("Quit.ClickEvent.Mode");
					String Action_Command = config.getString("Quit.ClickEvent.Actions.Command");
					String Action_Url = config.getString("Quit.ClickEvent.Actions.Url");
					String Action_Suggest= config.getString("Quit.ClickEvent.Actions.Suggest-Command");
					//String Title_Text = config.getString("Quit.Title.Title");
					//String SubTitle_Text = config.getString("Quit.Title.SubTitle");
					//String Actionbar_Text = config.getString("Quit.ActionBar.Text");
					String Sound_Name = config.getString("Quit.Sound.Name");
					
					//int FadeIn = config.getInt("Quit.Title.FadeIn");
					//int Stay = config.getInt("Quit.Title.Stay");
					//int FadeOut = config.getInt("Quit.Title.FadeOut");
					int Sound_Volume = config.getInt("Quit.Sound.Volume");
					
					float Sound_Pitch = Float.valueOf(config.getString("Quit.Sound.Pitch"));
					
					if(isHover) {
						if(isClick) {
							if(isClick_Mode.equalsIgnoreCase("command")) {
								json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("url")) {
								json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
								json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
							}
						}else {
							json.setHover(Hover_Text).sendToAll();
						}
					}else{
						if(isClick) {
							if(isClick_Mode.equalsIgnoreCase("command")) {
								json.setExecuteCommand(Action_Command).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("url")) {
								json.setOpenURL(Action_Url).sendToAll();
							}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
								json.setSuggestCommand(Action_Suggest).sendToAll();
							}
						}else {
							json.sendToAll();
						}
					}
					
					//if(isTitle) {
					//	Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)), Utils.color(Utils.getVar(p, SubTitle_Text)));
					//}
					
					//if(isActionBar) {
					//	ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
					//}
					
					if(isSound) {
						 if(isSoundAll) {
							 Location location = p.getLocation();
							 p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
						 }else {
							 for(Player pp : Bukkit.getOnlinePlayers()) {
								 Location location = p.getLocation();
								 pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch); 
							 }
						 }
					}
				}
				
				return;
			}else if(isGroup) {
				e.setQuitMessage(null);
				
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
					boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");
					
					String text = config.getString("Groups." + key + ".Quit-Text");
					String isPermission = config.getString("Groups." + key + ".Permission");
					
					text = Utils.color(text);
					text = Utils.getVar(p, text);
					
					Json json = new Json(p, text);
					
					if(isNormalType) {
						
						if(p.hasPermission(isPermission));
						
						json.sendToAll();
						return;
					}else if(isModifyType) {
						
						boolean isHover = config.getString("Groups." + key + ".HoverEvent.Enabled").equals("true");
						boolean isClick = config.getString("Groups." + key + ".ClickEvent.Enabled").equals("true");
						//boolean isTitle = config.getString("Groups." + key + ".Title.Enabled").equals("true");
						boolean isSound = config.getString("Groups." + key + ".Sound.Enabled").equals("true");
						//boolean isActionBar = config.getString("Groups." + key + ".ActionBar").equals("true");
						boolean isSoundAll = config.getString("Groups." + key + ".Sound.Send-To-All").equals("true");
						
						List<String> Hover_Text = config.getStringList("Groups." + key + ".HoverEvent.Hover");
						
						String isClick_Mode = config.getString("Groups." + key + ".ClickEvent.Mode");
						String Action_Command = config.getString("Groups." + key + ".ClickEvent.Actions.Command");
						String Action_Url = config.getString("Groups." + key + ".ClickEvent.Actions.Url");
						String Action_Suggest= config.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
						//String Title_Text = config.getString("Groups." + key + ".Title.Title");
						//String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
						//String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
						String Sound_Name = config.getString("Groups." + key + ".Sound.Name");
						
						//int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
						//int Stay = config.getInt("Groups." + key + ".Title.Stay");
						//int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
						int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");
						
						float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));
						
						if(p.hasPermission(isPermission));
						
						if(isHover) {
							if(isClick) {
								if(isClick_Mode.equalsIgnoreCase("command")) {
									json.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("url")) {
									json.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
									json.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
								}
							}else {
								json.setHover(Hover_Text).sendToAll();
							}
						}else{
							if(isClick) {
								if(isClick_Mode.equalsIgnoreCase("command")) {
									json.setExecuteCommand(Action_Command).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("url")) {
									json.setOpenURL(Action_Url).sendToAll();
								}else if(isClick_Mode.equalsIgnoreCase("suggest")) {
									json.setSuggestCommand(Action_Suggest).sendToAll();
								}
							}else {
								json.sendToAll();
							}
						}
						
						//if(isTitle) {
						//	Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)), Utils.color(Utils.getVar(p, SubTitle_Text)));
						//}
						
						//if(isActionBar) {
						//	ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
						//}
						
						if(isSound) {
							 if(isSoundAll) {
								 Location location = p.getLocation();
								 p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
							 }else {
								 for(Player pp : Bukkit.getOnlinePlayers()) {
									 Location location = p.getLocation();
									 pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch); 
								 }
							 }
						}
					}
				}
				return;
			}else if(isNone) {
				e.setQuitMessage(null);
				return;
			}
		}	
	}
	
}
