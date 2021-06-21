package de.lpd.challenges.utils;

import org.bukkit.Bukkit;
import de.lpd.challenges.main.ChallengesMainClass;

public abstract class Scheduler {
	
	public Scheduler(int delay, int ticks, ChallengesMainClass plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				scheduler();
			}
			
		}, delay, ticks);
	}
	
	public abstract void scheduler();
	
}
