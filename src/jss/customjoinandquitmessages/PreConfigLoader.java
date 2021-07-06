package jss.customjoinandquitmessages;

import org.bukkit.configuration.file.FileConfiguration;

public class PreConfigLoader {
	
	private CustomJoinAndQuitMessages plugin;
	
	public void load() {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
