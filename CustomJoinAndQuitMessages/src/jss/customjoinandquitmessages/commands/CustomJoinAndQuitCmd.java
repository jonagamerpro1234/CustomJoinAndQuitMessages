package jss.customjoinandquitmessages.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class CustomJoinAndQuitCmd implements CommandExecutor, TabCompleter{
	
	private CustomJoinAndQuitMessages plugin;
	
	public CustomJoinAndQuitCmd(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("CustomJoinAndQuitMessages").setExecutor(this);
		plugin.getCommand("CustomJoinAndQuitMessages").setTabCompleter(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player j = (Player) sender;
		
		if(args.length >= 1) {
			
			if(args[0].equalsIgnoreCase("Reload")) {
				if(!(j.hasPermission("")) || !(j.isOp())) {
					return true;
				}
				
				return true;
			}
			if(args[0].equalsIgnoreCase("Info")) {
				if(!(j.hasPermission("")) || !(j.isOp())) {
					return true;
				}
				
				return true;
			}			
			return true;
		}
		
		return true;
	}
	
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		return null;
	}
}
