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

public class Menu extends Inventory {

	private String ITEM_CHALLENGES = "§6Challenges",
			       ITEM_SETTINGS = "§6Einstellungen";

	public Menu(ChallengesMainClass plugin) {
		super(plugin, 5*9, false, "Menu");
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e) {
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_CHALLENGES)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("chmenu").getInventory(1));
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_SETTINGS)) {
			p.closeInventory();
			p.openInventory(ChallengesMainClass.getInvManager().invs.get("settings").getInventory(1));
		}
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page) {
		inv = placeHolder(inv);

		inv.setItem(13 + 9, new HeadBuilder("Cooler_LK").setDisplayName("§bDev §7| §6Cooler_LK").build());
		inv.setItem(11 + 9, new ItemBuilder(Material.REDSTONE).setDisplayName(ITEM_SETTINGS).build());
		inv.setItem(15 + 9, new ItemBuilder(Material.CLOCK).setDisplayName(ITEM_CHALLENGES).build());
		return getPage(ChallengesMainClass.getChMa().getAllItems(), inv, page);
	}
}
