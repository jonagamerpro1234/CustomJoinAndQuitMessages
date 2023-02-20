package jss.customjoinandquitmessage.commands.subcommands;

import jss.customjoinandquitmessage.commands.utils.SubCommand;
import org.bukkit.command.CommandSender;

public class InfoCommand extends SubCommand {

    public String name() {
        return "info";
    }

    @Override
    public String permission() {
        return null;
    }

    public void onCommand(CommandSender sender, String[] args) {

    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return null;
    }
}
