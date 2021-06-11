package de.lpd.challenges.chg.impl.OneFoodChallenge;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class TheOneFoodChallenge extends Challenge {
	
	// public static HashMap<Material, Integer> eaten = new HashMap<>();
	private Config cfg;
	
	public TheOneFoodChallenge(ChallengesMainClass plugin) {
		super(plugin, "theonefoodchallenge", "config.yml", "foodchallenge");
		cfg.saveHashMap("eaten.hashmap", new HashMap<>());
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
		cfg.saveHashMap("eaten.hashmap", new HashMap<>());
	}

	@Override
	public void ifPlayerDies() {
		cfg.saveHashMap("eaten.hashmap", new HashMap<>());
	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(isEnabled()) {
			HashMap<Object, Object> eaten = cfg.loadHashMap("eaten.hashmap");
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
			setOption(cfg, "eaten.hashmap", eaten);
		}
	}
	
}
