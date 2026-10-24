package jss.customjoinandquitmessage.libs.luckperms;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.utils.logger.Logger;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.Nullable;

public class LuckPermsHook {

    private static LuckPermsHook instance;
    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    private LuckPermsHook() {}

    public static LuckPermsHook get() {
        if (instance == null) {
            instance = new LuckPermsHook();
        }

        return instance;
    }

    public boolean isLuckPerms() {
        return plugin.getManager().isPluginEnabled("LuckPerms");
    }

    public @Nullable String getGroupName(String playerName) {
        LuckPerms api = getApi();

        if (api == null) {
            return null;
        }

        return api.getUserManager()
                .getLoadedUsers()
                .stream()
                .filter(user -> user.getUsername() != null)
                .filter(user -> user.getUsername()
                        .equalsIgnoreCase(playerName))
                .findFirst()
                .map(User::getPrimaryGroup)
                .orElse(null);
    }

    private @Nullable LuckPerms getApi() {
        if (!isLuckPerms()) {
            return null;
        }

        try {
            return LuckPermsProvider.get();
        } catch (IllegalStateException exception) {
            Logger.warning("LuckPerms is enabled, but its API is not available.");
            return null;
        }
    }
}