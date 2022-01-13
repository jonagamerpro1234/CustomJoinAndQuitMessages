package jss.customjoinandquitmessages.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;

import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXDiscordHook;
import jss.customjoinandquitmessages.hook.LuckPermsHook;
import jss.customjoinandquitmessages.hook.VaultHook;
import jss.customjoinandquitmessages.json.Json;

public class GroupHelper {
	
	public static void useLuckPermsJoin(LuckPermsHook luckPermsHook, DiscordSRVHHook discordSRVHHook, EssentialsXDiscordHook essentialsXDiscordHook, FileConfiguration config, Player p) {
		if(luckPermsHook.isEnabled()) {
			String key = luckPermsHook.getGroup(p);

			boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
			boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");

			String text = config.getString("Groups." + key + ".Join-Text");

			text = Utils.color(text);
			text = Utils.getVar(p, text);

			Json json = new Json(p, text);

			if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
				Logger.info(json.getText());
			}

			if (isNormalType) {
				json.sendToAll();
				if (discordSRVHHook.isEnabled()) {
					
					if(Settings.hook_discordsrv_channelid.equalsIgnoreCase("none")) return;
					
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}
				
				if (essentialsXDiscordHook.isEnabled()) {
					
					if(Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none")) return;
					
					essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid, Utils.colorless(json.getText()));
				}
				
				return;
			} else if (isModifyType) {

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
				String Action_Suggest = config
						.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
				String Title_Text = config.getString("Groups." + key + ".Title.Title");
				String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
				String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
				String Sound_Name = config.getString("Groups." + key + ".Sound.Name");

				int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
				int Stay = config.getInt("Groups." + key + ".Title.Stay");
				int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
				int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");

				float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));

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

				if (discordSRVHHook.isEnabled()) {
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}

				if (isTitle) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)),
							Utils.color(Utils.getVar(p, SubTitle_Text)));
				}

				if (isActionBar) {
					ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
				}

				try {
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
				}catch(Exception ex) {
					Logger.warning("&eVerify that the sound name is correct or belongs to the version");
				}
				
			}else {
				Logger.error("&cthe luckperms could not be found to activate the group system");
				Logger.warning("&eplease check that luckperms is active or inside your plugins folder");
				return;
			}
		}
	}
	
	//Use of the Vault system
	public static void useVaultJoin(VaultHook vaultHook, DiscordSRVHHook discordSRVHHook, EssentialsXDiscordHook essentialsXDiscordHook, FileConfiguration config, Player p) {
		if(vaultHook.isEnabled()) {
			String key = VaultHook.getVaultHook().getChat().getPrimaryGroup(p);

			boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
			boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");

			String text = config.getString("Groups." + key + ".Join-Text");

			text = Utils.color(text);
			text = Utils.getVar(p, text);

			Json json = new Json(p, text);

			if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
				Logger.info(json.getText());
			}

			if (isNormalType) {
				json.sendToAll();
				if (discordSRVHHook.isEnabled()) {
					
					if(Settings.hook_discordsrv_channelid.equalsIgnoreCase("none")) return;
					
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}
				
				if (essentialsXDiscordHook.isEnabled()) {
					
					if(Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none")) return;
					
					essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid, Utils.colorless(json.getText()));
				}
				
				return;
			} else if (isModifyType) {

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
				String Action_Suggest = config
						.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
				String Title_Text = config.getString("Groups." + key + ".Title.Title");
				String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
				String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
				String Sound_Name = config.getString("Groups." + key + ".Sound.Name");

				int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
				int Stay = config.getInt("Groups." + key + ".Title.Stay");
				int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
				int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");

				float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));

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

				if (discordSRVHHook.isEnabled()) {
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}

				if (isTitle) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)),
							Utils.color(Utils.getVar(p, SubTitle_Text)));
				}

				if (isActionBar) {
					ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
				}

				try {
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
				}catch(Exception ex) {
					Logger.warning("&eVerify that the sound name is correct or belongs to the version");
				}
				
			}else {
				Logger.error("&cthe vault could not be found to activate the group system");
				Logger.warning("&eplease check that Vault is active or inside your plugins folder");
				return;
			}
		}
	}
	
	public static void useLuckPermsQuit(LuckPermsHook luckPermsHook, DiscordSRVHHook discordSRVHHook, EssentialsXDiscordHook essentialsXDiscordHook, FileConfiguration config, Player p) {
		if(luckPermsHook.isEnabled()) {
			String key = luckPermsHook.getGroup(p);

			boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
			boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");

			String text = config.getString("Groups." + key + ".Quit-Text");

			text = Utils.color(text);
			text = Utils.getVar(p, text);

			Json json = new Json(p, text);

			if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
				Logger.info(json.getText());
			}

			if (isNormalType) {
				json.sendToAll();
				if (discordSRVHHook.isEnabled()) {
					
					if(Settings.hook_discordsrv_channelid.equalsIgnoreCase("none")) return;
					
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}
				
				if (essentialsXDiscordHook.isEnabled()) {
					
					if(Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none")) return;
					
					essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid, Utils.colorless(json.getText()));
				}
				
				return;
			} else if (isModifyType) {

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
				String Action_Suggest = config
						.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
				String Title_Text = config.getString("Groups." + key + ".Title.Title");
				String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
				String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
				String Sound_Name = config.getString("Groups." + key + ".Sound.Name");

				int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
				int Stay = config.getInt("Groups." + key + ".Title.Stay");
				int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
				int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");

				float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));

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

				if (discordSRVHHook.isEnabled()) {
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}

				if (isTitle) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)),
							Utils.color(Utils.getVar(p, SubTitle_Text)));
				}

				if (isActionBar) {
					ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
				}

				try {
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
				}catch(Exception ex) {
					Logger.warning("&eVerify that the sound name is correct or belongs to the version");
				}
				
			}else {
				Logger.error("&cthe luckperms could not be found to activate the group system");
				Logger.warning("&eplease check that luckperms is active or inside your plugins folder");
				return;
			}
		}
	}
	
	//Use of the Vault system
	public static void useVaultQuit(VaultHook vaultHook, DiscordSRVHHook discordSRVHHook, EssentialsXDiscordHook essentialsXDiscordHook, FileConfiguration config, Player p) {
		if(vaultHook.isEnabled()) {
			String key = VaultHook.getVaultHook().getChat().getPrimaryGroup(p);

			boolean isNormalType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("normal");
			boolean isModifyType = config.getString("Groups." + key + ".Type").equalsIgnoreCase("modify");

			String text = config.getString("Groups." + key + ".Quit-Text");

			text = Utils.color(text);
			text = Utils.getVar(p, text);

			Json json = new Json(p, text);

			if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
				Logger.info(json.getText());
			}

			if (isNormalType) {
				json.sendToAll();
				if (discordSRVHHook.isEnabled()) {
					
					if(Settings.hook_discordsrv_channelid.equalsIgnoreCase("none")) return;
					
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}
				
				if (essentialsXDiscordHook.isEnabled()) {
					
					if(Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none")) return;
					
					essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid, Utils.colorless(json.getText()));
				}
				
				return;
			} else if (isModifyType) {

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
				String Action_Suggest = config
						.getString("Groups." + key + ".ClickEvent.Actions.Suggest-Command");
				String Title_Text = config.getString("Groups." + key + ".Title.Title");
				String SubTitle_Text = config.getString("Groups." + key + ".Title.SubTitle");
				String Actionbar_Text = config.getString("Groups." + key + ".ActionBar.Text");
				String Sound_Name = config.getString("Groups." + key + ".Sound.Name");

				int FadeIn = config.getInt("Groups." + key + ".Title.FadeIn");
				int Stay = config.getInt("Groups." + key + ".Title.Stay");
				int FadeOut = config.getInt("Groups." + key + ".Title.FadeOut");
				int Sound_Volume = config.getInt("Groups." + key + ".Sound.Volume");

				float Sound_Pitch = Float.valueOf(config.getString("Groups." + key + ".Sound.Pitch"));

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

				if (discordSRVHHook.isEnabled()) {
					DiscordUtil.sendMessageBlocking(DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid), Utils.colorless(json.getText()));
				}

				if (isTitle) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)),
							Utils.color(Utils.getVar(p, SubTitle_Text)));
				}

				if (isActionBar) {
					ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
				}

				try {
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
				}catch(Exception ex) {
					Logger.warning("&eVerify that the sound name is correct or belongs to the version");
				}
				
			}else {
				Logger.error("&cthe vault could not be found to activate the group system");
				Logger.warning("&eplease check that Vault is active or inside your plugins folder");
				return;
			}
		}
	}
}
