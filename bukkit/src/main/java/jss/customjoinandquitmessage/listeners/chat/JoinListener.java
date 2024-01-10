package jss.customjoinandquitmessage.listeners.chat;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.JoinQuitMessageHandlerFactory;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class JoinListener implements Listener {

    private final JoinQuitMessageHandlerFactory handlerFactory = JoinQuitMessageHandlerFactory.getInstance();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(@NotNull PlayerJoinEvent e){
        Player p = e.getPlayer();

        boolean isNormal = Settings.chatformat_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.chatformat_type.equalsIgnoreCase("group");

        handlerFactory.getActiveHandler().welcome(p);

        if(isNormal){
            e.setJoinMessage(null);
            handlerFactory.getActiveHandler().handlerJoinAndQuitMessages(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitTitle(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitActionbar(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitSound(p,true);
        }else if (isGroup) {
            e.setJoinMessage(null);
            handlerFactory.getActiveHandler().handlerJoinAndQuitMessages(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitTitle(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitActionbar(p,true);
            handlerFactory.getActiveHandler().handlerJoinAndQuitSound(p,true);
        }

    }

    @EventHandler
    private void onUpdate(PlayerJoinEvent e){

    }

}
