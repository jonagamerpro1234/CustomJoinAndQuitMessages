package jss.customjoinandquitmessage.utils;

import com.google.common.collect.ImmutableMap;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    private static final MiniMessage miniMessage = MiniMessage.builder().build();

    // Map to convert color codes to tag names
    private static final ImmutableMap<String, String> colorMap = ImmutableMap.<String, String>builder()
            .put("&1", "dark_blue")
            .put("&2", "dark_green")
            .put("&3", "dark_aqua")
            .put("&4", "dark_red")
            .put("&5", "dark_purple")
            .put("&6", "gold")
            .put("&7", "gray")
            .put("&8", "dark_gray")
            .put("&9", "blue")
            .put("&0", "black")
            .put("&a", "green")
            .put("&b", "aqua")
            .put("&c", "red")
            .put("&d", "light_purple")
            .put("&e", "yellow")
            .put("&f", "white")
            .build();

    // Map to convert special color codes to tag names
    private static final ImmutableMap<String, String> specialColorMap = ImmutableMap.<String, String>builder()
            .put("&l", "bold")
            .put("&m", "strikethrough")
            .put("&n", "underline")
            .put("&o", "italic")
            .put("&k", "obfuscated")
            .put("&r", "reset")
            .build();

    // Replace color codes in a message with tag names
    public static @NotNull String replaceColorCodes(String message) {
        Pattern pattern = Pattern.compile("&[0-9a-fA-FlLnNoOkKrR]");
        Matcher matcher = pattern.matcher(message);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String colorCode = matcher.group();
            String tagName = convertColorToTag(colorCode);
            matcher.appendReplacement(sb, "<" + tagName + ">");
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    // Convert a color code to a tag name
    private static String convertColorToTag(@NotNull String colorCode) {
        String tag = colorMap.getOrDefault(colorCode, specialColorMap.getOrDefault(colorCode, ""));
        assert tag != null;
        return tag.isEmpty() ? colorCode : tag;
    }

    // Colorize a text using MiniMessage
    public static @NotNull Component colorize(String text){
        return miniMessage.deserialize(replaceColorCodes(text));
    }

    // Send a colorized message to a CommandSender
    public static void sendColorMessage(@NotNull CommandSender sender, String message){
        plugin.adventure().sender(sender).sendMessage(colorize(addTags(message,sender)));
    }

    // Send a colorized message to a Player
    public static void sendColorMessage(@NotNull Player player, String message){
        plugin.adventure().player(player).sendMessage(colorize(addTags(message,player)));
    }

    public static void showTitle(Player player, String title, String subtitle, long fadeIn, long stay, long fadeOut){
        Title.Times titleTimes = Title.Times.times(Ticks.duration(fadeIn),Ticks.duration(stay),Ticks.duration(fadeOut));
        Title titleText = Title.title(colorize(addTags(title,player)),colorize(addTags(subtitle,player)), titleTimes);

        plugin.adventure().player(player).showTitle(titleText);
    }

    public static void showActionbar(Player player, String message){
        plugin.adventure().player(player).sendActionBar(colorize(addTags(message,player)));
    }

    //Test
    private static @NotNull TagResolver papiTag(final @NotNull Player player) {
        return TagResolver.resolver("papi", (argumentQueue, context) -> {

            // Get the string placeholder that they want to use.
            final String papiPlaceholder = argumentQueue.popOr("papi tag requires an argument").value();

            // Then get PAPI to parse the placeholder for the given player.
            final String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, '%' + papiPlaceholder + '%');

            // We need to turn this ugly legacy string into a nice component.
            final Component componentPlaceholder = LegacyComponentSerializer.legacySection().deserialize(parsedPlaceholder);

            // Finally, return the tag instance to insert the placeholder!
            return Tag.selfClosingInserting(componentPlaceholder);
        });
    }

    public static @NotNull String addTags(String message, @NotNull CommandSender sender){

        if(sender instanceof Player){
            Player player = (Player) sender;
            message = Utils.onPlaceholderAPI(player, message);
            message = message.replace("{player}", player.getName());
        }

        message = message.replace("{0}", " ");
        message = message.replace("{version}", plugin.version);
        message = message.replace("{spigot}","https://www.spigotmc.org/resources/custom-join-and-quit-message.57006/");
        message = message.replace("{github}", "https://github.com/jonagamerpro1234/CustomJoinAndQuitMessages/releases");
        message = message.replace("{modrith}", "https://modrinth.com/plugin/customjoinandquitmessages");

        //message = miniMessage.stripTags (message, papiTag(player));
        return message;
    }
}
