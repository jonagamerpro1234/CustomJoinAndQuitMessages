package jss.customjoinandquitmessages.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventsUtils;
import jss.customjoinandquitmessages.utils.Utils;

public class SoundsListener extends EventsUtils implements Listener{

	private CustomJoinAndQuitMessages plugin;
	private CommandSender c = Bukkit.getConsoleSender();
	
	public SoundsListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		getManger().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void sendSoundJoin(PlayerJoinEvent e) {	
		Player j = e.getPlayer();
		FileConfiguration config = plugin.getConfig();
		String path = "Sounds.Enabled";
		String slipsound = config.getString("Sounds.Join.Sound");
		String useperm = config.getString("Sounds.Join.Enabled-Permission");
		String perm = config.getString("Sounds.Join.Custom-Permission");
		String sap = config.getString("Sounds.Settings.Send-All-Sounds");
		String je = config.getString("Sounds.Join.Enabled");
		String[] split = slipsound.split(";");
		
		try {
			if(config.getString(path).equals("true")) {
				if(je.equals("true")) {
					if(useperm.equals("true")) {
						if(!(j.hasPermission(perm)) || !(j.isOp())) {
							Sound sound = Sound.valueOf(split[0]);
							int vol = Integer.valueOf(split[1]);
							float pitch = Float.valueOf(split[2]);
							if(sap.equals("true")) {
								for(Player p : Bukkit.getOnlinePlayers()) {
									Location l = p.getLocation();
									p.playSound(l, sound, vol, pitch);
								}
							}else if(sap.equals("false")) {
								Location l = j.getLocation();
								j.playSound(l, sound, vol, pitch);
							}
						}
					}else if(useperm.equals("false")){
						Sound sound = Sound.valueOf(split[0]);
						int vol = Integer.valueOf(split[1]);
						float pitch = Float.valueOf(split[2]);
						if(sap.equals("true")) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								Location l = p.getLocation();
								p.playSound(l, sound, vol, pitch);
							}
						}else if(sap.equals("false")) {
							Location l = j.getLocation();
							j.playSound(l, sound, vol, pitch);
						}
					}
				}
			}
			
		/*}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		}catch(IllegalArgumentException ex) {
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + plugin.Locale().Error_Sound);
		}catch(NullPointerException ex){
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			if(split[0] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 0 &d?");
			}
			if(split[1] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 1 &d?");
			}
			if(split[2] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 2 &d?");
			}
			if(split[0] == null || split[1] == null || split[2] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull all &d?");
			}
		}
	}
	
	@EventHandler
	public void sendSoundQuit(PlayerQuitEvent e) {	
		Player j = e.getPlayer();
		FileConfiguration config = plugin.getConfig();
		String path = "Sounds.Enabled";
		String slipsound = config.getString("Sounds.Quit.Sound");
		String useperm = config.getString("Sounds.Quit.Enabled-Permission");
		String perm = config.getString("Sounds.Quit.Custom-Permission");
		String sap = config.getString("Sounds.Settings.Send-All-Sounds");
		String qe = config.getString("Sounds.Quit.Enabled");
		String[] split = slipsound.split(";");
		
		try {
			if(config.getString(path).equals("true")) {
				if(qe.equals("true")) {
					if(useperm.equals("true")) {
						if(!(j.hasPermission(perm)) || !(j.isOp())) {
							Sound sound = Sound.valueOf(split[0]);
							int vol = Integer.valueOf(split[1]);
							float pitch = Float.valueOf(split[2]);
							if(sap.equals("true")) {
								for(Player p : Bukkit.getOnlinePlayers()) {
									Location l = p.getLocation();
									p.playSound(l, sound, vol, pitch);
								}
							}else if(sap.equals("false")) {
								Location l = j.getLocation();
								j.playSound(l, sound, vol, pitch);
							}
						}
					}else if(useperm.equals("false")){
						Sound sound = Sound.valueOf(split[0]);
						int vol = Integer.valueOf(split[1]);
						float pitch = Float.valueOf(split[2]);
						if(sap.equals("true")) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								Location l = p.getLocation();
								p.playSound(l, sound, vol, pitch);
							}
						}else if(sap.equals("false")) {
							Location l = j.getLocation();
							j.playSound(l, sound, vol, pitch);
						}
					}
				}
			}
		/*}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		
		}catch(IllegalArgumentException ex) {
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + plugin.Locale().Error_Sound);
		}catch(NullPointerException ex){
			Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &aNull &d?");
			if(split[0] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 0 &d?");
			}
			if(split[1] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 1 &d?");
			}
			if(split[2] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull 2 &d?");
			}
			if(split[0] == null || split[1] == null || split[2] == null) {
				Utils.sendColorMessage(c, Utils.getPrefixCJMConsole() + " " + "&cError: &b Sounds.Enabled &9== &eNull all &d?");
			}
		}
	}
}
