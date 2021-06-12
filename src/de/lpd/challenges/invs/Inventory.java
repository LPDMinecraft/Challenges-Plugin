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

	private String TITLE = "§aChallenges §7- §6",
			             ITEM_BACK = "§cZur§ck zum §6",
			             ITEM_NextPage = "§6§lN§chste Seite",
			             ITEM_BeforePage = "§6§lVorherige Seite",
	                     NAME;
	private Config cfg;
	public org.bukkit.inventory.Inventory inv;

	public String getITEM_BACK() {
		return ITEM_BACK;
	}

	public String getTITLE() {
		return TITLE;
	}

	public String getITEM_BeforePage() {
		return ITEM_BeforePage;
	}

	public String getITEM_NextPage() {
		return ITEM_NextPage;
	}

	public String getNAME() {
		return NAME;
	}

	public int getSize() {
		return size;
	}

	private int size = 5*9;
	public Inventory(ChallengesMainClass plugin, int size, boolean hasMoreThen1Site, String name, String backName) {
		cfg = new Config(name, "inv.yml");

		this.size = size;
		plugin.registerListener(this);
		this.hasMoreThen1Site = hasMoreThen1Site;
		NAME = name;

		NAME = (String) cfg.getOption(cfg, "settings.name", NAME);

		TITLE = TITLE + NAME;
		ITEM_BACK = ITEM_BACK + backName;

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
		try {
			return Integer.valueOf(title.replaceFirst((TITLE + " "), ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public abstract void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page);
	public abstract org.bukkit.inventory.Inventory getInventory(int page, Player p);

	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getWhoClicked() != null && e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if(e.getView() != null) {
				if(e.getView().getTitle() != null) {
					String a = TITLE.split(" §7- ")[0];
					String b = e.getView().getTitle().split(" §7- ")[0];
					if(a.startsWith(b)) {
						e.setCancelled(true);
						if(e.getCurrentItem() != null) {
							if (e.getCurrentItem().getItemMeta() != null) {
								if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
									if (e.getCurrentItem().getType() != null && e.getCurrentItem().getType() != Material.AIR) {
										int currentpage = getCurrentPage(e.getView().getTitle());

										if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BeforePage)) {
											if(currentpage > 1) {
												p.closeInventory();
												p.openInventory(getInventory(currentpage - 1, p));
											}
										} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_NextPage)) {
											if(currentpage < Arrays.stream(e.getView().getTopInventory().getContents()).count()) {
												p.closeInventory();
												p.openInventory(getInventory(currentpage + 1, p));
											}
										} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ITEM_BACK)) {
											p.closeInventory();
											p.openInventory(ChallengesMainClass.getInvManager().invs.get("menu").getInventory(1, p));
										} else {
											onClickOnItemEvent(p, e.getCurrentItem(), e, getCurrentPage(e.getView().getTitle()));
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
