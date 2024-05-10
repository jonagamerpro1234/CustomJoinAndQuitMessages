package jss.customjoinandquitmessage.managers.handlers;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.AbstractJoinQuitMessageHandler;
import org.bukkit.entity.Player;

public class Group extends AbstractJoinQuitMessageHandler {

    public void handlerJoinAndQuitMessages(Player p, boolean isJoin) {
        if (isJoin) {

        } else {

        }
    }

    public void welcome(Player p) {
        if (!Settings.welcome_UseWelcomePerGroup) return;


    }

    public void handlerJoinAndQuitTitle(Player p, boolean isJoin) {
        if (isJoin) {

        } else {

        }
    }

    public void handlerJoinAndQuitActionbar(Player p, boolean isJoin) {
        if (isJoin) {

        } else {

        }
    }

    public void handlerJoinAndQuitSound(Player p, boolean isJoin) {
        if (isJoin) {

        } else {

        }
    }

}
