package jss.customjoinandquitmessages.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class DisplayGui {
	
	private Inventory inv;
	private ItemStack item;
	private ItemMeta meta;
	private Player player;
	private CustomJoinAndQuitMessages plugin;
	
	public DisplayGui(Player player, CustomJoinAndQuitMessages plugin) {
		this.player = player;
		this.plugin = plugin;
	}

	public void setItems() {
		
	}
	
	public void open() {
		player.openInventory(inv);
	}
}
