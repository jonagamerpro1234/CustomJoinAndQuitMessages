package jss.customjoinandquitmessages.utils.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.events.custom.TitleEvent;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Utils;


public class Title extends Nms {

	private static CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.getPlugin();
	
	public static void sendTitle(Player player ,String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		TitleEvent titleEvent = new TitleEvent(player, title, subtitle, fadeIn, stay, fadeOut);			
		EventUtils eventUtils = new EventUtils(plugin);
		eventUtils.getEventManager().callEvent(titleEvent);
		if(titleEvent.isCancelled()) {
			return;
		}
		try {
			Object e;
			Object chatTitle;
			Object chatSubTitle;
			Constructor<?> subtitleConstructor;
			Object titlePacket;
			Object subtitlePacket;
			
			if(title != null) {
				title = Utils.color(title);
				title = Utils.hexcolor(title);
                e = getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatTitle = getNMSClass(IChatBaseComponent).getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = getNMSClass(PacketPlayOutTitle).getConstructor(new Class[]{getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0], getNMSClass(IChatBaseComponent), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle, fadeIn, stay, fadeOut});
                sendPacket(player, titlePacket);
                e = getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0].getField("TITLE").get((Object) null);
                chatTitle = getNMSClass(IChatBaseComponent).getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = getNMSClass(PacketPlayOutTitle).getConstructor(new Class[]{getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0], getNMSClass(IChatBaseComponent)});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
                sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                subtitle = Utils.color(subtitle);
                subtitle = Utils.hexcolor(subtitle);
                e = getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatSubTitle = getNMSClass(IChatBaseComponent).getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = getNMSClass(PacketPlayOutTitle).getConstructor(new Class[]{getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0], getNMSClass(IChatBaseComponent), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubTitle, fadeIn, stay, fadeOut});
                sendPacket(player, subtitlePacket);

                e = getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
                chatSubTitle = getNMSClass(IChatBaseComponent).getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
                subtitleConstructor = getNMSClass(PacketPlayOutTitle).getConstructor(new Class[]{getNMSClass(PacketPlayOutTitle).getDeclaredClasses()[0], getNMSClass(IChatBaseComponent), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubTitle, fadeIn, stay, fadeOut});
                sendPacket(player, subtitlePacket);
            }
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
    public static void clearTitle(Player player) {
        sendTitle(player, "", "", 0, 0, 0);
    }
	
}
