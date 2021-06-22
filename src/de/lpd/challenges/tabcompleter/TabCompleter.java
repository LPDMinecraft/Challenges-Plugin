package de.lpd.challenges.tabcompleter;

import de.lpd.challenges.main.ChallengesMainClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements Listener, org.bukkit.command.TabCompleter {

    private String commandSlash;
    private String[] beforeArgs;
    private ArrayList<String> pos;

    public TabCompleter(ChallengesMainClass plugin, String commandSlah, String[] beforeArgs, ArrayList<String> possebilitis) {
        plugin.registerListener(this);
        this.commandSlash = commandSlah;
        this.beforeArgs = beforeArgs;
        this.pos = possebilitis;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSlash.equals(command.getName())) {
            if(strings == beforeArgs) {
                return pos;
            }
        }
        return null;
    }

}
