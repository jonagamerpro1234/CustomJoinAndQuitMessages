package jss.customjoinandquitmessage.listeners.chat;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

    }

    @EventHandler
    private void onUpdate(PlayerJoinEvent e){

    }

}
