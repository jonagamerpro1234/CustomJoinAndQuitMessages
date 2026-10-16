package jss.customjoinandquitmessages.commands;

import jss.commandapi.BaseCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandHandler extends BaseCommand {

    public CommandHandler(){
        this.name("CustomJoinAndQuitMessages").addSubCommand();
    }

    @Override
    public boolean onCommandMain(@NotNull CommandSender sender, String[] args) {
        return super.onCommandMain(sender, args);
    }
}
