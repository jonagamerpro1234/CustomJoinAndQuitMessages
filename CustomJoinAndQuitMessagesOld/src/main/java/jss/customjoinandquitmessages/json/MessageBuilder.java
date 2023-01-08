package jss.customjoinandquitmessages.json;

import jss.customjoinandquitmessages.utils.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageBuilder {

    private final Player player;
    private final String text;
    private BaseComponent[] hoverText;
    private String suggestCommand;
    private String executeCommand;
    private String openUrl;

    public MessageBuilder(Player player, String text) {
        this.player = player;
        this.text = text;
        this.hoverText = null;
    }

    public Player getPlayer() {
        return player;
    }

    public String getText() {
        return text;
    }

    public MessageBuilder setHover(@NotNull List<String> hover) {
        this.hoverText = new BaseComponent[hover.size()];
        for (int i = 0; i < hover.size(); i++) {
            TextComponent component = new TextComponent();
            if (i == hover.size() - 1) {
                component.setText(Util.color(Util.getVar(this.player, hover.get(i))));
            } else {
                component.setText(Util.color(Util.getVar(this.player, hover.get(i)) + "\n"));
            }
            this.hoverText[i] = component;
        }
        return this;
    }

    public MessageBuilder setSuggestCommand(String suggestCommand) {
        this.suggestCommand = suggestCommand;
        return this;
    }

    public MessageBuilder setExecuteCommand(String executeCommand) {
        this.executeCommand = executeCommand;
        return this;
    }

    public MessageBuilder setOpenURL(String url) {
        this.openUrl = url;
        return this;
    }

    public void send() {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(this.text));
        if (this.hoverText != null) {
            //noinspection deprecation
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        }
        if (this.executeCommand != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.executeCommand));
        }
        if (this.suggestCommand != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
        }
        if (this.openUrl != null) {
            component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
        }
        player.spigot().sendMessage(component);
    }

    public void sendToAll() {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(this.text));
        if (this.hoverText != null) {
            //noinspection deprecation
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.hoverText));
        }
        if (this.executeCommand != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.executeCommand));
        }
        if (this.suggestCommand != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestCommand));
        }
        if (this.openUrl != null) {
            component.setClickEvent(new ClickEvent(Action.OPEN_URL, this.openUrl));
        }
        Util.sendAllPlayerBaseComponent(component);
    }
}
