package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import jss.customjoinandquitmessages.utils.logger.Logger;
import org.bukkit.Bukkit;

public class PlaceholderApiHook implements IHook {

    private boolean isEnabled;


    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.isEnabled = false;
            Logger.warning("&ePlaceholderAPI not enabled! - Disable Features...");
            return;
        }

        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&aLoading PlaceholderAPI features...");
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

}
