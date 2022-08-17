package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.libs.iridiumcolorapi.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class Util {

    private final static String prefix = getPrefix();

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
    public static @NotNull String getPrefix() {
        return "&e[&dCustomJoinAndQuitMessages&e]&7 ";
    }

    @Contract(pure = true)
    public static @NotNull String getPrefixPlayer() {
        return "&6[&dCustomJoinAndQuitMessages&6]&7 ";
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

    public static @NotNull List<String> setTabLimit(final @NotNull List<String> list, final String inic) {
        final List<String> returned = new ArrayList<>();
        for (String s : list) {
            if (s == null) {
                continue;
            }
            if (s.toLowerCase().startsWith(inic.toLowerCase())) {
                returned.add(s);
            }
        }
        return returned;
    }

    @SuppressWarnings("unused")
    public static void sendTextComponentHover(@NotNull Player j, String action, String message, String submessage, String color) {
        TextComponent msg = new TextComponent(color(message));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)), new ComponentBuilder(submessage).color(ChatColor.of(color)).create()));
        j.spigot().sendMessage(msg);
    }

    public static void sendTextComponent116Hover(@NotNull Player j, String action, String message, String submessage) {
        TextComponent msg = new TextComponent(color(message));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(getActionHoverType(action)), new ComponentBuilder(color(submessage)).create()));
        j.spigot().sendMessage(msg);
    }

    @SuppressWarnings("unused")
    public static void sendTextComponentClick(@NotNull Player j, String action, String message, String arg0) {
        TextComponent msg = new TextComponent(color(message));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(getActionClickType(action)), arg0));
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

    public static @Nullable String getActionClickType(@NotNull String arg) {
        if (arg.equalsIgnoreCase("url")) {
            return "OPEN_URL";
        }
        if (arg.equalsIgnoreCase("cmd")) {
            return "RUN_COMMAND";
        }
        return null;
    }

    public static @NotNull String getVar(@NotNull Player player, String text) {
        text = text.replace("<name>", player.getName());
        text = text.replace("<displayname>", player.getDisplayName());
        text = text.replaceAll("<world>", player.getWorld().getName());
        text = text.replace("<0>", " ");
        text = placeholderReplace(text, player);
        text = getOnlinePlayers(text);
        return text;
    }

    private static String placeholderReplace(String text, Player player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    public static @NotNull String getOnlinePlayers(String text) {
        int playersOnline = 0;
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                playersOnline = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).size();
            } else {
                playersOnline = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0])).length;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        text = text.replace("<online>", "" + playersOnline);
        text = text.replace("<Online>", "" + playersOnline);
        return text;
    }

}
