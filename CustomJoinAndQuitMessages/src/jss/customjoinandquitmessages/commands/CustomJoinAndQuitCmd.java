package jss.customjoinandquitmessages.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Utils;

public class CustomJoinAndQuitCmd implements CommandExecutor, TabCompleter{
	
	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
	public CustomJoinAndQuitCmd(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		plugin.getCommand("CustomJoinAndQuitMessages").setExecutor(this);
		plugin.getCommand("CustomJoinAndQuitMessages").setTabCompleter(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("help")) {
					List<String> list = plugin.Locale().help_1;
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5-=-=-=-=-=-=-=-=-=-=-=&6[&d"+plugin.name+"&6]&5=-=-=-=-=-=-=-=-=-=-=-");
					for(int i = 0; i < list.size(); i++) {
						String text = (String) list.get(i);
						Utils.sendColorMessage(eventUtils.getConsoleSender(), text);
					}
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				}else if(args[0].equalsIgnoreCase("reload")) {
					plugin.getPreConfigLoader().loadConfig();
					plugin.getPreConfigLoader().loadLangs();
					plugin.reloadConfig();
					Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + plugin.Locale().reload);
				}else if(args[0].equalsIgnoreCase("info")) {
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <||=-=-=-=-="+Utils.getPrefix()+"&5=-=-=-=-=-=-");
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <|| &c* &bName: &3"+plugin.name);
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <|| &c* &bAuthor: &3jonagamerpro1234");
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <|| &c* &bVersion: &a"+plugin.version);
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <|| &c* &bUpdate: &e" + plugin.getUpdateVersion());
					Utils.sendColorMessage(eventUtils.getConsoleSender(), "&5 <||=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				}else {
					Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + plugin.Locale().Error_Cmd);
				}
				return true;
			}
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + plugin.Locale().Help_cmd);
			return false;
		}
		Player j = (Player) sender;
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("help")) {
				if((j.isOp()) ||  (j.hasPermission("Cjm.Help"))) {
					List<String> list = plugin.Locale().help_1;
					Utils.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=&6[&d"+plugin.name+"&6]&5=-=-=-=-=-=-=-=-=-=-=-");
					for(int i = 0; i < list.size(); i++) {
						String text = (String) list.get(i);
						Utils.sendColorMessage(j, text);
					}
					Utils.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				}else {
					Utils.sendTextComponent116Hover(j, "TEXT", plugin.Locale().No_Permission, plugin.Locale().No_Permission_Label);
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
				if((j.isOp()) || (j.hasPermission("Cjm.Reload"))) {
					plugin.getPreConfigLoader().loadConfig();
					plugin.getPreConfigLoader().loadLangs();
					plugin.reloadConfig();
					Utils.sendColorMessage(j, Utils.getPrefixPlayer() + " " + plugin.Locale().reload);
				}else {
					Utils.sendTextComponent116Hover(j, "TEXT", plugin.Locale().No_Permission, plugin.Locale().No_Permission_Label);
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("info")) {
				Utils.sendColorMessage(j, "&5-=-=-=-=-=[&b"+plugin.name+"&5]=-=-=-=-=-=-");
				Utils.sendColorMessage(j, "&5> &3Name: &b"+plugin.name);
				Utils.sendColorMessage(j, "&5> &3Author: &6jonagamerpro1234");
				Utils.sendColorMessage(j, "&5> &3Version: &6"+plugin.version);
				Utils.sendColorMessage(j, "&5> &3Update: &a" + plugin.getUpdateVersion());
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
		List<String> options = new ArrayList<>();
		String lastArgs = args.length != 0 ? args[(args.length - 1)] : "";
		if(!(sender instanceof Player)) {
			switch (args.length) {
			case 0:
			case 1:
				options.add("help");
				options.add("reload");
				options.add("info");
				break;
			}
			return Utils.TabLimit(options, lastArgs);
		}

		Player j = (Player) sender;
		if((j.isOp()) || (j.hasPermission("Cjm.Tab"))) {
			switch (args.length) {
			case 0:
			case 1:
				options.add("help");
				options.add("reload");
				options.add("info");
				break;
			}
		}
		return Utils.TabLimit(options, lastArgs);
	}
}
