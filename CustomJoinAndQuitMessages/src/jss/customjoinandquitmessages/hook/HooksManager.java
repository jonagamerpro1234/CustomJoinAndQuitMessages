package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.interfaces.LoaderHook;

public class HooksManager {
	
	private CustomJoinAndQuitMessages plugin;
	private HooksManager manager;
	private VaultHook vaultHook;
	private PlaceholderApiHook placeholderApiHook;

	public HooksManager(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}
	
	public void load() {
		initHooks(new VaultHook(this),
				new PlaceholderApiHook(this));
	}
	
	private void initHooks(LoaderHook... hooks) {
		for(LoaderHook hook : hooks) {
			hook.load();
		}
	}
	
	public CustomJoinAndQuitMessages getPlugin() {
		return plugin;
	}

	public HooksManager getManager() {
		return manager;
	}

	public PlaceholderApiHook getPlaceholderApiHook() {
		return placeholderApiHook;
	}
	
	public VaultHook getVaultHook() {
		return vaultHook;
	}
}
