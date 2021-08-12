package jss.customjoinandquitmessages.config;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Utils;


public class FileListener {
	
    private final CustomJoinAndQuitMessages plugin;
    private final static String FOLDERPATH = "lang";
	private CommandSender c = Bukkit.getConsoleSender();
    
    public FileListener(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public List<String> list() throws IOException {
        List<String> result = new ArrayList<String>();

        File localeDir = new File(plugin.getDataFolder(), FOLDERPATH);
        if (localeDir.exists()) {
            FilenameFilter ymlFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String lowercaseName = name.toLowerCase();
            		if(plugin.getConfig().getString("Config.Debug").equals("true")) {
            			Utils.sendColorMessage(c, Utils.getPrefix()+"&5<|&eLoad Lang File: " + name);	
            		}
                    if (lowercaseName.endsWith(".yml") && name.length() == 9 && name.substring(2,3).equals("-")) {
                        return true;
                    } else {
                        if (lowercaseName.endsWith(".yml") && !lowercaseName.equals("lang.yml")) {
                        	Utils.sendColorMessage(c, Utils.getPrefix()+"&eFile: " + name + "is not in the correct format for a lang file - skipping...");
                        }
                        return false;
                    }
                }
            };
            for (String fileName : localeDir.list(ymlFilter)) {
                result.add(fileName.replace(".yml", ""));
            }
            if (!result.isEmpty())
                return result;
        }
        File jarfile = null;
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);

            jarfile = (File) method.invoke(this.plugin);
        } catch (Exception e) {
            throw new IOException(e);
        }
        JarFile jar = new JarFile(jarfile);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String path = entry.getName();

            if (!path.startsWith(FOLDERPATH)) {
                continue;
            }

            if (entry.getName().endsWith(".yml")) {
                String name = entry.getName().replace(".yml","").replace("lang/", "");
                if (name.length() == 5 && name.substring(2,3).equals("-")) {
                    result.add(name);
                }
            }

        }
        jar.close();
        return result;
    }
}
