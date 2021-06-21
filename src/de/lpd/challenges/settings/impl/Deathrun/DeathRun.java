package de.lpd.challenges.settings.impl.Deathrun;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.settings.Setting;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class DeathRun extends Setting {
	
	private Config conf;
	
	public DeathRun(ChallengesMainClass plugin) {
		super(plugin, "DeathRun", "config.yml", "deathrun");
	}

	@Override
	public void onTimerStarted() {
		
	}

	@Override
	public void onTimerStoped() {
		
	}

	@Override
	public void cfg(Config cfg) {
		this.conf = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.TNT);
		if(isEnabled()) {
			ib.setDisplayName("§6DeathRun " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6DeathRun " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[7];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man in Minecraft";
		lore[1] = "§amehr Strecke gelaufen sein als seine Gegner.";
		lore[2] = "§aDerzeitige maxmiale Zeit§7: §6" + conf.cfg().getInt("deathrun.minutes");
		lore[3] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		lore[4] = "§6Rechtsklick §7> §a+1 Minute";
		lore[5] = "§6Linksklick §7> §a-1 Minute";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		conf.cfg().set("deathrun.minutes", conf.cfg().getInt("deathrun.minutes") + 1);
		conf.save();
	}

	@Override
	public void onLeftClick(Player p) {
		conf.cfg().set("deathrun.minutes", conf.cfg().getInt("deathrun.minutes") - 1);
		conf.save();
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		conf.cfg().set("deathrun.minutes", 120);
		conf.save();
	}
	
}
