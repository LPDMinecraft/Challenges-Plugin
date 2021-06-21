package de.lpd.challenges.invs;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.Starter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public abstract class Inventory extends Starter implements Listener {

	private String TITLE = "§aChallenges §7- §6",
			             ITEM_BACK = "§cZurück zum §6",
			             ITEM_NextPage = "§6§lNächste Seite",
			             ITEM_BeforePage = "§6§lVorherige Seite",
	                     NAME,
	                     BACK_NAME,
	                     SHOW_BACK_NAME;
	private Config cfg;
	public org.bukkit.inventory.Inventory inv;

	public String getITEM_BACK(UUID p) {
		return LanguagesManager.translate(ITEM_BACK, p);
	}

	public String getTITLE() {
		return TITLE;
	}

	public String getITEM_BeforePage(UUID p) {
		return LanguagesManager.translate(ITEM_BeforePage, p);
	}

	public String getITEM_NextPage(UUID p) {
		return LanguagesManager.translate(ITEM_NextPage, p);
	}

	public String getNAME() {
		return NAME;
	}

	public int getSize() {
		return size;
	}

	private int size = 5*9;
	public Inventory(ChallengesMainClass plugin, int size, boolean hasMoreThen1Site, String name, String backName, String showBackName, Config invConfig) {
		cfg = invConfig;

		this.size = size;
		plugin.registerListener(this);
		this.hasMoreThen1Site = hasMoreThen1Site;
		NAME = name;
		this.SHOW_BACK_NAME = showBackName;

		BACK_NAME = backName;
		if(backName == null) {
			isCanBack = false;
		} else {
			isCanBack = true;
		}
		NAME = (String) cfg.getOption(cfg, "settings.name", NAME);

		TITLE = TITLE + NAME;
		ITEM_BACK = ITEM_BACK + this.SHOW_BACK_NAME;

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

	public int getNeedSites(ArrayList<ItemStack> items, int plus) {
		int maxitems = size;
		// Die Menge von Challenges
		int needpages = 1 + plus;
		int i = items.size();
		while(i > maxitems) {
			needpages++;
			i = i - (5*9 - 3);
		}
		return needpages;
	}

	public org.bukkit.inventory.Inventory getPage(ArrayList<ItemStack> items, org.bukkit.inventory.Inventory inv, int page, int pluspages) {
		int slot = 0;
		int i = 0;
		org.bukkit.inventory.Inventory in = Bukkit.createInventory(null, size, TITLE + " " + page + "/" + getNeedSites(items, pluspages));
		in.setContents(inv.getContents());
		for(i = ((page - 1) * page); i < (page * size); i++) {
			if(!items.isEmpty()) {
				try {
					if(items.size() > i && items.get(i) != null) {
						ItemStack item = items.get(i);
						in.setItem(slot, item);
						slot++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return in;
	}

	public int getCurrentPage(String title) {
		int site = 1;
		try {
			String numbers = title.replaceFirst(TITLE + " ", "");
			String numb = numbers.split("/")[0];
			site = Integer.valueOf(numb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return site;
	}

	private float bugFix2222 = 0;

	public abstract void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page);
	public abstract org.bukkit.inventory.Inventory getInventory(int page, Player p);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractEvente7237872749872989288283847757573474762366(InventoryClickEvent e) {
		if(e.getWhoClicked() != null && e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if(e.getView() != null) {
				if(e.getView().getTitle() != null) {
					if(e.getView().getTitle().startsWith(TITLE)) {
						e.setCancelled(true);
						if(e.getCurrentItem() != null) {
							if (e.getCurrentItem().getItemMeta() != null) {
								normalClickAnyInventory(p, e.getCurrentItem(), e);
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
											if(isCanBack) {
												System.out.println(BACK_NAME);
												p.openInventory(ChallengesMainClass.getInvManager().invs.get(BACK_NAME).getInventory(1, p));
											} else {
												p.openInventory(ChallengesMainClass.getInvManager().invs.get("menu").getInventory(1, p));
											}
										} else {
											bugFix2222++;
											if(bugFix2222 >= 1) {
												onClickOnItemEvent(p, e.getCurrentItem(), e, currentpage);
												bugFix2222 = 0;
											}
											Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
												@Override
												public void run() {
													org.bukkit.inventory.Inventory inv = getInventory(currentpage, p);
													System.out.println(currentpage);
													if(inv != null && p.getOpenInventory() != null && p.getOpenInventory().getTitle().startsWith(TITLE)) {
														p.openInventory(inv);
													}
												}
											}, 5l);
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

	public void normalClickAnyInventory(Player p, ItemStack currentItem, InventoryClickEvent e) {

	}

}
