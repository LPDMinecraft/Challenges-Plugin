package de.lpd.challenges.utils;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Command extends Starter implements CommandExecutor, Listener, TabCompleter {
	
	protected static ChallengesMainClass plugin;
	//private volatile HashMap<String[], ArrayList<String>> autoCompleteList;

	public Command(ChallengesMainClass plugin) {
		this.plugin = plugin;
		// autoCompleteList = new HashMap<>();
		plugin.registerListener(this);
	}
	
	public abstract boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args);
	
	@Override
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
		return run(arg0, arg1, arg2, arg3);
	}

	public boolean hasPermissions(Player p, String perm) {
		return ChallengesMainClass.getPermsManager().hasPermissions(p, perm);
	}

	public de.lpd.challenges.tabcompleter.TabCompleter addTabComplete(String commandSlah, String[] beforeArgs, ArrayList<String> possebilitis) {
		return new de.lpd.challenges.tabcompleter.TabCompleter(plugin, commandSlah, beforeArgs, possebilitis);
	}
	
	public String getHelpMessage(Player p, String... help) {
		String end = PREFIX + "§cBitte benutze §6/" + help[0];
		for(int i = 1; i < help.length; i++) {
			end = end + " §coder §6/" + help[i];
		}
		end = end + " §c!";
		if(p == null) {
			return end;
		}
		return LanguagesManager.translate(end, p.getUniqueId());
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		return null;
	}
}
