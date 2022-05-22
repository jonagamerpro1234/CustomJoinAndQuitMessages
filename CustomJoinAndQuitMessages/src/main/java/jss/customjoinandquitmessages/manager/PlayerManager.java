package jss.customjoinandquitmessages.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger;

public class PlayerManager {
	
	private CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();
	private FileConfiguration config = plugin.getPlayerFile().getConfig();
	
	public String getGroup(Player player) {
		if(existsPlayer(player.getName())) config.getString("Players." + player.getName() + ".Group"); return null;
	}
	
	public void setGroup(Player player, String group) {
		if(existsPlayer(player.getName())) config.set("Players." + player.getName() + ".Group", group);
	}
	
	public void createPlayer(Player player, String group) {
		if(!existsPlayer(player.getName())) {
			config.set("Players." + player.getName() + ".Group", group);
			this.save();
		}else {
			Logger.debug("&aThe player already exists");
		}
	}
	
	private void save() {
		plugin.getPlayerFile().saveConfig();
	}
	
	public boolean existsPlayer(String path) {
		return config.contains("Players." + path);
	}
}