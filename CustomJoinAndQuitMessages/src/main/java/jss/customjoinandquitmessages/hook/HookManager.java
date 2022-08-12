package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import org.jetbrains.annotations.NotNull;

public class HookManager {

    private static HookManager instance;
    private final CustomJoinAndQuitMessages plugin;
    private final DiscordSRVHHook discordSRVHHook = new DiscordSRVHHook(this);
    private final EssentialsXDiscordHook essentialsXDiscordHook = new EssentialsXDiscordHook(this);
    private final EssentialsXHook essentialsXHook = new EssentialsXHook(this);
    private final LuckPermsHook luckPermsHook = new LuckPermsHook(this);

    public HookManager(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
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
                new LuckPermsHook(this));
    }

    private void initHooks(IHook @NotNull ... hooks) {
        for (IHook hook : hooks) {
            hook.setup();
        }
    }

    public CustomJoinAndQuitMessages getPlugin() {
        return plugin;
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
