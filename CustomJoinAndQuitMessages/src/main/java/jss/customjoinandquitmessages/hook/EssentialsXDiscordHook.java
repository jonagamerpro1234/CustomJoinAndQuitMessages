package jss.customjoinandquitmessages.hook;

import org.bukkit.Bukkit;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.interfaces.IHook;
import net.essentialsx.api.v2.services.discord.DiscordService;
import net.essentialsx.api.v2.services.discord.MessageType;

public class EssentialsXDiscordHook implements IHook{

	private HookManager hookManager;
	private boolean isEnabled;
	private DiscordService service;
	
	public EssentialsXDiscordHook(HookManager hookManager) {
		this.hookManager = hookManager;
	}
	
	public void setup() {
		if(!Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord")) {
			Logger.warning("&eEssentialsDiscord not enabled! - Disable Features...");
			this.isEnabled = false;
			return;
		}
		
		if(!Settings.hook_essentialsDiscord) {
			this.isEnabled = false;
			Logger.warning("&eEssentialsDiscord not enabled! - Disable Features...");
			return;
		}
		
		this.service = Bukkit.getServicesManager().load(DiscordService.class);
		this.isEnabled = true;
		Utils.sendColorMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&aLoading EssentialsDiscord features...");	
		Logger.warning("&e!!These features are still under development and may have bugs!!");
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public DiscordService getService() {
		return service;
	}
	
	public void sendJoinMessage(String channelId, String message) {
		if(Settings.hook_essentialsDiscord_use_default_channel) {
			service.sendMessage(MessageType.DefaultTypes.JOIN, message, false);
		}else {
			service.sendMessage(new MessageType(Settings.hook_essentialsDiscord_channelid), message, false);
		}
	}
	
	public void sendQuitMessage(String channelId, String message) {
		if(Settings.hook_essentialsDiscord_use_default_channel) {
			service.sendMessage(MessageType.DefaultTypes.LEAVE, message, false);
		}else {
			service.sendMessage(new MessageType(Settings.hook_essentialsDiscord_channelid), message, false);
		}
	}
	
	public HookManager getHookManager() {
		return hookManager;
	}
}
