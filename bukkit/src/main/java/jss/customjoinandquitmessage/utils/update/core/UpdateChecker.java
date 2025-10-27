package jss.customjoinandquitmessage.utils.update.core;

import jss.customjoinandquitmessage.utils.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class UpdateChecker {

    protected final JavaPlugin plugin;
    protected final UpdateSettings settings;

    public UpdateChecker(JavaPlugin plugin, UpdateSettings settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    public abstract void checkForUpdates();

    protected void logError(@NotNull Exception e) {
        Logger.warning("[UpdateChecker] Error checking for updates: " + e.getMessage());
    }
}
