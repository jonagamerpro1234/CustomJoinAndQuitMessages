package jss.customjoinandquitmessage.storage;

public class PlayerData {

    private final String group;
    private String hover;

    public PlayerData(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getHover() {
        return hover;
    }

    public void setHover(String hover) {
        this.hover = hover;
    }
}
