package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Logger.Level;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.interfaces.LoaderHook;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHook implements LoaderHook{

	private CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.getPlugin();
	private HooksManager hooksManager;
	private EventUtils eventUtils = new EventUtils(plugin);
	private Logger logger = new Logger(plugin);
	private Economy economy;
	private Permission permission;
	private Chat chat;

	public VaultHook(HooksManager hooksManager) {
		this.hooksManager = hooksManager;
	}
	
	public void load() {
		if(!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			logger.Log(Level.WARNING, "vault not enabled! - Disable Features...");
			return;
		}
		
		Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&aLoading vault features...");
		RegisteredServiceProvider<Economy> rspE = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		RegisteredServiceProvider<Permission> rspP = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		RegisteredServiceProvider<Chat> rspC = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		
		if(rspE != null) {
			economy = rspE.getProvider();
		}
		if(rspP != null) {
			permission = rspP.getProvider();
		}
		if(rspC != null) {
			chat = rspC.getProvider();
		}
	}
	
	
	public Economy getEconomy() {
		return economy;
	}
	
	public Chat getChat() {
		return chat;
	}
	
	public Permission getPermission() {
		return permission;
	}
	
	public HooksManager getHooksManager() {
		return hooksManager;
	}
}
