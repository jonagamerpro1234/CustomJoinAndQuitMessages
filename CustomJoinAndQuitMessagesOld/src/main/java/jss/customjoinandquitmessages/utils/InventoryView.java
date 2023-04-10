package jss.customjoinandquitmessages.utils;

import org.bukkit.entity.Player;

public class InventoryView {

    private final Player player;
    private final String inventoryName;

    public InventoryView(Player player, String inventoryName) {
        this.player = player;
        this.inventoryName = inventoryName;
    }

    public Player getPlayer() {
        return player;
    }

    public String getInventoryName() {
        return inventoryName;
    }

}
