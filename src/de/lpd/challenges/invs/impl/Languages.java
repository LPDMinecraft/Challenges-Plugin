package de.lpd.challenges.invs.impl;

import de.lpd.challenges.chg.ChallengesManager;
import de.lpd.challenges.invs.Inventory;
import de.lpd.challenges.languages.Language;
import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.HeadBuilder;
import de.lpd.challenges.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Languages extends Inventory  {

    public Languages(ChallengesMainClass plugin) {
        super(plugin, 6*9, true, "Sprachen", "menu", "Menu");
    }

    @Override
    public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
        if(item.getType() != Material.BLACK_STAINED_GLASS_PANE) {
            for(Language l : LanguagesManager.langs.values()) {
                if(item.getItemMeta().getDisplayName().startsWith(l.getItem(p).getItemMeta().getDisplayName())) {
                    l.onClick(p, item, e);
                    String c = item.getItemMeta().getDisplayName().split(" - ")[0].toLowerCase().replace("§6", "");
                    LanguagesManager.setPlayer(p.getUniqueId(), LanguagesManager.getLanguage(c));
                    break;
                }
            }
        }
    }

    @Override
    public org.bukkit.inventory.Inventory getInventory(int page, Player p) {
        inv = placeHolder(inv);

        ArrayList<ItemStack> items = LanguagesManager.getAllItems(p);
        items.add(new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

        return getPage(items, inv, page);
    }

}
