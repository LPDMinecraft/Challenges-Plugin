package de.lpd.challenges.chg;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.impl.BreakUpgradeTool.BreakUpgradeTool;
import de.lpd.challenges.chg.impl.Hearths.GeteilteHearths;
import de.lpd.challenges.chg.impl.Hearths.MaxHearth;
import de.lpd.challenges.chg.impl.LockedSlots.LockedSlots;
import de.lpd.challenges.chg.impl.OneFoodChallenge.TheOneFoodChallenge;
import de.lpd.challenges.chg.impl.WaterMLG.WaterMLG;
import de.lpd.challenges.main.ChallengesMainClass;

public class ChallengesManager {
	
	private ChallengesMainClass plugin;
	private static HashMap<String, Challenge> idtoclass;
	
	public ChallengesManager(ChallengesMainClass plugin) {
		this.plugin = plugin;
		idtoclass = new HashMap<>();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				idtoclass.put("theonefoodchallenge", new TheOneFoodChallenge(plugin));
				idtoclass.put("watermlg", new WaterMLG(plugin));
				idtoclass.put("geteilteherzen", new GeteilteHearths(plugin));
				idtoclass.put("maxheaths", new MaxHearth(plugin));
				idtoclass.put("enchantbreakblocktool", new BreakUpgradeTool(plugin));
				idtoclass.put("lockedslots", new LockedSlots(plugin));
			}
		}, 1l);
	}

	public static HashMap<String, Challenge> getIdtoclass() {
		return idtoclass;
	}

	public ArrayList<ItemStack> getAllItems() {
		ArrayList<ItemStack> items = new ArrayList<>();
		for(Challenge c : idtoclass.values()) {
			items.add(c.getItem());
		}
		return items;
	}
	
	public static HashMap<String, ItemStack> getItemStack() {
		HashMap<String, ItemStack> all = new HashMap<>();
		for(String s : idtoclass.keySet()) {
			all.put(s, idtoclass.get(s).getItem());
		}
		return all;
	}
	
	public static ArrayList<ItemStack> getItemStackArray() {
		ArrayList<ItemStack> i = new ArrayList<>();
		for(String key : getItemStack().keySet()) {
			i.add(getItemStack().get(key));
		}
		return i;
	}
	
}
