package de.lpd.challenges.invs.impl;

import java.util.Arrays;
import de.lpd.challenges.invs.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.main.ChallengesMainClass;

public class ChallengesMenu extends Inventory {

	public ChallengesMenu(ChallengesMainClass plugin) {
		super(plugin, 6*9, true, "Challenges");
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e) {
		if(item.getType() != Material.BLACK_STAINED_GLASS_PANE) {
			for(String key : ChallengesManager.getItemStack().keySet()) {
				ItemStack i = ChallengesManager.getItemStack().get(key);
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase(i.getItemMeta().getDisplayName()) && item.getType() == i.getType()) {
					if(e.getClick() == ClickType.MIDDLE) {
						ChallengesManager.idtoclass.get(key).onMiddleClick(p);
					} else if(e.isLeftClick()) {
						ChallengesManager.idtoclass.get(key).onLeftClick(p);
					} else if(e.isRightClick()) {
						ChallengesManager.idtoclass.get(key).onRightClick(p);
					}
					Bukkit.getScheduler().runTaskLater(ChallengesMainClass.getPlugin(), new Runnable() {

						@Override
						public void run() {
							p.openInventory(getInventory((int) Arrays.stream(e.getView().getTopInventory().getContents()).count()));
						}

						}, 1L);
					break;
				}
			}
		}
	}

	public org.bukkit.inventory.Inventory getInventory(int page) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);
		
		return getPage(ChallengesMainClass.getChMa().getAllItems(), inv, page);
	}
	
}
