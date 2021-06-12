package de.lpd.challenges.invs.impl;

import java.util.Arrays;
import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.utils.ItemBuilder;
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
		super(plugin, 6*9, true, "Challenges", "Menu");
	}

	public static String TITLE = "§6Challenges §aMenu";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
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
					break;
				}
			}
		}
	}

	public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());
		
		return getPage(ChallengesMainClass.getChMa().getAllItems(), inv, page);
	}
	
}
