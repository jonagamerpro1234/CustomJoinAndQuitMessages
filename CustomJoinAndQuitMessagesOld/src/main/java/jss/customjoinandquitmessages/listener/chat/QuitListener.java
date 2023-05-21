package jss.customjoinandquitmessages.listener.chat;

import github.scarsz.discordsrv.util.DiscordUtil;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXDiscordHook;
import jss.customjoinandquitmessages.hook.EssentialsXHook;
import jss.customjoinandquitmessages.hook.SuperVanishHook;
import jss.customjoinandquitmessages.json.MessageBuilder;
import jss.customjoinandquitmessages.manager.HookManager;
import jss.customjoinandquitmessages.manager.PlayerManager;
import jss.customjoinandquitmessages.utils.GroupHelper;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class QuitListener implements Listener {

    private final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();

    public QuitListener() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(@NotNull PlayerQuitEvent e) {
        FileConfiguration config = plugin.getConfigFile().getConfig();
        Player p = e.getPlayer();

        DiscordSRVHHook discordSRVHHook = HookManager.getInstance().getDiscordSRVHHook();
        EssentialsXDiscordHook essentialsXDiscordHook = HookManager.getInstance().getEssentialsXDiscordHook();
        EssentialsXHook essentialsXHook = HookManager.get().getEssentialsXHook();
        SuperVanishHook superVanishHook = HookManager.get().getSuperVanishHook();
        PlayerManager playerManager = new PlayerManager();

        boolean isNormal = Settings.chatformat_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.chatformat_type.equalsIgnoreCase("group");
        boolean isNone = Settings.chatformat_type.equalsIgnoreCase("none");

        if(Util.isVanished(p)){
            e.setQuitMessage(null);
            return;
        }

        if (superVanishHook.isEnabled()){
            if (superVanishHook.isVanishPlayer(p)){
                e.setQuitMessage(null);
                return;
            }
        }

        if (essentialsXHook.isEnabled()) {
            if (Settings.hook_essentials_hidePlayerVanish) {
                if (essentialsXHook.isVanish(p)) {
                    e.setQuitMessage(null);
                    return;
                }
            }
        }

        if (Settings.quit) {
            if (isNormal) {
                e.setQuitMessage(null);

                String text = config.getString("Quit.Text");

                text = Util.color(Util.getVar(p, text));

                MessageBuilder messageBuilder = new MessageBuilder(p, text);

                if (config.getBoolean("Config.Show-Chat-In-Console")) {
                    Logger.info(messageBuilder.getText());
                }

                boolean isHover = config.getBoolean("Quit.HoverEvent.Enabled");
                boolean isClick = config.getBoolean("Quit.ClickEvent.Enabled");
                boolean isSound = config.getBoolean("Quit.Sound.Enabled");
                boolean isSoundAll = config.getBoolean("Quit.Sound.Send-To-All");

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
                e.setQuitMessage(null);
                GroupHelper groupHelper = GroupHelper.get();
                groupHelper.setGroup(playerManager.getGroup(p));
                groupHelper.setDiscord(discordSRVHHook);
                groupHelper.setEssentials(essentialsXDiscordHook);
                groupHelper.onQuit(p, config);
            } else if (isNone) {
                e.setQuitMessage(null);
            }
        }
    }

}
