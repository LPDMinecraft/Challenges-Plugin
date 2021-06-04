package de.lpd.challenges.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;

public class ChallengesCommand extends Command{
	
	public ChallengesCommand(ChallengesMainClass plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			((Player) s).openInventory(ChallengesMainClass.getInvManager().getInventory(0));
			s.sendMessage(PREFIX + "§aDas Inventar wurde geöffnet.");
		} else {
			s.sendMessage(NO_PLAYER);
		}
		return false;
	}
	
}
