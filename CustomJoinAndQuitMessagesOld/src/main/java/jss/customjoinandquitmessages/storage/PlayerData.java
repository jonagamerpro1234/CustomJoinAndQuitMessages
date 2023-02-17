package jss.customjoinandquitmessages.storage;

import java.util.List;

public class PlayerData {

    private final String name;
    private String group;
    private List<String> joinMessages, quitMessages;

    public PlayerData(String name) {
        this.name = name;
        this.group = "default";
        this.joinMessages = null;
        this.quitMessages = null;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getJoinMessages() {
        return joinMessages;
    }

    public void setJoinMessages(List<String> joinMessages) {
        this.joinMessages = joinMessages;
    }

    public List<String> getQuitMessages() {
        return quitMessages;
    }

    public void setQuitMessages(List<String> quitMessages) {
        this.quitMessages = quitMessages;
    }
}
