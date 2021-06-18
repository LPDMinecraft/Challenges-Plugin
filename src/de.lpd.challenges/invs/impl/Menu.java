package de.lpd.challenges.invs.impl;

import de.lpd.challenges.languages.LanguagesManager;
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
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_CHALLENGES) && item.getType() == Material.CLOCK) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("chmenu").getInventory(1, p));
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_SETTINGS) && item.getType() == Material.REDSTONE) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("settings").getInventory(1, p));
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_LANGUAGES) && item.getType() == Material.DIRT) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("langs").getInventory(1, p));
		}
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(13 + 9, new HeadBuilder("Cooler_LK").setDisplayName("§bDev §7| §6Cooler_LK").build());
		if(ChallengesMainClass.getPermsManager().hasPermissions(p, "ch.invs.challenges")) {
			inv.setItem(11 + 9, new ItemBuilder(Material.REDSTONE).setDisplayName(ITEM_SETTINGS).build());
		} else {
			inv.setItem(11 + 9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId())).build());
		}
		if(ChallengesMainClass.getPermsManager().hasPermissions(p, "ch.invs.challenges")) {
			inv.setItem(15 + 9, new ItemBuilder(Material.CLOCK).setDisplayName(ITEM_CHALLENGES).build());
		} else {
			inv.setItem(15 + 9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId())).build());
		}
		inv.setItem(4 + (9 * 3), new ItemBuilder(Material.DIRT).setDisplayName(ITEM_LANGUAGES).build());

		return getPage(items, inv, page);
	}
}
