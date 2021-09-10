package de.lpd.challenges.chg;

import java.util.ArrayList;
import java.util.HashMap;

import de.lpd.challenges.chg.impl.Randomizer.Randomizer;
import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
				addChallenge(new BreakUpgradeTool(plugin));
				addChallenge(new TheOneFoodChallenge(plugin));
				addChallenge(new GeteilteHearths(plugin));
				addChallenge(new MaxHearth(plugin));
				addChallenge(new LockedSlots(plugin));
				addChallenge(new WaterMLG(plugin));
				addChallenge(new Randomizer(plugin));
			}
		}, 1l);
	}

	public static void addChallenge(Challenge challenge) {
		idtoclass.put(challenge.getId(), challenge);
	}

	public static HashMap<String, Challenge> getIdtoclass() {
		return idtoclass;
	}

	public ArrayList<ItemStack> getAllItems(Player p) {
		ArrayList<ItemStack> items = new ArrayList<>();
		for(Challenge c : idtoclass.values()) {
			items.add(c.getItem(p));
		}
		return items;
	}
	
	public static HashMap<String, ItemStack> getItemStack(Player p) {
		HashMap<String, ItemStack> all = new HashMap<>();
		for(String s : idtoclass.keySet()) {
			all.put(s, idtoclass.get(s).getItem(p));
		}
		return all;
	}
	
	public static ArrayList<ItemStack> getItemStackArray(Player p) {
		ArrayList<ItemStack> i = new ArrayList<>();
		for(String key : getItemStack(p).keySet()) {
			i.add(getItemStack(p).get(key));
		}
		return i;
	}
	
}
