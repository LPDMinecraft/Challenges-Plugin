package de.lpd.challenges.chg;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;

public abstract class Challenge implements Listener {
	
	public abstract void cfg(Config cfg);
	public abstract ItemStack getItem();
	public abstract void onRightClick(Player p);
	public abstract void onLeftClick(Player p);
	public abstract void onMiddleClick(Player p);
	public abstract void reset();
	public abstract void ifPlayerDies();
	
	private boolean isEnabled = false;
	
	public Challenge(ChallengesMainClass plugin, String cfgPath, String filename, String root) {
		plugin.registerListener(this);
		cfg(new Config("challenges//" + cfgPath, filename));
		isEnabled = (boolean) getOption(new Config("challenges//" + cfgPath, filename), root + ".isEnabled", isEnabled);
	}
	
	public static Object getOption(Config cfg, String path, Object start) {
		if(cfg.cfg().contains(path)) {
			return cfg.cfg().get(path);
		} else {
			cfg.cfg().set(path, start);
			cfg.save();
			return start;
		}
	}
	
	public static void setOption(Config cfg, String path, Object obj) {
		cfg.cfg().set(path, obj);
		cfg.save();
	}
	
	public boolean isEnabled() {
		if(isToggled() && ChallengesMainClass.t.isStarted()) {
			return true;
		}
		return false;
	}
	
	public boolean isToggled() {
		if(isEnabled) {
			return true;
		}
		return false;
	}
	
	public void toggle() {
		if(isEnabled) isEnabled = false;
		else isEnabled = true;
	}
	
}
