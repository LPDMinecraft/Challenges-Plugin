package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HealCommand extends Command  {

    public HealCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        ArrayList all = new ArrayList<String>();
        all.add("*");
        for(Player p : Bukkit.getOnlinePlayers()) {
            all.add(p.getName());
        }
        addTabComplete(cmd.getName(), new String[0], all);

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("*")) {
                if(s instanceof Player) {
                    if(hasPermissions((Player) s, "ch.heal.all")) {
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.setHealth(p.getMaxHealth());
                            p.setFoodLevel(10);
                            p.sendMessage(PREFIX + LanguagesManager.translate("§aDu wurdest von §6", p.getUniqueId()) + s.getName() + LanguagesManager.translate("§a gehealt", p.getUniqueId()));
                            if(s instanceof Player) {
                                s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", p.getUniqueId()) + p.getName() + LanguagesManager.translate("§a gehealt.", p.getUniqueId()));
                            } else {
                                s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", "en") + p.getName() + LanguagesManager.translate("§a gehealt.", "en"));
                            }
                        }
                    } else {
                        s.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, ((Player) s).getUniqueId()));
                    }
                } else {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.setHealth(p.getMaxHealth());
                        p.setFoodLevel(10);
                        p.sendMessage(PREFIX + LanguagesManager.translate("§aDu wurdest von §6", p.getUniqueId()) + s.getName() + LanguagesManager.translate("§a gehealt", p.getUniqueId()));
                        if(s instanceof Player) {
                            s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", p.getUniqueId()) + p.getName() + LanguagesManager.translate("§a gehealt.", p.getUniqueId()));
                        } else {
                            s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", "en") + p.getName() + LanguagesManager.translate("§a gehealt.", "en"));
                        }
                    }
                }
            } else {
                boolean perms = false;

                if(s instanceof Player) {
                    if(hasPermissions((Player) s, "ch.heal")) {
                        perms = true;
                    } else {
                        perms = false;
                        return false;
                    }
                } else {
                    perms = true;
                }

                if(perms) {
                    Player c = null;
                    try {
                        c = Bukkit.getPlayer(args[0]);
                    } catch (Exception e) {
                        if(s instanceof Player) {
                            s.sendMessage(PREFIX + LanguagesManager.translate(NO_PLAYER_FOUND, ((Player) s).getUniqueId()));
                        } else {
                            s.sendMessage(PREFIX + LanguagesManager.translate(NO_PLAYER_FOUND, "en"));
                        }
                        return false;
                    }
                    if(c == null) return false;
                    c.setHealth(c.getMaxHealth());
                    c.setFoodLevel(10);
                    c.sendMessage(PREFIX + LanguagesManager.translate("§aDu wurdest von §6", c.getUniqueId()) + s.getName() + LanguagesManager.translate("§a gehealt", c.getUniqueId()));
                    if(s instanceof Player) {
                        s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", c.getUniqueId()) + c.getName() + LanguagesManager.translate("§a gehealt.", ((Player) s).getUniqueId()));
                    } else {
                        s.sendMessage(PREFIX + LanguagesManager.translate("§aDu hast §6", "en") + c.getName() + LanguagesManager.translate("§a gehealt.", "en"));
                    }
                }
            }
        } else {
            s.sendMessage(getHelpMessage(null, "heal [*, Player]"));
        }

        return false;
    }

}
