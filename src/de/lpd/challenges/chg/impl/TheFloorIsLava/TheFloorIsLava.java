package de.lpd.challenges.chg.impl.TheFloorIsLava;

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
import de.lpd.challenges.utils.Starter;

public class TheFloorIsLava extends Challenge {
	
	public static Double status = 2.00;
	private Config cfg;
	private ChallengesMainClass plugin;
	
	public TheFloorIsLava(ChallengesMainClass plugin) {
		super(plugin, "thefloorislava", "thefloorislava.yml", "thefloorislava");
		this.plugin = plugin;
		setOption(cfg, "thefloorislava.max", 2);
		status = 2.00;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Player c : Bukkit.getOnlinePlayers()) {
					if(c.getGameMode() == GameMode.SURVIVAL) {
						if(isEnabled() && ChallengesMainClass.t.isStarted()) {
							on(c);
						}
					}
				}
			}
			
		}, 0L, (long) (20L * status));
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
		try {
			status = (double) getOption(cfg, "thefloorislava.max", 2.00);
		} catch (Exception e) {
			status = Double.valueOf(String.valueOf(getOption(cfg, "thefloorislava.max", 2.00)));
		}
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.LAVA_BUCKET);
		if(isEnabled()) {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss du in x Sekunden";
		lore[1] = "§avor der Lava entfliehen";
		lore[2] = "§7Derzeitig ausgewählte Zeit§8: §6" + status;
		lore[3] = "§6Linksklick §7> §a-0.01 Sekunde";
		lore[4] = "§6Rechtsklick §7> §a+0.01 Sekunde";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		status = status + 0.01;
		setOption(cfg, "thefloorislava.max", status);
	}

	@Override
	public void onLeftClick(Player p) {
		if(status > 0) {
			status = status - 0.01;
			setOption(cfg, "thefloorislava.max", status);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		a = new HashMap<>();
	}
	
	private HashMap<Location, Material> a = new HashMap<>();
	
	private void on(Player p) {
		Location loc = p.getLocation();
		loc.setY(loc.getY() - 1);
		a.put(loc, loc.getBlock().getType());
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				loc.getBlock().setType(Material.MAGMA_BLOCK);
				ona(p, loc);
			}
			
		}, 20L);
		
	}
	
	private void ona(Player p, Location loc) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    @Override
		     public void run() {
		    	on1(p, loc);
		     }
		    
		}, (long) ((status * 20) * 3));
	}
	
	private void on1(Player p, Location loc) {
		loc.getBlock().setType(Material.LAVA);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    @Override
		     public void run() {
		    	on2(p, loc);
		     }
		    
		}, (long) ((status * 20) * 5));
	}
	
	private void on2(Player p, Location loc) {
		Material m = a.get(loc);
		loc.getBlock().setType(m);
		a.remove(loc);
	}
	
}
