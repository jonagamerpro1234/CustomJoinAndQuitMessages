package jss.customjoinandquitmessage.managers;

import org.bukkit.entity.Player;

// This is an abstract class that defines the methods for handling join and quit messages.
public abstract class AbstractJoinQuitMessageHandler {

    // This method handles join and quit messages for a player.
    public abstract void handlerJoinAndQuitMessages(Player player, boolean isJoin);

    // This method sends a welcome message to a player.
    public abstract void welcome(Player player);

    // This method handles join and quit titles for a player.
    public abstract void handlerJoinAndQuitTitle(Player player, boolean isJoin);

    // This method handles join and quit actionbar messages for a player.
    public abstract void handlerJoinAndQuitActionbar(Player player, boolean isJoin);

    // This method handles join and quit sounds for a player.
    public abstract void handlerJoinAndQuitSound(Player player, boolean isJoin);
}
