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
            if (args.length == 2) {
                if (hasPermissions(p, "ch.loc")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (hasPermissions(p, "ch.loc.set")) {
                            cfg.saveLocation("location." + args[1], p.getLocation());
                            Location loc = cfg.getLocation("location." + args[1]);

                            p.sendMessage(PREFIX + LanguagesManager.translate("Die Location wurde erfolgreich gespiechert. Alle Informationen: ", p.getUniqueId()));
                            p.sendMessage(LanguagesManager.translate("§6X: ", p.getUniqueId()) + loc.getX());
                            p.sendMessage(LanguagesManager.translate("§6Y: ", p.getUniqueId()) + loc.getY());
                            p.sendMessage(LanguagesManager.translate("§6Z: ", p.getUniqueId()) + loc.getZ());
                            p.sendMessage(LanguagesManager.translate("§6Yaw: ", p.getUniqueId()) + loc.getYaw());
                            p.sendMessage(LanguagesManager.translate("§6Pitch: ", p.getUniqueId()) + loc.getPitch());
                        } else {
                            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                        }
                    } else if (args[0].equalsIgnoreCase("info")) {
                        if (hasPermissions(p, "ch.loc.info")) {
                            try {
                                Location loc = cfg.getLocation("location." + args[1]);
                                p.sendMessage(PREFIX + LanguagesManager.translate("§aAlle Informationen zur Location: §6" + args[1], p.getUniqueId()));
                                p.sendMessage(LanguagesManager.translate("§6X: ", p.getUniqueId()) + loc.getX());
                                p.sendMessage(LanguagesManager.translate("§6Y: ", p.getUniqueId()) + loc.getY());
                                p.sendMessage(LanguagesManager.translate("§6Z: ", p.getUniqueId()) + loc.getZ());
                                p.sendMessage(LanguagesManager.translate("§6Yaw: ", p.getUniqueId()) + loc.getYaw());
                                p.sendMessage(LanguagesManager.translate("§6Pitch: ", p.getUniqueId()) + loc.getPitch());
                            } catch (Exception e) {
                                p.sendMessage(PREFIX + LanguagesManager.translate("§cDiese Location wurde nicht gefunden", p.getUniqueId()));
                            }
                        } else {
                            s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                        }
                    } else {
                        s.sendMessage(getHelpMessage(p, "location [set/info/list]"));
                    }
                } else {
                    s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    if(hasPermissions(p, "ch.loc.list")) {
                        for(String c : cfg.cfg().getConfigurationSection("location").getKeys(false)) {
                            p.sendMessage("§6" + c);
                        }
                    } else {
                        s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                    }
                } else {
                    s.sendMessage(getHelpMessage(p, "location [set/info/list]"));
                }
            } else {
                s.sendMessage(getHelpMessage(p, "location [set/info/list]"));
            }
        } else {
            s.sendMessage(LanguagesManager.translate(NO_PLAYER, "en"));
        }
        return false;
    }

}
