package lcraft.challenges.api.commands;

import lcraft.challenges.api.main.ChallengesApi;

import java.util.ArrayList;

public class CommandManager {

    private ChallengesApi plugin;
    private ArrayList<Command> cmds;

    public CommandManager(ChallengesApi plugin) throws InterruptedException {
        this.plugin = plugin;
        cmds = new ArrayList<>();
    }

    public void registerCommand(String cmd, Command executor) {
        plugin.getCommand(cmd).setExecutor(executor);
        cmds.add(executor);
    }

    public ArrayList<String> docAllPermissions() {
        ArrayList<String> allPerms = new ArrayList<>();

        for(Command cmd : cmds) {
            allPerms = cmd.allPermissions(allPerms);
        }

        return allPerms;
    }

    public ArrayList<String> docAllTranslations() {
        ArrayList<String> allLangs = new ArrayList<>();

        for(Command cmd : cmds) {
            allLangs = cmd.allLanguages(allLangs);
        }

        return allLangs;
    }

}

