package jss.customjoinandquitmessages.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXDiscordHook;
import jss.customjoinandquitmessages.json.MessageBuilder;
import jss.customjoinandquitmessages.manager.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupHelper {

    private final GroupManager groupManager = new GroupManager();
    private String group;
    private DiscordSRVHHook discordSRVHHook;
    private EssentialsXDiscordHook essentialsXDiscordHook;

    @Contract(" -> new")
    public static @NotNull GroupHelper get(){
        return new GroupHelper();
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setDiscord(DiscordSRVHHook discordSRVHHook) {
        this.discordSRVHHook = discordSRVHHook;
    }

    public void setEssentials(EssentialsXDiscordHook essentialsXDiscordHook) {
        this.essentialsXDiscordHook = essentialsXDiscordHook;
    }

    public void onJoin(Player player, FileConfiguration config) {
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

        MessageBuilder messageBuilder = new MessageBuilder(player, temp);

        if (config.getBoolean("Config.Show-Chat-In-Console")) {
            Logger.info(messageBuilder.getText());
        }

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

    public void onQuit(Player player, @NotNull FileConfiguration config) {
        String quit = groupManager.getQuit(group);

        quit = Util.color(Util.getVar(player, quit));

        MessageBuilder messageBuilder = new MessageBuilder(player, quit);
        if (config.getBoolean("Config.Show-Chat-In-Console")) {
            Logger.info(messageBuilder.getText());
        }

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

            if (Settings.hook_discordSrv_channelId.equalsIgnoreCase("none"))
                return;

            DiscordUtil.sendMessageBlocking(
                    DiscordUtil.getTextChannelById(Settings.hook_discordSrv_channelId),
                    Util.colorless(messageBuilder.getText()));
        }

        if (essentialsXDiscordHook.isEnabled()) {

            if (Settings.hook_essentialsDiscord_channelId.equalsIgnoreCase("none"))
                return;

            essentialsXDiscordHook.sendQuitMessage(Settings.hook_essentialsDiscord_channelId,
                    Util.colorless(messageBuilder.getText()));
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
