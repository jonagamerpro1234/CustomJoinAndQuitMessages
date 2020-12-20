package jss.customjoinandquitmessages.events;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class JoinListener implements Listener{

	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
	public JoinListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventUtils.addEventList(this);
	}
	
	@EventHandler
	public void JoinMessages(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Join.Enabled";
		String text = config.getString("Join.Text");
		String type = "Join.Type";
		String hovetext = config.getString("Join.HoverEvent.Text");
		String hovecolor = config.getString("Join.HoverEvent.Color");
		String hovemode = config.getString("Join.HoverEvent.Mode");
		String clickaction = config.getString("Join.ClickEvent.Action");
		String clickmode = config.getString("Join.ClickEvent.Mode");
		
		text = Utils.color(text);
		text = replacePlaceholderAPI(j, text);
		text = getAllVars(j, text);
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Default")) {
				e.setJoinMessage(text);
			}
			if (config.getString(type).equals("Double")){
				e.setJoinMessage(null);
				TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.valueOf(hovecolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);
			}
			if (config.getString(type).equals("Hover")){
				if(config.getString("Join.HoverEvent.Legacy-Color").equals("true")) {
					e.setJoinMessage(null);
					TextComponent msg = new TextComponent();
					msg.setText(text);
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.valueOf(hovecolor)).create()));
					j.spigot().sendMessage(msg);					
				}else if(config.getString("Join.HoverEvent.Legacy-Color").equals("false")) {
					e.setJoinMessage(null);
					TextComponent tc = new TextComponent();
					HoverEvent hover = new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)), new ComponentBuilder(Utils.color(hovetext)).create());
					tc.setHoverEvent(hover);
					tc.setText(text);
					j.spigot().sendMessage(tc);
				}
			}
			if(config.getString(type).equals("Click")) {
				e.setJoinMessage(null);
				TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);
			}
			if(config.getString(type).equals("None")){
				e.setJoinMessage(null);
			}
			if(config.getString(type).equals("Group")) {
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					String jointext = config.getString("Groups."+key+".Join.Text");
					String joinperm = config.getString("Groups."+key+".Join.Permission");					
					if(j.hasPermission(joinperm)){}
					
					e.setJoinMessage(null);
					jointext = getAllVars(j, jointext);
					jointext = replacePlaceholderAPI(j, jointext);
					
					Utils.sendColorMessage(j, jointext);
					
				}
			}
		}		
	}
	
	@EventHandler
	public void JoinWelcome(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		try {
			String path = "Welcome.Enabled";
			int max = config.getInt("Welcome.Max-Line");
			List<String> list = config.getStringList("Welcome.Text");
			if(config.getString(path).equals("true")) {
				for(int i = 0; i < list.size(); i++){
					String text = (String) list.get(i);
					text = replacePlaceholderAPI(j, text);
					text = getAllVars(j, text);
					Utils.sendColorMessage(j, text.replace("<0>", " "));
					if(i == max) {
						break;
					}
				}	
			}
		}catch(NullPointerException ex) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError-Path: Welcome section ?");
		}
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void sendJoinTitle(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Title.Enabled";
		String title = config.getString("Title.Text.Title");
		String subtitle = config.getString("Title.Text.SubTitle");
		int fadeIn = config.getInt("Title.Settings.Time.FadeIn");
		int stay = config.getInt("Title.Settings.Time.Stay");
		int fadeOut = config.getInt("Title.Settings.Time.FadeOut");
		
		title = Utils.color(title);
		title = replacePlaceholderAPI(j, title);
		title = getAllVars(j, title);
		
		subtitle = Utils.color(subtitle);
		subtitle = replacePlaceholderAPI(j, subtitle);
		subtitle = getAllVars(j, subtitle);
		
		if(config.getString(path).equals("true")) {
		}
	}
	
	@EventHandler
	public void sendJoinActionBar(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "ActionBar.Enabled";
		String text = config.getString("ActionBar.Text");
		
		text = Utils.color(text);
		text = replacePlaceholderAPI(j, text);
		text = getAllVars(j, text);
		
		if(config.getString(path).equals("true")) {
			plugin.sendActionBar(j, text);
		}
	}
	
	@EventHandler
	public void sendJoinUpdate(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		UpdateChecker update = new UpdateChecker(plugin);
		if(!(j.hasPermission("MultiOptions.Update.Notify")) || !(j.isOp())) {
			if(config.getString("Config.Debug.Enabled").equals("true")) {
				TextComponent msg = new TextComponent();
				msg.setText(Utils.color("&c&l&n[!]&7 You do not have permission"));
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT , new ComponentBuilder("ask an admin or owner for help for more information").color(ChatColor.YELLOW).create()));
				j.spigot().sendMessage(msg);
			}
			return;
		}
		update.Update(j);
	}
	
	@EventHandler
	public void QuitMessages(PlayerQuitEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Quit.Enabled";
		String text = config.getString("Quit.Text");
		String type = "Quit.Type";
		String hovetext = config.getString("Quit.HoverEvent.Text");
		String hovecolor = config.getString("Quit.HoverEvent.Color");
		String hovemode = config.getString("Quit.HoverEvent.Mode");
		String clickaction = config.getString("Quit.ClickEvent.Action");
		String clickmode = config.getString("Quit.ClickEvent.Mode");
		
		text = Utils.color(text);
		text = replacePlaceholderAPI(j, text);
		text = getAllVars(j, text);
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Default")) {
				e.setQuitMessage(text);
			}
			if (config.getString(type).equals("Double")){
				e.setQuitMessage(null);
				TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.valueOf(hovecolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);
			}
			if (config.getString(type).equals("Hover")){
				if(config.getString("Quit.HoverEvent.Legacy-Color").equals("true")) {
					e.setQuitMessage(null);
					TextComponent msg = new TextComponent();
					msg.setText(text);
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.valueOf(hovecolor)).create()));
					j.spigot().sendMessage(msg);					
				}else if(config.getString("Quit.HoverEvent.Legacy-Color").equals("false")) {
					e.setQuitMessage(null);
					TextComponent tc = new TextComponent();
					HoverEvent hover = new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(hovemode)), new ComponentBuilder(Utils.color(hovetext)).create());
					tc.setHoverEvent(hover);
					tc.setText(text);
					j.spigot().sendMessage(tc);
				}	
			}
			if(config.getString(type).equals("Click")) {
				e.setQuitMessage(null);
				TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);
			}
			if(config.getString(type).equals("None")){
				e.setQuitMessage(null);
			}
			if(config.getString(type).equals("Group")) {
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					String quittext = config.getString("Groups."+key+".Quit.Text");
					String quitperm = config.getString("Groups."+key+".Quit.Permission");
					
					if(j.hasPermission(quitperm)){}
					e.setQuitMessage(null);
					quittext = getAllVars(j, quittext);
					quittext = replacePlaceholderAPI(j, quittext);
					
					Utils.sendColorMessage(j, quittext);
				}
			}
		}			
	}
	
	public  String replacePlaceholderAPI(Player p, String message){
	    String holders = message;
	    if ((plugin.placeholders) && (PlaceholderAPI.containsPlaceholders(holders))) {
	      holders = PlaceholderAPI.setPlaceholders(p, holders);
	    }
	    return holders;
	}
	
	public String getAllVars(Player j, String msg) {	
		int playersOnline = 0;
	    try
	    {
	      if (Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).getReturnType() == Collection.class) {
	        playersOnline = ((Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).size();
	      } else {
	        playersOnline = ((Player[])Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).length;
	      }
	    }
	    catch (NoSuchMethodException ex) {}catch (InvocationTargetException ex) {}catch (IllegalAccessException ex) {}		
		try {
			msg = msg.replace("<Name>", j.getName());
			msg = msg.replace("<DisplayName>", j.getDisplayName());
			msg = msg.replace("<MaxPlayer>", "" +plugin.getServer().getMaxPlayers());
			msg = msg.replace("<Online>", "" + playersOnline);
			//msg = msg.replace("<Server>", plugin.getServer().getServerName());
			msg = msg.replace("<Version>",  plugin.getServer().getBukkitVersion());
			msg = msg.replace("<World>", j.getWorld().getName());
			
			msg = msg.replace("<name>", j.getName());
			msg = msg.replace("<displayname>", j.getDisplayName());
			msg = msg.replace("<maxPlayer>", "" +plugin.getServer().getMaxPlayers());
			msg = msg.replace("<online>", "" + playersOnline);
			//msg = msg.replace("<server>", plugin.getServer().getServerName());
			msg = msg.replace("<version>",  plugin.getServer().getBukkitVersion());
			msg = msg.replace("<world>", j.getWorld().getName());
		}catch(Exception  e) {}
		return msg;
	}

	public String getActionHoverType(String arg) {
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
	
	public String getActionClickType(String arg) {
		String temp = arg;
		if(temp.equalsIgnoreCase("url")) {
			return "OPER_URL";
 		}
		if(temp.equalsIgnoreCase("cmd")) {
			return "RUN_COMMAND";
		}
		return null;
	}
}
