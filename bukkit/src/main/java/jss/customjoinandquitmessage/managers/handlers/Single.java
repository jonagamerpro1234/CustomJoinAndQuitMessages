package jss.customjoinandquitmessage.managers.handlers;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.AbstractJoinQuitMessageHandler;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.entity.Player;

public class Single extends AbstractJoinQuitMessageHandler {

    public void handlerJoinAndQuitMessages(Player p, boolean isJoin) {
        if(isJoin){
            if(Settings.chatformat_join_enabled){

                String mainMessage = "";

                if (Settings.chatformat_firstJoin_enabled){
                    if(!p.hasPlayedBefore()){
                        mainMessage = Settings.chatformat_firstJoin_message;
                    } else {
                        mainMessage = Settings.chatformat_join_message;
                    }
                }

                MessageUtils.sendColorMessage(p, mainMessage);

            }
        }else{
            MessageUtils.sendColorMessage(p, Settings.chatformat_quit_message);
        }
    }

    public void welcome(Player p) {
        Settings.welcome_message.forEach( s -> MessageUtils.sendColorMessage(p, s));
    }

    public void handlerJoinAndQuitTitle(Player p, boolean isJoin) {

    }

    public void handlerJoinAndQuitActionbar(Player p, boolean isJoin) {

    }

    public void handlerJoinAndQuitSound(Player p, boolean isJoin) {

    }

}
