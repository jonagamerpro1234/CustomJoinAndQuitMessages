package jss.customjoinandquitmessages.utils.nms;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class Nms {

	private static CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.getPlugin();
	private static String nmsname = "net.minecraft.server.";
	public final static String PacketPlayOutTitle = "PacketPlayOutTitle";
	public final static String IChatBaseComponent = "IChatBaseComponent";
	public static String nmsversion = plugin.getNmsversion();
	public static boolean useLegacyversions = plugin.isUseLegacyversions();
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(23);

	
	public static void sendPacket(Player player, Object object) {
		try {
			getPlayerConnection(player).getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(getPlayerConnection(player), object);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Class<?> getNMSClass(String packet){
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName(nmsname + version + "." + packet);
		} catch (ClassCastException |ClassNotFoundException e) {
			throw new Error(e);
		}
	}
	
	public static Object getPlayerConnection(Player player) {
		try {
			Object object = getHandle(player).getClass().getField("playerConnection").get(getHandle(player));
			return object;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new Error(e);
		}
	}
	
	public static Object getHandle(Player player) {
		try {
			Object object = player.getClass().getMethod("").invoke(player);
			return object;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new Error(e);
		}
	}

	public static CustomJoinAndQuitMessages getPlugin() {
		return plugin;
	}
	
}
