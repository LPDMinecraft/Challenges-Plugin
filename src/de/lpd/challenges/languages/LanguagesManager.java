package de.lpd.challenges.languages;

import de.lpd.challenges.utils.Config;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LanguagesManager {

    public static HashMap<String, Language> langs;
    public static Config playerCfg;

    public LanguagesManager() {
        playerCfg = new Config("langs", "players.yml");
        langs = new HashMap<>();
    }

    public void addLang(Language lang) {
        langs.put(lang.getName(), lang);
    }

    public static Language getPlayer(UUID player) {
        String root = "users." + player.toString() + ".lang";
        if(playerCfg.cfg().contains(root)) {
            return getLanguage(playerCfg.cfg().get(root).toString());
        } else {
            return langs.get("en");
        }
    }

    public static void setPlayer(UUID player, Language lang) {
        String root = "users." + player.toString() + ".lang";
        playerCfg.cfg().set(root, lang.getName());
        playerCfg.save();
    }

    public static Language getLanguage(String kurz) {
        for(Language c : langs.values()) {
            if(c.getName().equals(kurz)) {
                return c;
            }
        }
        return langs.get("en");
    }

    public ArrayList<ItemStack> getAllItems() {
        ArrayList<ItemStack> c = new ArrayList<>();
        for(Language l : langs.values()) {
            c.add(l.getItem());
        }
        return c;
    }

}
