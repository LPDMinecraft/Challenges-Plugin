package lcraft.challenges.api.listener;

import lcraft.challenges.api.main.ChallengesApi;
import lcraft.challenges.api.utils.Config;
import lcraft.challenges.api.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private ChallengesApi plugin;

    public JoinListener(ChallengesApi plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        boolean isMsg = (boolean) Config.getOption(plugin.getMainCFG(), "server.join.msg.enabled", true);
        ChallengesApi.getPlugin().users.add(new User(e.getPlayer().getUniqueId()));
        e.setJoinMessage("");
        if(isMsg) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(plugin.getLanguagesManager().getPlayer(p.getUniqueId()).getJoinMessage().replace("%PLAYER%", e.getPlayer().getName()));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        boolean isMsg = (boolean) Config.getOption(plugin.getMainCFG(), "server.leave.msg.enabled", false);
        ChallengesApi.getPlugin().users.remove(ChallengesApi.getPlugin().getUser(e.getPlayer().getUniqueId()));
        e.setQuitMessage("");

        if(isMsg) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(plugin.getLanguagesManager().getPlayer(p.getUniqueId()).getQuitMessage().replace("%PLAYER%", e.getPlayer().getName()));
            }
        }
    }

}
