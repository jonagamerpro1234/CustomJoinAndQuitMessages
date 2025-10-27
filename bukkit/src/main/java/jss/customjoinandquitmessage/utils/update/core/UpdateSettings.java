package jss.customjoinandquitmessage.utils.update.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UpdateSettings {

    private final boolean notifyAdmins;
    private final boolean showInConsole;
    private final boolean checkOnStartup;

    public UpdateSettings(boolean notifyAdmins, boolean showInConsole, boolean checkOnStartup) {
        this.notifyAdmins = notifyAdmins;
        this.showInConsole = showInConsole;
        this.checkOnStartup = checkOnStartup;
    }

    public boolean notifyAdmins() {
        return notifyAdmins;
    }

    public boolean showInConsole() {
        return showInConsole;
    }

    public boolean checkOnStartup() {
        return checkOnStartup;
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull UpdateSettings defaultSettings() {
        return new UpdateSettings(true, true, true);
    }
}

