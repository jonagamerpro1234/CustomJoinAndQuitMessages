package jss.customjoinandquitmessages.listener;

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
import org.bukkit.scheduler.BukkitScheduler;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;

import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXDiscordHook;
import jss.customjoinandquitmessages.hook.HookManager;
import jss.customjoinandquitmessages.hook.LuckPermsHook;
import jss.customjoinandquitmessages.hook.VaultHook;
import jss.customjoinandquitmessages.json.Json;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.GroupHelper;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class JoinListener implements Listener {

	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventsUtils = new EventUtils(plugin);
	private int taskGroupId;
	
	public JoinListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventsUtils.getEventManager().registerEvents(this, plugin);

	}

	@EventHandler
	public void onJoinListener(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		DiscordSRVHHook discordSRVHHook = HookManager.getInstance().getDiscordSRVHHook();
		VaultHook vaultHook = HookManager.getInstance().getVaultHook();
		LuckPermsHook luckPermsHook = HookManager.getInstance().getLuckPermsHook();
		EssentialsXDiscordHook essentialsXDiscordHook = HookManager.getInstance().getEssentialsXDiscordHook();		
		Player p = e.getPlayer();

		String tempGroup = "";
		
		if(luckPermsHook.isEnabled()) {
			Logger.error("&cThe LuckPerms could not be found to activate the group system");
			Logger.warning("&eplease check that LuckPerms is active or inside your plugins folder");
		}
		
		if(Settings.hook_luckperms_use_group) {
			tempGroup = LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup();
		}else {
			tempGroup = "default";
		}
		
		PlayerManager playerManager = new PlayerManager();
		playerManager.createPlayer(p, tempGroup);

		boolean isDefault = Settings.c_type.equalsIgnoreCase("default");
		boolean isNormal = Settings.c_type.equalsIgnoreCase("normal");
		boolean isGroup = Settings.c_type.equalsIgnoreCase("group");
		boolean isNone = Settings.c_type.equalsIgnoreCase("none");
		
		if (Settings.welcome) {
			Settings.list_welcome.forEach( (text) -> Utils.sendColorMessage(p, Utils.getVar(p, text)));
		}
		
		
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		taskGroupId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if(Settings.hook_luckperms_autoUpdate_group) {
					if(!playerManager.getGroup(p).equalsIgnoreCase(LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup())) {
						playerManager.setGroup(p, LuckPermsHook.getApi().getUserManager().getUser(p.getName()).getPrimaryGroup());
					}
				}else {
					scheduler.cancelTask(taskGroupId);
				}
			}
		}, 0L, 600L);
		
		

		if (Settings.join) {
			if (isDefault) {
				return;
			} else if (isNormal) {
				e.setJoinMessage(null);

				String join = Settings.join_message;
				String firstjoin = Settings.join_message_first;
				
				String text = "";
				
				if(Settings.firstjoin) {
					if (!p.hasPlayedBefore()) {
						text = firstjoin;
					} else {
						text = join;
					}
				}else {
					text = join;
				}
				
				boolean isNormalType = Settings.join_type.equalsIgnoreCase("normal");
				boolean isModifyType = Settings.join_type.equalsIgnoreCase("modify");

				text = Utils.color(Utils.getVar(p, text));

				Json json = new Json(p, text);

				if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
					Logger.info(json.getText());
				}

				if (isNormalType) {
					json.sendToAll();
					if (discordSRVHHook.isEnabled()) {

						if (Settings.hook_discordsrv_channelid.equalsIgnoreCase("none"))
							return;

						DiscordUtil.sendMessageBlocking(
								DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
								Utils.colorless(json.getText()));
					}

					if (essentialsXDiscordHook.isEnabled()) {

						if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
							return;

						essentialsXDiscordHook.sendJoinMessage(Settings.hook_essentialsDiscord_channelid,
								Utils.colorless(json.getText()));
					}

					return;
				} else if (isModifyType) {

					boolean isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
					boolean isClick = config.getString("Join.ClickEvent.Enabled").equals("true");
					boolean isTitle = config.getString("Join.Title.Enabled").equals("true");
					boolean isSound = config.getString("Join.Sound.Enabled").equals("true");
					boolean isActionBar = config.getString("Join.ActionBar.Enabled").equals("true");
					boolean isSoundAll = config.getString("Join.Sound.Send-To-All").equals("true");

					List<String> Hover_Text = config.getStringList("Join.HoverEvent.Hover");

					String isClick_Mode = config.getString("Join.ClickEvent.Mode");
					String Action_Command = config.getString("Join.ClickEvent.Actions.Command");
					String Action_Url = config.getString("Join.ClickEvent.Actions.Url");
					String Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
					String Title_Text = config.getString("Join.Title.Title");
					String SubTitle_Text = config.getString("Join.Title.SubTitle");
					String Actionbar_Text = config.getString("Join.ActionBar.Text");
					String Sound_Name = config.getString("Join.Sound.Name");

					int FadeIn = config.getInt("Join.Title.FadeIn");
					int Stay = config.getInt("Join.Title.Stay");
					int FadeOut = config.getInt("Join.Title.FadeOut");
					int Sound_Volume = config.getInt("Join.Sound.Volume");

					float Sound_Pitch = Float.valueOf(config.getString("Join.Sound.Pitch"));

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
						DiscordUtil.sendMessageBlocking(
								DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
								Utils.colorless(json.getText()));
					}

					if (isTitle) {
						Titles.sendTitle(p, FadeIn, Stay, FadeOut, Utils.color(Utils.getVar(p, Title_Text)), Utils.color(Utils.getVar(p, SubTitle_Text)));
					}

					if (isActionBar) {
						ActionBar.sendActionBar(p, Utils.color(Utils.getVar(p, Actionbar_Text)));
					}

					try {
						if (isSound) {
							if (isSoundAll) {
								Location location = p.getLocation();
								p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
							} else {
								for (Player pp : Bukkit.getOnlinePlayers()) {
									Location location = p.getLocation();
									pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
								}
							}
						}
					} catch (Exception ex) {
						Logger.warning("&eVerify that the sound name is correct or belongs to the version");
					}
				}

				return;
			} else if (isGroup) {
				e.setJoinMessage(null);

				if (Settings.hook_vault_use_group) {
					GroupHelper.useVaultJoin(vaultHook, discordSRVHHook, essentialsXDiscordHook, config, p);
					return;
				}

				if (Settings.hook_luckperms_use_group) {
					GroupHelper.useLuckPermsJoin(luckPermsHook, discordSRVHHook, essentialsXDiscordHook, config, p);
					return;
				}

				return;
			} else if (isNone) {
				e.setJoinMessage(null);
				return;
			}
		}

		if (Settings.update) {
			if ((p.isOp()) || (p.hasPermission("Cjm.Update.Notify"))) {
				new UpdateChecker(CustomJoinAndQuitMessages.get(), UpdateSettings.ID)
						.getUpdateVersion(version -> {
							if (!CustomJoinAndQuitMessages.get().getDescription().getVersion()
									.equalsIgnoreCase(version)) {
								TextComponent component = new TextComponent(Utils.color(Utils.getPrefixPlayer()
										+ " &aThere is a new version available for download, Click on this message to copy the link"));
								component.setClickEvent(new ClickEvent(Action.OPEN_URL, UpdateSettings.URL_PlUGIN[0]));
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

		DiscordSRVHHook discordSRVHHook = HookManager.getInstance().getDiscordSRVHHook();
		VaultHook vaultHook = HookManager.getInstance().getVaultHook();
		LuckPermsHook luckPermsHook = HookManager.getInstance().getLuckPermsHook();
		EssentialsXDiscordHook essentialsXDiscordHook = HookManager.getInstance().getEssentialsXDiscordHook();

		boolean isDefault = Settings.c_type.equalsIgnoreCase("default");
		boolean isNormal = Settings.c_type.equalsIgnoreCase("normal");
		boolean isGroup = Settings.c_type.equalsIgnoreCase("group");
		boolean isNone = Settings.c_type.equalsIgnoreCase("none");

		if (Settings.quit) {
			if (isDefault) {
				return;
			} else if (isNormal) {
				e.setQuitMessage(null);

				boolean isNormalType = config.getString("Quit.Type").equalsIgnoreCase("normal");
				boolean isModifyType = config.getString("Quit.Type").equalsIgnoreCase("modify");

				String text = config.getString("Quit.Text");

				text = Utils.color(text);
				text = Utils.getVar(p, text);

				Json json = new Json(p, text);

				if (config.getString("Config.Show-Chat-In-Console").equals("true")) {
					Logger.info(json.getText());
				}

				if (isNormalType) {
					json.sendToAll();
					if (discordSRVHHook.isEnabled()) {
						if (Settings.hook_discordsrv_channelid.equalsIgnoreCase("none"))
							return;
						DiscordUtil.sendMessageBlocking(
								DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
								Utils.colorless(json.getText()));
					}

					if (essentialsXDiscordHook.isEnabled()) {
						if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
							return;
						essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid,
								Utils.colorless(json.getText()));
					}

					return;
				} else if (isModifyType) {

					boolean isHover = config.getString("Quit.HoverEvent.Enabled").equals("true");
					boolean isClick = config.getString("Quit.ClickEvent.Enabled").equals("true");
					boolean isSound = config.getString("Quit.Sound.Enabled").equals("true");
					boolean isSoundAll = config.getString("Quit.Sound.Send-To-All").equals("true");

					List<String> Hover_Text = config.getStringList("Quit.HoverEvent.Hover");

					String isClick_Mode = config.getString("Quit.ClickEvent.Mode");
					String Action_Command = config.getString("Quit.ClickEvent.Actions.Command");
					String Action_Url = config.getString("Quit.ClickEvent.Actions.Url");
					String Action_Suggest = config.getString("Quit.ClickEvent.Actions.Suggest-Command");
					String Sound_Name = config.getString("Quit.Sound.Name");

					int Sound_Volume = config.getInt("Quit.Sound.Volume");

					float Sound_Pitch = Float.valueOf(config.getString("Quit.Sound.Pitch"));

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

						if (Settings.hook_discordsrv_channelid.equalsIgnoreCase("none"))
							return;

						DiscordUtil.sendMessageBlocking(
								DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
								Utils.colorless(json.getText()));
					}

					if (essentialsXDiscordHook.isEnabled()) {

						if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
							return;

						essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid,
								Utils.colorless(json.getText()));
					}

					try {
						if (isSound) {
							if (isSoundAll) {
								Location location = p.getLocation();
								p.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
							} else {
								for (Player pp : Bukkit.getOnlinePlayers()) {
									Location location = p.getLocation();
									pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
								}
							}
						}
					} catch (Exception ex) {
						Logger.warning("&eVerify that the sound name is correct or belongs to the version");
					}
				}

				return;
			} else if (isGroup) {
				e.setQuitMessage(null);

				if (Settings.hook_vault_use_group) {
					GroupHelper.useVaultJoin(vaultHook, discordSRVHHook, essentialsXDiscordHook, config, p);
					return;
				}

				if (Settings.hook_luckperms_use_group) {
					GroupHelper.useLuckPermsJoin(luckPermsHook, discordSRVHHook, essentialsXDiscordHook, config, p);
					return;
				}

				return;
			} else if (isNone) {
				e.setQuitMessage(null);
				return;
			}
		}
	}

}
