package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvSeeCommand extends Command {

    public InvSeeCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player) s;
            if(args.length == 1) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    Player c = Bukkit.getPlayer(args[0]);
                    p.openInventory(c.getInventory());
                    p.sendMessage(LanguagesManager.translate("§aDas Inventar vom Spieler wurde geöffnet", p.getUniqueId()));
                } else {
                    p.sendMessage(LanguagesManager.translate(NO_PLAYER_FOUND, p.getUniqueId()));
                }
            } else {
                p.sendMessage(getHelpMessage(p, "invsee [Player]"));
            }
        } else {
            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, "en"));
        }
        return false;
    }

}
