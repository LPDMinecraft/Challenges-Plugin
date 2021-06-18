package de.lpd.challenges.invs.impl;

import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.settings.Setting;
import de.lpd.challenges.settings.SettingManager;
import de.lpd.challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Settings extends Inventory {

	public Settings(ChallengesMainClass plugin) {
		super(plugin, 5 * 9, true, "Settings", "menu", "Menu", new Config("invs/settings", "config.yml"));
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		if(item.getType() != Material.BLACK_STAINED_GLASS_PANE) {
			for(Setting c : ChallengesMainClass.getSettingManager().settings) {
				ItemStack i = c.getItem(p);
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase(i.getItemMeta().getDisplayName()) && item.getType() == i.getType()) {
					if(e.getClick() == ClickType.MIDDLE) {
						c.onMiddleClick(p);
					} else if(e.isLeftClick()) {
						c.onLeftClick(p);
					} else if(e.isRightClick()) {
						c.onRightClick(p);
					}
					Bukkit.getScheduler().runTaskLater(ChallengesMainClass.getPlugin(), new Runnable() {

						@Override
						public void run() {
							p.openInventory(ChallengesMainClass.getInvManager().invs.get("settings").getInventory(1, p));
						}

					}, 1L);
					break;
				}
			}
		}
	}

	@Override
	public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		for(Setting c : SettingManager.settings) {
			items.add(c.getItem(p));
		}

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
