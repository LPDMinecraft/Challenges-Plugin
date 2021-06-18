package de.lpd.challenges.languages;

import de.lpd.challenges.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Language {

    private String name,
                   langName,
                   interName;
    private Config cfgLang,
                   cfg;

    public Language(String name, String langName, String interName) {
        cfgLang = new Config("langs/" + name, "translations.yml");
        cfg = new Config("langs/" + name, "config.yml");
        this.name = name;
        this.langName = langName;
        this.interName = interName;
    }

    public abstract ItemStack getItem(Player p);
    public abstract void onClick(Player p, ItemStack item, InventoryClickEvent e);

    public Config getCfgLang() {
        return cfgLang;
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
    public void setCfgLang(Config cfgLang) {
        this.cfgLang = cfgLang;
    }
    public String[] getHelp() {
        if(cfg.cfg().contains("config.help")) {
            String[] help = new String[cfg.cfg().getConfigurationSection("config.help").getKeys(false).size()];
            int i = 0;
            for(String root : cfg.cfg().getConfigurationSection("config.help").getKeys(false)) {
                help[i] = cfg.cfg().getString("config.help" + root);
                i++;
            }
            return help;
        }
        String[] help = new String[1];
        help[0] = "§6Das ist noch nicht eingerichtet jetzt";

        cfg.cfg().set("config.help.0", help[0]);
        cfg.save();

        return help;
    }

}
