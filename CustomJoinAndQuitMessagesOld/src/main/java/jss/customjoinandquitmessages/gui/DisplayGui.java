package jss.customjoinandquitmessages.gui;

import com.cryptomorin.xseries.XMaterial;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DisplayGui {

    private Inventory inv;
    private ItemStack item;
    private ItemMeta meta;
    private final Player player;
    private final CustomJoinAndQuitMessages plugin;

    public DisplayGui(Player player, CustomJoinAndQuitMessages plugin) {
        this.player = player;
        this.plugin = plugin;
        this.create();
    }

    public void create() {
        inv = Bukkit.createInventory(null, 54, Util.color("&6&lDisplay Option"));

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, setDecoration());
        }
        setItems();
    }

    public ItemStack setDecoration() {
        item = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
        assert item != null;
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(null);
        item.setItemMeta(meta);
        return item;
    }

    public void setItems() {

        item = XMaterial.RED_STAINED_GLASS.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&cGroup - (Coming Soon)"));
        item.setItemMeta(meta);
        inv.setItem(8, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aJoin Message"));
        item.setItemMeta(meta);
        inv.setItem(11, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aQuit Message"));
        item.setItemMeta(meta);
        inv.setItem(13, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aWelcome Message"));
        item.setItemMeta(meta);
        inv.setItem(15, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aTitle Message"));
        item.setItemMeta(meta);
        inv.setItem(29, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aActionBar Message"));
        item.setItemMeta(meta);
        inv.setItem(31, item);

        item = XMaterial.OAK_SIGN.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aFirst Join Message"));
        item.setItemMeta(meta);
        inv.setItem(33, item);

        item = XMaterial.NOTE_BLOCK.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aJoin Sound"));
        item.setItemMeta(meta);
        inv.setItem(39, item);

        item = XMaterial.NOTE_BLOCK.parseItem();
        meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&aQuit Sound"));
        item.setItemMeta(meta);
        inv.setItem(41, item);

    }

    public void open() {
        plugin.registerInventory(player, "displaygui");
        player.openInventory(inv);
    }
}
