package de.lpd.challenges.utils;

import de.lpd.challenges.languages.Language;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.permissions.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;

public class Starter {

	public static String PREFIX = "§7[§6Challenges§7] §r",
			START_PREFIX = " §7>> §r",
			NO_PERMISSIONS = "§cYou do not have permissions for that!",
			NO_PLAYER = "§cYou must be a player to use that!",
			NO_COMMAND_FOUND = "§cThat command do not exists!",
			NO_NUMBER = "§cThats not a normal number!",
			ON_LOAD = PREFIX + "§aThe Plugin is loaded.",
			ON_START = PREFIX + "§aThe Plugin is started.",
			ON_STOP = PREFIX + "§aThe Plugin is stopped.",
			NO_PLAYER_FOUND = "§cThis Player doesn't exists!",
			WORLD_NOT_FOUND = "§cThis World doesn't exists!";

	public Config startPlugin(Config mainCFG, JavaPlugin plugin) {
		try {
			mainCFG = new Config("config.yml");
			PREFIX = (String) Config.getOption(mainCFG, "cb.PREFIX", PREFIX);
			START_PREFIX = (String) Config.getOption(mainCFG, "cb.START_PREFIX", START_PREFIX);
			NO_PERMISSIONS = (String) Config.getOption(mainCFG, "cb.NO_PERMISSIONS", NO_PERMISSIONS);
			NO_PLAYER = (String) Config.getOption(mainCFG, "cb.NO_PLAYER", NO_PLAYER);
			NO_COMMAND_FOUND = (String) Config.getOption(mainCFG, "cb.NO_COMMAND_FOUND", NO_COMMAND_FOUND);
			NO_NUMBER = (String) Config.getOption(mainCFG, "cb.NO_NUMBER", NO_NUMBER);
			NO_PLAYER_FOUND = (String) Config.getOption(mainCFG, "cb.NO_PLAYER_FOUND", NO_PLAYER_FOUND);
			WORLD_NOT_FOUND = (String) Config.getOption(mainCFG, "cb.WORLD_NOT_FOUND", WORLD_NOT_FOUND);

			ON_LOAD = (String) Config.getOption(mainCFG, "cb.ON_LOAD", ON_LOAD);
			ON_START = (String) Config.getOption(mainCFG, "cb.ON_START", ON_START);
			ON_STOP = (String) Config.getOption(mainCFG, "cb.ON_STOP", ON_STOP);

			Bukkit.broadcastMessage(ON_LOAD);
			return mainCFG;
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("§cERRRRRROR!!!");
		}
		return null;
	}

	public void init(ChallengesMainClass plugin) {
		if(plugin.getCommandManager().docAllPermissions() != null) {
			for(String c : plugin.getCommandManager().docAllPermissions()) {
				PermissionsManager.Permission perm = new PermissionsManager.Permission();
				perm.load(c, plugin.getPermsManager().getAllPermissionsCfg());
			}
		}
		if(plugin.getCommandManager().docAllTranslations() != null) {
			for(String c : plugin.getCommandManager().docAllTranslations()) {
				for(String LStr : LanguagesManager.langs.keySet()) {
					Language lang = LanguagesManager.langs.get(LStr);
					LanguagesManager.translate(c, lang);
				}
			}
		}
		/*if(plugin.getSysManager().docAllPermissions() != null) {
			for(String c : plugin.getSysManager().docAllPermissions()) {
				PermissionsManager.Permission perm = new PermissionsManager.Permission();
				perm.load(c, plugin.getPermsManager().getAllPermissionsCfg());
			}
		}
		if(plugin.getSysManager().docAllTranslations() != null) {
			for(String c : plugin.getSysManager().docAllTranslations()) {
				for(String LStr : LanguagesManager.langs.keySet()) {
					Language lang = LanguagesManager.langs.get(LStr);
					LanguagesManager.translate(c, lang);
				}
			}
		}*/
	}

	public static void sendActionBar(Player p, String msg){
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
	}

}