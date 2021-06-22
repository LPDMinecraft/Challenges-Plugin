package de.lpd.challenges.chg.impl.Randomizer;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;
import org.apache.logging.log4j.core.appender.ScriptAppenderSelector;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Randomizer extends Challenge {

    private Config cfg;

    public Randomizer(ChallengesMainClass plugin) {
        super(plugin, "randomizer", "randomizer.yml", "randomizer", 3*9, true, "Randomizer", "chmenu", "challenge-randomizer", "Challenges Menu");
    }

    @Override
    public void cfg(Config cfg) {
        this.cfg = cfg;
    }

    @Override
    public ItemStack getItem(Player p) {
        ItemBuilder ib = new ItemBuilder(Material.WATER_BUCKET);
        ib.setDisplayName(LanguagesManager.translate("§6Randomizer", p.getUniqueId()));
        String[] lore = new String[3];
        lore[0] = Starter.START_PREFIX + LanguagesManager.translate("§aIn werden bestimmte Dinge vertauscht.", p.getUniqueId());
        lore[1] = LanguagesManager.translate("§azum Beispiel Mobs, Blöcke, Loot, Crafting", p.getUniqueId());
        lore[2] = LanguagesManager.translate("§6Mittelklick §7> §aÖffne das Inventar", p.getUniqueId());

        ib.setLoreString(lore);
        return ib.build();
    }

    @Override
    public void onRightClick(Player p) { }

    @Override
    public void onLeftClick(Player p) { }

    @Override
    public void onMiddleClick(Player p) {
        p.openInventory(getInventory(1, p));
    }

    @Override
    public void reset() { }

    @Override
    public void ifPlayerDies(Player p) { }

    String  randomizer1 = "",
            randomizer1_anzahl_plus_min = "",
            randomizer1_anzahl_plus_max = "",
            randomizer1_anzahl_minus_min = "",
            randomizer1_anzahl_minus_max = "",
            randomizer1_anzahl_min = "",
            randomizer1_anzahl_max = "",
            randomizer1_types_plus_min = "",
            randomizer1_types_plus_max = "",
            randomizer1_types_minus_min = "",
            randomizer1_types_minus_max = "",
            randomizer1_types_min = "",
            randomizer1_types_max = "",
            randomizer1_saveInListOrConfig = "";

    String  randomizer2 = "",
            randomizer2_anzahl_plus_min = "",
            randomizer2_anzahl_plus_max = "",
            randomizer2_anzahl_minus_min = "",
            randomizer2_anzahl_minus_max = "",
            randomizer2_anzahl_min = "",
            randomizer2_anzahl_max = "",
            randomizer2_types_plus_min = "",
            randomizer2_types_plus_max = "",
            randomizer2_types_minus_min = "",
            randomizer2_types_minus_max = "",
            randomizer2_types_min = "",
            randomizer2_types_max = "",
            randomizer2_saveInListOrConfig = "";

    String  randomizer3 = "",
            randomizer3_anzahl_plus_min = "",
            randomizer3_anzahl_plus_max = "",
            randomizer3_anzahl_minus_min = "",
            randomizer3_anzahl_minus_max = "",
            randomizer3_anzahl_min = "",
            randomizer3_anzahl_max = "",
            randomizer3_types_plus_min = "",
            randomizer3_types_plus_max = "",
            randomizer3_types_minus_min = "",
            randomizer3_types_minus_max = "",
            randomizer3_types_min = "",
            randomizer3_types_max = "",
            randomizer3_saveInListOrConfig = "";

    String  randomizer4 = "",
            randomizer4_anzahl_plus_min = "",
            randomizer4_anzahl_plus_max = "",
            randomizer4_anzahl_minus_min = "",
            randomizer4_anzahl_minus_max = "",
            randomizer4_anzahl_min = "",
            randomizer4_anzahl_max = "",
            randomizer4_types_plus_min = "",
            randomizer4_types_plus_max = "",
            randomizer4_types_minus_min = "",
            randomizer4_types_minus_max = "",
            randomizer4_types_min = "",
            randomizer4_types_max = "",
            randomizer4_saveInListOrConfig = "";

    public void reloadNames(Player p) {
        randomizer1 = LanguagesManager.translate("§6Randomizer: Blöcke (" + isEnabled("blocks"), p.getUniqueId());
        randomizer1_anzahl_plus_min = LanguagesManager.translate("§6+1 Minimum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_plus_max = LanguagesManager.translate("§6+1 Maximum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_minus_min = LanguagesManager.translate("§6-1 Minimum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_minus_max = LanguagesManager.translate("§6-1 Maximum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_types_plus_min = LanguagesManager.translate("§6+1 Minimum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_plus_max = LanguagesManager.translate("§6+1 Maximum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_minus_min = LanguagesManager.translate("§6-1 Minimum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_minus_max = LanguagesManager.translate("§6-1 Maximum an Typen von Blöcken", p.getUniqueId());
        randomizer1_saveInListOrConfig = LanguagesManager.translate("§6Speichern in Config(" + isToggled("config-blocks") + ")", p.getUniqueId());
        if(!cfg.cfg().contains("random.block.amount.min")) cfg.cfg().set("random.block.amount.min", 1.00);
        if(!cfg.cfg().contains("random.block.amount.max")) cfg.cfg().set("random.block.amount.max", 2.00);
        if(!cfg.cfg().contains("random.block.types.min")) cfg.cfg().set("random.block.types.min", 1.00);
        if(!cfg.cfg().contains("random.block.types.max")) cfg.cfg().set("random.block.types.max", 4.00);
        cfg.save();
        randomizer1_anzahl_min = LanguagesManager.translate("§6Derzeitige minimale Anzahl von Blöcken: ", p.getUniqueId()) + cfg.cfg().get("random.block.amount.min");
        randomizer1_anzahl_max = LanguagesManager.translate("§6Derzeitige maximale Anzahl von Blöcken: ", p.getUniqueId()) + cfg.cfg().get("random.block.amount.max");
        randomizer1_types_min = LanguagesManager.translate("§6Derzeitige minimale Types von Blöcken: ", p.getUniqueId()) + cfg.cfg().get("random.block.types.min");
        randomizer1_types_max = LanguagesManager.translate("§6Derzeitige maximale Types von Blöcken: ", p.getUniqueId()) + cfg.cfg().get("random.block.types.max");

        randomizer2 = LanguagesManager.translate("§6Randomizer: Crafting (" + isEnabled("crafting"), p.getUniqueId());
        randomizer2_anzahl_plus_min = LanguagesManager.translate("§6+1 Minimum an Anzahl von Output Items", p.getUniqueId());
        randomizer2_anzahl_plus_max = LanguagesManager.translate("§6+1 Maximum an Anzahl von Output Items", p.getUniqueId());
        randomizer2_anzahl_minus_min = LanguagesManager.translate("§6-1 Minimum an Anzahl von Output Items", p.getUniqueId());
        randomizer2_anzahl_minus_max = LanguagesManager.translate("§6-1 Maximum an Anzahl von Output Items", p.getUniqueId());
        randomizer2_types_plus_min = LanguagesManager.translate("§6+1 Minimum an Typen von Output Items", p.getUniqueId());
        randomizer2_types_plus_max = LanguagesManager.translate("§6+1 Maximum an Typen von Output Items", p.getUniqueId());
        randomizer2_types_minus_min = LanguagesManager.translate("§6-1 Minimum an Typen von Output Items", p.getUniqueId());
        randomizer2_types_minus_max = LanguagesManager.translate("§6-1 Maximum an Typen von Output Items", p.getUniqueId());
        randomizer2_saveInListOrConfig = LanguagesManager.translate("§6Speichern in Config(" + isToggled("config-crafting") + ")", p.getUniqueId());
        if(!cfg.cfg().contains("random.crafting.amount.min")) cfg.cfg().set("random.crafting.amount.min", 1.00);
        if(!cfg.cfg().contains("random.crafting.amount.max")) cfg.cfg().set("random.crafting.amount.max", 2.00);
        if(!cfg.cfg().contains("random.crafting.types.min")) cfg.cfg().set("random.crafting.types.min", 1.00);
        if(!cfg.cfg().contains("random.crafting.types.max")) cfg.cfg().set("random.crafting.types.max", 4.00);
        cfg.save();
        randomizer2_anzahl_min = LanguagesManager.translate("§6Derzeitige minimale Anzahl von Output Items: ", p.getUniqueId()) + cfg.cfg().get("random.crafting.amount.min");
        randomizer2_anzahl_max = LanguagesManager.translate("§6Derzeitige maximale Anzahl von Output Items: ", p.getUniqueId()) + cfg.cfg().get("random.crafting.amount.max");
        randomizer2_types_min = LanguagesManager.translate("§6Derzeitige minimale Types von Output Items: ", p.getUniqueId()) + cfg.cfg().get("random.crafting.types.min");
        randomizer2_types_max = LanguagesManager.translate("§6Derzeitige maximale Types von Output Items: ", p.getUniqueId()) + cfg.cfg().get("random.crafting.types.max");

        randomizer3 = LanguagesManager.translate("§6Randomizer: Loot (" + isEnabled("loot"), p.getUniqueId());
        randomizer3_anzahl_plus_min = LanguagesManager.translate("§6+1 Minimum an Anzahl von Loot Items", p.getUniqueId());
        randomizer3_anzahl_plus_max = LanguagesManager.translate("§6+1 Maximum an Anzahl von Loot Items", p.getUniqueId());
        randomizer3_anzahl_minus_min = LanguagesManager.translate("§6-1 Minimum an Anzahl von Loot Items", p.getUniqueId());
        randomizer3_anzahl_minus_max = LanguagesManager.translate("§6-1 Maximum an Anzahl von Loot Items", p.getUniqueId());
        randomizer3_types_plus_min = LanguagesManager.translate("§6+1 Minimum an Typen von Loot Items", p.getUniqueId());
        randomizer3_types_plus_max = LanguagesManager.translate("§6+1 Maximum an Typen von Loot Items", p.getUniqueId());
        randomizer3_types_minus_min = LanguagesManager.translate("§6-1 Minimum an Typen von Loot Items", p.getUniqueId());
        randomizer3_types_minus_max = LanguagesManager.translate("§6-1 Maximum an Typen von Loot Items", p.getUniqueId());
        randomizer3_saveInListOrConfig = LanguagesManager.translate("§6Speichern in Config(" + isToggled("config-loot") + ")", p.getUniqueId());
        if(!cfg.cfg().contains("random.loot.amount.min")) cfg.cfg().set("random.loot.amount.min", 1.00);
        if(!cfg.cfg().contains("random.loot.amount.max")) cfg.cfg().set("random.loot.amount.max", 2.00);
        if(!cfg.cfg().contains("random.loot.types.min")) cfg.cfg().set("random.loot.types.min", 1.00);
        if(!cfg.cfg().contains("random.loot.types.max")) cfg.cfg().set("random.loot.types.max", 4.00);
        cfg.save();
        randomizer3_anzahl_min = LanguagesManager.translate("§6Derzeitige minimale Anzahl von Loot Items: ", p.getUniqueId()) + cfg.cfg().get("random.loot.amount.min");
        randomizer3_anzahl_max = LanguagesManager.translate("§6Derzeitige maximale Anzahl von Loot Items: ", p.getUniqueId()) + cfg.cfg().get("random.loot.amount.max");
        randomizer3_types_min = LanguagesManager.translate("§6Derzeitige minimale Types von Loot Items: ", p.getUniqueId()) + cfg.cfg().get("random.loot.types.min");
        randomizer3_types_max = LanguagesManager.translate("§6Derzeitige maximale Types von Loot Items: ", p.getUniqueId()) + cfg.cfg().get("random.loot.types.max");

        randomizer4 = LanguagesManager.translate("§6Randomizer: Mobs (" + isEnabled("mobs"), p.getUniqueId());
        randomizer4_anzahl_plus_min = LanguagesManager.translate("§6+1 Minimum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_plus_max = LanguagesManager.translate("§6+1 Maximum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_minus_min = LanguagesManager.translate("§6-1 Minimum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_minus_max = LanguagesManager.translate("§6-1 Maximum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_types_plus_min = LanguagesManager.translate("§6+1 Minimum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_plus_max = LanguagesManager.translate("§6+1 Maximum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_minus_min = LanguagesManager.translate("§6-1 Minimum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_minus_max = LanguagesManager.translate("§6-1 Maximum an Typen von Mobs", p.getUniqueId());
        randomizer4_saveInListOrConfig = LanguagesManager.translate("§6Speichern in Config(" + isToggled("config-mobs") + ")", p.getUniqueId());
        if(!cfg.cfg().contains("random.mobs.amount.min")) cfg.cfg().set("random.mobs.amount.min", 1.00);
        if(!cfg.cfg().contains("random.mobs.amount.max")) cfg.cfg().set("random.mobs.amount.max", 2.00);
        if(!cfg.cfg().contains("random.mobs.types.min")) cfg.cfg().set("random.mobs.types.min", 1.00);
        if(!cfg.cfg().contains("random.mobs.types.max")) cfg.cfg().set("random.mobs.types.max", 4.00);
        cfg.save();
        randomizer4_anzahl_min = LanguagesManager.translate("§6Derzeitige minimale Anzahl von Mobs: ", p.getUniqueId()) + cfg.cfg().get("random.mobs.amount.min");
        randomizer4_anzahl_max = LanguagesManager.translate("§6Derzeitige maximale Anzahl von Mobs: ", p.getUniqueId()) + cfg.cfg().get("random.mobs.amount.max");
        randomizer4_types_min = LanguagesManager.translate("§6Derzeitige minimale Types von Mobs: ", p.getUniqueId()) + cfg.cfg().get("random.mobs.types.min");
        randomizer4_types_max = LanguagesManager.translate("§6Derzeitige maximale Types von Mobs: ", p.getUniqueId()) + cfg.cfg().get("random.mob.types.max");
    }

    @Override
    public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
        reloadNames(p);

        if(!cfg.cfg().contains("random.block.amount.min")) cfg.cfg().set("random.block.amount.min", 1.00);
        if(!cfg.cfg().contains("random.block.amount.max")) cfg.cfg().set("random.block.amount.max", 2.00);
        if(!cfg.cfg().contains("random.block.types.min")) cfg.cfg().set("random.block.types.min", 1.00);
        if(!cfg.cfg().contains("random.block.types.max")) cfg.cfg().set("random.block.types.max", 4.00);

        switch (page) {
            case 1:
                if(item.getItemMeta().getDisplayName().equals(randomizer1)) {
                    toggle("blocks");
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_anzahl_plus_min)) {
                    cfg.cfg().set("random.block.amount.min", cfg.cfg().getDouble("random.block.amount.min") + 1.00);
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_anzahl_minus_min)) {
                    if(cfg.cfg().getDouble("random.block.amount.min") > 1.00) {
                        if(cfg.cfg().getDouble("random.block.amount.max") > cfg.cfg().getDouble("random.block.amount.min")) {
                            cfg.cfg().set("random.block.amount.min", cfg.cfg().getDouble("random.block.amount.min") - 1.00);
                        }
                    }
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_anzahl_plus_max)) {
                    cfg.cfg().set("random.block.amount.max", cfg.cfg().getDouble("random.block.amount.max") + 1.00);
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_anzahl_minus_max)) {
                    if(cfg.cfg().getDouble("random.block.amount.max") > 1.00) {
                        if(cfg.cfg().getDouble("random.block.amount.max") > cfg.cfg().getDouble("random.block.amount.min")) {
                            cfg.cfg().set("random.block.amount.max", cfg.cfg().getDouble("random.block.amount.max") - 1.00);
                        }
                    }
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_types_plus_min)) {
                    cfg.cfg().set("random.block.types.min", cfg.cfg().getDouble("random.block.types.min") + 1.00);
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_types_minus_min)) {
                    if(cfg.cfg().getDouble("random.block.types.min") > 1.00) {
                        if(cfg.cfg().getDouble("random.block.types.max") > cfg.cfg().getDouble("random.block.types.min")) {
                            cfg.cfg().set("random.block.types.min", cfg.cfg().getDouble("random.block.types.min") - 1.00);
                        }
                    }
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_types_plus_max)) {
                    cfg.cfg().set("random.block.types.max", cfg.cfg().getDouble("random.block.types.max") + 1.00);
                } else if(item.getItemMeta().getDisplayName().equals(randomizer1_types_minus_max)) {
                    if(cfg.cfg().getDouble("random.block.types.max") > 1.00) {
                        if(cfg.cfg().getDouble("random.block.types.max") > cfg.cfg().getDouble("random.block.types.min")) {
                            cfg.cfg().set("random.block.types.max", cfg.cfg().getDouble("random.block.types.max") - 1.00);
                        }
                    }
                }
                cfg.save();
                break;
        }
    }

    @Override
    public Inventory getInventory(int page, Player p) {
        inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);
        ArrayList<ItemStack> items = new ArrayList<>();
        reloadNames(p);
        Material m;

        switch (page) {
            case 1:
                if(isEnabled("blocks")) {
                    m = Material.EMERALD_BLOCK;
                } else {
                    m = Material.REDSTONE_BLOCK;
                }
                inv.setItem(9, new ItemBuilder(m).setDisplayName(randomizer1).build());
                inv.setItem(1, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_anzahl_plus_min).build());
                inv.setItem(10, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer1_anzahl_min).build());
                inv.setItem(19, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_anzahl_minus_min).build());

                inv.setItem(2, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_anzahl_plus_max).build());
                inv.setItem(11, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer1_anzahl_max).build());
                inv.setItem(20, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_anzahl_minus_max).build());

                inv.setItem(4, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_types_plus_min).build());
                inv.setItem(13, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer1_types_min).build());
                inv.setItem(22, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_types_minus_min).build());

                inv.setItem(5, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_types_plus_max).build());
                inv.setItem(14, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer1_types_max).build());
                inv.setItem(23, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer1_types_minus_max).build());
                break;

            case 2:
                if(isEnabled("crafting")) {
                    m = Material.EMERALD_BLOCK;
                } else {
                    m = Material.REDSTONE_BLOCK;
                }
                inv.setItem(9, new ItemBuilder(m).setDisplayName(randomizer2).build());
                inv.setItem(1, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_anzahl_plus_min).build());
                inv.setItem(10, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer2_anzahl_min).build());
                inv.setItem(19, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_anzahl_minus_min).build());

                inv.setItem(2, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_anzahl_plus_max).build());
                inv.setItem(11, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer2_anzahl_max).build());
                inv.setItem(20, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_anzahl_minus_max).build());

                inv.setItem(4, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_types_plus_min).build());
                inv.setItem(13, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer2_types_min).build());
                inv.setItem(22, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_types_minus_min).build());

                inv.setItem(5, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_types_plus_max).build());
                inv.setItem(14, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer2_types_max).build());
                inv.setItem(23, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer2_types_minus_max).build());
                break;

            case 3:
                if(isEnabled("loot")) {
                    m = Material.EMERALD_BLOCK;
                } else {
                    m = Material.REDSTONE_BLOCK;
                }
                inv.setItem(9, new ItemBuilder(m).setDisplayName(randomizer3).build());
                inv.setItem(1, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_anzahl_plus_min).build());
                inv.setItem(10, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer3_anzahl_min).build());
                inv.setItem(19, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_anzahl_minus_min).build());

                inv.setItem(2, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_anzahl_plus_max).build());
                inv.setItem(11, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer3_anzahl_max).build());
                inv.setItem(20, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_anzahl_minus_max).build());

                inv.setItem(4, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_types_plus_min).build());
                inv.setItem(13, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer3_types_min).build());
                inv.setItem(22, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_types_minus_min).build());

                inv.setItem(5, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_types_plus_max).build());
                inv.setItem(14, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer3_types_max).build());
                inv.setItem(23, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer3_types_minus_max).build());
                break;

            case 4:
                if(isEnabled("mobs")) {
                    m = Material.EMERALD_BLOCK;
                } else {
                    m = Material.REDSTONE_BLOCK;
                }
                inv.setItem(9, new ItemBuilder(m).setDisplayName(randomizer4).build());
                inv.setItem(1, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_anzahl_plus_min).build());
                inv.setItem(10, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer4_anzahl_min).build());
                inv.setItem(19, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_anzahl_minus_min).build());

                inv.setItem(2, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_anzahl_plus_max).build());
                inv.setItem(11, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer4_anzahl_max).build());
                inv.setItem(20, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_anzahl_minus_max).build());

                inv.setItem(4, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_types_plus_min).build());
                inv.setItem(13, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer4_types_min).build());
                inv.setItem(22, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_types_minus_min).build());

                inv.setItem(5, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_types_plus_max).build());
                inv.setItem(14, new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(randomizer4_types_max).build());
                inv.setItem(23, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(randomizer4_types_minus_max).build());
                break;
        }

        inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

        return getPage(items, inv, page, 3);
    }
}
