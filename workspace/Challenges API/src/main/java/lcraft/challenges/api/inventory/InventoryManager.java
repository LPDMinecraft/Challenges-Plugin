package lcraft.challenges.api.inventory;

import lcraft.challenges.api.main.ChallengesApi;

import java.util.HashMap;

public class InventoryManager {
	
	public static HashMap<String, Inventory> invs;
	
	public InventoryManager(ChallengesApi plugin) {
		invs = new HashMap<>();
		
		//invs.put("menu", new Menu(plugin));
	}

	public static HashMap<String, Inventory> getInvs() {
		return invs;
	}
}
