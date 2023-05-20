package jss.customjoinandquitmessage.commands;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.commands.subcommands.HelpCommand;
import jss.customjoinandquitmessage.commands.subcommands.InfoCommand;
import jss.customjoinandquitmessage.commands.subcommands.ReloadCommand;
import jss.customjoinandquitmessage.commands.utils.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler implements TabExecutor {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public void register(){
        PluginCommand pluginCommand = plugin.getCommand("customjoinandquitmessages");
        assert pluginCommand != null;
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        subCommands.addAll(Arrays.asList(new HelpCommand(), new ReloadCommand(), new InfoCommand()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        if(args.length >= 1){
            // execute subcommand
            for(SubCommand s : getSubCommands()){
                if (args[0].equalsIgnoreCase(s.name())){
                    if (s.isEnabled()){

                        if (!s.allowConsole() && !(sender instanceof Player)) {
                            Utils.sendColorMessage(sender, Settings.lang_allowConsoleCommand);
                            return true;
                        }

                        if (s.requiresPermission() && !sender.hasPermission(s.permission())) {
                            Utils.sendColorMessage(sender, Settings.lang_nopermission);
                            return true;
                        }

                        s.onCommand(sender, args);
                        return true;
                    } else {
                        Utils.sendColorMessage(sender, s.disabledMessage());
                    }
                    return true;
                }
            }

            Utils.sendColorMessage(sender, Settings.lang_unknownArguments);
            return true;
        }

        Utils.sendColorMessage(sender, Settings.lang_usageMainCommand);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String @NotNull [] args) {
        List<String> listOptions = new ArrayList<>();
        String lastArgs = args.length != 0 ? args[args.length - 1] : "";

        Player player = (Player) sender;

        if(!player.isOp() || !player.hasPermission("cjm.command.tabcomplete")) return new ArrayList<>();

        switch (args.length){
            case 0:
            case 1:
                listOptions.add("display");
                listOptions.add("info");
                listOptions.add("help");
                listOptions.add("reload");
                break;
        }

        return Utils.setLimitTab(listOptions, lastArgs);
    }


    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
