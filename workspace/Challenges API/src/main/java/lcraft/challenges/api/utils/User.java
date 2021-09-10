package lcraft.challenges.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private static UUID uuid;
    private static Player player;
    private static OfflinePlayer offPlayer;
    private static boolean isAdmin;

    public User(Player p) {
        this(p.getUniqueId());
    }

    public User(OfflinePlayer p) {
        this(p.getUniqueId());
    }

    public User(UUID uuid) {
        if(Bukkit.getOfflinePlayer(uuid) != null) {
            offPlayer = Bukkit.getOfflinePlayer(uuid);
        }
        if(Bukkit.getPlayer(uuid) != null) {
            player = Bukkit.getPlayer(uuid);
        }
        this.uuid = uuid;
    }

    public OfflinePlayer getOffPlayer() {
        return offPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean setGamemode(String numbOrString) {
        if(isInteger(numbOrString)) {
            int number = Integer.valueOf(numbOrString);
            if(number == 0) {
                if(player != null) {
                    player.setGameMode(GameMode.SURVIVAL);
                    return true;
                }
            } else if(number == 1) {
                if(player != null) {
                    player.setGameMode(GameMode.CREATIVE);
                    return true;
                }
            } else if(number == 2) {
                if(player != null) {
                    player.setGameMode(GameMode.ADVENTURE);
                    return true;
                }
            } else if(number == 3) {
                if(player != null) {
                    player.setGameMode(GameMode.SPECTATOR);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            String gamemode = numbOrString;
            if(gamemode.equalsIgnoreCase(GameMode.SURVIVAL.name())) {
                if(player != null) {
                    player.setGameMode(GameMode.SURVIVAL);
                    return true;
                }
            } else if(gamemode.equalsIgnoreCase(GameMode.CREATIVE.name())) {
                if(player != null) {
                    player.setGameMode(GameMode.CREATIVE);
                    return true;
                }
            } else if(gamemode.equalsIgnoreCase(GameMode.ADVENTURE.name())) {
                if(player != null) {
                    player.setGameMode(GameMode.ADVENTURE);
                    return true;
                }
            } else if(gamemode.equalsIgnoreCase(GameMode.SPECTATOR.name())) {
                if(player != null) {
                    player.setGameMode(GameMode.SPECTATOR);
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
