package jss.customjoinandquitmessages.listener;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.manager.DisplayManager;
import jss.customjoinandquitmessages.manager.InventoryView;
import jss.customjoinandquitmessages.utils.EventUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private CustomJoinAndQuitMessages plugin;
    private EventUtils eventUtils = new EventUtils(plugin);

    public InventoryListener(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
        eventUtils.getEventManager().registerEvents(this, plugin);
    }

    public void onInventoryClick(InventoryClickEvent e) {
        Player j = (Player) e.getWhoClicked();
        InventoryView view = plugin.getInventoryView(j);

        if (view == null) return;
        if (!view.getInventoryName().contains("displaygui")) return;
        if (e.isShiftClick()) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().name().contains("AIR")) {
            e.setCancelled(true);
            return;
        }

        if (e.getSlotType() == null) {
            e.setCancelled(true);
            return;
        } else {
            if (!e.getClickedInventory().equals(j.getOpenInventory().getTopInventory())) return;

            int slot = e.getSlot();
            e.setCancelled(true);

            DisplayManager displayManager = new DisplayManager(j);

            if (slot == 11) {
                displayManager.showJoinMessage();
            }

            if (slot == 13) {
                displayManager.showQuitMessage();
            }

            if (slot == 15) {
                displayManager.showWelcomeMessage();
            }

            if (slot == 29) {
                displayManager.showTitleMessage();
            }

            if (slot == 31) {
                displayManager.showActionbar();
            }

            if (slot == 33) {
                displayManager.showFirstJoinMessage();
            }

            if (slot == 39) {
                displayManager.showJoinSound();
            }

            if (slot == 41) {
                displayManager.showQuitSound();
            }
        }
    }

}
