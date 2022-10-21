package jss.customjoinandquitmessages.manager;

import org.bukkit.entity.Player;

public class InventoryView {

    private Player player;
    private String inventoryName;

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
