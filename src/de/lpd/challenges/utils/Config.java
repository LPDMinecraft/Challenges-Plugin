package de.lpd.challenges.utils;


import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.lpd.challenges.main.ChallengesMainClass;

public class Config {
	
	private YamlConfiguration cfgBackup;
	private FileConfiguration cfg;
	private File file;
	
	public Config(String startpath, String path, String filename) {
		file = new File(startpath + "//" + path, filename);
		cfg = YamlConfiguration.loadConfiguration(file);
		cfgBackup = (YamlConfiguration) cfg;
	}
	
	public Config(String path, String filename) {
		this(ChallengesMainClass.getPlugin().getDataFolder().getAbsoluteFile().getAbsolutePath(), path, filename);
	}
	
	public Config(String filename) {
		this("", filename);
	}
	
	public void backup() {
		cfgBackup = (YamlConfiguration) cfg;
	}
	
	public FileConfiguration cfg() {
		return cfg;
	}
	
	public YamlConfiguration getCfgBackup() {
		return cfgBackup;
	}
	
	public static Object getOption(Config cfg, String path, Object start) {
		if(cfg.cfg().contains(path)) {
			return cfg.cfg().get(path);
		} else {
			cfg.cfg.set(path, start);
			cfg.save();
			return start;
		}
	}
	
	public void save() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
