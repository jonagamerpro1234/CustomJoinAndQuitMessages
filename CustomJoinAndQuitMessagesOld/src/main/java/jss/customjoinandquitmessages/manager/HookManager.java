package jss.customjoinandquitmessages.manager;

import jss.customjoinandquitmessages.hook.*;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.jetbrains.annotations.NotNull;

public class HookManager {

    private static HookManager instance;
    private final DiscordSRVHHook discordSRVHHook = new DiscordSRVHHook(this);
    private final EssentialsXDiscordHook essentialsXDiscordHook = new EssentialsXDiscordHook(this);
    private final EssentialsXHook essentialsXHook = new EssentialsXHook(this);
    private final LuckPermsHook luckPermsHook = new LuckPermsHook();

    public HookManager() {
        instance = this;
    }

    public static HookManager getInstance() {
        return instance;
    }

    public static HookManager get() {
        return instance;
    }

    public void load() {
        initHooks(new PlaceholderApiHook(this),
                new DiscordSRVHHook(this),
                new EssentialsXDiscordHook(this),
                new EssentialsXHook(this),
                luckPermsHook);
    }

    private void initHooks(IHook @NotNull ... hooks) {
        for (IHook hook : hooks) {
            hook.setup();
        }
    }

    public DiscordSRVHHook getDiscordSRVHHook() {
        return discordSRVHHook;
    }

    public EssentialsXDiscordHook getEssentialsXDiscordHook() {
        return essentialsXDiscordHook;
    }

    public EssentialsXHook getEssentialsXHook() {
        return essentialsXHook;
    }

    public LuckPermsHook getLuckPermsHook() {
        return luckPermsHook;
    }
}
