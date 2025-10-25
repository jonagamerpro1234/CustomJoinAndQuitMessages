package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class DisplayCommand extends SubCommand {

    public String name() {
        return "display";
    }

    public String permission() {
        return "command.display";
    }

    public boolean requiresPermission() {
        return true;
    }

    public boolean onCommand(CommandSender sender, String @NotNull [] args) {

        if(args.length >= 2){

            if (args[1].equalsIgnoreCase("welcome")){
                if (Settings.welcome_enabled){
                    for(String s : Settings.welcome_message){
                        MessageUtils.sendColorMessage(sender,s);
                    }
                    //Settings.welcome_message.forEach( s -> MessageUtils.sendColorMessage(sender, s));
                }else {
                    MessageUtils.sendColorMessage(sender, "<red> This featured is disabled");
                }
                return true;
            }
            MessageUtils.sendColorMessage(sender, Settings.lang_unknownArguments);
            return true;
        }
        MessageUtils.sendColorMessage(sender, Settings.lang_usageDisplayCommand);
        return true;
    }

    public boolean allowConsole() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return Settings.lang_disableCommand;
    }
}
