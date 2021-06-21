package de.lpd.challenges.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.settings.SettingManager;

public class Timer {
	
	private int millsecounds = 0;
	private boolean isStarted = false;
	
	public Timer(ChallengesMainClass plugin) {
		millsecounds = 0;
		isStarted = false;
		new Scheduler(0, 1, plugin) {
			
			@Override
			public void scheduler() {
				if(isStarted) millsecounds += 50;
			}
		};
	}
	
	public void start() {
		if(!isStarted) {
			SettingManager.on(0);
			isStarted = true;
			millsecounds = 0;
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
		millsecounds = 0;
		isStarted = false;
		
	}
	
	public int getSecounds() {
		return millsecounds / 1000;
	}
	
	public int getMinutes() {
		return getSecounds() / 60;
	}
	
	public int getMillsecounds() {
		return millsecounds;
	}
	
	public int getHours() {
		return getMinutes() / 60;
	}
	
	public int getDays() {
		return getHours() / 24;
	}
	
	public String getDisplay(String start, String end) {
		if(isStarted()) {
			DateFormat day = new SimpleDateFormat("dd");
			DateFormat hour = new SimpleDateFormat("HH");
			DateFormat minute = new SimpleDateFormat("mm");
			DateFormat secound = new SimpleDateFormat("ss");
			DateFormat millsecound = new SimpleDateFormat("SSS");
			String s = "§a";
			if(Integer.valueOf(day.format(millsecounds)) > 0) {
				if((Integer.valueOf(day.format(millsecounds)) - 1) != 0) {
					s = s + correntSay((Integer.valueOf(day.format(millsecounds)) - 1), "ein Tag ", " Tage ");
				}
			}
			if(Integer.valueOf(hour.format(millsecounds)) > 0 || Integer.valueOf(day.format(millsecounds)) > 0) {
				if((Integer.valueOf(hour.format(millsecounds)) - 1) < 10) {
					s = s + "0" + (Integer.valueOf(hour.format(millsecounds)) - 1);
				} else {
					s = s + (Integer.valueOf(hour.format(millsecounds)) - 1);
				}
			}
			if(Integer.valueOf(minute.format(millsecounds)) > 0 || Integer.valueOf(hour.format(millsecounds)) > 0 || Integer.valueOf(day.format(millsecounds)) > 0) {
				if(Integer.valueOf(minute.format(millsecounds)) < 10) {
					s = s + ":0" + Integer.valueOf(minute.format(millsecounds));
				} else {
					s = s + ":" + Integer.valueOf(minute.format(millsecounds));
				}
			}
			
			if(Integer.valueOf(secound.format(millsecounds)) > 0 || Integer.valueOf(minute.format(millsecounds)) > 0 || Integer.valueOf(hour.format(millsecounds)) > 0 || Integer.valueOf(day.format(millsecounds)) > 0) {
				if(Integer.valueOf(secound.format(millsecounds)) < 10) {
					s = s + ":0" + Integer.valueOf(secound.format(millsecounds));
				} else {
					s = s + ":" + Integer.valueOf(secound.format(millsecounds));
				}
			}
			
			if(Integer.valueOf(millsecound.format(millsecounds)) > 0 || Integer.valueOf(secound.format(millsecounds)) > 0 || Integer.valueOf(minute.format(millsecounds)) > 0 || Integer.valueOf(hour.format(millsecounds)) > 0 || Integer.valueOf(day.format(millsecounds)) > 0) {
				if(Integer.valueOf(millsecound.format(millsecounds)) < 10) {
	            	s = s + ":00" + Integer.valueOf(millsecound.format(millsecounds));
				} else if(Integer.valueOf(millsecound.format(millsecounds)) < 100) {
					s = s + ":0" + Integer.valueOf(millsecound.format(millsecounds));
				} else {
					s = s + ":" + Integer.valueOf(millsecound.format(millsecounds));
				}
			}
			
			return s;
		} else if(isPaused()) {
			return "§aDer Countdown ist pausitert.";
		} else {
			return "§aDer Countdown wurde noch nicht gestartet.";
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
		if(millsecounds != 0 && isStarted == false) {
			return true;
		}
		return false;
	}
	
}
