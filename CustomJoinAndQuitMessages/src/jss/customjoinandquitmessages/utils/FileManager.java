package jss.customjoinandquitmessages.utils;

import java.io.File;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class FileManager {
	
	private CustomJoinAndQuitMessages plugin;

	public FileManager(CustomJoinAndQuitMessages plugin) {
		super();
		this.plugin = plugin;
	}
	
	public void createVoidFolder(String name) {
		File folder = new File(getDataFolder(), name);
		if(!folder.exists()){
			try {
				folder.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createFolderAndFile(String namefolder, String namefile) {
		File folder = new File(getDataFolder(), namefolder);
		if(!folder.exists()) {
			try {
				folder.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
		
		File file = new File(folder, namefile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Deprecated
	public JavaPlugin getJavaPlugin() {
		return plugin;
	}
	
	public void saveResources(String filename, boolean replace) {
		 plugin.saveResource(filename, replace);
	}
	
	public InputStream getResources(String filename) {
		return plugin.getResource(filename);
	}
	
	public File getDataFolder() {
		return plugin.getDataFolder();
	}
	
}
