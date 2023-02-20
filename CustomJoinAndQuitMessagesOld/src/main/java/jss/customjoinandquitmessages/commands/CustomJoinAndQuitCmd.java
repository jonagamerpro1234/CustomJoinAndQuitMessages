package jss.customjoinandquitmessages.commands;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomJoinAndQuitCmd implements CommandExecutor, TabCompleter {

    private final CustomJoinAndQuitMessages plugin;

    @SuppressWarnings("ConstantConditions")
    public CustomJoinAndQuitCmd(@NotNull CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
        plugin.getCommand("CustomJoinAndQuitMessages").setExecutor(this);
        plugin.getCommand("CustomJoinAndQuitMessages").setTabCompleter(this);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("help")) {
                    List<String> list = plugin.Locale().help_1;
                    Util.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=-=-=&6[&d" + plugin.name + "&6]&5=-=-=-=-=-=-=-=-=-=-=-");
                    for (String text : list) {
                        Util.sendColorMessage(sender, text);
                    }
                    Util.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadAllFiles();
                    Util.sendColorMessage(sender, Util.getPrefix(false) + plugin.Locale().reload);
                } else if (args[0].equalsIgnoreCase("info")) {
                    Util.sendColorMessage(sender, "&5-=-=-=-=-=[&b" + plugin.name + "&5]=-=-=-=-=-=-");
                    Util.sendColorMessage(sender, "&6 ● &bAuthor: &3jonagamerpro1234");
                    Util.sendColorMessage(sender, "&6 ● &eYour Version: &7" + plugin.version);
                    Util.sendColorMessage(sender, "&6 ● &aLast version: &7" + plugin.getUpdateVersion());
                    Util.sendColorMessage(sender, "&6 ● &9Discord: &7https://discord.gg/c5GhQDQCK5");
                    Util.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                } else {
                    Util.sendColorMessage(sender, Util.getPrefix(true) + plugin.Locale().Error_Cmd);
                }
                return true;
            }

            Util.sendColorMessage(sender, Util.getPrefix(true) + plugin.Locale().Help_cmd);
            return false;
        }
        Player j = (Player) sender;

        if (args.length >= 1) {

            if (args[0].equalsIgnoreCase("help")) {
                if ((j.isOp()) || (j.hasPermission("cjm.command.help"))) {
                    Util.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=&6[&d" + plugin.name + "&6]&5=-=-=-=-=-=-=-=-=-=-=-");
                    for (String text : plugin.Locale().help_1) {
                        Util.sendColorMessage(j, text);
                    }
                    Util.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                } else {
                    Util.sendTextComponent116Hover(j, "TEXT", plugin.Locale().No_Permission, plugin.Locale().No_Permission_Label);
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                if ((j.isOp()) || (j.hasPermission(" cjm.command.reload"))) {
                    plugin.reloadAllFiles();
                    Util.sendColorMessage(j, Util.getPrefix(false) + plugin.Locale().reload);
                } else {
                    Util.sendTextComponent116Hover(j, "TEXT", plugin.Locale().No_Permission, plugin.Locale().No_Permission_Label);
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("info")) {
                Util.sendColorMessage(j, "&5-=-=-=-=-=[&b" + plugin.name + "&5]=-=-=-=-=-=-");
                Util.sendColorMessage(j, "&6 ● &bAuthor: &3jonagamerpro1234");
                Util.sendColorMessage(j, "&6 ● &eYour Version: &7" + plugin.version);
                Util.sendColorMessage(j, "&6 ● &aLast version: &7" + plugin.getUpdateVersion());
                Util.sendColorMessage(j, "&6 ● &9Discord: &7https://discord.gg/c5GhQDQCK5");
                Util.sendColorMessage(j, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                return true;
            }

            Util.sendColorMessage(j, Util.getPrefix(true) + plugin.Locale().Error_Cmd);
            return true;
        }

        Util.sendColorMessage(j, Util.getPrefix(true) + plugin.Locale().Help_cmd);
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        List<String> options = new ArrayList<>();
        String lastArgs = args.length != 0 ? args[(args.length - 1)] : "";
        if (!(sender instanceof Player)) {
            switch (args.length) {
                case 0:
                case 1:
                    options.add("help");
                    options.add("reload");
                    options.add("info");
                    break;
            }
            return Util.setTabLimit(options, lastArgs);
        }

        Player j = (Player) sender;
        if (!j.isOp() || !j.hasPermission("cjm.command.tabcomplete")) return new ArrayList<>();
        switch (args.length) {
            case 0:
            case 1:
                options.add("help");
                options.add("reload");
                options.add("info");
                break;
        }
        return Util.setTabLimit(options, lastArgs);
    }
}
