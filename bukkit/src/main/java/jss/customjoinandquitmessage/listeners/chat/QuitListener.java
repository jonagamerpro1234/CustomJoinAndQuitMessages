package jss.customjoinandquitmessage.listeners.chat;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.JoinQuitMessageHandlerFactory;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class QuitListener implements Listener {

    private final JoinQuitMessageHandlerFactory handlerFactory = JoinQuitMessageHandlerFactory.getInstance();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onQuit(@NotNull PlayerQuitEvent e){
        Player p = e.getPlayer();
        boolean isNormal = Settings.chatformat_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.chatformat_type.equalsIgnoreCase("group");

        handlerFactory.getActiveHandler().welcome(p);

        if(isNormal){
            e.setQuitMessage(null);
            handlerFactory.getActiveHandler().handlerJoinAndQuitMessages(p,false);
            handlerFactory.getActiveHandler().handlerJoinAndQuitTitle(p,false);
            handlerFactory.getActiveHandler().handlerJoinAndQuitActionbar(p,false );
        }else if (isGroup) {
            e.setQuitMessage(null);
            MessageUtils.sendColorMessage(p,"Test group");
        }
    }
}
