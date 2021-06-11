package de.lpd.challenges.invs.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class Settings extends Inventory {

	public Settings(ChallengesMainClass plugin) {
		super(plugin, 6*9, false, "Settings");
	}
	
	public static String TITLE = "§6Settings §aMen§",
			             ITEM_BACK = "§cBack";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e) {
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BACK)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("settings").getInventory(0));
		}
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page) {
		inv = placeHolder(inv);

		inv.setItem((9*5) - 1, new ItemBuilder(Material.BARRIER).setDisplayName(ITEM_BACK).build());

		return getPage(ChallengesMainClass.getChMa().getAllItems(), inv, page);
	}
}
