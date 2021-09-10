package lcraft.challenges.api.chg;

import java.util.ArrayList;
import java.util.HashMap;
import lcraft.challenges.api.main.ChallengesApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengesManager {
	
	private ChallengesApi plugin;
	private static HashMap<String, Challenge> idtoclass;
	
	public ChallengesManager(ChallengesApi plugin) {
		this.plugin = plugin;
		idtoclass = new HashMap<>();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

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
