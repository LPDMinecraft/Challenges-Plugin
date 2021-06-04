package de.lpd.challenges.settings;

import java.util.ArrayList;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.settings.impl.Deathrun.DeathRun;

public class SettingManager {
	
	public static ArrayList<Setting> settings;
	
	public SettingManager(ChallengesMainClass plugin) {
		settings = new ArrayList<>();
		
		settings.add(new DeathRun(plugin));
	}
	
	public void add(Setting s) {
		settings.add(s);
	}
	
	public static void on(int id) {
		for(Setting c : settings) {
			if(c.isEnabled()) {
				if(id == 0) {
					c.onTimerStarted();
				} else if(id == 1) {
					c.onTimerStoped();
				}
			}
		}
	}
	
}
