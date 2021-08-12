package jss.customjoinandquitmessages.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cryptomorin.xseries.XSound;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;

import jss.customjoinandquitmessages.utils.Utils;

public class SoundsListener implements Listener{

	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
	public SoundsListener(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
		eventUtils.getEventManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {	
		Player j = e.getPlayer();
		FileConfiguration config = plugin.getConfigFile().getConfig();
		String path = "Sounds.Enabled";
		String slipsound = config.getString("Sounds.Join.Sound");
		String useperm = config.getString("Sounds.Join.Enabled-Permission");
		String perm = config.getString("Sounds.Join.Custom-Permission");
		String sap = config.getString("Sounds.Settings.Send-All-Sounds");
		String je = config.getString("Sounds.Join.Enabled");
		String[] split = slipsound.split(";");
		
		try {
			if(config.getString(path).equals("true")) {
				if(config.getString("Sounds.Type").equals("Normal")) {
					if(je.equals("true")) {
						if(useperm.equals("true")) {
							if(!(j.hasPermission(perm)) || !(j.isOp())) {
								Sound sound = XSound.valueOf(split[0]).parseSound();
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
							Sound sound = XSound.valueOf(split[0]).parseSound();
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
				}else if(config.getString("Sounds.Type").equals("Group")) {
					for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
						String gslipsound = config.getString("Groups."+key+".Join.Sound");
						String gperm = config.getString("Groups."+key+".Join.Permission");
						String gsap = config.getString("Groups."+key+".Join.Send-All-Sound");
						String gqe = config.getString("Groups."+key+".Join.Enabled-Sound");
						String[] gsplit = gslipsound.split(";");
						
						Sound sound = XSound.valueOf(split[0]).parseSound();
						int vol = Integer.valueOf(gsplit[1]);
						float pitch = Float.valueOf(gsplit[2]);
						
						if(gqe.equals("true")) {
							if(j.isOp() || j.hasPermission(gperm)) {
								if(gsap.equals("true")) {
									for(Player p : Bukkit.getOnlinePlayers()) {
										Location l = p.getLocation();
										p.playSound(l, sound, vol, pitch);
									}
								}else if(gsap.equals("false")) {
									Location l = j.getLocation();
									j.playSound(l, sound, vol, pitch);
								}
							}
						}						
					}
				}
				
			}
		}catch(IllegalArgumentException ex) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + plugin.Locale().Error_Sound);
			ex.printStackTrace();
		}catch(NullPointerException ex){
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			ex.printStackTrace();
			if(split[0].isEmpty()) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 0 &d?");
			}
			if(split[1].isEmpty()) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 1 &d?");
			}
			if(split[2].isEmpty()) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 2 &d?");
			}
			if(split[0].isEmpty() || split[1].isEmpty() || split[2].isEmpty()) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull all &d?");
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {	
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
				if(config.getString("Sounds.Type").equals("Normal")) {
					if(qe.equals("true")) {
						if(useperm.equals("true")) {
							if(!(j.hasPermission(perm)) || !(j.isOp())) {
								Sound sound = XSound.valueOf(split[0]).parseSound();
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
							Sound sound = XSound.valueOf(split[0]).parseSound();
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
				}else if(config.getString("Sounds.Type").equals("Group")) {
					for(String key : config.getConfigurationSection("Groups").getKeys(false)) {
						String gslipsound = config.getString("Groups."+key+".Quit.Sound");
						String gperm = config.getString("Groups."+key+".Quit.Permission");
						String gsap = config.getString("Groups."+key+".Quit.Send-All-Sound");
						String gqe = config.getString("Groups."+key+".Quit.Enabled-Sound");
						String[] gsplit = gslipsound.split(";");
						
						Sound sound = XSound.valueOf(split[0]).parseSound();
						int vol = Integer.valueOf(gsplit[1]);
						float pitch = Float.valueOf(gsplit[2]);
						
						if(gqe.equals("true")) {
							if(j.isOp() || j.hasPermission(gperm)) {
								if(gsap.equals("true")) {
									for(Player p : Bukkit.getOnlinePlayers()) {
										Location l = p.getLocation();
										p.playSound(l, sound, vol, pitch);
									}
								}else if(gsap.equals("false")) {
									Location l = j.getLocation();
									j.playSound(l, sound, vol, pitch);
								}
							}
						}						
					}
				}
			}
		}catch(IllegalArgumentException ex) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull &d?");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + plugin.Locale().Error_Sound);
		}catch(NullPointerException ex){
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &aNull &d?");
			if(split[0] == null) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 0 &d?");
			}
			if(split[1] == null) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 1 &d?");
			}
			if(split[2] == null) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull 2 &d?");
			}
			if(split[0] == null || split[1] == null || split[2] == null) {
				Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&cError: &b Sounds.Enabled &9== &eNull all &d?");
			}
		}
	}
}
