package de.lpd.challenges.settings.impl.Deathrun;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.settings.Setting;
import de.lpd.challenges.utils.Config;

public class DeathRun extends Setting {

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
		
	}

	@Override
	public ItemStack getItem() {
		return null;
	}

	@Override
	public void onRightClick(Player p) {
		
	}

	@Override
	public void onLeftClick(Player p) {
		
	}

	@Override
	public void onMiddleClick(Player p) {
		
	}

	@Override
	public void reset() {
		
	}
	
}
