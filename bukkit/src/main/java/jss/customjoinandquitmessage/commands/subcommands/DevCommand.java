package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.groupmanager.GroupHelper;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DevCommand extends SubCommand {

    public String name() {
        return "dev";
    }

    public String permission() {
        return "cjm.dev";
    }

    public boolean requiresPermission() {
        return false;
    }

    public boolean onCommand(CommandSender sender, String @NotNull [] args) {

        if(args.length >= 2){

            if(args[1].equalsIgnoreCase("group")){

                GroupHelper groupHelper = new GroupHelper();
                MessageUtils.sendColorMessage(sender, "<yellow>Group List is:");

                for(String key : groupHelper.availableGroups()){
                    MessageUtils.sendColorMessage(sender, " - <green>" + key);
                }

                return true;
            }
             if(args[1].equalsIgnoreCase("find")){
                 GroupHelper groupHelper = new GroupHelper();

                 String name = args[2];

                 MessageUtils.sendColorMessage(sender, "<green>Group: " + groupHelper.findGroup(name));

                 return true;
             }

            if (args[1].equalsIgnoreCase("file")){
                GroupHelper groupHelper = new GroupHelper();
                MessageUtils.sendColorMessage(sender, "<yellow>Group File:");

                for (String key : groupHelper.getDevKeys()){
                    MessageUtils.sendColorMessage(sender, " - <aqua>" + key);
                }

                return true;
            }

            MessageUtils.sendColorMessage(sender, Settings.lang_unknownArguments);
            return true;
        }

        GroupHelper groupHelper = new GroupHelper();

        for(String key : groupHelper.availableGroups()){
            MessageUtils.sendColorMessage(sender, "<yellow>Group List is: <green>" + key);
        }

        return false;
    }

    public boolean allowConsole() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return "This command is disabled!... -_-";
    }
}
