package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends Command {

    public HelpCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            s.sendMessage(LanguagesManager.getPlayer(((Player) s).getUniqueId()).getHelp());
        } else {
            s.sendMessage(LanguagesManager.getLanguage("en").getHelp());
        }
        return false;
    }

}
