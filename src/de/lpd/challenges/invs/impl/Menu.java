package de.lpd.challenges.invs.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.HeadBuilder;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Menu extends Inventory {

	private String ITEM_CHALLENGES = "§6Challenges",
			       ITEM_SETTINGS = "§6Einstellungen",
	               ITEM_LANGUAGES = "§6Sprachen";

	public Menu(ChallengesMainClass plugin) {
		super(plugin, 5*9, false, "Menu", null, null);
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_CHALLENGES)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("chmenu").getInventory(1, p));
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_SETTINGS)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("settings").getInventory(1, p));
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_LANGUAGES)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("langs").getInventory(1, p));
		}
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(13 + 9, new HeadBuilder("Cooler_LK").setDisplayName("§bDev §7| §6Cooler_LK").build());
		inv.setItem(11 + 9, new ItemBuilder(Material.REDSTONE).setDisplayName(ITEM_SETTINGS).build());
		inv.setItem(15 + 9, new ItemBuilder(Material.CLOCK).setDisplayName(ITEM_CHALLENGES).build());
		inv.setItem(4 + (9 * 3), new ItemBuilder(Material.DIRT).setDisplayName(ITEM_LANGUAGES).build());

		return getPage(items, inv, page);
	}
}
