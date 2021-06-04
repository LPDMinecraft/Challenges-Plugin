package de.lpd.challenges.chg.impl.Hearths;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class GeteilteHearths extends Challenge {
	
	public static int health = 20;
	public Config cfg;

	public GeteilteHearths(ChallengesMainClass plugin) {
		super(plugin, "geteilteherzen", "config.yml", "geteiltehearths");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(ChallengesMainClass.t.isStarted()) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getGameMode() == GameMode.SURVIVAL) {
							p.setHealth(health);
						}
					}
				}
				setOption(cfg, "geteilteherzen.herzen", health);
			}
			
		}, 0, 1L);
	}

	@Override
	public void cfg(Config cfg) {
		health = (int) getOption(cfg, "geteilteherzen.herzen", 20);
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.HEART_OF_THE_SEA, 2);
		if(isEnabled()) {
			ib.setDisplayName("§6GeteilteHerzen " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6GeteilteHerzen " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[4];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§ageteilten Herzen durspielen. Das heißt. Wenn Spieler 1 Damage";
		lore[2] = "§abekommt, bekommt der Rest auch Damage.";
		lore[3] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {}

	@Override
	public void onLeftClick(Player p) {}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		health = 20;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(isEnabled()) {
				health = (int) (health - (e.getDamage() * 2));
			}
		}
	}
	
}
