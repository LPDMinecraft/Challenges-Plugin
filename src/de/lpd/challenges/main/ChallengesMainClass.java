package de.lpd.challenges.main;

import de.lpd.challenges.chg.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.commands.ChallengesCommand;
import de.lpd.challenges.commands.ResetCommand;
import de.lpd.challenges.commands.TimerCommand;
import de.lpd.challenges.invs.InventoryManager;
import de.lpd.challenges.invs.impl.ChallengesMenu;
import de.lpd.challenges.listener.DeathEvent;
import de.lpd.challenges.settings.SettingManager;
import de.lpd.challenges.utils.Command;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.Starter;
import de.lpd.challenges.utils.Timer;
import de.lpd.challenges.utils.WorldUtil;

public class ChallengesMainClass extends JavaPlugin {
	
	// Commands:   Locations(/loc)
	// Settings:   DeathRun
	// Challenges: GeteiltesEssen, RandomBlocks, NoCrafting, RandomLoot, FallingAnvils, BedrockWall
	
	private static ChallengesMainClass plugin;
	private static Config mainCFG;
	private static InventoryManager invManager;
	private static SettingManager settingManager;
	private static ChallengesManager ChMa;
	
	public static Timer t;
	
	@Override
	public void onLoad() {
		Config cfg = new Config("config.yml");
		if(cfg.cfg().contains("command.reset")) {
			if(cfg.cfg().getBoolean("command.reset") == true) {
				new WorldUtil().resetWorld();
				cfg.cfg().set("command.reset", false);
			}
		}
	}
	
	public void load() {
		plugin = this;
		t = new Timer(plugin);
		t.reset();
		ChMa = new ChallengesManager(this);
		invManager = new InventoryManager(this);
		settingManager = new SettingManager(this);
		new Starter().startPlugin(mainCFG, this);
		
		registerCommand("timer", new TimerCommand(this));
		registerCommand("challenges", new ChallengesCommand(this));
		registerCommand("ch", new ChallengesCommand(this));
		registerCommand("reset", new ResetCommand(this));

		registerListener(new DeathEvent());
	}
	
	@Override
	public void onEnable() {
		load();
		plugin = this;
		
		Bukkit.broadcastMessage(Starter.ON_START);
	}
	
	@Override
	public void onDisable() {
		Bukkit.broadcastMessage(Starter.ON_STOP);
		Bukkit.getScheduler().cancelTasks(plugin);
	}
	
	public void registerCommand(String cmd, Command executor) {
		getCommand(cmd).setExecutor(executor);
	}
	
	public void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public static ChallengesMainClass getPlugin() {
		return plugin;
	}
	
	public static InventoryManager getInvManager() {
		return invManager;
	}
	
	public static ChallengesManager getChMa() {
		return ChMa;
	}
	
	public static SettingManager getSettingManager() {
		return settingManager;
	}
	
	public static int getHighestY(Location loc) {
		int y = 255;
		while(new Location(loc.getWorld(), loc.getX(), y, loc.getZ()).getBlock().getType() == Material.AIR) {
			y--;
		}
		return y;
	}
	
	public static void fail(int reason) {

		for(Challenge c : getChMa().idtoclass.values()) {
			c.ifPlayerDies();
		}

		if(reason == 0) {
			Bukkit.broadcastMessage("§7---------------------------------------");
			Bukkit.broadcastMessage("§6Der Enderdrache wurde besiegt!");
			Bukkit.broadcastMessage("§6Die Challenge ist bestanden.");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§6" + t.getDisplay("", ""));
			Bukkit.broadcastMessage("§7Seed§8: §6" + Bukkit.getWorld("world").getSeed());
			Bukkit.broadcastMessage("§7---------------------------------------");
		} else if(reason == 1) {
			Bukkit.broadcastMessage("§7---------------------------------------");
			Bukkit.broadcastMessage("§6Die Challenge ist nicht bestanden.");
			Bukkit.broadcastMessage("§a#pech §6in den Chat.");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§6" + t.getDisplay("", ""));
			Bukkit.broadcastMessage("§7Seed§8: §6" + Bukkit.getWorld("world").getSeed());
			Bukkit.broadcastMessage("§7---------------------------------------");
		} else if(reason == 2) {
			Bukkit.broadcastMessage("§7---------------------------------------");
			Bukkit.broadcastMessage("§6Die Challenge ist nicht bestanden. [Tod]");
			Bukkit.broadcastMessage("§a#pech §6in den Chat.");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§6" + t.getDisplay("", ""));
			Bukkit.broadcastMessage("§7Seed§8: §6" + Bukkit.getWorld("world").getSeed());
			Bukkit.broadcastMessage("§7---------------------------------------");
		}
		t.reset();
		t.stop();
		for(Player c : Bukkit.getOnlinePlayers()) {
			c.setGameMode(GameMode.SPECTATOR);
		}
	}
	
}
