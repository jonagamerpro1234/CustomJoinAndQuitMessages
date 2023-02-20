package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.libs.iridiumcolorapi.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class Util {
    private final static String prefix = getPrefix(true);
    private static final String PERMISSION_PREFIX = "cjm.";

    public static @NotNull String setLine(String color) {
        return color(color + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    public static @NotNull String color(String text) {
        return IridiumColorAPI.process(text);
    }

    public static void sendColorConsoleMessage(@NotNull CommandSender sender, String text) {
        sender.sendMessage(color(text));
    }

    public static String colorless(String text) {
        return ChatColor.stripColor(text);
    }

    public static void sendColorMessage(@NotNull Player player, String text) {
        player.sendMessage(color(text));
    }

    public static void sendColorMessage(@NotNull CommandSender sender, String text) {
        sender.sendMessage(color(text));
    }

    @SuppressWarnings("SameParameterValue")
    private static void sendEnable(String prefix, String message) {
        sendEnable(prefix + message);
    }

    private static void sendEnable(String message) {
        CommandSender c = Bukkit.getConsoleSender();
        sendColorMessage(c, message);
    }

    @Contract(pure = true)
    public static @NotNull String getPrefix(boolean ignore) {
        String prefix;
        if(ignore){
            prefix = "&e[&dCustomJoinAndQuitMessages&e] &7";
        }else{
            prefix = Settings.messages_prefix + " &7";
        }
        return prefix;
    }

    public static @NotNull List<String> setTabLimit(final @NotNull List<String> options, final String lastArgs) {
        final List<String> returned = new ArrayList<>();
        for (String s : options) {
            if (s == null) {
                continue;
            }
            if (s.toLowerCase().startsWith(lastArgs.toLowerCase())) {
                returned.add(s);
            }
        }
        return returned;
    }

    public static void sendTextComponent116Hover(@NotNull Player j, String action, String message, String subMessage) {
        TextComponent msg = new TextComponent(color(message));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)), new ComponentBuilder(color(subMessage)).create()));
        j.spigot().sendMessage(msg);
    }

    public static void sendAllPlayerBaseComponent(BaseComponent component) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(component);
        }
    }

    public static @Nullable String getActionHoverType(@NotNull String arg) {
        if (arg.equalsIgnoreCase("text")) {
            return "SHOW_TEXT";
        }
        if (arg.equalsIgnoreCase("item")) {
            return "SHOW_ITEM";
        }
        if (arg.equalsIgnoreCase("entity")) {
            return "SHOW_ENTITY";
        }
        return null;
    }

    public static @NotNull String getVar(@NotNull Player player, String text) {
        text = text.replace("<name>", player.getName());
        text = text.replace("<displayname>", player.getDisplayName());
        text = text.replace("<0>", " ");
        text = onPlaceholderAPI(player, text);
        return text;
    }

    public static String onPlaceholderAPI(Player player, String text){
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, text) : text;
    }

    public static void sendLoadTitle(String version) {
        sendEnable("&d   ______    _____   ____    __  ___");
        sendEnable("&d   / ____/   / /   | / __ \\  /  |/  /");
        sendEnable("&d  / /   __  / / /| |/ / / / / /|_/ /  &bBy jonagamerpro1234");
        sendEnable("&d / /___/ /_/ / ___ / /_/ / / /  / /   &bVersion &a" + version);
        sendEnable("&d \\____/\\____/_/  |_\\___\\_\\/_/  /_/    &aThanks for using CustomJoinAndQuitMessage &c<3");
    }

    public static void setEnabled(String version) {
        sendEnable(prefix, "&5 <||============================================----");
        sendEnable(prefix, "&5 <|| &c* &bThe plugin is &d[&aSuccessfully activated&d]");
        sendEnable(prefix, "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
        sendEnable(prefix, "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
        sendEnable(prefix, "&5 <|| &c* &bTested Versions &3|&a1.8.x &3- &a1.19.x&3| &eComing Soon -> &c1.20");
        sendEnable(prefix, "&5 <||============================================----");
    }

    public static void setDisabled(String version) {
        sendEnable(prefix, "&5 <||============================================----");
        sendEnable(prefix, "&5 <|| &c* &bThe plugin is &d[&cSuccessfully disabled&c]");
        sendEnable(prefix, "&5 <|| &c* &bVersion: &e[&a" + version + "&e]");
        sendEnable(prefix, "&5 <|| &c* &bBy: &e[&bjonagamerpro1234&e]");
        sendEnable(prefix, "&5 <|| &c* &bTested Versions &3|&a1.8.x &3- &a1.19.x&3| &eComing Soon -> &c1.20");
        sendEnable(prefix, "&5 <|| &a* &eThanks for using CustomJoinAndQuitMessage &c<3");
        sendEnable(prefix, "&5 <||============================================----");
    }

    @SuppressWarnings("unused")
    public static boolean setPerm(@NotNull Player player, String permName){
        return player.hasPermission(PERMISSION_PREFIX + permName);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFolder(@NotNull CustomJoinAndQuitMessages plugin, String folderName){
        File file = new File(plugin.getDataFolder(), folderName);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public static boolean isVanished(@NotNull Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

}
