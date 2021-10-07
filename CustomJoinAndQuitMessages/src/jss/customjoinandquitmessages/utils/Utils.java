package jss.customjoinandquitmessages.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class Utils {

	private final static String prefix = getPrefix();
	
	public static String hexcolor(String text) {
		if((Bukkit.getVersion().contains("1.16")) || (Bukkit.getVersion().contains("1.17"))) {
			Pattern hex = Pattern.compile("#[a-fA-F0-9]{6}");
			Matcher match = hex.matcher(text);
			while(match.find()) {
				String color = text.substring(match.start(), match.end());
				text = text.replace(color, fixColor(color) + "");
				match = hex.matcher(text);
			}
		}
		return legacycolor(text);
	}

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
		if(text == null  || text.isEmpty()) return ""; 
		return IridiumColorAPI.process(text);
	}
	
	public static String legacycolor(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
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
		return ChatColor.of(hexcolor(color));
	}
	
	public static void sendColorMessage(Player player, String text) {
		player.sendMessage(hexcolor(text));
	}
	
	public static void sendColorMessage(CommandSender sender, String text) {
		sender.sendMessage(hexcolor(text));
	}
	
	public static void sendColorMessage(String msg) {
		Bukkit.broadcastMessage(hexcolor(msg));
	}
	
	private static void sendEnable(String prefix, String message) {
		CommandSender c = Bukkit.getConsoleSender();
		sendColorMessage(c, prefix + message);
	}
	
	public static String getPrefix() {
		return "&e[&dCustomJoinAndQuitMessages&e]&7";
	}
	
	public static String getPrefixPlayer() {
		return "&6[&dCustomJoinAndQuitMessages&6]&7";
	}
	
	public static void setEnabled(String version) {
	    sendEnable(prefix , "&5 <||============================================----");
	    sendEnable(prefix , "&5 <|| &c* &bThe plugin is &d[&aSuccessfully activated&d]");
	    sendEnable(prefix , "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
	    sendEnable(prefix , "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
	    sendEnable(prefix , "&5 <|| &c* &bTested Versions &3|&d1.7.x&3|&a1.8.x&3|&a1.9.x&3|&a1.10.x&3|&a1.11.x&3|&a1.12.x&3|&a1.13.x&3|&a1.14.x&3|&a1.15.x&3|&a1.16.x&e|&a1.17.x");
	    sendEnable(prefix , "&5 <||============================================----");
	}
	
	public static void setDisabled(String version) {
	    sendEnable(prefix , "&5 <||============================================----");
	    sendEnable(prefix , "&5 <|| &c* &bThe plugin is &d[&cSuccessfully disabled&c]");
	    sendEnable(prefix , "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
	    sendEnable(prefix , "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
	    sendEnable(prefix , "&5 <|| &c* &bTested Versions &3|&d1.7.x&3|&a1.8.x&3|&a1.9.x&3|&a1.10.x&3|&a1.11.x&3|&a1.12.x&3|&a1.13.x&3|&a1.14.x&3|&a1.15.x&3|&a1.16.x&e|&a1.17.x");
	    sendEnable(prefix , "&5 <||============================================----");	
	}	
	
	public static List<String> TabLimit(final List<String> list, final String inic){
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
		TextComponent msg = new TextComponent(hexcolor(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(submessage).color(ChatColor.of(color)).create()));
		j.spigot().sendMessage(msg);
	}
	
	public static void sendTextComponent116Hover(Player j, String action, String message, String submessage) {
		TextComponent msg = new TextComponent(hexcolor(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(hexcolor(submessage)).create()));
		j.spigot().sendMessage(msg);
	}
	
	public static void sendTextComponent116ALL(String action, String message, String submessage) {
		TextComponent msg = new TextComponent(hexcolor(message));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)) , new ComponentBuilder(hexcolor(submessage)).create()));
		sendAllPlayerBaseComponent(msg);
	}
	
	public static void sendTextComponentClick(Player j, String action, String message, String arg0) {
		TextComponent msg = new TextComponent(hexcolor(message));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(action)) , arg0));
		j.spigot().sendMessage(msg);
	}
	

	public static void sendTextComponentDouble(Player player, String text, String subtext, String hoverAction, String clickAction, String action) {
		TextComponent component = new TextComponent(hexcolor(text));
		component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hoverAction)) , new ComponentBuilder(hexcolor(subtext)).create()));
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
        text = text.replace("<displayname>", player.getDisplayName());
        text = text.replaceAll("<world>", player.getWorld().getName());
        text = text.replace("<0>", " ");
        text = text.replace("<xp_level>", "" + player.getExpToLevel());
        text = text.replace("<xp>", "" + player.getExp());
        text = text.replace("<ping>", "" + player.getPing());
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
