package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.bukkit.Bukkit;

public class PlaceholderApiHook implements IHook {

    private HookManager hooksManager;
    private boolean isEnabled;

    public PlaceholderApiHook(HookManager hooksManager) {
        this.hooksManager = hooksManager;
    }

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.isEnabled = false;
            Logger.warning("&ePlaceholderAPI not enabled! - Disable Features...");
            return;
        }

        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix() + "&aLoading PlaceholderAPI features...");
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public HookManager getHooksManager() {
        return hooksManager;
    }

}
