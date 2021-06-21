package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class EntchantCommand extends Command {

    public EntchantCommand(ChallengesMainClass plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player) s;
            // [entchantment] []
            if(args.length == 2) {
                if(hasPermissions(p, "ch.entchant.self")) {
                    if(p.getItemInHand() != null &&
                            p.getItemInHand().getType() != null &&
                            p.getItemInHand().getType() != Material.AIR) {

                        if(Enchantment.getByName(args[0]) != null) {
                            Enchantment ench = Enchantment.getByName(args[0]);
                            int levels;
                            try {
                                levels = Integer.valueOf(args[1]);
                            } catch (Exception e) {
                                p.sendMessage(PREFIX + LanguagesManager.translate("§cDu musst ein Level als Zahl eingeben!", p.getUniqueId()));
                                return false;
                            }
                            entchantPlayer(p, ench, levels);
                            p.sendMessage(PREFIX + LanguagesManager.translate("§aDir wurde erfolgreich das Entchantment §6", p.getUniqueId()) +
                                    ench.getName() + " " + levels + " " + LanguagesManager.translate("Level geupgradet.", p.getUniqueId()));
                        } else {
                            p.sendMessage(PREFIX + LanguagesManager.translate("§cDieses Entchantment existiert nicht!", p.getUniqueId()));
                        }

                    } else {
                        p.sendMessage(PREFIX + LanguagesManager.translate("§cDu musst ein Item in der Hand haben!", p.getUniqueId()));
                    }
                } else {
                    p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                }
            } else if(args.length == 3) {
                if(hasPermissions(p, "ch.entchant.others")) {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player c = Bukkit.getPlayer(args[0]);

                        if(c.getItemInHand() != null &&
                                c.getItemInHand().getType() != null &&
                                c.getItemInHand().getType() != Material.AIR) {

                            if(Enchantment.getByName(args[1]) != null) {
                                Enchantment ench = Enchantment.getByName(args[1]);
                                int levels;
                                try {
                                    levels = Integer.valueOf(args[2]);
                                } catch (Exception e) {
                                    p.sendMessage(PREFIX + LanguagesManager.translate("§cDu musst ein Level als Zahl eingeben!", p.getUniqueId()));
                                    return false;
                                }
                                entchantPlayer(c, ench, levels);
                                p.sendMessage(PREFIX + LanguagesManager.translate("§aDen Spieler wurde erfolgreich das Entchantment §6", p.getUniqueId()) +
                                        ench.getName() + " " + levels + " " + LanguagesManager.translate("Level geupgradet.", p.getUniqueId()));
                                c.sendMessage(PREFIX + LanguagesManager.translate("§aDir wurde wurde das Entchantment §6", p.getUniqueId()) +
                                        ench.getName() + " " + levels + " " + LanguagesManager.translate("Level geupgradet.", p.getUniqueId()));
                            } else {
                                p.sendMessage(PREFIX + LanguagesManager.translate("§cDieses Entchantment existiert nicht!", p.getUniqueId()));
                            }

                        } else {
                            p.sendMessage(PREFIX + LanguagesManager.translate("§cDer Spieler hat kein Item in der Hand!", p.getUniqueId()));
                        }
                    } else {
                        p.sendMessage(LanguagesManager.translate(NO_PLAYER_FOUND, p.getUniqueId()));
                    }
                } else {
                    p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    if(hasPermissions(p, "ch.entchant.list")) {
                        for(Enchantment enchantment : Enchantment.values()) {
                            p.sendMessage(LanguagesManager.translate("§6" + enchantment.getName(), p.getUniqueId()));
                        }
                    } else {
                        p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
                    }
                } else {
                    p.sendMessage(getHelpMessage(p, "entchant [Entchantment] [Level]", "entchant [Spieler] [Entchantment] [Level]", "enchant list"));
                }
            } else {
                p.sendMessage(getHelpMessage(p, "entchant [Entchantment] [Level]", "entchant [Spieler] [Entchantment] [Level]", "enchant list"));
            }
        } else {
            s.sendMessage(LanguagesManager.translate(NO_PLAYER, "en"));
        }
        return false;
    }

    public void entchantPlayer(Player p, Enchantment enchantment, int level) {
        if(p.getItemInHand().containsEnchantment(enchantment)) {
            level = level + p.getItemInHand().getEnchantmentLevel(enchantment);
        }
        p.getItemInHand().addUnsafeEnchantment(enchantment, level);
    }

}
