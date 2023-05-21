package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import net.essentialsx.api.v2.services.discord.DiscordService;
import net.essentialsx.api.v2.services.discord.MessageType;
import org.bukkit.Bukkit;

public class EssentialsXDiscordHook implements IHook {

    private boolean isEnabled;
    private DiscordService service;

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord")) {
            Logger.warning("&eEssentialsDiscord not enabled! - Disable Features...");
            this.isEnabled = false;
            return;
        }

        if (!Settings.hook_essentialsDiscord) {
            this.isEnabled = false;
            Logger.warning("&eEssentialsDiscord not enabled! - Disable Features...");
            return;
        }

        this.service = Bukkit.getServicesManager().load(DiscordService.class);
        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&aLoading EssentialsDiscord features...");
        Logger.warning("&e!!These features are still under development and may have bugs!!");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @SuppressWarnings("unused")
    public void sendJoinMessage(String channelId, String message) {
        if (Settings.hook_essentialsDiscord_use_default_channel) {
            service.sendMessage(MessageType.DefaultTypes.JOIN, message, false);
        } else {
            service.sendMessage(new MessageType(Settings.hook_essentialsDiscord_channelId), message, false);
        }
    }

    @SuppressWarnings("unused")
    public void sendQuitMessage(String channelId, String message) {
        if (Settings.hook_essentialsDiscord_use_default_channel) {
            service.sendMessage(MessageType.DefaultTypes.LEAVE, message, false);
        } else {
            service.sendMessage(new MessageType(Settings.hook_essentialsDiscord_channelId), message, false);
        }
    }

}
