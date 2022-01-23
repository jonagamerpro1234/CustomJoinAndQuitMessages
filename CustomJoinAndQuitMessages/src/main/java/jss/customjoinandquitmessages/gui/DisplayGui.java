package jss.customjoinandquitmessages.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.cryptomorin.xseries.XMaterial;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Utils;

public class DisplayGui {
	
	private Inventory inv;
	private ItemStack item;
	private ItemMeta meta;
	private Player player;
	private CustomJoinAndQuitMessages plugin;
	
	public DisplayGui(Player player, CustomJoinAndQuitMessages plugin) {
		this.player = player;
		this.plugin = plugin;
		this.create();
	}
	
	public void create() {
		inv = Bukkit.createInventory(null, 27, Utils.color("&6&lDisplay Option"));
		
		for(int i = 0; i < 27; i++) {
			inv.setItem(i, setDecoration());
			if(i == 27) {
				break;
			}
		}
	}
	
	public ItemStack setDecoration() {
		item = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
		meta = item.getItemMeta();
		meta.setDisplayName(null);
		item.setItemMeta(meta);
		return item;
	}
	
	public void setItems() {
		
		
		
	}
	
	public void open() {
		player.openInventory(inv);
	}
}
