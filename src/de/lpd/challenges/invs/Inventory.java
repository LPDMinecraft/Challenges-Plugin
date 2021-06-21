package de.lpd.challenges.invs;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.ItemBuilder;

public abstract class Inventory implements Listener {
	public abstract org.bukkit.inventory.Inventory createdInv();
	
	public Inventory(ChallengesMainClass plugin) {
		plugin.registerListener(this);
	}
	
	public static org.bukkit.inventory.Inventory placeHolder(org.bukkit.inventory.Inventory inv) {
		String s = "§7";
		for(int i = 0; i < inv.getContents().length; i++) {
			inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(s).build());
			s = s + " ";
		}
		return inv;
	}
	
}
