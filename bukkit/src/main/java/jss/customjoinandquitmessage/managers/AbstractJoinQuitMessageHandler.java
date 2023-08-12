package jss.customjoinandquitmessage.managers;

import org.bukkit.entity.Player;

public abstract class AbstractJoinQuitMessageHandler {

    public abstract void handlerJoinAndQuitMessages(Player player, boolean isJoin);

    public abstract void welcome(Player player);

    public abstract void handlerJoinAndQuitTitle(Player player, boolean isJoin);

    public abstract void handlerJoinAndQuitActionbar(Player player, boolean isJoin);

    public abstract void handlerJoinAndQuitSound(Player player, boolean isJoin);

}
