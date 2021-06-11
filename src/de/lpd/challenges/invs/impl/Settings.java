package de.lpd.challenges.invs.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Settings extends Inventory {

	public Settings(ChallengesMainClass plugin) {
		super(plugin, 5 * 9, true, "Settings", "Menu");
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e) {
		/*if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(getItemBack())) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("menu").getInventory(0));
		}*/
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
