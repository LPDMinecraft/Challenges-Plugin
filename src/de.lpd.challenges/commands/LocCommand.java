package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import de.lpd.challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LocCommand extends Command {

    public LocCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        ArrayList all = new ArrayList<String>();
        all.add("set");
        all.add("info");
        all.add("list");
        addTabComplete(cmd.getName(), new String[0], all);
        Config cfg = new Config("locations.yml");

        if(s instanceof Player) {
            Player p = (Player) s;
            if(args.length == 2) {
                if(hasPermissions(p, "ch.loc")) {
                    if(args[0].equalsIgnoreCase("set")) {
                        if(hasPermissions(p, "ch.loc.set")) {
                            cfg.saveLocation("location." + args[0], p.getLocation());
                            Location loc = cfg.getLocation("location." + args[0]);

                            p.sendMessage(PREFIX + LanguagesManager.translate("Die Location wurde erfolgreich gespiechert. Alle Informationen: ", p.getUniqueId()));
                            p.sendMessage("X:" + loc.getX());
                            p.sendMessage("Y:" + loc.getY());
                            p.sendMessage("Z:" + loc.getZ());
                            p.sendMessage("Yaw:" + loc.getYaw());
                            p.sendMessage("Pitch:" + loc.getPitch());
                        } else {
                            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                        }
                    } else if(args[0].equalsIgnoreCase("info")) {
                        if(hasPermissions(p, "ch.loc.info")) {
                            try {
                                Location loc = cfg.getLocation("location." + args[0]);
                                p.sendMessage("X:" + loc.getX());
                                p.sendMessage("Y:" + loc.getY());
                                p.sendMessage("Z:" + loc.getZ());
                                p.sendMessage("Yaw:" + loc.getYaw());
                                p.sendMessage("Pitch:" + loc.getPitch());
                            } catch (Exception e) {
                                p.sendMessage(LanguagesManager.translate("Diese Location wurde nicht gefunden", p.getUniqueId()));
                            }
                        } else {
                            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                        }
                    } else if(args[0].equalsIgnoreCase("list")) {
                        if(hasPermissions(p, "ch.loc.list")) {

                        } else {
                            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                        }
                    } else {
                        s.sendMessage(getHelpMessage(p, "loc [set/info]", "location [set/info/list]"));
                    }
                } else {
                    s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                }
            } else {
                s.sendMessage(getHelpMessage(p, "loc [set/info]", "location [set/info/list]"));
            }
        } else {
            s.sendMessage(LanguagesManager.translate(NO_PLAYER, "en"));
        }
        return false;
    }

}
