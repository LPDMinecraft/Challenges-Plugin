package lcraft.challenges.main;

import lcraft.challenges.api.main.ChallengesApi;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Main plugin;
    private ChallengesApi challengesApi;

    @Override
    public void onEnable() {
        challengesApi = new ChallengesApi();
        challengesApi.onEnable();
    }

    @Override
    public void onDisable() {
        challengesApi.onDisable();
    }

    public Main getPlugin() {
        return plugin;
    }

    public ChallengesApi getChallengesApi() {
        return challengesApi;
    }

}
