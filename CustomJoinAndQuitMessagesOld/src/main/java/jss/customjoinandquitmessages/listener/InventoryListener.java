package jss.customjoinandquitmessages.listener;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.manager.DisplayManager;
import jss.customjoinandquitmessages.manager.InventoryView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InventoryListener implements Listener {

    private final CustomJoinAndQuitMessages plugin;

    public InventoryListener(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void onInventoryClick(@NotNull InventoryClickEvent e) {
        Player j = (Player) e.getWhoClicked();
        InventoryView view = plugin.getInventoryView(j);

        if (view == null) return;
        if (!view.getInventoryName().contains("displaygui")) return;
        if (e.isShiftClick()) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().name().contains("AIR")) {
            e.setCancelled(true);
            return;
        }

        e.getSlotType();
        if (!Objects.equals(e.getClickedInventory(), j.getOpenInventory().getTopInventory())) return;

        int slot = e.getSlot();
        e.setCancelled(true);

        DisplayManager displayManager = new DisplayManager(j);

        switch (slot){
            case 11:
                displayManager.showJoinMessage();
                break;

            case 13:
                displayManager.showQuitMessage();
                break;
            case 15:
                displayManager.showWelcomeMessage();
                break;
            case 29:
                displayManager.showTitleMessage();
                break;
            case 31:
                displayManager.showActionbar();
                break;
            case 33:
                displayManager.showFirstJoinMessage();
                break;
            case 39:
                displayManager.showJoinSound();
                break;
            case 41:
                displayManager.showQuitSound();
                break;
        }

    }

}
