package jss.customjoinandquitmessages.events;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import jss.customjoinandquitmessages.ConfigFile;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;
import jss.customjoinandquitmessages.utils.nms.Actionbar;
import jss.customjoinandquitmessages.utils.nms.Title;
import net.md_5.bungee.api.ChatColor;
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
	public void onJoinMessages(PlayerJoinEvent e) {
		ConfigFile configFile = plugin.getConfigFile();
		FileConfiguration config = configFile.getConfig();
		Player j = e.getPlayer();
		
		String path = "Join.Enabled";
		String text = config.getString("Join.Text");
		String type = "Join.Type";
		String hovetext = config.getString("Join.Hover.Text");
		String hovecolor = config.getString("Join.Hover.Color");
		String hovemode = config.getString("Join.Hover.Mode");
		String clickaction = config.getString("Join.Click.Action");
		String clickmode = config.getString("Join.Click.Mode");
		
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Default")) {
				e.setJoinMessage(text);
			}
			if (config.getString(type).equals("Double")){
				e.setJoinMessage(null);
				//Test new methods
				/*TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(Utils.getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.of(hovecolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(Utils.getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);*/
				Utils.sendTextComponentDouble(j, text, hovetext, hovemode, clickmode, clickaction);
			}
			if (config.getString(type).equals("Hover")){
				if(config.getString("Join.HoverEvent.Legacy-Color").equals("true")) {
					e.setJoinMessage(null);
					Utils.sendTextComponentHover(j, hovemode, text, hovetext, hovecolor);					
				}else if(config.getString("Join.HoverEvent.Legacy-Color").equals("false")) {
					e.setJoinMessage(null);
					Utils.sendTextComponent116Hover(j, hovemode, text, hovetext);
				}
			}
			if(config.getString(type).equals("Click")) {
				e.setJoinMessage(null);
				Utils.sendTextComponentClick(j, clickmode, text, clickaction);
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
		
		subtitle = Utils.color(subtitle);
		
		if(config.getString(path).equals("true")) {
			Title.sendTitle(j, title, subtitle, fadeIn, stay, fadeOut);
		}
	}
	
	@EventHandler
	public void sendJoinActionBar(PlayerJoinEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "ActionBar.Enabled";
		String text = config.getString("ActionBar.Text");
		
		text = Utils.color(text);
		if(config.getString(path).equals("true")) {
			Actionbar actionbar = new Actionbar();
			actionbar.sendActionBar(j, text);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void sendJoinUpdate(PlayerJoinEvent e) {
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
		
		String path = "Config.Update.Notify";
		
		if(config.getString(path).equals("true")) {
			if(j.isOp() || j.hasPermission("Cjm.Update.Notify")) {
				new UpdateChecker(CustomJoinAndQuitMessages.getPlugin(), UpdateSettings.ID).getUpdateVersion(version ->{
                    TextComponent component = new TextComponent(Utils.color(Utils.getPrefixPlayer() + " &aThere is a new version available for download"));
                    component.setClickEvent(new ClickEvent(Action.OPEN_URL, UpdateSettings.URL_PlUGIN));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.color("&6Click on this message to copy the link")).create()));
                    j.spigot().sendMessage(component);
				});
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void QuitMessages(PlayerQuitEvent e) {
		FileConfiguration config = plugin.getConfig();
		Player j = e.getPlayer();
		
		String path = "Quit.Enabled";
		String text = config.getString("Quit.Text");
		String type = "Quit.Type";
		String hovetext = config.getString("Quit.Hover.Text");
		String hovecolor = config.getString("Quit.Hover.Color");
		String hovemode = config.getString("Quit.Hover.Mode");
		String clickaction = config.getString("Quit.Click.Action");
		String clickmode = config.getString("Quit.Click.Mode");
		
		text = Utils.color(text);
		
		if(config.getString(path).equals("true")) {
			if(config.getString(type).equals("Default")) {
				e.setQuitMessage(text);
			}
			if (config.getString(type).equals("Double")){
				e.setQuitMessage(null);
				TextComponent msg = new TextComponent();
				msg.setText(text);
				msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(Utils.getActionHoverType(hovemode)) , new ComponentBuilder(hovetext).color(ChatColor.valueOf(hovecolor)).create()));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(Utils.getActionClickType(clickmode)), clickaction));
				j.spigot().sendMessage(msg);
			}
			if (config.getString(type).equals("Hover")){
				if(config.getString("Quit.HoverEvent.Legacy-Color").equals("true")) {
					e.setQuitMessage(null);
					Utils.sendTextComponentHover(j, hovemode, text, hovetext, hovecolor);
				}else if(config.getString("Quit.HoverEvent.Legacy-Color").equals("false")) {
					e.setQuitMessage(null);
					Utils.sendTextComponent116Hover(j, hovemode, text, hovetext);
				}	
			}
			if(config.getString(type).equals("Click")) {
				e.setQuitMessage(null);
				Utils.sendTextComponentClick(j, clickmode, text, clickaction);
			}
			if(config.getString(type).equals("None")){
				e.setQuitMessage(null);
			}
			if(config.getString(type).equals("Group")) {
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
