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

    public Randomizer(ChallengesMainClass plugin) {
        super(plugin, "randomizer", "randomizer.yml", "randomizer", 3*9, true, "Randomizer", "chmenu", "challenge-randomizer", "Challenges Menu");
    }

    @Override
    public void cfg(Config cfg) { }

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

    String randomizer1 = "",
           randomizer1_anzahl_plus_min = "",
           randomizer1_anzahl_plus_max = "",
           randomizer1_anzahl_minus_min = "",
           randomizer1_anzahl_minus_max = "",
            randomizer1_types_plus_min = "",
            randomizer1_types_plus_max = "",
            randomizer1_types_minus_min = "",
            randomizer1_types_minus_max = "",
           randomizer1_saveInListOrConfig = "";

    String randomizer2 = "",
            randomizer2_anzahl_plus_min = "",
            randomizer2_anzahl_plus_max = "",
            randomizer2_anzahl_minus_min = "",
            randomizer2_anzahl_minus_max = "",
            randomizer2_types_plus_min = "",
            randomizer2_types_plus_max = "",
            randomizer2_types_minus_min = "",
            randomizer2_types_minus_max = "",
            randomizer2_saveInListOrConfig = "";

    String randomizer3 = "",
            randomizer3_anzahl_plus_min = "",
            randomizer3_anzahl_plus_max = "",
            randomizer3_anzahl_minus_min = "",
            randomizer3_anzahl_minus_max = "",
            randomizer3_types_plus_min = "",
            randomizer3_types_plus_max = "",
            randomizer3_types_minus_min = "",
            randomizer3_types_minus_max = "",
            randomizer3_saveInListOrConfig = "";

    String randomizer4 = "",
            randomizer4_anzahl_plus_min = "",
            randomizer4_anzahl_plus_max = "",
            randomizer4_anzahl_minus_min = "",
            randomizer4_anzahl_minus_max = "",
            randomizer4_types_plus_min = "",
            randomizer4_types_plus_max = "",
            randomizer4_types_minus_min = "",
            randomizer4_types_minus_max = "",
            randomizer4_saveInListOrConfig = "";

    public void reloadNames(Player p) {
        randomizer1 = LanguagesManager.translate("§6Randomizer: Blöcke (" + isEnabled("blocks"), p.getUniqueId());
        randomizer1_anzahl_plus_min = LanguagesManager.translate("+1 Minimum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_plus_max = LanguagesManager.translate("+1 Maximum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_minus_min = LanguagesManager.translate("-1 Minimum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_anzahl_minus_max = LanguagesManager.translate("-1 Maximum an Anzahl von Blöcken", p.getUniqueId());
        randomizer1_types_plus_min = LanguagesManager.translate("+1 Minimum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_plus_max = LanguagesManager.translate("+1 Maximum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_minus_min = LanguagesManager.translate("-1 Minimum an Typen von Blöcken", p.getUniqueId());
        randomizer1_types_minus_max = LanguagesManager.translate("-1 Maximum an Typen von Blöcken", p.getUniqueId());
        randomizer1_saveInListOrConfig = LanguagesManager.translate("Speichern in Config(" + isToggled("config-blocks") + ")", p.getUniqueId());

        randomizer2 = LanguagesManager.translate("§6Randomizer: Crafting (" + isEnabled("crafting"), p.getUniqueId());
        randomizer2_anzahl_plus_min = LanguagesManager.translate("+1 Minimum an Anzahl von Items", p.getUniqueId());
        randomizer2_anzahl_plus_max = LanguagesManager.translate("+1 Maximum an Anzahl von Items", p.getUniqueId());
        randomizer2_anzahl_minus_min = LanguagesManager.translate("-1 Minimum an Anzahl von Items", p.getUniqueId());
        randomizer2_anzahl_minus_max = LanguagesManager.translate("-1 Maximum an Anzahl von Items", p.getUniqueId());
        randomizer2_types_plus_min = LanguagesManager.translate("+1 Minimum an Typen von Items", p.getUniqueId());
        randomizer2_types_plus_max = LanguagesManager.translate("+1 Maximum an Typen von Items", p.getUniqueId());
        randomizer2_types_minus_min = LanguagesManager.translate("-1 Minimum an Typen von Items", p.getUniqueId());
        randomizer2_types_minus_max = LanguagesManager.translate("-1 Maximum an Typen von Items", p.getUniqueId());
        randomizer2_saveInListOrConfig = LanguagesManager.translate("Speichern in Config(" + isToggled("config-crafting") + ")", p.getUniqueId());

        randomizer3 = LanguagesManager.translate("§6Randomizer: Mob Drops (" + isEnabled("mobdrops"), p.getUniqueId());
        randomizer3_anzahl_plus_min = LanguagesManager.translate("+1 Minimum an Anzahl von Items", p.getUniqueId());
        randomizer3_anzahl_plus_max = LanguagesManager.translate("+1 Maximum an Anzahl von Items", p.getUniqueId());
        randomizer3_anzahl_minus_min = LanguagesManager.translate("-1 Minimum an Anzahl von Items", p.getUniqueId());
        randomizer3_anzahl_minus_max = LanguagesManager.translate("-1 Maximum an Anzahl von Items", p.getUniqueId());
        randomizer3_types_plus_min = LanguagesManager.translate("+1 Minimum an Typen von Items", p.getUniqueId());
        randomizer3_types_plus_max = LanguagesManager.translate("+1 Maximum an Typen von Items", p.getUniqueId());
        randomizer3_types_minus_min = LanguagesManager.translate("-1 Minimum an Typen von Items", p.getUniqueId());
        randomizer3_types_minus_max = LanguagesManager.translate("-1 Maximum an Typen von Items", p.getUniqueId());
        randomizer3_saveInListOrConfig = LanguagesManager.translate("Speichern in Config(" + isToggled("config-mobdrops") + ")", p.getUniqueId());

        randomizer4 = LanguagesManager.translate("§6Randomizer: Mobs (" + isEnabled("mobs"), p.getUniqueId());
        randomizer4_anzahl_plus_min = LanguagesManager.translate("+1 Minimum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_plus_max = LanguagesManager.translate("+1 Maximum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_minus_min = LanguagesManager.translate("-1 Minimum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_anzahl_minus_max = LanguagesManager.translate("-1 Maximum an Anzahl von Mobs", p.getUniqueId());
        randomizer4_types_plus_min = LanguagesManager.translate("+1 Minimum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_plus_max = LanguagesManager.translate("+1 Maximum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_minus_min = LanguagesManager.translate("-1 Minimum an Typen von Mobs", p.getUniqueId());
        randomizer4_types_minus_max = LanguagesManager.translate("-1 Maximum an Typen von Mobs", p.getUniqueId());
        randomizer4_saveInListOrConfig = LanguagesManager.translate("Speichern in Config(" + isToggled("config-mobs") + ")", p.getUniqueId());
    }

    @Override
    public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
        reloadNames(p);
    }

    @Override
    public Inventory getInventory(int page, Player p) {
        inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);
        ArrayList<ItemStack> items = new ArrayList<>();
        reloadNames(p);

        switch (page) {
            case 1:
                Material m;
                if(isEnabled("blocks")) {
                    m = Material.EMERALD_BLOCK;
                } else {
                    m = Material.REDSTONE_BLOCK;
                }
                inv.setItem(9, new ItemBuilder(m).setDisplayName(randomizer1).build());
                break;
        }

        inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

        return getPage(items, inv, page, 1);
    }
}
