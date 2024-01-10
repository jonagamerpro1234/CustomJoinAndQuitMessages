package jss.customjoinandquitmessage.managers.handlers;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.AbstractJoinQuitMessageHandler;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.Bukkit;
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
        if(Settings.welcome_enabled){
            if(Settings.welcome_UseWelcomePerGroup){
               return;
            }
            Settings.welcome_message.forEach( s -> MessageUtils.sendColorMessage(p, s));
        }
    }

    public void handlerJoinAndQuitTitle(Player p, boolean isJoin) {
        if(isJoin){
            if(Settings.chatformat_join_titleSettings_enabled){
                MessageUtils.showTitle(p,Settings.chatformat_join_titleSettings_title,Settings.chatformat_join_titleSettings_subtitle,
                        Settings.chatformat_join_titleSettings_fadeIn,Settings.chatformat_join_titleSettings_stay,Settings.chatformat_join_titleSettings_fadeOut);
            }
        }else{
            if(Settings.chatformat_quit_titleSettings_enabled){
                MessageUtils.showTitle(p,Settings.chatformat_quit_titleSettings_title,Settings.chatformat_quit_titleSettings_subtitle,
                        Settings.chatformat_quit_titleSettings_fadeIn,Settings.chatformat_quit_titleSettings_stay,Settings.chatformat_quit_titleSettings_fadeOut);
            }
        }
    }

    public void handlerJoinAndQuitActionbar(Player p, boolean isJoin) {
        if(isJoin){
            if(Settings.chatformat_join_actionbar_enabled){
                MessageUtils.showActionbar(p,Settings.chatformat_join_actionbar_message);
            }
        }else {
            if (Settings.chatformat_quit_actionbar_enabled){
                MessageUtils.showActionbar(p,Settings.chatformat_quit_actionbar_message);
            }
        }
    }

    public void handlerJoinAndQuitSound(Player p, boolean isJoin) {
        if(isJoin){
            if(Settings.chatformat_join_sound_enabled){
                if(Settings.chatformat_join_sound_sendToAll){
                    for(Player p2 : Bukkit.getOnlinePlayers()){
                        p2.playSound(p2.getLocation(), Settings.chatformat_join_sound_name, Settings.chatformat_join_sound_pitch, Settings.chatformat_join_sound_volume);
                    }
                }else{
                    p.playSound(p.getLocation(), Settings.chatformat_join_sound_name, Settings.chatformat_join_sound_pitch, Settings.chatformat_join_sound_volume);
                }
            }
        }else{
            if(Settings.chatformat_quit_sound_enabled){
                for(Player p2 : Bukkit.getOnlinePlayers()){
                    p2.playSound(p2.getLocation(), Settings.chatformat_quit_sound_name, Settings.chatformat_quit_sound_pitch, Settings.chatformat_quit_sound_volume);
                }
            }
        }
    }

}
