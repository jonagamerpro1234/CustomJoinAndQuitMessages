package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.interfaces.IHook;

public class HookManager {

    private static HookManager instance;
    private CustomJoinAndQuitMessages plugin;
    private HookManager manager;
    private PlaceholderApiHook placeholderApiHook = new PlaceholderApiHook(this);
    private DiscordSRVHHook discordSRVHHook = new DiscordSRVHHook(this);
    private VaultHook vaultHook = new VaultHook(this);
    private EssentialsXDiscordHook essentialsXDiscordHook = new EssentialsXDiscordHook(this);
    private EssentialsXHook essentialsXHook = new EssentialsXHook(this);
    private LuckPermsHook luckPermsHook = new LuckPermsHook(this);


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
                new VaultHook(this),
                new EssentialsXDiscordHook(this),
                new EssentialsXHook(this),
                new LuckPermsHook(this));
    }

    private void initHooks(IHook... hooks) {
        for (IHook hook : hooks) {
            hook.setup();
        }
    }

    public CustomJoinAndQuitMessages getPlugin() {
        return plugin;
    }

    public HookManager getManager() {
        return manager;
    }

    public PlaceholderApiHook getPlaceholderApiHook() {
        return placeholderApiHook;
    }

    public DiscordSRVHHook getDiscordSRVHHook() {
        return discordSRVHHook;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
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
