package jss.customjoinandquitmessages.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.PluginConfig;
import jss.customjoinandquitmessages.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class CustomJoinAndQuitCmd implements CommandExecutor, TabCompleter{
	
	private CustomJoinAndQuitMessages plugin;
	private CommandSender c = Bukkit.getConsoleSender();
	
	public CustomJoinAndQuitCmd(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("CustomJoinAndQuitMessages").setExecutor(this);
		plugin.getCommand("CustomJoinAndQuitMessages").setTabCompleter(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Utils.sendColorMessage(c, Utils.getPrefix() + " " + plugin.Locale().Error_Console);
			return false;
		}
		Player j = (Player) sender;
		
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("help")) {
				if(!(j.hasPermission("Cjm.Commands.Help")) || !(j.isOp())) {
					TextComponent msg = new TextComponent();
					msg.setText(Utils.color(plugin.Locale().No_Permission));
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT , new ComponentBuilder(plugin.Locale().No_Permission_Label).color(ChatColor.YELLOW).create()));
					j.spigot().sendMessage(msg);
					return true;
				}
				List<String> list = plugin.Locale().help_1;
				Utils.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=&6[&d"+plugin.name+"&6]&5=-=-=-=-=-=-=-=-=-=-=-");
				for(int i = 0; i < list.size(); i++) {
					String text = (String) list.get(i);
					Utils.sendColorMessage(j, text);
				}
				Utils.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				if(!(j.hasPermission("Cjm.Commands.Reload")) || !(j.isOp())) {
					TextComponent msg = new TextComponent();
					msg.setText(Utils.color(plugin.Locale().No_Permission));
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT , new ComponentBuilder(plugin.Locale().No_Permission_Label).color(ChatColor.YELLOW).create()));
					j.spigot().sendMessage(msg);
					return true;
				}
				PluginConfig.loadConfig(plugin);
				plugin.reloadConfig();
				Utils.sendColorMessage(j, Utils.getPrefixPlayer() + " " + plugin.Locale().reload);
				return true;
			}
			if(args[0].equalsIgnoreCase("info")) {
				Utils.sendColorMessage(j, "&5-=-=-=-=-=[&b"+plugin.name+"&5]=-=-=-=-=-=-");
				Utils.sendColorMessage(j, "&5> &3Name: &b"+plugin.name);
				Utils.sendColorMessage(j, "&5> &3Author: &6jonagamerpro1234");
				Utils.sendColorMessage(j, "&5> &3Version: &6"+plugin.version);
				Utils.sendColorMessage(j, "&5> &3Update: &a" + plugin.latestversion);
				Utils.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				return true;
			}
			Utils.sendColorMessage(j, Utils.getPrefixPlayer() + " " + plugin.Locale().Error_Cmd);
			return true;
		}
		Utils.sendColorMessage(j, Utils.getPrefixPlayer() + " " + plugin.Locale().Help_cmd);
		return true;
	}
	
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return new ArrayList<>();
		}
		
		List<String> options = new ArrayList<>();
		String lastArgs = args.length !=0 ? args[(args.length - 1)] : "";
		Player j = (Player) sender;
		
		switch (args.length) {
		case 0:
		case 1:
			if(!(j.hasPermission("Cjm.Tab")) || !(j.isOp())){
				return null;
			}
			options.add("help");
			options.add("reload");
			options.add("info");
			break;
		}
		
		return Utils.TabLimit(options, lastArgs);
	}
}
