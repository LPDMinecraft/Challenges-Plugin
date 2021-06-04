package de.lpd.challenges.invs;

import java.util.ArrayList;
import de.lpd.challenges.invs.impl.Menu;
import de.lpd.challenges.invs.impl.Settings;
import de.lpd.challenges.main.ChallengesMainClass;

public class InventoryManager {
	
	public static ArrayList<Inventory> invs;
	
	public InventoryManager(ChallengesMainClass plugin) {
		invs = new ArrayList<>();
		
		invs.add(new Menu(plugin));
		invs.add(new Settings(plugin));
	}
	
	public org.bukkit.inventory.Inventory getInventory(int page) {
		return invs.get(page).createdInv();
	}
	
}
