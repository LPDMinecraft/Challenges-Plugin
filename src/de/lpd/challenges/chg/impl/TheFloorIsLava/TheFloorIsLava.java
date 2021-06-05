package de.lpd.challenges.chg.impl.TheFloorIsLava;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class TheFloorIsLava extends Challenge {
	
	private Config cfg;
	private ChallengesMainClass plugin;
	
	public TheFloorIsLava(ChallengesMainClass plugin) {
		super(plugin, "thefloorislava", "thefloorislava.yml", "thefloorislava");
		this.plugin = plugin;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			
			@Override
			public void run() {
				if(isEnabled()) {
					String root = "thefloorislava.hashmap";
					for(String c : cfg.cfg().getConfigurationSection(root).getKeys(false)) {
						String r = root + "." + c;
						
						Location loc = cfg.getBlockLocation(r + ".loc");
						double secs = (double) cfg.cfg().get(r + ".secounds");
						boolean active = (boolean) cfg.cfg().get(r + ".active");
						Material m = (Material) cfg.cfg().get(r + ".material");
						
						double prozent = secs / (double)getOption(cfg, "thefloorislava.max", 30);
						
						if(secs > 0) {
							if(isEnabled()) {
								if(active) {
									
									Material set = null;
                                    if(prozent < 0.94) {
										set = Material.MAGMA_BLOCK;
									} else if(prozent < 0.67) {
										set = Material.LAVA;
									} else if(prozent < 0.19) {
										set = Material.OBSIDIAN;
									} else if(prozent < 0.1) {
										set = Material.BEDROCK;
									} else if(prozent < 0.04) {
										set = Material.BEDROCK;
									} else {
										set = m;
									}
                                    loc.getBlock().setType(set);
                                    
                                    cfg.cfg().set(root + ".secounds", (double)cfg.cfg().getDouble(root + ".secounds") - 1);
                                    cfg.save();
									
								}
							}
						} else {
							active = false;
						}
					}
				}
			}
			
		}, 0, 20l);
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.LAVA_BUCKET);
		if(isToggled()) {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss du in x Sekunden";
		lore[1] = "§avor der Lava entfliehen";
		lore[2] = "§7Derzeitig ausgewählte Zeit§8: §6" + getOption(cfg, "thefloorislava.max", 30);
		lore[3] = "§6Linksklick §7> §a-1 Sekunde";
		lore[4] = "§6Rechtsklick §7> §a+1 Sekunde";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		double a = (double)getOption(cfg, "thefloorislava.max", 30.00);
		setOption(cfg, "thefloorislava.max", a + 1);
	}

	@Override
	public void onLeftClick(Player p) {
		double a = (double)getOption(cfg, "thefloorislava.max", 30.00);
		if(a > 0) {
			setOption(cfg, "thefloorislava.max", a - 1);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		cfg.saveHashMap("thefloorislava.hashmap", new HashMap<>());
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		int y = e.getTo().getBlock().getY();
		y = y - 1;
		Location l = e.getTo();
		l.setY(y);
		if(isEnabled()) {
			float id = new Random().nextFloat();
			String root = "locations.hashmap." + id;
			while(cfg.cfg().contains(root)) {
				id = new Random().nextFloat();
				root = "locations.hashmap." + id;
			}
			cfg.saveBlockLocation(root + ".loc", l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
			cfg.cfg().set(root + ".secounds", (double)getOption(cfg, "thefloorislava.max", 30));
			cfg.cfg().set(root + ".active", true);
			cfg.cfg().set(root + ".material", l.getBlock().getType());
			
			cfg.save();
		}
	}
	
}
