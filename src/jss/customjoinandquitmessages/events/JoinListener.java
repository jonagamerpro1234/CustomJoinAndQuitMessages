package jss.customjoinandquitmessages.events;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;

import jss.customjoinandquitmessages.ConfigFile;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class JoinListener implements Listener{

	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
	public JoinListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventUtils.getEventManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ConfigFile configFile = plugin.getConfigFile();
		FileConfiguration config = configFile.getConfig();
		Player j = e.getPlayer();
		
		String path = "Join.Enabled";
		String text = config.getString("Join.Text");
		String type = "Join.Type";
		List<String> hovertext = config.getStringList("Join.Hover.Text");
		String hovercolor = config.getString("Join.Hover.Color");
		String hovermode = config.getString("Join.Hover.Mode");
		String clickaction = config.getString("Join.Click.Action");
		String clickmode = config.getString("Join.Click.Mode");
		
		text = Utils.getVar(j,text);
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Custom")) {
				e.setJoinMessage(Utils.hexcolor(text));
			}else if (config.getString(type).equals("Double")){
				e.setJoinMessage(null);
				//Test new methods
				/*TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(Utils.getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.of(hovecolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(Utils.getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);*/
				for(int i = 0; i < hovertext.size(); i++) {
					String a = (String) hovertext.get(i);
					Utils.sendTextComponentDouble(j, text, a, hovermode, clickmode, clickaction);
					if(i == hovertext.size()) {
						break;
					}
				}
			}else if (config.getString(type).equals("Hover")){
				if(config.getString("Join.Hover.Legacy-Color").equals("true")) {
					e.setJoinMessage(null);			
					for(int i = 0; i < hovertext.size(); i++) {
						String a = (String) hovertext.get(i);
						Utils.sendTextComponentHover(j, hovermode, text, a, hovercolor);
						if(i == hovertext.size()) break;
					}
				}else if(config.getString("Join.Hover.Legacy-Color").equals("false")) {
					e.setJoinMessage(null);
					String a = "";
					for(int i = 0; i < hovertext.size(); i++) {
						a = (String) hovertext.get(i);
						
					}
					Utils.sendTextComponent116Hover(j, hovermode, text, a);
				}
			}else if(config.getString(type).equals("Click")) {
				e.setJoinMessage(null);
				Utils.sendTextComponentClick(j, clickmode, text, clickaction);
			}else if(config.getString(type).equals("None")){
				e.setJoinMessage(null);
			}else if(config.getString(type).equals("Group")) {
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					String jointext = config.getString("Groups."+key+".Join.Text");
					String joinperm = config.getString("Groups."+key+".Join.Permission");					
					if(j.hasPermission(joinperm)){}
					
					e.setJoinMessage(null);
					
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
	
	@EventHandler
	public void onTitle(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Title.Enabled";
		String title = config.getString("Title.Text.Title");
		String subtitle = config.getString("Title.Text.SubTitle");
		int fadeIn = config.getInt("Title.Settings.Time.FadeIn");
		int stay = config.getInt("Title.Settings.Time.Stay");
		int fadeOut = config.getInt("Title.Settings.Time.FadeOut");
		
		title = Utils.hexcolor(title);
		title = Utils.getVar(j, title);
		subtitle = Utils.hexcolor(subtitle);
		subtitle = Utils.getVar(j, subtitle);
		if(config.getString(path).equals("true")) {
			//Title.sendTitle(j, title, subtitle, fadeIn, stay, fadeOut);
			Titles.sendTitle(j, fadeIn, stay, fadeOut, title, subtitle);
		}
	}
	
	@EventHandler
	public void onActionBar(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		Player j = e.getPlayer();
		
		String path = "Actionbar.Enabled";
		String text = config.getString("Actionbar.Text");
		
		text = Utils.hexcolor(text);
		text = Utils.getVar(j, text);
		if(config.getString(path).equals("true")) {
			ActionBar.sendActionBar(j, text);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUpdate(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		Player j = e.getPlayer();
		/*UpdateChecker update = new UpdateChecker(plugin);
		if((j.isOp()) || (j.hasPermission("Cjm.Update.Notify"))) {
			if(config.getString("Config.Debug.Enabled").equals("true")) {
				TextComponent msg = new TextComponent();
				msg.setText(Utils.color("&c&l&n[!]&7 You do not have permission"));
				msg.setColor(ChatColor.YELLOW);
				if(Bukkit.getVersion().equals("1.16")) {
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT , new Text("ask an admin or owner for help for more information")));	
				}else {
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT , new ComponentBuilder("ask an admin or owner for help for more information").create()));
				}
				
				j.spigot().sendMessage(msg);
			}
			return;
		}
		update.Update(j);*/
		
		String path = "Config.Update";
		
		if(config.getString(path).equals("true")) {
			if(j.isOp() || j.hasPermission("Cjm.Update.Notify")) {
				new UpdateChecker(CustomJoinAndQuitMessages.getPlugin(), UpdateSettings.ID).getUpdateVersion(version ->{
					if(!CustomJoinAndQuitMessages.getPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
	                    TextComponent component = new TextComponent(Utils.color(Utils.getPrefixPlayer() + " &aThere is a new version available for download"));
	                    component.setClickEvent(new ClickEvent(Action.OPEN_URL, UpdateSettings.URL_PlUGIN[0]));
	                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.color("&6Click on this message to copy the link")).create()));
	                    j.spigot().sendMessage(component);
					}
				});
			}
		}
	}
	
	@EventHandler
	public void QuitMessages(PlayerQuitEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Quit.Enabled";
		String text = config.getString("Quit.Text");
		String type = "Quit.Type";
		List<String> hovertext = config.getStringList("Quit.Hover.Text");
		String hovercolor = config.getString("Quit.Hover.Color");
		String hovermode = config.getString("Quit.Hover.Mode");
		String clickaction = config.getString("Quit.Click.Action");
		String clickmode = config.getString("Quit.Click.Mode");
		
		text = Utils.getVar(j, text);
		text = Utils.hexcolor(text);
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Custom")) {
				e.setQuitMessage(Utils.hexcolor(text));
			}else if (config.getString(type).equals("Double")){
				e.setQuitMessage(null);
				/*TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(Utils.getActionHoverType(hovermode)) , new ComponentBuilder(a).color(ChatColor.valueOf(hovercolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(Utils.getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);*/
				for(int i = 0; i < hovertext.size(); i++) {
					String a = (String) hovertext.get(i);
					Utils.sendTextComponentDouble(j, text, a, hovermode, clickmode, clickaction);
					if(i == hovertext.size()) {
						break;
					}
				}
			}else if (config.getString(type).equals("Hover")){
				if(config.getString("Quit.HoverEvent.Legacy-Color").equals("true")) {
					e.setQuitMessage(null);
					for(int i = 0; i < hovertext.size(); i++) {
						String a = (String) hovertext.get(i);
						Utils.sendTextComponentHover(j, hovermode, text, a, hovercolor);
						if(i == hovertext.size()) {
							break;
						}
					}
				}else if(config.getString("Quit.HoverEvent.Legacy-Color").equals("false")) {
					e.setQuitMessage(null);
					for(int i = 0; i < hovertext.size(); i++) {
						String a = (String) hovertext.get(i);
						Utils.sendTextComponent116Hover(j, hovermode, text, a);
						if(i == hovertext.size()) {
							break;
						}
					}
				}	
			}else if(config.getString(type).equals("Click")) {
				e.setQuitMessage(null);
				Utils.sendTextComponentClick(j, clickmode, text, clickaction);
			}else if(config.getString(type).equals("None")){
				e.setQuitMessage(null);
			}else if(config.getString(type).equals("Group")) {
				for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
					
					String quittext = config.getString("Groups."+key+".Quit.Text");
					String quitperm = config.getString("Groups."+key+".Quit.Permission");
					
					if(j.hasPermission(quitperm)){}
					
					Utils.sendColorMessage(j, quittext);
				}
			}
		}			
	}
	


}
