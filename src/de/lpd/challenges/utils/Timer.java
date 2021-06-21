package de.lpd.challenges.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import de.lpd.challenges.languages.LanguagesManager;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.settings.SettingManager;
import org.bukkit.entity.Player;

public class Timer {

	private Config cfg;
	private boolean isStarted = false;
	
	public Timer(ChallengesMainClass plugin) {
		cfg = new Config("timer", "time.yml");
		isStarted = false;
		new Scheduler(0, 1, plugin) {
			
			@Override
			public void scheduler() {
				if(isStarted) cfg.cfg().set("timer.millsecounds", (int)cfg.cfg().get("timer.millsecounds") + 50);
				cfg.save();
			}
		};
	}
	
	public void start() {
		if(!isStarted) {
			SettingManager.on(0);
			isStarted = true;
			cfg.cfg().set("timer.millsecounds", 0);
			cfg.save();
		}
	}
	
	public void stop() {
		if(isStarted) {
			SettingManager.on(1);
			isStarted = false;
		}
	}
	
	public void pause() {
		if(isStarted) {
			isStarted = false;
		}
	}
	
	public void resume() {
		if(isPaused()) {
			isStarted = true;
		}
	}
	
	public void reset() {
		cfg.cfg().set("timer.millsecounds", 0);
		isStarted = false;
	}
	
	public int getSecounds() {
		return (int)cfg.cfg().get("timer.millsecounds") / 1000;
	}
	
	public int getMinutes() {
		return getSecounds() / 60;
	}
	
	public int getMillsecounds() {
		return (int)cfg.cfg().get("timer.millsecounds");
	}
	
	public int getHours() {
		return getMinutes() / 60;
	}
	
	public int getDays() {
		return getHours() / 24;
	}
	
	public String getDisplay(String start, String end, Player p) {
		if(isStarted()) {
			DateFormat day = new SimpleDateFormat("dd");
			DateFormat hour = new SimpleDateFormat("HH");
			DateFormat minute = new SimpleDateFormat("mm");
			DateFormat secound = new SimpleDateFormat("ss");
			DateFormat millsecound = new SimpleDateFormat("SSS");
			String s = "§a";
			if(Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) > 0) {
				if((Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) - 1) != 0) {
					s = s + correntSay((Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) - 1), LanguagesManager.translate("ein Tag ", p.getUniqueId()), LanguagesManager.translate(" Tage ", p.getUniqueId()));
				}
			}
			if(Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) > 0) {
				if((Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) - 1) < 10) {
					s = s + "0" + (Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) - 1);
				} else {
					s = s + (Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) - 1);
				}
			}
			if(Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) > 0) {
				if(Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds"))) < 10) {
					s = s + ":0" + Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds")));
				} else {
					s = s + ":" + Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds")));
				}
			}
			
			if(Integer.valueOf(secound.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) > 0) {
				if(Integer.valueOf(secound.format((int)cfg.cfg().get("timer.millsecounds"))) < 10) {
					s = s + ":0" + Integer.valueOf(secound.format((int)cfg.cfg().get("timer.millsecounds")));
				} else {
					s = s + ":" + Integer.valueOf(secound.format((int)cfg.cfg().get("timer.millsecounds")));
				}
			}
			
			if(Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(secound.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(minute.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(hour.format((int)cfg.cfg().get("timer.millsecounds"))) > 0 || Integer.valueOf(day.format((int)cfg.cfg().get("timer.millsecounds"))) > 0) {
				if(Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds"))) < 10) {
	            	s = s + ":00" + Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds")));
				} else if(Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds"))) < 100) {
					s = s + ":0" + Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds")));
				} else {
					s = s + ":" + Integer.valueOf(millsecound.format((int)cfg.cfg().get("timer.millsecounds")));
				}
			}
			
			return s;
		} else if(isPaused()) {
			return LanguagesManager.translate("§aDer Countdown ist pausitert.", p.getUniqueId());
		} else {
			return LanguagesManager.translate("§aDer Countdown wurde noch nicht gestartet.", p.getUniqueId());
		}
	}
	
	private String correntSay(int a, String what1, String what2) {
		String s = "";
		if(a == 1) {
			s = s + what1;
		} else {
			s = s + a + what2;
		}
		return s;
	}
	
	public boolean isStarted() {
		return isStarted;
	}
	
	public boolean isPaused() {
		if((int)cfg.cfg().get("timer.millsecounds") != 0 && isStarted == false) {
			return true;
		}
		return false;
	}
	
}
