package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.bukkit.Bukkit;

public class DiscordSRVHHook implements IHook {

    private boolean isEnabled;

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
            Logger.warning("&eDiscordSRV not enabled! - Disable Features...");
            this.isEnabled = false;
            return;
        }

        if (!Settings.hook_discordSrv) {
            this.isEnabled = false;
            Logger.warning("&eDiscordSRV not enabled! - Disable Features...");
            return;
        }

        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&aLoading DiscordSRV features...");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
