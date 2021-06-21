package de.lpd.challenges.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.chg.ChallengesManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import de.lpd.challenges.utils.Config;

public class ResetCommand extends Command {
	
	public ResetCommand(ChallengesMainClass plugin) {
		super(plugin);
	}
	
	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		ArrayList all = new ArrayList<String>();
		all.add("world");
		all.add("challenges-options");
		addTabComplete(cmd.getName(), new String[0], all);

		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("world")) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.kickPlayer("§6Restart");
				}

				Config cfg = new Config("config.yml");
				cfg.cfg().set("command.reset", true);
				cfg.save();

				Bukkit.spigot().restart();
			} else if(args[0].equalsIgnoreCase("challenges-options")) {
				for(Challenge ch : ChallengesManager.getIdtoclass().values()) {
					ch.reset();
				}
			} else {
				s.sendMessage(getHelpMessage(null, "reset [world, options]"));
			}
		} else {
			s.sendMessage(getHelpMessage(null, "reset [world, options]"));
		}
		return false;
	}
	
	public void renewWorld(World w) {
		Bukkit.unloadWorld(w, false);
		
		try {
			Files.walk(w.getWorldFolder().toPath())
		      .sorted(Comparator.reverseOrder())
		      .map(Path::toFile)
		      .forEach(File::delete);
			
			w.getWorldFolder().delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*WorldCreator wc = WorldCreator.name(w.getName());
		wc.type(w.getWorldType());
		Bukkit.createWorld(wc);
		Bukkit.getWorlds().add(Bukkit.getWorld(w.getName()));*/
	}
	
}
