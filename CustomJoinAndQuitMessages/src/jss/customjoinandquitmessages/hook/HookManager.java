package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.interfaces.IHook;

public class HookManager {
	
	private CustomJoinAndQuitMessages plugin;
	private HookManager manager;
	private PlaceholderApiHook placeholderApiHook = new PlaceholderApiHook(this);
	private DiscordSRVHHook discordSRVHHook = new DiscordSRVHHook(this);
	private VaultHook vaultHook = new VaultHook(this);
	private static HookManager instance;
	

	public HookManager(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		instance = this;
	}
	
	public void load() {
		initHooks( new PlaceholderApiHook(this),
				new DiscordSRVHHook(this),
				new VaultHook(this));
	}
	
	private void initHooks(IHook... hooks) {
		for(IHook hook : hooks) {
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
	
	public static HookManager getInstance() {
		return instance;
	}
}
