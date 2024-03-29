package jss.customjoinandquitmessages.listener.chat;

import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.*;
import jss.customjoinandquitmessages.json.MessageBuilder;
import jss.customjoinandquitmessages.manager.HookManager;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.update.UpdateChecker;
import jss.customjoinandquitmessages.utils.GroupHelper;
import jss.customjoinandquitmessages.utils.MessageUtils;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
        SuperVanishHook superVanishHook = HookManager.get().getSuperVanishHook();
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

        if (Settings.chatformat_type.equalsIgnoreCase("group")){
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

        boolean isNormal = Settings.chatformat_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.chatformat_type.equalsIgnoreCase("group");
        boolean isNone = Settings.chatformat_type.equalsIgnoreCase("none");

        if (Settings.welcome) {
            for (String text : Settings.list_welcome)
                MessageUtils.sendColorMessage(p, Util.getVar(p, text));
        }

        if(Util.isVanished(p)){
            e.setJoinMessage(null);
            return;
        }

        if (superVanishHook.isEnabled()){
            if (superVanishHook.isVanishPlayer(p)){
                e.setJoinMessage(null);
                return;
            }
        }

        if (essentialsXHook.isEnabled()) {
            if (Settings.hook_essentials_hidePlayerVanish) {
                if (essentialsXHook.isVanish(p)) {
                    e.setJoinMessage(null);
                    return;
                }
            }
        }

        if (Settings.join) {
            if (isNormal) {
                e.setJoinMessage(null);

                String text;
                String join = Settings.join_message;
                String firstjoin = Settings.join_message_first;

                if (Settings.firstjoin) {
                    if (!p.hasPlayedBefore()) {
                        text = firstjoin;
                    } else {
                        text = join;
                    }
                } else {
                    text = join;
                }

                text = Util.color(Util.getVar(p, text));
                MessageBuilder messageBuilder = new MessageBuilder(p, text);

                if (config.getBoolean("Config.Show-Chat-In-Console")) {
                    Logger.info(messageBuilder.getText());
                }

                boolean isHover = config.getBoolean("Join.HoverEvent.Enabled");
                boolean isClick = config.getBoolean("Join.ClickEvent.Enabled");
                boolean isTitle = config.getBoolean("Join.Title.Enabled");
                boolean isSound = config.getBoolean("Join.Sound.Enabled");
                boolean isActionBar = config.getBoolean("Join.ActionBar.Enabled");
                boolean isSoundAll = config.getBoolean("Join.Sound.Send-To-All");

                List<String> Hover_Text = config.getStringList("Join.HoverEvent.Hover");

                String isClick_Mode = config.getString("Join.ClickEvent.Mode");
                String Action_Command = config.getString("Join.ClickEvent.Actions.Command");
                String Action_Url = config.getString("Join.ClickEvent.Actions.Url");
                String Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
                String Title_Text = config.getString("Join.Title.Title");
                String SubTitle_Text = config.getString("Join.Title.SubTitle");
                String Actionbar_Text = config.getString("Join.ActionBar.Text");
                String Sound_Name = config.getString("Join.Sound.Name");

                long FadeIn = config.getInt("Join.Title.FadeIn");
                long Stay = config.getInt("Join.Title.Stay");
                long FadeOut = config.getInt("Join.Title.FadeOut");
                long Sound_Volume = config.getInt("Join.Sound.Volume");

                float Sound_Pitch = Float.parseFloat(Objects.requireNonNull(config.getString("Join.Sound.Pitch")));

                if (isHover) {
                    if (isClick) {
                        assert isClick_Mode != null;
                        if (isClick_Mode.equalsIgnoreCase("command")) {
                            messageBuilder.setHover(Hover_Text).setExecuteCommand(Action_Command).sendToAll();
                        } else if (isClick_Mode.equalsIgnoreCase("url")) {
                            messageBuilder.setHover(Hover_Text).setOpenURL(Action_Url).sendToAll();
                        } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                            messageBuilder.setHover(Hover_Text).setSuggestCommand(Action_Suggest).sendToAll();
                        }
                    } else {
                        messageBuilder.setHover(Hover_Text).sendToAll();
                    }
                } else {

                    if (isClick) {
                        assert isClick_Mode != null;
                        if (isClick_Mode.equalsIgnoreCase("command")) {
                            messageBuilder.setExecuteCommand(Action_Command).sendToAll();
                        } else if (isClick_Mode.equalsIgnoreCase("url")) {
                            messageBuilder.setOpenURL(Action_Url).sendToAll();
                        } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                            messageBuilder.setSuggestCommand(Action_Suggest).sendToAll();
                        }
                    } else {
                        messageBuilder.sendToAll();
                    }
                }

                if (discordSRVHHook.isEnabled()) {
                    DiscordUtil.sendMessageBlocking(
                            DiscordUtil.getTextChannelById(Settings.hook_discordSrv_channelId),
                            Util.colorless(messageBuilder.getText()));
                }

                if (isTitle) {
                    MessageUtils.showTitle(p, Util.getVar(p, Title_Text), Util.getVar(p, SubTitle_Text), FadeIn,Stay,FadeOut);
                    //Titles.sendTitle(p, FadeIn, Stay, FadeOut, Util.color(Util.getVar(p, Title_Text)),Util.color(Util.getVar(p, SubTitle_Text)));
                }

                if (isActionBar) {
                    //ActionBar.sendActionBar(p, Util.color(Util.getVar(p, Actionbar_Text)));
                    MessageUtils.showActionbar(p, Util.getVar(p,Actionbar_Text));
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


            } else if (isGroup) {
                e.setJoinMessage(null);
                GroupHelper groupHelper = new GroupHelper();
                groupHelper.setGroup(playerManager.getGroup(p));
                groupHelper.setDiscord(discordSRVHHook);
                groupHelper.setEssentials(essentialsXDiscordHook);
                groupHelper.onJoin(p, config);
            } else if (isNone) {
                e.setJoinMessage(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onUpdate(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Settings.settings_update) {
            if ((p.isOp()) || (p.hasPermission("cjm.update"))) {
                new UpdateChecker(CustomJoinAndQuitMessages.get()).sendSpigotUpdate();
            }
        }
    }

}
