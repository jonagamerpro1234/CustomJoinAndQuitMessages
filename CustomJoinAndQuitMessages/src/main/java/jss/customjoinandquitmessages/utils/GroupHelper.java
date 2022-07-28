package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.hook.DiscordSRVHHook;
import jss.customjoinandquitmessages.hook.EssentialsXHook;

public class GroupHelper {

    private String group;
    private DiscordSRVHHook discordSRVHHook;
    private EssentialsXHook essentialsXHook;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setDiscord(DiscordSRVHHook discordSRVHHook) {
        this.discordSRVHHook = discordSRVHHook;
    }

    public void setEssentials(EssentialsXHook essentialsXHook) {
        this.essentialsXHook = essentialsXHook;
    }

    public void onJoin() {

    }

    public void onQuit() {

    }

}
