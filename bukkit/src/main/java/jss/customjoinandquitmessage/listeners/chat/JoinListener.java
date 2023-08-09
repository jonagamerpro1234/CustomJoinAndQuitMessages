package jss.customjoinandquitmessage.listeners.chat;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class JoinListener implements Listener {

    private static final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(@NotNull PlayerJoinEvent e){
        Player p = e.getPlayer();

        boolean isNormal = Settings.chatformat_type.equalsIgnoreCase("normal");
        boolean isGroup = Settings.chatformat_type.equalsIgnoreCase("group");

        if(isNormal){

            if(Settings.chatformat_join_enabled){

                String mainMessage;

                if (Settings.chatformat_firstJoin_enabled){
                    if(!p.hasPlayedBefore()){
                        mainMessage = Settings.chatformat_firstJoin_message;
                    } else {
                        mainMessage = Settings.chatformat_join_message;
                    }
                }



            }

        }else if (isGroup) {

        }

    }

    @EventHandler
    private void onUpdate(PlayerJoinEvent e){

    }

}
