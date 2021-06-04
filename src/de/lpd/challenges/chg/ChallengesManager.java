package de.lpd.challenges.chg;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.chg.impl.BreakUpgradeTool.BreakUpgradeTool;
import de.lpd.challenges.chg.impl.Hearths.GeteilteHearths;
import de.lpd.challenges.chg.impl.Hearths.MaxHearth;
import de.lpd.challenges.chg.impl.LockedSlots.LockedSlots;
import de.lpd.challenges.chg.impl.OneFoodChallenge.TheOneFoodChallenge;
import de.lpd.challenges.chg.impl.TheFloorIsLava.TheFloorIsLava;
import de.lpd.challenges.chg.impl.WaterMLG.WaterMLG;
import de.lpd.challenges.main.ChallengesMainClass;

public class ChallengesManager {
	
	private ChallengesMainClass plugin;
	public static HashMap<String, Challenge> idtoclass;
	
	public ChallengesManager(ChallengesMainClass plugin) {
		this.plugin = plugin;
		idtoclass = new HashMap<>();
		
		idtoclass.put("theonefoodchallenge", new TheOneFoodChallenge(this.plugin));
		idtoclass.put("watermlg", new WaterMLG(plugin));
		idtoclass.put("thefloorislava", new TheFloorIsLava(plugin));
		idtoclass.put("lockedslots", new LockedSlots(plugin));
		idtoclass.put("geteilteherzen", new GeteilteHearths(plugin));
		idtoclass.put("maxheaths", new MaxHearth(plugin));
		idtoclass.put("enchantbreakblocktool", new BreakUpgradeTool(plugin));
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
