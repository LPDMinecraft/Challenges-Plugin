package de.lpd.challenges.invs.impl;

import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.HeadBuilder;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Languages extends Inventory  {

    public Languages(ChallengesMainClass plugin) {
        super(plugin, 6*9, true, "Sprachen", "menu", "Menu");
    }

    @Override
    public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {

    }

    @Override
    public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
        inv = placeHolder(inv);

        inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

        ArrayList<ItemStack> items = new ArrayList<>();

        items.add(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cIst in Arbeit").build());

        return getPage(items, inv, page);
    }

}
