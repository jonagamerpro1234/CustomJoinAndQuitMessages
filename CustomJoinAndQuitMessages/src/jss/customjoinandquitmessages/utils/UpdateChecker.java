package jss.customjoinandquitmessages.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger.Level;
import jss.customjoinandquitmessages.utils.interfaces.UpdateHelper;

public class UpdateChecker implements UpdateHelper {

	private CustomJoinAndQuitMessages plugin;
    private Logger logger = new Logger(plugin);
    private int ID;

    public UpdateChecker(CustomJoinAndQuitMessages plugin, int ID) {
        this.plugin = plugin;
        this.ID = ID;
    }

    public void getUpdateVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.ID).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                logger.Log(Level.INFO, "Could not check for updates:&c " + e.getMessage());
            }
        });
    }
	
	/*private void sendMessage(CommandSender sender, String lang) {
		String temp = lang;
		
		if(temp.equalsIgnoreCase("es")) {
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &bVersion Actual &3[&a"+plugin.version+"&3] &9-->&b Version Nueva &3[&d"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &eHay una nueva version disponible, copia el enlace de abajo para dirigirte a la pag del plugin");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &ehttps://www.spigotmc.org/resources/custom-join-and-quit-message-1-7-x-1-16-x.57006/");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			return;
		}
		if(temp.equalsIgnoreCase("en")) {
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &bCurrent version &3[&a"+plugin.version+"&3] &9-->&b New version &3[&d"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &eThere is a new version available, copy the link below to go to the plugin page");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &ehttps://www.spigotmc.org/resources/custom-join-and-quit-message-1-7-x-1-16-x.57006/");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			return;
		}
	}
	
	private void sendMessage(Player player, String lang) {
		String temp = lang;
		
		if(temp.equalsIgnoreCase("es")) {
			
			TextComponent msg = new TextComponent();
			msg.setText(Utils.color(Utils.getPrefixPlayer() + " &eClick en este mensaje para copiar el enlace"));
			msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/custom-join-and-quit-message-1-7-x-1-16-x.57006/"));
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &bVersion Actual &3[&a"+plugin.version+"&3] &9-->&b Version Nueva &3[&d"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " ");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &eHay una nueva version disponible");
			player.spigot().sendMessage(msg);
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());	
			return;
		}		
		if(temp.equalsIgnoreCase("en")) {
			
			TextComponent msg = new TextComponent();
			msg.setText(Utils.color(Utils.getPrefixPlayer() + " &eClick on this message to copy the link"));
			msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/custom-join-and-quit-message-1-7-x-1-16-x.57006/"));
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &bCurrent version &3[&a"+plugin.version+"&3] &9-->&b New version &3[&d"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " ");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &eThere is a new version available");
			player.spigot().sendMessage(msg);
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			return;
		}
	}
	
	private void sendError(CommandSender sender, String lang) {
		String temp = lang;
		
		if(temp.equalsIgnoreCase("es")) {
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &bVersion Actual &3[&a"+plugin.version+"&3] &9-->&b Version Nueva &3[&c"+plugin.latestversion+"&3]");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &cNo se ha podido encontrar ninguna version nueva o ya tienes la version mas reciente");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &c!Verifica si estas conectado a internet!");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			return;
		}
		if(temp.equalsIgnoreCase("en")) {
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &bCurrent version &3[&a"+plugin.version+"&3] &9-->&b New version &3[&c"+plugin.latestversion+"&3]");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| ");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &cNo new version could be found or you already have the most recent version");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| &c!Check if you are connected to the internet!");
			Utils.sendColorMessage(sender, Utils.getPrefix() + " &5<| " + Utils.setLine());
			return;
		}
	}
	
	private void sendError(Player player, String lang) {
		String temp = lang;
		
		if(temp.equalsIgnoreCase("es")) {
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &bVersion Actual &3[&a"+plugin.version+"&3] &9-->&b Version Nueva &3[&c"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " ");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &cNo se ha podido encontrar ninguna version nueva o ya tienes la version mas reciente");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &c!Verifica si estas conectado a internet!");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			return;
		}
		if(temp.equalsIgnoreCase("en")) {
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &bCurrent version &3[&a"+plugin.version+"&3] &9-->&b New version &3[&c"+plugin.latestversion+"&3]" );
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " ");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &cNo new version could be found or you already have the most recent version");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + " &c!Check if you are connected to the internet!");
			Utils.sendColorMessage(player, Utils.getPrefixPlayer() + "&5 " + Utils.setLine());
			return;
		}
	}*/
}
