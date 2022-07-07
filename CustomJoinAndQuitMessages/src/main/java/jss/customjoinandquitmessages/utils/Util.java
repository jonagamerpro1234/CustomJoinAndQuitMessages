package jss.customjoinandquitmessages.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class Util {

	private final static String prefix = getPrefix();

	public static String setCustomLine(String arg, String color) {
		return color(color+"-=-=-=-=-=-=-=-=-=-=-="+arg+"=-=-=-=-=-=-=-=-=-=-=-");
	}
	
	public static String seEmptyLine(String color) {
		return color(color + "                                             ");
	}
	
	public static String setLine(String color) {
		return color(color + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	}
	
	public static String setLine() {
		return "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-";
	}
	
	public static String color(String text) {
		return IridiumColorAPI.process(text);
	}
	
	public static void sendColorConsoleMessage(CommandSender sender, String text) {
		sender.sendMessage(color(text));
	}
	
	public static String colorless(String text) {
		return ChatColor.stripColor(text);
	}
	
	public static ChatColor fixColor(String color) {
		return ChatColor.of(color);
	}
	
	public static ChatColor fixColor2(String color) {
		return ChatColor.of(color(color));
	}
	
	public static void sendColorMessage(Player player, String text) {
		player.sendMessage(color(text));
	}
	
	public static void sendColorMessage(CommandSender sender, String text) {
		sender.sendMessage(color(text));
	}
	
	public static void sendColorMessage(String msg) {
		Bukkit.broadcastMessage(color(msg));
	}
	
	private static void sendEnable(String prefix, String message) {
		CommandSender c = Bukkit.getConsoleSender();
		sendColorMessage(c, prefix + message);
	}
	
	private static void sendEnable(String message) {
		CommandSender c = Bukkit.getConsoleSender();
		sendColorMessage(c, message);
	}
	
	public static String getPrefix() {
		return "&e[&dCustomJoinAndQuitMessages&e]&7 ";
	}
	
	public static String getPrefixPlayer() {
		return "&6[&dCustomJoinAndQuitMessages&6]&7 ";
	}
	
	public static void sendLoadTitle(String version) {
		sendEnable("&d   ______    _____   ____    __  ___");
		sendEnable("&d   / ____/   / /   | / __ \\  /  |/  /");
	    sendEnable("&d  / /   __  / / /| |/ / / / / /|_/ /  &bBy jonagamerpro1234");
	    sendEnable("&d / /___/ /_/ / ___ / /_/ / / /  / /   &bVersion &a" + version);
        sendEnable("&d \\____/\\____/_/  |_\\___\\_\\/_/  /_/    &aThanks for using CustomJoinAndQuitMessage &c<3");
	}
	
	public static void setEnabled(String version) {
	    sendEnable(prefix, "&5 <||============================================----");
	    sendEnable(prefix, "&5 <|| &c* &bThe plugin is &d[&aSuccessfully activated&d]");
	    sendEnable(prefix, "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
	    sendEnable(prefix, "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
	    sendEnable(prefix, "&5 <|| &c* &bTested Versions &3|&a1.8.x &3- &a1.18.x&3| &eComing Soon -> &c1.19");
	    sendEnable(prefix, "&5 <||============================================----");
	}
	
	public static void setDisabled(String version) {
	    sendEnable(prefix, "&5 <||============================================----");
	    sendEnable(prefix, "&5 <|| &c* &bThe plugin is &d[&cSuccessfully disabled&c]");
	    sendEnable(prefix, "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
	    sendEnable(prefix, "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
	    sendEnable(prefix, "&5 <|| &c* &bTested Versions &3|&a1.8.x &3- &a1.18.x&3| &eComing Soon -> &c1.19");
	    sendEnable(prefix, "&5 <|| &a* &eThanks for using CustomJoinAndQuitMessage &c<3");
	    sendEnable(prefix, "&5 <||============================================----");	
	}	
	
	public static List<String> setTabLimit(final List<String> list, final String inic){
		final List<String> returned = new ArrayList<String>();
		for(String s : list) {
			if(s == null) {
				continue;
			}
			if(s.toLowerCase().startsWith(inic.toLowerCase())) {
				returned.add(s);
			}
		}
		return returned;
	}
	
	public static void sendTextComponentHover(Player j, String action, String message, String submessage, String color) {
		TextComponent msg = new TextComponent(color(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(submessage).color(ChatColor.of(color)).create()));
		j.spigot().sendMessage(msg);
	}
	
	public static void sendTextComponent116Hover(Player j, String action, String message, String submessage) {
		TextComponent msg = new TextComponent(color(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(color(submessage)).create()));
		j.spigot().sendMessage(msg);
	}
	
	public static void sendTextComponent116ALL(String action, String message, String submessage) {
		TextComponent msg = new TextComponent(color(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(color(submessage)).create()));
		sendAllPlayerBaseComponent(msg);
	}
	
	public static void sendTextComponentClick(Player j, String action, String message, String arg0) {
		TextComponent msg = new TextComponent(color(message));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(action)) , arg0));
		j.spigot().sendMessage(msg);
	}
	

	public static void sendTextComponentDouble(Player player, String text, String subtext, String hoverAction, String clickAction, String action) {
		TextComponent component = new TextComponent(color(text));
		component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hoverAction)) , new ComponentBuilder(color(subtext)).create()));
		component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(clickAction)), action));
		player.spigot().sendMessage(component);
	}
	
	public static void sendTextComponentType(String type , Player j, String text, String subtext , Object color, String action) {
		switch (type) {
		case "Hover":
			sendTextComponentHover(j, action, text, subtext, (String) color);
			break;
		case "Hover116":
			sendTextComponent116Hover(j, action, text, subtext);
			break;
		case "Click":
			sendTextComponentClick(j, action, text, subtext);
			break;
		}
	}
	
	public static void sendAllPlayerBaseComponent(BaseComponent component) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(component);			
		}
	}
	
	public static String getActionHoverType(String arg) {
		String temp = arg;
		
		if(temp.equalsIgnoreCase("text")) {
			return "SHOW_TEXT";
 		}
		if(temp.equalsIgnoreCase("item")) {
			return "SHOW_ITEM";
		}
		if(temp.equalsIgnoreCase("entity")) {
			return "SHOW_ENTITY";
		}
		return null;
	}
	
	public static String getActionClickType(String arg) {
		String temp = arg;
		if(temp.equalsIgnoreCase("url")) {
			return "OPER_URL";
 		}
		if(temp.equalsIgnoreCase("cmd")) {
			return "RUN_COMMAND";
		}
		return null;
	}
	
    public static String getVar(Player player, String text) {
        text = text.replace("<name>", player.getName());
        text = text.replace("%name%", player.getName());
        text = text.replace("<displayname>", player.getDisplayName());
        text = text.replaceAll("<world>", player.getWorld().getName());
        text = text.replace("<0>", " ");
        text = text.replace("<xp_level>", "" + player.getExpToLevel());
        text = text.replace("<xp>", "" + player.getExp());
        text = placeholderReplace(text, player);
        text = getOnlinePlayers(text);
        return text;
    }

    private static String placeholderReplace(String text, Player player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    public static String getOnlinePlayers(String text) {
        int playersOnline = 0;
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).getReturnType() == Collection.class) {
                playersOnline = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).size();
            } else {
                playersOnline = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).length;
            }
        } catch (NoSuchMethodException ex) {
        } catch (InvocationTargetException ex) {
        } catch (IllegalAccessException ex) {
        }
        text = text.replace("<online>", "" + playersOnline);
        text = text.replace("<Online>", "" + playersOnline);
        return text;
    }
    
	public static String randomText(List<String> list) {
		Random r = new Random();
		return list.get(r.nextInt(list.size()));
	}
	
}
