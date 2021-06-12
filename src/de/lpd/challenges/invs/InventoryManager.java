package de.lpd.challenges.invs;

import java.util.ArrayList;
import java.util.HashMap;
import de.lpd.challenges.invs.impl.ChallengesMenu;
import de.lpd.challenges.invs.impl.Languages;
import de.lpd.challenges.invs.impl.Menu;
import de.lpd.challenges.invs.impl.Settings;
import de.lpd.challenges.main.ChallengesMainClass;

public class InventoryManager {
	
	public static HashMap<String, Inventory> invs;
	
	public InventoryManager(ChallengesMainClass plugin) {
		invs = new HashMap<>();
		
		invs.put("menu", new Menu(plugin));
		invs.put("chmenu", new ChallengesMenu(plugin));
		invs.put("settings", new Settings(plugin));
		invs.put("langs", new Languages(plugin));
	}

	public static HashMap<String, Inventory> getInvs() {
		return invs;
	}
}
