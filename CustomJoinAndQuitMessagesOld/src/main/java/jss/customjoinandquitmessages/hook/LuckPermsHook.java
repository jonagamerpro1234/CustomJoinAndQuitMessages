package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class LuckPermsHook implements IHook {

    private boolean isEnabled;


    @Contract(pure = true)
    public static @NotNull LuckPerms getApi() {
        return LuckPermsProvider.get();
    }

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            Logger.warning("&eLuckPerms not enabled! - Disable Features...");
            this.isEnabled = false;
            System.out.println("Cjm Softdepend: Luckperms false - check plugin installed in plugin folder");
            return;
        }

        if (!Settings.hook_luckperms) {
            this.isEnabled = false;
            System.out.println("Cjm Softdepend: Luckperms false - Hook Enabled in config");
            Logger.warning("&eLuckPerms not enabled! - Disable Features...");
            return;
        }

        this.isEnabled = true;
        System.out.println("Cjm Softdepend: Luckperms true - The plugin has successfully loaded the dependency");
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&aLoading LuckPerms features...");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
