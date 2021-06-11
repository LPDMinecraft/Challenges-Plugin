package de.lpd.challenges.invs;

import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Inventory implements Listener {

	private static String TITLE = "§aChallenges §7- §6",
			             ITEM_BACK = "§cZur§ck zum §6",
			             ITEM_NextPage = "§6§lN§chste Seite",
			             ITEM_BeforePage = "§6§lVorherige Seite",
	                     NAME;
	private Config cfg;
	public org.bukkit.inventory.Inventory inv;

	private int size = 5*9;
	public Inventory(ChallengesMainClass plugin, int size, boolean hasMoreThen1Site, String name) {
		cfg = new Config(name, "config.yml");

		this.size = size;
		plugin.registerListener(this);
		this.hasMoreThen1Site = hasMoreThen1Site;
		NAME = name;

		NAME = (String) cfg.getOption(cfg, "settings.name", NAME);

		TITLE = TITLE + NAME;
		ITEM_BACK = ITEM_BACK + NAME;

		TITLE = (String) cfg.getOption(cfg, "settings.title", TITLE);
		ITEM_NextPage = (String) cfg.getOption(cfg, "settings.nextpage", ITEM_NextPage);
		ITEM_BeforePage = (String) cfg.getOption(cfg, "settings.beforepage", ITEM_BeforePage);

		inv = Bukkit.createInventory(null, size, TITLE);
		this.plugin = plugin;
	}

	private ChallengesMainClass plugin;
	protected org.bukkit.inventory.Inventory getInv() {
		return inv;
	}

	public ChallengesMainClass getPlugin() {
		return plugin;
	}

	public Config getCfg() {
		return cfg;
	}

	private boolean hasMoreThen1Site = false,
	                isCanBack = false;

	public static org.bukkit.inventory.Inventory placeHolder(org.bukkit.inventory.Inventory inv) {
		String s = "§7";
		for(int i = 0; i < inv.getContents().length; i++) {
			inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(s).build());
			s = s + " ";
		}
		return inv;
	}

	public int getNeedSites(ArrayList<ItemStack> items) {
		int maxitems = size;
		// Die Menge von Challenges
		int needpages = 1;
		int i = items.size();
		while(i > maxitems) {
			needpages++;
			i = i - 5*9;
		}
		return needpages;
	}

	public org.bukkit.inventory.Inventory getPage(ArrayList<ItemStack> items, org.bukkit.inventory.Inventory inv, int page) {
		int slot = 0;
		int i = 0;
		for(i = ((page - 1) * page); i < (page * size); i++) {
			if(!items.isEmpty()) {
				try {
					ItemStack item = items.get(i);
					if(item != null) {
						inv.setItem(slot, item);
						slot++;
					}
				} catch (Exception e) {}
			}
		}
		return inv;
	}

	public int getCurrentPage(String title) {
		return Integer.valueOf(title.replaceFirst((TITLE + " "), ""));
	}

	public abstract void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e);
	public abstract org.bukkit.inventory.Inventory getInventory(int page);

	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getWhoClicked() != null && e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			System.out.println(1);
			if(e.getView() != null) {
				System.out.println(2);
				if(e.getView().getTitle() != null) {
					System.out.println(3);
					System.out.println(TITLE);
					System.out.println(e.getView().getTitle());
					if(e.getView().getTitle().startsWith(TITLE)) {
						System.out.println(4);
						e.setCancelled(true);
						if(e.getCurrentItem() != null) {
							System.out.println(5);
							if (e.getCurrentItem().getItemMeta() != null) {
								System.out.println(6);
								if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
									System.out.println(7);
									if (e.getCurrentItem().getType() != null && e.getCurrentItem().getType() != Material.AIR) {
										System.out.println(8);
										int currentpage = getCurrentPage(e.getView().getTitle());

										if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BeforePage)) {
											if(currentpage > 1) {
												p.closeInventory();
												p.openInventory(getInventory(currentpage - 1));
											}
										} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_NextPage)) {
											if(currentpage < Arrays.stream(e.getView().getTopInventory().getContents()).count()) {
												p.closeInventory();
												p.openInventory(getInventory(currentpage + 1));
											}
										} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BACK)) {
											p.closeInventory();
											p.openInventory(ChallengesMainClass.getInvManager().invs.get("menu").getInventory(0));
										} else {
											onClickOnItemEvent(p, e.getCurrentItem(), e);
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
