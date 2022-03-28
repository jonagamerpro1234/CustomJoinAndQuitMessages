package jss.customjoinandquitmessages.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class PlayerManager {
	
	private CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.getInstance();
	private FileConfiguration config = plugin.getPlayerFile().getConfig();
	
	public String getGroup(Player player) {
		if(existsPlayer(player.getName())) config.getString("Players." + player.getName() + ".Group"); return null;
	}
	
	public void setGroup(Player player, String group) {
		if(existsPlayer(player.getName())) config.set("Players." + player.getName() + ".Group", group);
	}
	
	public boolean existsPlayer(String path) {
		return config.contains("Players." + path);
	}
}
