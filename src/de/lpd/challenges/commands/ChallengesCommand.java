package de.lpd.challenges.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;

public class ChallengesCommand extends Command {
	
	public ChallengesCommand(ChallengesMainClass plugin) {
		super(plugin);
	}

	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(args.length == 0) {
				p.openInventory(ChallengesMainClass.getInvManager().getInventory(0));
				p.sendMessage(PREFIX + "§aDas Inventar wurde geöffnet.");
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reset")) {
					if(ChallengesManager.idtoclass != null) {
						for(Challenge ch : ChallengesManager.idtoclass.values()) {
							ch.reset();
						}
					}
					p.sendMessage(PREFIX + "§aAlle Einstellungen wurden erfolgreich reseted.");
				} else {
					p.sendMessage(getHelpMessage("challenges", "challenges [reset]", "ch", "ch [reset]"));
				}
			} else {
				p.sendMessage(getHelpMessage("challenges", "challenges [reset]", "ch", "ch [reset]"));
			}
		} else {
			s.sendMessage(NO_PLAYER);
		}
		return false;
	}
	
}
