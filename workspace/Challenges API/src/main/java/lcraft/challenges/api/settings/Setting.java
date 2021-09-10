package lcraft.challenges.api.settings;

import lcraft.challenges.api.inventory.Inventory;
import lcraft.challenges.api.main.ChallengesApi;
import lcraft.challenges.api.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Setting extends Inventory implements Listener {
	
	public abstract void onTimerStarted();
	public abstract void onTimerStoped();
	public abstract void cfg(Config cfg);
	public abstract ItemStack getItem(Player p);
	public abstract void onRightClick(Player p);
	public abstract void onLeftClick(Player p);
	public abstract void onMiddleClick(Player p);
	public abstract void reset();
	public abstract void ifPlayerDies(Player p);
	
	private boolean isEnabled = false;
	private String path = "";
	
	public Setting(ChallengesApi plugin, String cfgPath, String filename, String root, int size, boolean hasMoreThen1Site, String name, String backName, String id, String showBackName) {
		super(plugin, size, hasMoreThen1Site, name, backName, showBackName, new Config("settings//" + cfgPath, "inv.yml"));
		plugin.registerListener(this);
		path = "settings//" + cfgPath;
		cfg(new Config(path, filename));
		isEnabled = (boolean) getOption(new Config(path, filename), root + ".isEnabled", isEnabled);
		plugin.getInventoryManager().invs.put(id, this);
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
		if(isEnabled && getPlugin().getTimer().isStarted()) {
			return true;
		}
		return false;
	}
	
	public void toggle() {
		if(isEnabled) isEnabled = false;
		else isEnabled = true;
	}
	
}
