package jss.customjoinandquitmessage.commands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.commands.subcommands.*;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import jss.customjoinandquitmessage.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public void register(){
        PluginCommand pluginCommand = this.plugin.getCommand("customjoinandquitmessages");
        assert pluginCommand != null;
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        subCommands.addAll(Arrays.asList(new HelpCommand(), new ReloadCommand(), new InfoCommand(), new DisplayCommand(), new DevCommand()));
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        if(args.length >= 1){

            for(SubCommand s :  getSubCommands()){
                if (args[0].equalsIgnoreCase(s.name())){
                    if (s.isEnabled()){

                        if (!s.allowConsole() && !(sender instanceof Player)) {
                            MessageUtils.sendColorMessage(sender, Settings.lang_allowConsoleCommand);
                            return true;
                        }

                        if (!sender.isOp() || (s.requiresPermission() && !sender.hasPermission("cjm." + s.permission()))) {
                            MessageUtils.sendColorMessage(sender, Settings.lang_noPermission);
                            return true;
                        }

                        s.onCommand(sender, args);
                        return true;
                    } else {
                    MessageUtils.sendColorMessage(sender, s.disabledMessage());
                    }
                    return true;
                }
            }

            MessageUtils.sendColorMessage(sender, Settings.lang_unknownArguments);
            return true;
        }

        MessageUtils.sendColorMessage(sender, Settings.lang_usageMainCommand);
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String @NotNull [] args) {
        List<String> listOptions = new ArrayList<>();
        String lastArgs = args.length != 0 ? args[args.length - 1] : "";

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp() || !player.hasPermission("cjm.command.tabcomplete")) return new ArrayList<>();
        }

        switch (args.length){
            case 0:
            case 1:
                listOptions.add("display");
                listOptions.add("info");
                listOptions.add("help");
                listOptions.add("reload");
                break;
            case 2:
                if(args[0].equalsIgnoreCase("reload")){
                    listOptions.add("config");
                    listOptions.add("lang");
                    listOptions.add("handlers");
                }
                if(args[0].equalsIgnoreCase("display")){
                    listOptions.add("welcome");
                }
                break;
        }

        return Utils.setLimitTab(listOptions, lastArgs);
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
