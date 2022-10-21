package jss.customjoinandquitmessage.commands.subcommands;

import jss.customjoinandquitmessage.commands.utils.SubCommand;
import org.bukkit.command.CommandSender;

public class DisplayCommand extends SubCommand {

    public String name() {
        return "display";
    }


    public void onCommand(CommandSender sender, String[] args) {

    }

    public boolean isEnabled() {
        return false;
    }

    public String disabledMessage() {
        return "";
    }
}
