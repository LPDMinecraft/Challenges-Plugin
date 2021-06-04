package de.lpd.challenges.chg.impl.Hearths;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class MaxHearth extends Challenge{
	
	public static double status = 0;
	private Config cfg;
	private ChallengesMainClass plugin;
	private boolean a = false;
	
	public MaxHearth(ChallengesMainClass plugin) {
		super(plugin, "maxhearth", "config.yml", "maxhearth");
		this.setPlugin(plugin);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(a != ChallengesMainClass.t.isStarted()) {
					if(isEnabled()) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.setMaxHealth(status);
						}
						status = 20;
						setOption(cfg, "maxhearths.max", status);
					}
				}
				
				a = ChallengesMainClass.t.isStarted();
				if(a) {
					if(isEnabled()) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.setMaxHealth(status);
						}
					}
				}
			}
			
		}, 0, 1L);
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
		try {
			status = (double) getOption(cfg, "maxhearths.max", 20);
		} catch (Exception e) {
			status = Double.valueOf(String.valueOf(getOption(cfg, "maxhearths.max", 20)));
		}
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.CUT_RED_SANDSTONE_SLAB);
		if(isEnabled()) {
			ib.setDisplayName("§6MaxLeben " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6MaxLeben " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Herzen durchspielen.";
		lore[2] = "§7Derzeitige Herzen§8: §6" + status + "/20";
		lore[3] = "§6Linksklick §7> §a-0.5 Herz";
		lore[4] = "§6Rechtsklick §7> §a+0.5 Herz";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		status = status + 1;
		setOption(cfg, "maxhearths.max", status);
	}

	@Override
	public void onLeftClick(Player p) {
		if(status > 1) {
			status = status - 1;
			setOption(cfg, "lmaxhearths.max", status);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {}

	public ChallengesMainClass getPlugin() {
		return plugin;
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	

}
