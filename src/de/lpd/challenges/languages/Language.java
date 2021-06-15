package de.lpd.challenges.languages;

import de.lpd.challenges.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Language {

    private String name,
                   langName,
                   interName;
    private Config cfg;

    public Language(String name, String langName, String interName) {
        cfg = new Config("langs", name + ".yml");
        this.name = name;
        this.langName = langName;
        this.interName = interName;
    }

    public abstract ItemStack getItem();
    public abstract void onClick(Player p, ItemStack item, InventoryClickEvent e);

    public Config getCfg() {
        return cfg;
    }
    public String getInterName() {
        return interName;
    }
    public String getLangName() {
        return langName;
    }
    public String getName() {
        return name;
    }
    public void setCfg(Config cfg) {
        this.cfg = cfg;
    }
}
