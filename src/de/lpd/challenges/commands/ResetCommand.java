package de.lpd.challenges.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
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
		if(args.length == 0) {
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("§6Restart");
			}
			
			Config cfg = new Config("config.yml");
			cfg.cfg().set("command.reset", true);
			cfg.save();
			
			Bukkit.spigot().restart();
		} else {
			s.sendMessage(getHelpMessage("reset"));
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
