package jss.customjoinandquitmessages.hook;

//import de.myzelyam.api.vanish.VanishAPI;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Util;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SuperVanishHook implements IHook {

    private boolean isEnabled;

    public void setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || !Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
            Logger.warning("&eSuperVanish or PremiumVanish not enabled! - Disable Features...");
            this.isEnabled = false;
            return;
        }

        this.isEnabled = true;
        Util.sendColorMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&aLoading SuperVanish or PremiumVanish features...");

    }

    public boolean isEnabled() {
        return isEnabled;
    }

   /* public boolean isVanishPlayer(Player player){
       return VanishAPI.isInvisible(player);
    }*/
}
