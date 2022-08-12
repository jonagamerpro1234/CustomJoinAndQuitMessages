package jss.customjoinandquitmessages.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXDiscordHook;
import jss.customjoinandquitmessages.json.Json;
import jss.customjoinandquitmessages.manager.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupHelper {

    private final GroupManager groupManager = GroupManager.get();
    private String group;
    private Json json;
    private DiscordSRVHHook discordSRVHHook;
    private EssentialsXDiscordHook essentialsXDiscordHook;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setDiscord(DiscordSRVHHook discordSRVHHook) {
        this.discordSRVHHook = discordSRVHHook;
    }

    public void setEssentials(EssentialsXDiscordHook essentialsXDiscordHook) {
        this.essentialsXDiscordHook = essentialsXDiscordHook;
    }

    public void onJoin(Player player, FileConfiguration config, PlayerJoinEvent playerJoinEvent) {

        String temp = "";

        String join = groupManager.getJoin(group);
        String firstjoin = groupManager.getFirstJoin(group);

        if (groupManager.isFirstJoin(group)) {
            if (!player.hasPlayedBefore()) {
                temp = firstjoin;
            } else {
                temp = join;
            }
        }

        temp = Util.color(Util.getVar(player, temp));

        boolean isNormalType = groupManager.getType(group).equalsIgnoreCase("normal");
        boolean isModifyType = groupManager.getType(group).equalsIgnoreCase("modify");

        json = new Json(player, temp);

        if (config.getBoolean("Config.Show-Chat-In-Console")) {
            Logger.info(json.getText());
        }

        if (isNormalType) {
            playerJoinEvent.setJoinMessage(temp);
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

            boolean isHover = groupManager.isHover(group);
            boolean isClick = groupManager.isClick(group);
            boolean isTitle = groupManager.isTitle(group);
            boolean isSound = groupManager.isSound(group);
            boolean isActionBar = groupManager.isActionbar(group);
            boolean isSoundAll = groupManager.isSoundAll(group);

            List<String> Hover_Text = groupManager.getHover(group);

            String isClick_Mode = groupManager.getClickMode(group);
            String Action_Command = groupManager.getClickCommand(group);
            String Action_Url = groupManager.getClickUrl(group);
            String Action_Suggest = groupManager.getClickSuggestCommand(group);
            String Title_Text = groupManager.getTitle(group);
            String SubTitle_Text = groupManager.getSubTitle(group);
            String Actionbar_Text = groupManager.getActionbar(group);
            String Sound_Name = groupManager.getSound(group);

            int FadeIn = groupManager.getFadeIn(group);
            int Stay = groupManager.getStay(group);
            int FadeOut = groupManager.getFadeOut(group);
            int Sound_Volume = groupManager.getVolume(group);
            float Sound_Pitch = groupManager.getPitch(group);

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
                        Util.colorless(json.getText()));
            }

            if (isTitle) {
                Titles.sendTitle(player, FadeIn, Stay, FadeOut, Util.color(Util.getVar(player, Title_Text)),
                        Util.color(Util.getVar(player, SubTitle_Text)));
            }

            if (isActionBar) {
                ActionBar.sendActionBar(player, Util.color(Util.getVar(player, Actionbar_Text)));
            }

            try {
                if (isSound) {
                    if (isSoundAll) {
                        Location location = player.getLocation();
                        player.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
                    } else {
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            Location location = player.getLocation();
                            pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.warning("&eVerify that the sound name is correct or belongs to the version");
            }
        }
    }

    public void onQuit(Player player, @NotNull FileConfiguration config, PlayerQuitEvent playerQuitEvent) {
        String quit = groupManager.getQuit(group);
        quit = Util.color(Util.getVar(player, quit));

        boolean isNormalType = groupManager.getType(group).equalsIgnoreCase("normal");
        boolean isModifyType = groupManager.getType(group).equalsIgnoreCase("modify");

        json = new Json(player, quit);
        if (config.getBoolean("Config.Show-Chat-In-Console")) {
            Logger.info(json.getText());
        }

        if (isNormalType) {
            playerQuitEvent.setQuitMessage(quit);
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

            boolean isHover = groupManager.isHover(group);
            boolean isClick = groupManager.isClick(group);
            boolean isSound = groupManager.isSound(group);
            boolean isSoundAll = groupManager.isSoundAll(group);

            List<String> Hover_Text = groupManager.getHover(group);

            String isClick_Mode = groupManager.getClickMode(group);
            String Action_Command = groupManager.getClickCommand(group);
            String Action_Url = groupManager.getClickUrl(group);
            String Action_Suggest = groupManager.getClickSuggestCommand(group);
            String Sound_Name = groupManager.getSound(group);

            int Sound_Volume = groupManager.getVolume(group);
            float Sound_Pitch = groupManager.getPitch(group);

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
                        Location location = player.getLocation();
                        player.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
                    } else {
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            Location location = player.getLocation();
                            pp.playSound(location, Sound.valueOf(Sound_Name), Sound_Volume, Sound_Pitch);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.warning("&eVerify that the sound name is correct or belongs to the version");
            }
        }

    }

}
