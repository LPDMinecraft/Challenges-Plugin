package de.lpd.challenges.listeners;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private ChallengesMainClass plugin;

    public JoinListener(ChallengesMainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        boolean isMsg = (boolean) Config.getOption(plugin.getMainCFG(), "server.join.msg.enabled", true);
        ChallengesMainClass.getPlugin().users.add(new User(e.getPlayer().getUniqueId()));
        e.setJoinMessage("");
        if(isMsg) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(LanguagesManager.getPlayer(p).getJoinMessage().replace("%PLAYER%", e.getPlayer().getName()));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        boolean isMsg = (boolean) Config.getOption(plugin.getMainCFG(), "server.leave.msg.enabled", false);
        ChallengesMainClass.getPlugin().users.remove(ChallengesMainClass.getPlugin().getUser(e.getPlayer().getUniqueId()));
        e.setQuitMessage("");

        if(isMsg) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(LanguagesManager.getPlayer(p).getQuitMessage().replace("%PLAYER%", e.getPlayer().getName()));
            }
        }
    }

}
