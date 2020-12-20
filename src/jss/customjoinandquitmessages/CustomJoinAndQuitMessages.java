package jss.customjoinandquitmessages;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import jss.customjoinandquitmessages.commands.CustomJoinAndQuitCmd;
import jss.customjoinandquitmessages.custom.SendActionBarEvent;
import jss.customjoinandquitmessages.events.JoinListener;
import jss.customjoinandquitmessages.events.SoundsListener;
import jss.customjoinandquitmessages.hook.Placeholderapi;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Lang;
import jss.customjoinandquitmessages.utils.PluginConfig;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.Utils;

public class CustomJoinAndQuitMessages extends JavaPlugin{

	PluginDescriptionFile jss = getDescription();
	public String name = this.jss.getName();
	public String version = this.jss.getVersion();
	private static CustomJoinAndQuitMessages plugin;
	public boolean placeholders = false;
	public Metrics metrics;
	public String latestversion;
	private UpdateChecker update = new UpdateChecker(this);
    public boolean useLegacyversions = false;
    public String nmsversion;
	private Map<String,Lang> availableLocales = new HashMap<>();
	private Placeholderapi placeholderapi = new Placeholderapi(this);
	private EventUtils eventUtils = new EventUtils(this);
	
	public void onEnable() {
		Utils.setEnabled(version);


		if(!PluginConfig.loadConfig(this)) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&b"+ name +"&e]&c error load lang and config file");
			return;
		}		
		metrics = new Metrics(this);
		plugin = this;
        nmsversion = Bukkit.getServer().getClass().getPackage().getName();
        nmsversion = nmsversion.substring(nmsversion.lastIndexOf(".") + 1);
        if (nmsversion.equalsIgnoreCase("v1_8_R1") || nmsversion.equalsIgnoreCase("v1_7_")) { 
        	useLegacyversions = true;
        	if(useLegacyversions == true) {
        		Utils.sendColorMessage(eventUtils.getConsoleSender(), " Test 1.7_? | 1.8_R1");
        	}
        }
        if (nmsversion.equalsIgnoreCase("v1_8_R3")) { 
        	useLegacyversions = true;
        	if(useLegacyversions == true) {
        		Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&7Use " + nmsversion + " &cdisabled &7method &b1.16");
        	}
        }else if(nmsversion.equalsIgnoreCase("v1_16_R2")){
        	Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&7Use " + nmsversion + " &aenabled &7method &b1.16");
        }
		setupCommands();
		setupEvents();
		placeholderapi.onPlaceHolderAPI();
		update.Update(eventUtils.getConsoleSender());
	}
	
	public void onDisable() {
		Utils.setDisabled(version);
		metrics = null;
		placeholders = false;
	}
	
	public void setupCommands() {
		new CustomJoinAndQuitCmd(this);
	}
	
	public void setupEvents() {
		new JoinListener(this);
		new SoundsListener(this);
	}
	
	public static CustomJoinAndQuitMessages getPlugin() {
		return plugin;
	}
	
	
	public Lang Locale() {
		return availableLocales.get(Settings.defaultLanguage);
	}   
	
	public Map<String, Lang> getAvailableLocales() {
		return availableLocales;
	}
	    
	public void setAvailableLocales(HashMap<String, Lang> availableLocales) {
		this.availableLocales = availableLocales;
	}
	
	public static void sendPacket(Player player, Object obj) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, obj);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Class<?> getNMSClass(String packet){
		String versionserver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + versionserver + "." + packet);
		}catch(ClassCastException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
 
    public void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return; 
        }
        SendActionBarEvent actionBarMessageEvent = new SendActionBarEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
        if (actionBarMessageEvent.isCancelled())
            return;
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsversion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsversion + ".PacketPlayOutChat");
            Class<?> packetClass = Class.forName("net.minecraft.server." + nmsversion + ".Packet");
            if (useLegacyversions) {
                Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsversion + ".ChatSerializer");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsversion + ".IChatBaseComponent");
                Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsversion + ".ChatComponentText");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsversion + ".IChatBaseComponent");
                try {
                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsversion + ".ChatMessageType");
                    Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                    Object chatMessageType = null;
                    for (Object obj : chatMessageTypes) {
                        if (obj.toString().equals("GAME_INFO")) {
                            chatMessageType = obj;
                        }
                    }
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                } catch (ClassNotFoundException cnfe) {
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                }
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
