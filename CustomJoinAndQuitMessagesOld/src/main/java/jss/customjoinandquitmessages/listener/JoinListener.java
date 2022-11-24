package jss.customjoinandquitmessages.listener;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.*;
import jss.customjoinandquitmessages.json.Json;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class JoinListener implements Listener {

    private final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();

    public JoinListener() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoinListener(@NotNull PlayerJoinEvent e) {
        FileConfiguration config = plugin.getConfigFile().getConfig();
        DiscordSRVHHook discordSRVHHook = HookManager.getInstance().getDiscordSRVHHook();
        LuckPermsHook luckPermsHook = HookManager.getInstance().getLuckPermsHook();
        EssentialsXDiscordHook essentialsXDiscordHook = HookManager.getInstance().getEssentialsXDiscordHook();
        EssentialsXHook essentialsXHook = HookManager.get().getEssentialsXHook();
        Player p = e.getPlayer();

        String tempGroup;

        if (luckPermsHook.isEnabled()) {
            tempGroup = Objects.requireNonNull(LuckPermsHook.getApi().getUserManager().getUser(p.getName())).getPrimaryGroup();
        } else {
            tempGroup = "default";
        }

        PlayerManager playerManager = new PlayerManager();
        playerManager.createPlayer(p, tempGroup);

        if(Settings.settings_debug){
            Logger.debug(" &eLuckPermHook is: " + luckPermsHook.isEnabled());
        }

        if (Settings.c_type.equalsIgnoreCase("group")){
            if (luckPermsHook.isEnabled()) {
                if (!playerManager.getGroup(p).equalsIgnoreCase(Objects.requireNonNull(LuckPermsHook.getApi().
                        getUserManager().getUser(p.getName())).getPrimaryGroup())) {
                    playerManager.setGroup(p, Objects.requireNonNull(LuckPermsHook.getApi().getUserManager().getUser(p.getName())).getPrimaryGroup());
                }
            }else{
                Logger.error("&cThe LuckPerms could not be found to activate the group system");
                Logger.warning("&eplease check that LuckPerms is active or inside your plugins folder");
            }
        }

        boolean isNormal = Settings.c_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.c_type.equalsIgnoreCase("group");
        boolean isNone = Settings.c_type.equalsIgnoreCase("none");

        if (Settings.welcome) {
            for (String text : Settings.list_welcome)
                Util.sendColorMessage(p, Util.getVar(p, text));
        }

        if (essentialsXHook.isEnabled()) {
            if (Settings.hook_essentials_hideplayervanish) {
                if (essentialsXHook.isVanish(p)) {
                    e.setJoinMessage(null);
                    return;
                }
            }
        }

        if (Settings.join) {
            if (isNormal) {
                e.setJoinMessage(null);

                String join = Settings.join_message;
                String firstjoin = Settings.join_message_first;

                String text;

                if (Settings.firstjoin) {
                    if (!p.hasPlayedBefore()) {
                        text = firstjoin;
                    } else {
                        text = join;
                    }
                } else {
                    text = join;
                }

                boolean isNormalType = Settings.join_type.equalsIgnoreCase("normal");
                boolean isModifyType = Settings.join_type.equalsIgnoreCase("modify");

                text = Util.color(Util.getVar(p, text));

                Json json = new Json(p, text);

                if (config.getBoolean("Config.Show-Chat-In-Console")) {
                    Logger.info(json.getText());
                }

                if (isNormalType) {
                    e.setJoinMessage(text);
                    if (discordSRVHHook.isEnabled()) {

                        if (Settings.hook_discordsrv_channelid.equalsIgnoreCase("none"))
                            return;

                        DiscordUtil.sendMessageBlocking(
                                DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
                                Util.colorless(json.getText()));
                    }

                    if (essentialsXDiscordHook.isEnabled()) {
                        if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
                            return;

                        essentialsXDiscordHook.sendJoinMessage(Settings.hook_essentialsDiscord_channelid,
                                Util.colorless(json.getText()));
                    }
                } else if (isModifyType) {
                    boolean isHover = Objects.equals(config.getString("Join.HoverEvent.Enabled"), "true");
                    boolean isClick = Objects.equals(config.getString("Join.ClickEvent.Enabled"), "true");
                    boolean isTitle = Objects.equals(config.getString("Join.Title.Enabled"), "true");
                    boolean isSound = Objects.equals(config.getString("Join.Sound.Enabled"), "true");
                    boolean isActionBar = Objects.equals(config.getString("Join.ActionBar.Enabled"), "true");
                    boolean isSoundAll = Objects.equals(config.getString("Join.Sound.Send-To-All"), "true");

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

                    float Sound_Pitch = Float.parseFloat(Objects.requireNonNull(config.getString("Join.Sound.Pitch")));

                    if (isHover) {
                        if (isClick) {
                            assert isClick_Mode != null;
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
                            assert isClick_Mode != null;
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
                                Util.colorless(json.getText()));
                    }

                    if (isTitle) {
                        Titles.sendTitle(p, FadeIn, Stay, FadeOut, Util.color(Util.getVar(p, Title_Text)),
                                Util.color(Util.getVar(p, SubTitle_Text)));
                    }

                    if (isActionBar) {
                        ActionBar.sendActionBar(p, Util.color(Util.getVar(p, Actionbar_Text)));
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
            } else if (isGroup) {
                e.setJoinMessage(null);
                GroupHelper groupHelper = new GroupHelper();
                groupHelper.setGroup(playerManager.getGroup(p));
                groupHelper.setDiscord(discordSRVHHook);
                groupHelper.setEssentials(essentialsXDiscordHook);
                groupHelper.onJoin(p, config, e);
            } else if (isNone) {
                e.setJoinMessage(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onUpdate(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Settings.update) {
            if ((p.isOp()) || (p.hasPermission("Cjm.Update.Notify"))) {
                new UpdateChecker(CustomJoinAndQuitMessages.get(), UpdateSettings.ID).getUpdateVersion(version -> {
                    if (!CustomJoinAndQuitMessages.get().getDescription().getVersion().equalsIgnoreCase(version)) {
                        TextComponent component = new TextComponent(Util.color(Util.getPrefixPlayer()
                                + " &aThere is a new version available for download, Click on this message to copy the link"));
                        component.setClickEvent(new ClickEvent(Action.OPEN_URL, UpdateSettings.URL_PlUGIN[0]));
                        p.spigot().sendMessage(component);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent e) {
        FileConfiguration config = plugin.getConfigFile().getConfig();
        Player p = e.getPlayer();

        DiscordSRVHHook discordSRVHHook = HookManager.getInstance().getDiscordSRVHHook();
        EssentialsXDiscordHook essentialsXDiscordHook = HookManager.getInstance().getEssentialsXDiscordHook();
        EssentialsXHook essentialsXHook = HookManager.get().getEssentialsXHook();
        PlayerManager playerManager = new PlayerManager();

        boolean isNormal = Settings.c_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.c_type.equalsIgnoreCase("group");
        boolean isNone = Settings.c_type.equalsIgnoreCase("none");

        if (essentialsXHook.isEnabled()) {
            if (Settings.hook_essentials_hideplayervanish) {
                if (essentialsXHook.isVanish(p)) {
                    e.setQuitMessage(null);
                    return;
                }
            }
        }

        if (Settings.quit) {
            if (isNormal) {
                e.setQuitMessage(null);

                boolean isNormalType = Objects.requireNonNull(config.getString("Quit.Type")).equalsIgnoreCase("normal");
                boolean isModifyType = Objects.requireNonNull(config.getString("Quit.Type")).equalsIgnoreCase("modify");

                String text = config.getString("Quit.Text");

                text = Util.color(text);
                text = Util.getVar(p, text);

                Json json = new Json(p, text);

                if (config.getBoolean("Config.Show-Chat-In-Console")) {
                    Logger.info(json.getText());
                }

                if (isNormalType) {
                    e.setQuitMessage(text);
                    if (discordSRVHHook.isEnabled()) {
                        if (Settings.hook_discordsrv_channelid.equalsIgnoreCase("none"))
                            return;
                        DiscordUtil.sendMessageBlocking(
                                DiscordUtil.getTextChannelById(Settings.hook_discordsrv_channelid),
                                Util.colorless(json.getText()));
                    }

                    if (essentialsXDiscordHook.isEnabled()) {
                        if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
                            return;
                        essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid,
                                Util.colorless(json.getText()));
                    }
                } else if (isModifyType) {

                    boolean isHover = Objects.equals(config.getString("Quit.HoverEvent.Enabled"), "true");
                    boolean isClick = Objects.equals(config.getString("Quit.ClickEvent.Enabled"), "true");
                    boolean isSound = Objects.equals(config.getString("Quit.Sound.Enabled"), "true");
                    boolean isSoundAll = Objects.equals(config.getString("Quit.Sound.Send-To-All"), "true");

                    List<String> Hover_Text = config.getStringList("Quit.HoverEvent.Hover");

                    String isClick_Mode = config.getString("Quit.ClickEvent.Mode");
                    String Action_Command = config.getString("Quit.ClickEvent.Actions.Command");
                    String Action_Url = config.getString("Quit.ClickEvent.Actions.Url");
                    String Action_Suggest = config.getString("Quit.ClickEvent.Actions.Suggest-Command");
                    String Sound_Name = config.getString("Quit.Sound.Name");

                    int Sound_Volume = config.getInt("Quit.Sound.Volume");
                    float Sound_Pitch = Float.parseFloat(Objects.requireNonNull(config.getString("Quit.Sound.Pitch")));

                    if (isHover) {
                        if (isClick) {
                            assert isClick_Mode != null;
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
                            assert isClick_Mode != null;
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
                                Util.colorless(json.getText()));
                    }

                    if (essentialsXDiscordHook.isEnabled()) {

                        if (Settings.hook_essentialsDiscord_channelid.equalsIgnoreCase("none"))
                            return;

                        essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelid,
                                Util.colorless(json.getText()));
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
            } else if (isGroup) {
                e.setQuitMessage(null);
                GroupHelper groupHelper = GroupHelper.get();
                groupHelper.setGroup(playerManager.getGroup(p));
                groupHelper.setDiscord(discordSRVHHook);
                groupHelper.setEssentials(essentialsXDiscordHook);
                groupHelper.onQuit(p, config, e);
            } else if (isNone) {
                e.setQuitMessage(null);
            }
        }
    }

}
