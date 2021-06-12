package de.lpd.challenges.chg.impl.OneFoodChallenge;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	// public static HashMap<Material, Integer> eaten = new HashMap<>();
	private Config cfg;
	private HashMap<Material, Integer> eaten;
	
	public TheOneFoodChallenge(ChallengesMainClass plugin) {
		super(plugin, "theonefoodchallenge", "config.yml", "foodchallenge", 6*9, true, "TheMaxFood", "Max Food", "challenge-maxfood");
		eaten = new HashMap<>();
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.COOKED_BEEF);
		if(isToggled()) {
			ib.setDisplayName("§6TheOneFoodChallenge " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6TheOneFoodChallenge " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[9];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge kannst du 1/2/ect. mal einen Essenstyp essen.";
		lore[1] = "§cAchtung! §6Es wird in dieser Challenge die Essenmale zusammen gez§hlt.";
		lore[2] = "§aDas hei§t wenn der 1. Spieler rohes Kuhfleisch gegessen hat und danach ein ";
		lore[3] = "§aanderer Kuhfleisch ist sind sie tot.";
		lore[4] = "§aAber nur dann, wenn die maximale Begrenzung auf 1 gestellt ist.";
		lore[5] = "§7Derzeitig ausgew§hlte Begrenzung§8: §6" + (int) getOption(cfg, "foodchallenge.max", 1);
		lore[6] = "§6Linksklick §7> §a-1";
		lore[7] = "§6Rechtsklick §7> §a+1";
		lore[8] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		setOption(cfg, "foodchallenge.max", (int) getOption(cfg, "foodchallenge.max", 1) + 1);
	}

	@Override
	public void onLeftClick(Player p) {
		if((int) getOption(cfg, "foodchallenge.max", 1) > 1) {
			setOption(cfg, "foodchallenge.max", (int) getOption(cfg, "foodchallenge.max", 1) - 1);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		eaten = new HashMap<>();
	}

	@Override
	public void ifPlayerDies() {
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
				if(a > (int) getOption(cfg, "foodchallenge.max", 1)) {
					ChallengesMainClass.fail(1);
				}
			} else {
				eaten.put(e.getItem().getType(), 1);
			}
		}
	}

	String plusMaxFood1 = "§6Ändere das Maximum vom Essentyp um +1",
			minusMaxFood1 = "§6Ändere das Maximum vom Essentyp um -1";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {

	}

	@Override
	public Inventory getInventory(int page, Player p) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxFood1).build());
		inv.setItem(9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("§6Maximales Essen von einem Essentyp").build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxFood1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
