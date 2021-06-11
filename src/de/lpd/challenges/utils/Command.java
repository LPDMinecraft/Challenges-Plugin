package de.lpd.challenges.utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.lpd.challenges.main.ChallengesMainClass;

public abstract class Command extends Starter implements CommandExecutor {
	
	protected static ChallengesMainClass plugin;
	
	@SuppressWarnings("static-access")
	public Command(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	
	public abstract boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args);
	
	@Override
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
		return run(arg0, arg1, arg2, arg3);
	}
	
	public String getHelpMessage(String... help) {
		String end = PREFIX + "§cBitte benutze §6/" + help[0];
		for(int i = 1; i < help.length; i++) {
			end = end + " §coder §6/" + help[i];
		}
		end = end + " §c!";
		return end;
	}
	
}
