package de.lpd.challenges.languages.impl;

import de.lpd.challenges.languages.Language;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class English extends Language  {

    public English() {
        super("en", "English", "English");
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER).setDisplayName("§6EN - English - Englisch").build();
    }

    @Override
    public void onClick(Player p, ItemStack item, InventoryClickEvent e) {
        
    }
}
