package jss.customjoinandquitmessage.utils;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.libs.iridium.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static @NotNull String getPrefix(boolean ignoreCustomPrefix) {
        String prefixTemp;
        if(ignoreCustomPrefix){
            prefixTemp = "&e[&dAdvancedChat&e]&7 ";
        }else{
            prefixTemp = Settings.lang_prefix + " ";
        }
        return prefixTemp;
    }

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

    public static  void sendUpdate(){
        String modrinthApiUrl = "https://api.modrinth.com/v2/version/1ywOweL3";

        try {
            URL url = new URL(modrinthApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();


                String jsonResponse = response.toString();
                System.out.println("Respuesta de Modrinth API: " + jsonResponse);
            } else {
                System.out.println("La solicitud a la api de Modrinth  falló. Código de error: " + responseCode);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addDelayMessage(int delayTime){

    }
}
