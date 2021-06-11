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
	
	private Config cfg;
	private ChallengesMainClass plugin;	
	
	public MaxHearth(ChallengesMainClass plugin) {
		super(plugin, "maxhearth", "config.yml", "maxhearth");
		this.setPlugin(plugin);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(isEnabled()) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						System.out.println(p.getMaxHealth());
						if(p.getMaxHealth() != (int) getOption(cfg, "maxhearths.max", 20)) {
							p.setMaxHealth((int) getOption(cfg, "maxhearths.max", 20));
							
							System.out.println(getOption(cfg, "maxhearths.max", 20));
						}
					}
				}
			}
			
		}, 0, 1L);
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.CUT_RED_SANDSTONE_SLAB);
		if(isToggled()) {
			ib.setDisplayName("§6MaxLeben " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6MaxLeben " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Herzen durchspielen.";
		lore[2] = "§7Derzeitige Herzen§8: §6" + getOption(cfg, "maxhearths.max", 20) + "/20";
		lore[3] = "§6Linksklick §7> §a-1 Herz";
		lore[4] = "§6Rechtsklick §7> §a+1 Herz";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		setOption(cfg, "maxhearths.max", (int)getOption(cfg, "maxhearths.max", 20) + 1);
	}

	@Override
	public void onLeftClick(Player p) {
		if((int)getOption(cfg, "maxhearths.max", 20) > 1) {
			setOption(cfg, "maxhearths.max", (int)getOption(cfg, "maxhearths.max", 20) - 1);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reset() {
		if(isEnabled()) {
			setOption(cfg, "maxhearths.max", 20);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.setMaxHealth((int) getOption(cfg, "maxhearths.max", 20));
			}
		}
	}

	@Override
	public void ifPlayerDies() {

	}

	public ChallengesMainClass getPlugin() {
		return plugin;
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	

}
