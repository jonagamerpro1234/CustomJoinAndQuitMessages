package jss.customjoinandquitmessage.libs.luckperms;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;

public class LuckpermsHook {

    private CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    public boolean isLuckPerms(){
        return plugin.getManager().isPluginEnabled("LuckPerms");
    }
}
