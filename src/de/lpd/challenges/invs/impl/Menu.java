package de.lpd.challenges.invs.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.HeadBuilder;
import de.lpd.challenges.utils.ItemBuilder;

public class Menu extends Inventory {
	
	public Menu(ChallengesMainClass plugin) {
		super(plugin);
	}

	public static String TITLE = "§6Menu",
			             ITEM_SETTINGS = "§cSettings",
			             ITEM_CHALLENGES = "§aChallenges";
	
	@Override
	public org.bukkit.inventory.Inventory createdInv() {
		org.bukkit.inventory.Inventory inv = Bukkit.getServer().createInventory(null, 9*5, TITLE);
		inv = placeHolder(inv);
		
		inv.setItem(13 + 9, new HeadBuilder("Cooler_LK").setDisplayName("§bDev §7| §6Cooler_LK").build());
		inv.setItem(11 + 9, new ItemBuilder(Material.REDSTONE).setDisplayName(ITEM_SETTINGS).build());
		inv.setItem(15 + 9, new ItemBuilder(Material.CLOCK).setDisplayName(ITEM_CHALLENGES).build());
		
		return inv;
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(TITLE)) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().getItemMeta() != null) {
						if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_CHALLENGES)) {
								p.closeInventory();
								p.openInventory(new ChallengesMenu().getInventory(1));
							} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_SETTINGS)) {
								p.closeInventory();
								p.openInventory(ChallengesMainClass.getInvManager().getInventory(1));
							}
						}
					}
				}
			}
		}
	}
	
}
