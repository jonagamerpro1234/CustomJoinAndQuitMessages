package jss.customjoinandquitmessages.manager;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.json.MessageBuilder;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("all")
public class DisplayManager {

    private final FileConfiguration config = CustomJoinAndQuitMessages.get().getConfigFile().getConfig();
    private final FileConfiguration groups = CustomJoinAndQuitMessages.get().getGroupsFile().getConfig();
    private final Player player;
    private String group;

    public DisplayManager(Player player) {
        this.player = player;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void showAllMessage() {
        this.showFirstJoinMessage();
        this.showJoinMessage();
        this.showQuitMessage();
        this.showTitleMessage();
        this.showActionbar();
        this.showWelcomeMessage();
    }

    public void showFirstJoinMessage() {
        if (!Settings.firstjoin)
            Logger.warning("&e[showFirstJoinMessage] &b-> &7This feature is disabled and you will not be able to see the preview");

        String text;
        boolean isHover;
        boolean isClick;
        List<String> Hover_Text;
        String isClick_Mode;
        String Action_Command;
        String Action_Url;
        String Action_Suggest;

        if (Settings.is_Group_Display) {
            text = groups.getString(group + ".FirstJoin.Text");
            isHover = groups.getString(group + ".HoverEvent.Enabled").equals("true");
            isClick = groups.getString(group + ".ClickEvent.Enabled").equals("true");
            Hover_Text = groups.getStringList(group + ".HoverEvent.Hover");
            isClick_Mode = groups.getString(group + ".ClickEvent.Mode");
            Action_Command = groups.getString(group + ".ClickEvent.Actions.Command");
            Action_Url = groups.getString(group + ".ClickEvent.Actions.Url");
            Action_Suggest = groups.getString(group + ".ClickEvent.Actions.Suggest-Command");
        } else {
            text = Settings.join_message_first;

            isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
            isClick = config.getString("Join.ClickEvent.Enabled").equals("true");
            Hover_Text = config.getStringList("Join.HoverEvent.Hover");
            isClick_Mode = config.getString("Join.ClickEvent.Mode");
            Action_Command = config.getString("Join.ClickEvent.Actions.Command");
            Action_Url = config.getString("Join.ClickEvent.Actions.Url");
            Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
        }

        MessageBuilder messageBuilder = new MessageBuilder(player, Util.color(Util.getVar(player, text)));

        if (isHover) {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setHover(Hover_Text).setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setHover(Hover_Text).setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setHover(Hover_Text).setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.setHover(Hover_Text).send();
            }
        } else {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.send();
            }
        }
    }

    public void showJoinMessage() {
        if (!Settings.join)
            Logger.warning("&e[showJoinMessage] &b-> &7This feature is disabled and you will not be able to see the preview");

        String text;
        boolean isNormalType;
        boolean isModifyType;
        boolean isHover;
        boolean isClick;
        List<String> Hover_Text;
        String isClick_Mode;
        String Action_Command;
        String Action_Url;
        String Action_Suggest;

        if (Settings.is_Group_Display) {
            text = groups.getString(group + ".Join-Text");

            isHover = groups.getString(group + ".HoverEvent.Enabled").equals("true");
            isClick = groups.getString(group + ".ClickEvent.Enabled").equals("true");

            Hover_Text = groups.getStringList(group + "HoverEvent.Hover");

            isClick_Mode = groups.getString(group + ".ClickEvent.Mode");
            Action_Command = groups.getString(group + ".ClickEvent.Actions.Command");
            Action_Url = groups.getString(group + ".ClickEvent.Actions.Url");
            Action_Suggest = groups.getString(group + ".ClickEvent.Actions.Suggest-Command");
        } else {
            text = Settings.join_message;

            isHover = config.getString("Join.HoverEvent.Enabled").equals("true");
            isClick = config.getString("Join.ClickEvent.Enabled").equals("true");

            Hover_Text = config.getStringList("Join.HoverEvent.Hover");

            isClick_Mode = config.getString("Join.ClickEvent.Mode");
            Action_Command = config.getString("Join.ClickEvent.Actions.Command");
            Action_Url = config.getString("Join.ClickEvent.Actions.Url");
            Action_Suggest = config.getString("Join.ClickEvent.Actions.Suggest-Command");
        }

        MessageBuilder messageBuilder = new MessageBuilder(player, Util.color(Util.getVar(player, text)));

        if (isHover) {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setHover(Hover_Text).setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setHover(Hover_Text).setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setHover(Hover_Text).setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.setHover(Hover_Text).send();
            }
        } else {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.send();
            }
        }
    }

    public void showQuitMessage() {
        if (!Settings.quit)
            Logger.warning("&e[showQuitMessage] &b-> &7This feature is disabled and you will not be able to see the preview");

        String text;
        boolean isNormalType;
        boolean isModifyType;
        boolean isHover;
        boolean isClick;
        List<String> Hover_Text;
        String isClick_Mode;
        String Action_Command;
        String Action_Url;
        String Action_Suggest;

        if (Settings.is_Group_Display) {
            text = groups.getString(group + ".Quit-Text");
            isNormalType = groups.getString(group + ".Type").equalsIgnoreCase("normal");
            isModifyType = groups.getString(group + ".Type").equalsIgnoreCase("modify");

            isHover = groups.getString(group + ".HoverEvent.Enabled").equals("true");
            isClick = groups.getString(group + ".ClickEvent.Enabled").equals("true");

            Hover_Text = groups.getStringList(group + "HoverEvent.Hover");

            isClick_Mode = groups.getString(group + ".ClickEvent.Mode");
            Action_Command = groups.getString(group + ".ClickEvent.Actions.Command");
            Action_Url = groups.getString(group + ".ClickEvent.Actions.Url");
            Action_Suggest = groups.getString(group + ".ClickEvent.Actions.Suggest-Command");
        } else {
            text = Settings.quit_message;

            isHover = config.getString("Quit.HoverEvent.Enabled").equals("true");
            isClick = config.getString("Quit.ClickEvent.Enabled").equals("true");

            Hover_Text = config.getStringList("Quit.HoverEvent.Hover");

            isClick_Mode = config.getString("Quit.ClickEvent.Mode");
            Action_Command = config.getString("Quit.ClickEvent.Actions.Command");
            Action_Url = config.getString("Quit.ClickEvent.Actions.Url");
            Action_Suggest = config.getString("Quit.ClickEvent.Actions.Suggest-Command");
        }

        MessageBuilder messageBuilder = new MessageBuilder(player, Util.color(Util.getVar(player, text)));

        if (isHover) {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setHover(Hover_Text).setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setHover(Hover_Text).setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setHover(Hover_Text).setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.setHover(Hover_Text).send();
            }
        } else {
            if (isClick) {
                if (isClick_Mode.equalsIgnoreCase("command")) {
                    messageBuilder.setExecuteCommand(Action_Command).send();
                } else if (isClick_Mode.equalsIgnoreCase("url")) {
                    messageBuilder.setOpenURL(Action_Url).send();
                } else if (isClick_Mode.equalsIgnoreCase("suggest")) {
                    messageBuilder.setSuggestCommand(Action_Suggest).send();
                }
            } else {
                messageBuilder.send();
            }
        }

    }

    public void showWelcomeMessage() {
        if (!Settings.welcome)
            Logger.warning("&e[showWelcomeMessage] &b-> &7This feature is disabled and you will not be able to see the preview");

        Settings.list_welcome.forEach((text) -> {
            Util.sendColorMessage(player, Util.getVar(player, text));
        });
    }

    public void showTitleMessage() {
        if (!Settings.join_title)
            Logger.warning("&e[showTitleMessage] &b-> &7This feature is disabled and you will not be able to see the preview");

        Titles.sendTitle(player, Settings.join_title_fadeIn, Settings.join_title_stay, Settings.join_title_fadeOut, Settings.join_message_title_title, Settings.join_message_title_subtitle);
    }

    public void showActionbar() {
        if (!Settings.join_actionbar)
            Logger.warning("&e[showActionbar] &b-> &7This feature is disabled and you will not be able to see the preview");
        ActionBar.sendActionBar(player, Settings.join_message_actionbar_text);
    }

    public void showJoinSound() {
        Util.sendColorMessage(player, "&cTest join sound");
    }

    public void showQuitSound() {
        Util.sendColorMessage(player, "&cTest quit sound");
    }

}
