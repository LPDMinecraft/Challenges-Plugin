package de.lpd.challenges.chg.impl.WaterMLG;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Mathe;
import de.lpd.challenges.utils.Starter;

public class WaterMLG extends Challenge {
	
	private Config cfg;
	private ChallengesMainClass plugin;
	
	public WaterMLG(ChallengesMainClass plugin) {
		super(plugin, "watermlg", "watermlg.yml", "watermlg");
		this.plugin = plugin;
		send();
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.WATER_BUCKET);
		if(isToggled()) {
			ib.setDisplayName("§6WaterMLG " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6WaterMLG " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[7];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss du in x Sekunden";
		lore[1] = "§aeinen WaterMLG machen. Wenn einer dabei stirbt ist die Challange";
		lore[2] = "§avorbei.";
		lore[3] = "§7Derzeitig ausgew§hlte Zeit§8: §6" + getOption(cfg, "watermlg.max", 30);
		lore[4] = "§6Linksklick §7> §a-5 Sekunden";
		lore[5] = "§6Rechtsklick §7> §a+5 Sekunden";
		lore[6] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}
	
	@Override
	public void onRightClick(Player p) {
		setOption(cfg, "watermlg.max", (int)getOption(cfg, "watermlg.max", 120) + 5);
	}
	
	@Override
	public void onLeftClick(Player p) {
		if((int)getOption(cfg, "watermlg.max", 30) > 5) {
			setOption(cfg, "watermlg.max", (int)getOption(cfg, "watermlg.max", 120) - 5);
		}
	}
	
	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}
	
	@Override
	public void reset() {
		inv = new HashMap<>();
		loc = new HashMap<>();
	}

	@Override
	public void ifPlayerDies() {

	}

	private HashMap<Player, ItemStack[]> inv = new HashMap<>();
	private HashMap<Player, Location> loc = new HashMap<Player, Location>();
	
	public void send() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				inv = new HashMap<>();
				if(isEnabled()) {
					for(Player c : Bukkit.getOnlinePlayers()) {
						if(c.getGameMode() == GameMode.SURVIVAL) {
							inv.put(c, c.getInventory().getContents());
							c.getInventory().clear();
							c.leaveVehicle();
							
							loc.put(c, c.getLocation());
							
							// 30 - 50 Bl§cke
							int r = Mathe.getRandom(30, 50);
							c.teleport(new Location(c.getWorld(), c.getLocation().getX(), ChallengesMainClass.getHighestY(c.getLocation()) + r, c.getLocation().getZ()));
							c.getInventory().addItem(new ItemBuilder(Material.WATER_BUCKET).setDisplayName("§6Der beste Springer").build());
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
								
								@Override
								public void run() {
									c.getInventory().setContents(inv.get(c));
									inv.remove(c);
									c.teleport(loc.get(c));
									loc.remove(c);
								}
								
							}, ((r / 10) * 20));
						}
					}
				}
			}
			
		}, 0, (int)getOption(cfg, "watermlg.max", 120) * 20);
	}
	
}
