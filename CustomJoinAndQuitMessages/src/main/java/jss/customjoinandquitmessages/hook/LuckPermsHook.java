package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsHook implements IHook {

    private HookManager hookManager;
    private boolean isEnabled;

    public LuckPermsHook(HookManager hookManager) {
        this.hookManager = hookManager;
    }

    public static LuckPerms getApi() {
        return LuckPermsProvider.get();
    }

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            Logger.warning("&eLuckPerms not enabled! - Disable Features...");
            this.isEnabled = false;
            return;
        }

        if (!Settings.hook_luckperms) {
            this.isEnabled = false;
            Logger.warning("&eLuckPerms not enabled! - Disable Features...");
            return;
        }

        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix() + "&aLoading LuckPerms features...");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isGroup(Player player, String name) {
        LuckPerms api = LuckPermsProvider.get();
        String group = api.getUserManager().getUser(player.getName()).getPrimaryGroup();
        boolean a = false;
        if (name.equals(group)) {
            a = true;
        }
        return a;
    }

    public String getGroup(Player player) {
        if (player != null) {
            Logger.debug("Player: N/A");
        }
        LuckPerms api = LuckPermsProvider.get();
        String group = api.getUserManager().getUser(player.getName()).getPrimaryGroup();
        return group;
    }

    public HookManager getHookManager() {
        return hookManager;
    }
}
