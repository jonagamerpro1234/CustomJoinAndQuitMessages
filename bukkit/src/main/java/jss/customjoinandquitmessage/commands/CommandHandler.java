package jss.customjoinandquitmessage.commands;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.commands.subcommands.HelpCommand;
import jss.customjoinandquitmessage.commands.subcommands.InfoCommand;
import jss.customjoinandquitmessage.commands.subcommands.ReloadCommand;
import jss.customjoinandquitmessage.commands.utils.SubCommand;
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
        PluginCommand pluginCommand = plugin.getCommand("CustomJoinAndQuitMessage");
        assert pluginCommand != null;
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        subCommands.addAll(Arrays.asList(new HelpCommand(), new ReloadCommand(), new InfoCommand()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        Player player = (Player) sender;

        if(args.length >= 1){

            // execute subcommand
            getSubCommands().forEach( s -> {
                if (args[0].equalsIgnoreCase(s.name())){
                    if (s.isEnabled()){
                        s.onCommand(sender, args);
                    }else{
                     Utils.sendColorMessage(player, s.disabledMessage());
                    }
                }
            });

        }else{
            // unknown argument
            Utils.sendColorMessage(player, "&cArgumento desconosido!");
            return true;
        }

        //usage main command
        Utils.sendColorMessage(player, "&7Usa &e/Cjm &9help &7para mas informacion");
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
