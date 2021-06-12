package de.lpd.challenges.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import de.lpd.challenges.main.ChallengesMainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Starter {

	public static String PREFIX = "§7[§6Challenges§7] §r",
            START_PREFIX = " §7>> §r",
            NO_PERMISSIONS = "§cDazu hast du keine Rechte!",
            NO_PLAYER = "§cDazu musst du ein Spieler sein!",
            NO_COMMAND_FOUND = "§cDieser Command wurde nicht gefunden!",
            NO_NUMBER = "§cDies ist keine g§ltige Nummer.",
            ON_LOAD = PREFIX + "§aDas Plugin wurde erfolgreich geladen.",
            ON_START = PREFIX + "§aDas Plugin wurde erfolgreich gestartet.",
            ON_STOP = PREFIX + "§aDas Plugin wurde erfolgreich gestoppt.";
	
	public void startPlugin(Config mainCFG, ChallengesMainClass plugin) {
		try {
			mainCFG = new Config("config.yml");
			PREFIX = (String) Config.getOption(mainCFG, "challenges.PREFIX", PREFIX);
			START_PREFIX = (String) Config.getOption(mainCFG, "challenges.START_PREFIX", START_PREFIX);
			NO_PERMISSIONS = (String) Config.getOption(mainCFG, "challenges.NO_PERMISSIONS", NO_PERMISSIONS);
			NO_PLAYER = (String) Config.getOption(mainCFG, "challenges.NO_PLAYER", NO_PLAYER);
			NO_COMMAND_FOUND = (String) Config.getOption(mainCFG, "challenges.NO_COMMAND_FOUND", NO_COMMAND_FOUND);
			NO_NUMBER = (String) Config.getOption(mainCFG, "challenges.NO_NUMBER", NO_NUMBER);
			
			ON_LOAD = (String) Config.getOption(mainCFG, "challenges.ON_LOAD", ON_LOAD);
			ON_START = (String) Config.getOption(mainCFG, "challenges.ON_START", ON_START);
			ON_STOP = (String) Config.getOption(mainCFG, "challenges.ON_STOP", ON_STOP);
			
			Bukkit.broadcastMessage(ON_LOAD);
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("§cERRRRRROR!!!");
		}
		
		all1Tick(plugin);
	}
	
	public static void sendActionBar(Player p, String msg){
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
	}
	
	private void all1Tick(ChallengesMainClass plugin) {
		new Scheduler(0, 1, plugin) {
			
			@Override
			public void scheduler() {
				for(Player current : Bukkit.getOnlinePlayers()) {
					sendActionBar(current, ChallengesMainClass.t.getDisplay("§a", ""));
				}
			}
		};
	}
	
}
