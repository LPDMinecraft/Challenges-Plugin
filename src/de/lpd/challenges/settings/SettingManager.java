package de.lpd.challenges.settings;

import java.util.ArrayList;

import de.lpd.challenges.main.ChallengesMainClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingManager {
	
	public static ArrayList<Setting> settings;
	
	public SettingManager(ChallengesMainClass plugin) {
		settings = new ArrayList<>();
	}
	
	public void add(Setting s) {
		settings.add(s);
	}

	public ArrayList<ItemStack> getAllItems(Player p) {
		ArrayList<ItemStack> items = new ArrayList<>();

		for(Setting c : settings) {
			items.add(c.getItem(p));
		}

		return items;
	}
	
	public static void on(int id) {
		if(settings != null && !settings.isEmpty()) {
			for(Setting c : settings) {
				if(c.isEnabled()) {
					if(id == 0) {
						c.onTimerStarted();
					} else if(id == 1) {
						c.onTimerStoped();
					}
				}
			}
		}
	}
	
}
