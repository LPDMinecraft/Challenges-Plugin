package lcraft.challenges.api.commands;

import lcraft.challenges.api.languages.Language;
import lcraft.challenges.api.main.ChallengesApi;
import lcraft.challenges.api.main.Starter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Command extends Starter implements CommandExecutor, Listener {
	
	protected static ChallengesApi plugin;

	public Command(ChallengesApi plugin) {
		this.plugin = plugin;
		plugin.registerListener(this);
	}
	
	public abstract boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args);
	public abstract ArrayList<String> allPermissions(ArrayList<String> allPerms);
	public abstract ArrayList<String> allLanguages(ArrayList<String> allLang);
	
	@Override
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
		return run(arg0, arg1, arg2, arg3);
	}

	public boolean hasPermissions(Player p, String perm) {
		return ChallengesApi.getPlugin().getPermissionsManager().hasPermissions(p, perm);
	}

	public boolean hasPermissions(Player p, String... perm) {
		boolean a = false;
		for(String c : perm) {
			if(ChallengesApi.getPlugin().getPermissionsManager().hasPermissions(p, c)) {
				a = true;
			}
		}
		return a;
	}

	public String getHelpMessage(Language lang, String... help) {
		String end = PREFIX + lang.translate("§cPlease use") + " §6/" + help[0];
		for(int i = 1; i < help.length; i++) {
			end = end + " " + lang.translate("§cPlease use") + " §6/" + help[i];
		}
		end = end + " §c!";
		if(lang == null) {
			return end;
		}
		return end;
	}

	public String NO_PERMISSIONS(UUID p) {
		if(p == null) {
			return PREFIX + plugin.getLanguagesManager().getDefaultLanguage().translate(NO_PERMISSIONS);
		} else {
			return PREFIX + plugin.getLanguagesManager().getPlayer(p).translate(NO_PERMISSIONS);
		}
	}

	public String NO_PLAYER(UUID p) {
		if(p == null) {
			return PREFIX + plugin.getLanguagesManager().getDefaultLanguage().translate(NO_PLAYER);
		} else {
			return PREFIX + plugin.getLanguagesManager().getPlayer(p).translate(NO_PLAYER);
		}
	}

	public String translate(UUID p, String msg) {
		if(p != null) {
			return PREFIX + plugin.getLanguagesManager().getPlayer(p).translate(msg);
		} else {
			return PREFIX + plugin.getLanguagesManager().getDefaultLanguage().translate(msg);
		}
	}

	public String getHelpMessage(UUID p, String... help) {
		return getHelpMessage(plugin.getLanguagesManager().getPlayer(p), help);
	}

	public String getHelpMessage(String... help) {
		return getHelpMessage(plugin.getLanguagesManager().getDefaultLanguage(), help);
	}

}
