package de.lpd.challenges.invs.impl;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;

public class ChallengesMenu implements Listener {
	
	public static String TITLE = "§aChallenges §7- §6Menu",
                         ITEM_BACK = "§cZurück zum Menu",
                         ITEM_NextPage = "§6§lNächste Seite",
	                     ITEM_BeforePage = "§6§lVorherige Seite";
	
	int needpages = 1;
	int currentpage = 1;
	
	public Inventory getInventory(int page) {
		currentpage = page;
		int maxitems = 5*9;
		// Die Menge von Challenges
		ArrayList<ItemStack> chint = ChallengesManager.getItemStackArray();
		needpages = 1;
		int i = chint.size();
		while(i > maxitems) {
			needpages++;
			i = i - 5*9;
		}
		int minminnimenu = maxitems;
		Inventory inv = Bukkit.getServer().createInventory(null, 6*9, TITLE + " " + currentpage + "/" + needpages);
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);
		
		inv.setItem(minminnimenu, new ItemBuilder(Material.BARRIER).setDisplayName(ITEM_BACK).build());
		inv.setItem(minminnimenu + 1, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(ITEM_BeforePage).build());
		inv.setItem(minminnimenu + 8, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayName(ITEM_NextPage).build());
		int slot = 0;
		for(i = ((currentpage - 1) * maxitems); i < (currentpage * maxitems); i++) {
			if(!chint.isEmpty()) {
				try {
					ItemStack item = chint.get(i);
					if(item != null) {
						inv.setItem(slot, item);
						slot++;
					}
				} catch (Exception e) {}
			}
		}
		
		return inv;
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getView().getTitle().startsWith(TITLE)) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().getItemMeta() != null) {
						if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							if(e.getCurrentItem().getType() != null && e.getCurrentItem().getType() != Material.AIR) {
								
								if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BeforePage)) {
									if(currentpage > 1) {
										p.closeInventory();
										p.openInventory(getInventory(currentpage - 1));
									}
								} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_NextPage)) {
									if(currentpage < needpages) {
										p.closeInventory();
										p.openInventory(getInventory(currentpage + 1));
									}
								} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BACK)) {
									p.closeInventory();
									p.openInventory(ChallengesMainClass.getInvManager().getInventory(0));
								} else {
									ItemStack item = e.getCurrentItem();
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
														p.openInventory(getInventory(currentpage));
													}
													
												}, 1L);
												break;
											}
										}
									}
								}
								
							}
						}
					}
				}
			}
		}
	}
	
}
