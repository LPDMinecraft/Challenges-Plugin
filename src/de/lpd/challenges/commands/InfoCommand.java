package de.lpd.challenges.commands;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.command.CommandSender;

public class InfoCommand extends Command {

    public InfoCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        s.sendMessage("§7-------------------------------------------------------");
        s.sendMessage("");
        s.sendMessage("§aThis Plugin was created by §6LPDMinecraft");
        s.sendMessage("");
        s.sendMessage("§7-------------------------------------------------------");
        return false;
    }

}
