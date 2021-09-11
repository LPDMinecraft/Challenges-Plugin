package lcraft.challenges.commands;

import lcraft.challenges.api.chg.Challenge;
import lcraft.challenges.api.chg.ChallengesManager;
import lcraft.challenges.api.commands.Command;
import lcraft.challenges.api.main.ChallengesApi;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChallengesCommand extends Command {
	
	public ChallengesCommand(ChallengesApi plugin) {
		super(plugin);
	}

	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		/*ArrayList all = new ArrayList<String>();
		all.add("reset");
		addTabComplete(cmd.getName(), new String[0], all);*/

		if(s instanceof Player) {
			Player p = (Player) s;
			if(args.length == 0) {
				p.openInventory(ChallengesApi.getInvManager().invs.get("menu").getInventory(1, p));
				p.sendMessage(PREFIX + "§aDas Inventar wurde ge§ffnet.");
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reset")) {
					if(ChallengesManager.getIdtoclass() != null) {
						for(Challenge ch : ChallengesManager.getIdtoclass().values()) {
							ch.reset();
						}
					}
					p.sendMessage(PREFIX + "§aAlle Einstellungen wurden erfolgreich reseted.");
				} else {
					p.sendMessage(getHelpMessage(p, "challenges", "challenges [reset]", "ch", "ch [reset]"));
				}
			} else {
				p.sendMessage(getHelpMessage(p, "challenges", "challenges [reset]", "ch", "ch [reset]"));
			}
		} else {
			s.sendMessage(NO_PLAYER);
		}
		return false;
	}
	
}
