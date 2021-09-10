package lcraft.challenges.api.main;

import lcraft.challenges.api.chg.ChallengesManager;
import lcraft.challenges.api.commands.Command;
import lcraft.challenges.api.commands.CommandManager;
import lcraft.challenges.api.inventory.Inventory;
import lcraft.challenges.api.inventory.InventoryManager;
import lcraft.challenges.api.languages.LanguagesManager;
import lcraft.challenges.api.listener.JoinListener;
import lcraft.challenges.api.permissions.PermissionsManager;
import lcraft.challenges.api.settings.SettingManager;
import lcraft.challenges.api.utils.Config;
import lcraft.challenges.api.utils.Timer;
import lcraft.challenges.api.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.UUID;

public final class ChallengesApi extends JavaPlugin {

    private static ChallengesApi plugin;
    private Config mainCFG;
    private LanguagesManager languagesManager;
    private PermissionsManager permissionsManager;
    public ArrayList<User> users;
    private Starter starter;
    private CommandManager commandManager;
    private InventoryManager inventoryManager;
    private ChallengesManager challengesManager;
    private SettingManager settingManager;
    private Timer timer;

    public synchronized void load() throws InterruptedException {
        plugin = this;
        users = new ArrayList<>();
        starter = new Starter();
        mainCFG = starter.startPlugin(mainCFG, plugin);
        languagesManager = new LanguagesManager(plugin);
        timer = new Timer(plugin);
        permissionsManager = new PermissionsManager(plugin);
        commandManager = new CommandManager(plugin);
        inventoryManager = new InventoryManager(plugin);
        challengesManager = new ChallengesManager(plugin);
        settingManager = new SettingManager(plugin);
    }

    @Override
    public void onEnable() {
        try {
            load();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Register all Commands

        // Register all Listeners
        registerListener(new JoinListener(plugin));

        // Init the Starter
        starter.init(plugin);
    }

    @Override
    public void onDisable() {}

    public void registerCommand(String cmd, Command executor) {
        commandManager.registerCommand(cmd, executor);
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static int getHighestY(Location loc) {
        int y = 255;
        while(new Location(loc.getWorld(), loc.getX(), y, loc.getZ()).getBlock().getType() == Material.AIR) {
            y--;
        }
        return y;
    }

    public static User getUser(UUID p) {
        for(User c : getPlugin().getUsers()) {
            if(c != null && c.getUUID() != null && c.getUUID().equals(p)) {
                return c;
            }
        }
        return null;
    }

    public static ChallengesApi getPlugin() {
        return plugin;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Config getMainCFG() {
        return mainCFG;
    }

    public LanguagesManager getLanguagesManager() {
        return languagesManager;
    }

    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    public Starter getStarter() {
        return starter;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Timer getTimer() {
        return timer;
    }

}
