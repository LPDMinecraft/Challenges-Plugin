package de.lpd.challenges.chg.impl.Randomizer;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
           randomizer1_plus_min = "",
           randomizer1_plus_max = "",
           randomizer1_minus_min = "",
           randomizer1_minus_max = "",
           randomizer1_saveInListOrConfig = "";

    @Override
    public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {

    }

    @Override
    public Inventory getInventory(int page, Player p) {
        return null;
    }
}
