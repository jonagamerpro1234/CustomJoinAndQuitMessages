package jss.customjoinandquitmessages.hook;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Vault {

	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	public Economy econ = null;
	public Permission perm = null;

	public Vault(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public boolean getVault() {
		if (plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
			plugin.vault = true;
		}
		return plugin.vault;
	}

	public void onVault() {
		if (getVault()) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &eVault:&b true");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
		} else {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &eVault:&b false");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
		}

	}

}
