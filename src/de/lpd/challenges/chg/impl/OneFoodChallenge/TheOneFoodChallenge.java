package de.lpd.challenges.chg.impl.OneFoodChallenge;

import java.util.ArrayList;
import java.util.HashMap;

import de.lpd.challenges.languages.LanguagesManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class TheOneFoodChallenge extends Challenge {
	
	// public static HashMap<Material, doubleeger> eaten = new HashMap<>();
	private Config cfg;
	private HashMap<Material, Integer> eaten;
	
	public TheOneFoodChallenge(ChallengesMainClass plugin) {
		super(plugin,
				"theonefoodchallenge",
				"config.yml",
				"foodchallenge",
				3*9,
				true,
				"The Max Food",
				"chmenu",
				"challenge-maxfood",
				"Challenges Menu");
		eaten = new HashMap<>();
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public ItemStack getItem(Player p) {
		ItemBuilder ib = new ItemBuilder(Material.COOKED_BEEF);

		ib.setDisplayName(LanguagesManager.translate("§6TheOneFoodChallenge", p.getUniqueId()));

		String[] lore = new String[6];
		lore[0] = Starter.START_PREFIX + LanguagesManager.translate("§aIn dieser Challenge kannst du 1/2/ect. mal einen Essenstyp essen.", p.getUniqueId());
		lore[1] = LanguagesManager.translate("§cAchtung! §6Es wird in dieser Challenge die Essenmale zusammen gez§hlt.", p.getUniqueId());
		lore[2] = LanguagesManager.translate("§aDas hei§t wenn der 1. Spieler rohes Kuhfleisch gegessen hat und danach ein ", p.getUniqueId());
		lore[3] = LanguagesManager.translate("§aanderer Kuhfleisch ist sind sie tot.", p.getUniqueId());
		lore[4] = LanguagesManager.translate("§aAber nur dann, wenn die maximale Begrenzung auf 1 gestellt ist.", p.getUniqueId());
		lore[5] = LanguagesManager.translate("§6Mittelklick §7> §aÖffne das Inventar", p.getUniqueId());
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {

	}

	@Override
	public void onLeftClick(Player p) {

	}

	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}

	@Override
	public void reset() {
		eaten = new HashMap<>();
	}

	@Override
	public void ifPlayerDies(Player p) {
		eaten = new HashMap<>();
	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(isEnabled()) {
			if(eaten.containsKey(e.getItem().getType())) {
				Material t = e.getItem().getType();
				int a = (int) eaten.get(e.getItem().getType());
				a++;
				eaten.remove(t);
				eaten.put(t, a);
				if((a - 1) > (double)getOption(cfg, "foodchallenge.max", 1.00)) {
					ChallengesMainClass.fail(1, e.getPlayer());
				}
			} else {
				eaten.put(e.getItem().getType(), 1);
			}
		}
	}

	String plusMaxFood1 = "§6Ändere das Maximum vom Essentyp um +1",
			minusMaxFood1 = "§6Ändere das Maximum vom Essentyp um -1",
			namei = "";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		namei = LanguagesManager.translate("§6The One Food(" + isToggled() + "): ", p.getUniqueId()) + getOption(cfg, "foodchallenge.max", 1.00) + LanguagesManager.translate(" Food Arten", p.getUniqueId());
		plusMaxFood1 = LanguagesManager.translate("§6Ändere das Maximum vom Essentyp um +1", p.getUniqueId());
		minusMaxFood1 = LanguagesManager.translate("§6Ändere das Maximum vom Essentyp um -1", p.getUniqueId());

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusMaxFood1)) {
			setOption(cfg, "foodchallenge.max", (double) getOption(cfg, "foodchallenge.max", 1.00) + 0.5);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusMaxFood1)) {
			if((double) getOption(cfg, "foodchallenge.max", 1) > 1) {
				setOption(cfg, "foodchallenge.max", (double) getOption(cfg, "foodchallenge.max", 1.00) - 0.5);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle();
		}
	}

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		namei = LanguagesManager.translate("§6The One Food(" + isToggled() + "): ", p.getUniqueId()) + getOption(cfg, "foodchallenge.max", 1.00) + LanguagesManager.translate(" Food Arten", p.getUniqueId());
		plusMaxFood1 = LanguagesManager.translate("§6Ändere das Maximum vom Essentyp um +1", p.getUniqueId());
		minusMaxFood1 = LanguagesManager.translate("§6Ändere das Maximum vom Essentyp um -1", p.getUniqueId());
		Material b;
		if(isToggled()) {
			b = Material.EMERALD_BLOCK;
		} else {
			b = Material.REDSTONE_BLOCK;
		}

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxFood1).build());
		inv.setItem(9, new ItemBuilder(b).setDisplayName(namei).build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxFood1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

		return getPage(items, inv, page);
	}
}
