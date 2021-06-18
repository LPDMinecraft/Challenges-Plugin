package de.lpd.challenges.languages.impl;

import de.lpd.challenges.languages.Language;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class German extends Language  {

    public German() {
        super("de", "Deutsch", "German");
    }

    @Override
    public ItemStack getItem(Player p) {
        if(LanguagesManager.getPlayer(p.getUniqueId()) == this) {
            return new ItemBuilder(Material.PAPER).setDisplayName("§6DE - German - Deutsch §7| §aEnabled").build();
        }
        return new ItemBuilder(Material.PAPER).setDisplayName("§6DE - German - Deutsch").build();
    }

    @Override
    public void onClick(Player p, ItemStack item, InventoryClickEvent e) {

    }
}
