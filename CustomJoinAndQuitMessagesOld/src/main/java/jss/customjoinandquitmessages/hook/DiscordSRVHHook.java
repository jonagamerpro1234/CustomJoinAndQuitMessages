package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.manager.HookManager;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.bukkit.Bukkit;

public class DiscordSRVHHook implements IHook {

    private HookManager hookManager;
    private boolean isEnabled;

    public DiscordSRVHHook(HookManager hookManager) {
        this.hookManager = hookManager;
    }

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
            Logger.warning("&eDiscordSRV not enabled! - Disable Features...");
            this.isEnabled = false;
            return;
        }

        if (!Settings.hook_discordsrv) {
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

    public HookManager getHookManager() {
        return hookManager;
    }
}
