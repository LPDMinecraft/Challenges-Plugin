package lcraft.challenges.api.inventory;

import lcraft.challenges.api.main.ChallengesApi;
import lcraft.challenges.api.main.Starter;
import lcraft.challenges.api.utils.Config;
import lcraft.challenges.api.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Inventory extends Starter implements Listener {

	private ChallengesApi plugin;
	private ItemBuilder BACK_TO_LAST_INV,
	                    BACK_TO_PAGE,
	                    NEXT_TO_PAGE;
	private Inventory backInv;
	private boolean canBack;
	private Config invConfig;

	public Inventory(ChallengesApi plugin, Inventory backInv, boolean canBack, String id) {
		plugin.registerListener(this);
		this.plugin = plugin;
		this.backInv = backInv;
		this.canBack = canBack;
		invConfig = new Config("invs/" + id, "config.yml");
	}

	public String getListTitle(String title, UUID player, int page, int pages) {
		title = plugin.getLanguagesManager().getPlayer(player).translate(title + " §6%cpage%§7/§6%maxpage%").replace("%cpage%", page + "").replace("%maxpage%", pages + "");
		return title;
	}

	public org.bukkit.inventory.Inventory getListPage(ArrayList<ItemStack> items, int page, int invSize, UUID player, String title) {
		title = getListTitle(title, player, page, getListPages(items, invSize));
		org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, invSize, title);

		int pagethings = 0;
		int pages = 1;
		ArrayList<ItemStack> item = new ArrayList<>();
		for(int i = 0; i < items.size(); i++) {
			pagethings++;
			if(pagethings > (invSize - 9)) {
				pagethings = 0;
				pages = pages + 1;
			}
			if(pages == page) {
				item.add(items.get(i));
			}
		}

		for(ItemStack c : item) {
			inv.addItem(c);
		}
		inv = placeHolder(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build());
		BACK_TO_LAST_INV = new ItemBuilder(Material.ORANGE_BANNER).setDisplayName(plugin.getLanguagesManager().getPlayer(player).translate("§aGo back to last Inv"));
		BACK_TO_PAGE = new ItemBuilder(Material.RED_BANNER).setDisplayName(plugin.getLanguagesManager().getPlayer(player).translate("§aGo back to last Page"));
		NEXT_TO_PAGE = new ItemBuilder(Material.LIME_BANNER).setDisplayName(plugin.getLanguagesManager().getPlayer(player).translate("§aGo next Page"));

		if(canBack) inv.setItem(invSize - 1, BACK_TO_LAST_INV.build());
		if(page > 1) inv.setItem(invSize - 2, BACK_TO_PAGE.build());
		if(getListPages(items, invSize) > page) inv.setItem(invSize - 8, NEXT_TO_PAGE.build());

		return inv;
	}

	public org.bukkit.inventory.Inventory placeHolder(org.bukkit.inventory.Inventory inv, ItemStack placeHolder) {
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(0) == null || (inv.getItem(0) != null && (inv.getItem(0).getType() == Material.AIR || inv.getItem(0) instanceof InventoryHolder))) {
				inv.setItem(i, placeHolder);
			}
		}
		return inv;
	}

	public int getListPages(ArrayList<ItemStack> items, int invSize) {
		int pagethings = 0;
		int pages = 1;
		ArrayList<ItemStack> item = new ArrayList<>();
		for(int i = 0; i < items.size(); i++) {
			pagethings++;
			if(pagethings > (invSize - 9)) {
				pagethings = 0;
				pages = pages + 1;
			}
		}
		return pages;
	}

	public ChallengesApi getPlugin() {
		return plugin;
	}
	public void setBACK_TO_LAST_INV(ItemBuilder BACK_TO_LAST_INV) {
		this.BACK_TO_LAST_INV = BACK_TO_LAST_INV;
	}
	public void setBACK_TO_PAGE(ItemBuilder BACK_TO_PAGE) {
		this.BACK_TO_PAGE = BACK_TO_PAGE;
	}
	public void setNEXT_TO_PAGE(ItemBuilder NEXT_TO_PAGE) {
		this.NEXT_TO_PAGE = NEXT_TO_PAGE;
	}
	public Config getInvConfig() {
		return invConfig;
	}
	public Inventory getBackInv() {
		return backInv;
	}
	public abstract ArrayList<String> allPermissions(ArrayList<String> allPermissions);
	public abstract ArrayList<String> allLanguages(ArrayList<String> allLanguages);

}
