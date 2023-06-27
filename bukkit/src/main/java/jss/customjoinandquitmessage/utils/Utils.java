package jss.customjoinandquitmessage.utils;

import jss.customjoinandquitmessage.libs.iridium.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static @NotNull String colorized(String text){
        return IridiumColorAPI.process(text);
    }

    @Contract(pure = true)
    public static @NotNull String unColorized(String text){
        return IridiumColorAPI.stripColorFormatting(text);
    }

    public static  void sendColorMessage(@NotNull CommandSender sender, String text){
        sender.sendMessage(colorized(text));
    }

    public static void sendColorMessage(@NotNull Player player, String text){
        player.sendMessage(colorized(onPlaceholderAPI(player, text)));
    }

    public static String onPlaceholderAPI(Player player, String text){
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, text) : text;
    }

    public static @NotNull List<String> setLimitTab(@NotNull List<String> options, String lastArgs){
        List<String> returned = new ArrayList<>();

        options.forEach( s -> {
            if(s != null && s.toLowerCase().startsWith(lastArgs.toLowerCase())){
                returned.add(s);
            }
        });
        return returned;
    }

    public static boolean hasPerm(@NotNull CommandSender sender, String perm){
        if(perm == null) sendColorMessage(sender, "[Warning] permission could not be found or it is null, please check if it is not null permission could not be found");
        return sender.hasPermission("cjm." + perm);
    }

    public static void addDelayMessage(int delayTime){

    }
}
