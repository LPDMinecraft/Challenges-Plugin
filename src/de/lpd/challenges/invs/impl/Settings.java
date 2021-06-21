package de.lpd.challenges.invs.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;

public class Settings extends Inventory {

	public Settings(ChallengesMainClass plugin) {
		super(plugin);
	}
	
	public static String TITLE = "§6Settings §aMenü",
			             ITEM_BACK = "§cBack";

	@Override
	public org.bukkit.inventory.Inventory createdInv() {
		org.bukkit.inventory.Inventory inv = Bukkit.getServer().createInventory(null, 9*5, TITLE);
		inv = placeHolder(inv);
		inv.setItem((9*5) - 1, new ItemBuilder(Material.BARRIER).setDisplayName(ITEM_BACK).build());
		
		return inv;
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(TITLE)) {
			if(e.getWhoClicked() instanceof Player) {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().getItemMeta() != null) {
						if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							Player p = (Player) e.getWhoClicked();
							e.setCancelled(true);
				            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BACK)) {
				            	p.closeInventory();
								p.openInventory(ChallengesMainClass.getInvManager().getInventory(0));
							}
						}
					}
				}
			}
		}
	}

}
