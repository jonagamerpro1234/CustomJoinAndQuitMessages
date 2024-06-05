package jss.customjoinandquitmessage.libs.luckperms;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;

public class LuckPermsHook {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    public boolean isLuckPerms(){
        return plugin.getManager().isPluginEnabled("LuckPerms");
    }
}
