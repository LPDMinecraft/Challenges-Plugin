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
	
	public static HashMap<Material, Integer> eaten = new HashMap<>();
	public static int status = 1;
	private Config cfg;
	
	public TheOneFoodChallenge(ChallengesMainClass plugin) {
		super(plugin, "theonefoodchallenge", "config.yml", "foodchallenge");
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
		status = (int) getOption(cfg, "foodchallenge.max", 1);
	}
	
	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.COOKED_BEEF);
		if(isEnabled()) {
			ib.setDisplayName("§6TheOneFoodChallenge " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6TheOneFoodChallenge " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[9];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge kannst du 1/2/ect. mal einen Essenstyp essen.";
		lore[1] = "§cAchtung! §6Es wird in dieser Challenge die Essenmale zusammen gezählt.";
		lore[2] = "§aDas heißt wenn der 1. Spieler rohes Kuhfleisch gegessen hat und danach ein ";
		lore[3] = "§aanderer Kuhfleisch ist sind sie tot.";
		lore[4] = "§aAber nur dann, wenn die maximale Begrenzung auf 1 gestellt ist.";
		lore[5] = "§7Derzeitig ausgewählte Begrenzung§8: §6" + status;
		lore[6] = "§6Linksklick §7> §a-1";
		lore[7] = "§6Rechtsklick §7> §a+1";
		lore[8] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		status++;
		setOption(cfg, "foodchallenge.max", status);
	}

	@Override
	public void onLeftClick(Player p) {
		if(status > 1) {
			status--;
		}
		setOption(cfg, "foodchallenge.max", status);
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		eaten = new HashMap<>();
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(isEnabled()) {
			if(ChallengesMainClass.t.isStarted()) {
				if(eaten.containsKey(e.getItem().getType())) {
					Material t = e.getItem().getType();
					int a = eaten.get(e.getItem().getType());
					a++;
					eaten.remove(t);
					eaten.put(t, a);
					if(a > status) {
						ChallengesMainClass.fail(1);
					}
				} else {
					eaten.put(e.getItem().getType(), 1);
				}
			}
		}
	}
	
}
