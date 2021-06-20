package de.lpd.challenges.commands;

import de.lpd.challenges.languages.LanguagesManager;
import org.bukkit.command.CommandSender;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TimerCommand extends Command {
	
	public TimerCommand(ChallengesMainClass plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		ArrayList all = new ArrayList<String>();
		all.add("start");
		all.add("reset");
		all.add("pause");
		all.add("resume");
		all.add("restart");
		all.add("status");

		addTabComplete(cmd.getName(), new String[0], all);

		if(args.length == 0) {
			s.sendMessage(getStatusSender());
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("status")) {
				if(s instanceof Player) {
					s.sendMessage(getStatusPlayer((Player) s));
				} else {
					s.sendMessage(getStatusSender());
				}
			} else if(args[0].equalsIgnoreCase("start")) {
				if(s instanceof Player) {
					Player p = (Player) s;
					if(hasPermissions(p, "ch.timer.start")) {
						if(!ChallengesMainClass.t.isStarted()) {
							if(ChallengesMainClass.t.getMillsecounds() == 0) {
								ChallengesMainClass.t.start();
								s.sendMessage(PREFIX + LanguagesManager.translate("§aDer Countdown wurde gestartet.", "en"));
							} else {
								s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde bereits gestartet. ", "en") + getHelpMessage(null, "timer resume"));
							}
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown ist bereit gestartet.", "en"));
						}
					} else {
						p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
					}
				} else {
					if(!ChallengesMainClass.t.isStarted()) {
						if(ChallengesMainClass.t.getMillsecounds() == 0) {
							ChallengesMainClass.t.start();
							s.sendMessage(PREFIX + LanguagesManager.translate("§aDer Countdown wurde gestartet.", "en"));
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde bereits gestartet. ", "en") + getHelpMessage(null, "timer resume"));
						}
					} else {
						s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown ist bereit gestartet.", "en"));
					}
				}
			} else if(args[0].equalsIgnoreCase("reset")) {
				if(s instanceof Player) {
					Player p = (Player) s;
					if(hasPermissions(p, "ch.timer.reset")) {
						ChallengesMainClass.t.reset();
						s.sendMessage(PREFIX + LanguagesManager.translate("§aDer Countdown wurde resetet.", p.getUniqueId()));
					} else {
						p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
					}
				} else {
					ChallengesMainClass.t.reset();
					s.sendMessage(PREFIX + LanguagesManager.translate("§aDer Countdown wurde resetet.", "en"));
				}
			} else if(args[0].equalsIgnoreCase("resume")) {
				if(s instanceof Player) {
					Player p = (Player) s;
					if(hasPermissions(p, "ch.timer.resume")) {
						if(!ChallengesMainClass.t.isStarted()) {
							if(ChallengesMainClass.t.getMillsecounds() != 0) {
								ChallengesMainClass.t.resume();
							} else {
								s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", p.getUniqueId()));
							}
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown l§uft bereits.", p.getUniqueId()));
						}
					} else {
						p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
					}
				} else {
					if(!ChallengesMainClass.t.isStarted()) {
						if(ChallengesMainClass.t.getMillsecounds() != 0) {
							ChallengesMainClass.t.resume();
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", "en"));
						}
					} else {
						s.sendMessage(PREFIX + "§cDer Countdown l§uft bereits.");
					}
				}
			} else if(args[0].equalsIgnoreCase("pause")) {
				if(s instanceof Player) {
					Player p = (Player) s;
					if(hasPermissions(p, "ch.timer.pause")) {
						if(ChallengesMainClass.t.isStarted()) {
							if(ChallengesMainClass.t.getMillsecounds() != 0) {
								ChallengesMainClass.t.pause();
							} else {
								s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", p.getUniqueId()));
							}
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", p.getUniqueId()));
						}
					} else {
						p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
					}
				} else {
					if(ChallengesMainClass.t.isStarted()) {
						if(ChallengesMainClass.t.getMillsecounds() != 0) {
							ChallengesMainClass.t.pause();
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", "en"));
						}
					} else {
						s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet.", "en"));
					}
				}
			} else if(args[0].equalsIgnoreCase("restart")) {
				if(s instanceof Player) {
					Player p = (Player) s;
					if(hasPermissions(p, "ch.timer.restart")) {
						if(ChallengesMainClass.t.isPaused() || ChallengesMainClass.t.isStarted()) {
							if(ChallengesMainClass.t.isStarted()) {
								ChallengesMainClass.t.stop();
								ChallengesMainClass.t.reset();
							}
							ChallengesMainClass.t.start();
						} else {
							s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet/pausiert.", p.getUniqueId()));
						}
					} else {
						p.sendMessage(LanguagesManager.translate(NO_PERMISSIONS, p.getUniqueId()));
					}
				} else {
					if(ChallengesMainClass.t.isPaused() || ChallengesMainClass.t.isStarted()) {
						if(ChallengesMainClass.t.isStarted()) {
							ChallengesMainClass.t.stop();
							ChallengesMainClass.t.reset();
						}
						ChallengesMainClass.t.start();
					} else {
						s.sendMessage(PREFIX + LanguagesManager.translate("§cDer Countdown wurde noch nicht gestartet/pausiert.", "en"));
					}
				}
			} else {
				s.sendMessage(getHelpMessage(null, "timer", "timer [start, reset, resume, pause, restart, status]"));
			}
		} else {
			s.sendMessage(getHelpMessage(null, "timer", "timer [start, reset, resume, pause, restart, status]"));
		}
		return false;
	}
	
	public static String getStatusSender() {
		String msg = "";
		if(!ChallengesMainClass.t.isStarted()) {
			if(ChallengesMainClass.t.getMillsecounds() == 0) {
				msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6noch nicht gestartet worden§a.", "en");
			} else if(ChallengesMainClass.t.isPaused()) {
				msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6bereits gestoppt§a.", "en");
			}
		} else {
			msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6bereits gestartet§a.", "en");
		}
		return msg;
	}

	public static String getStatusPlayer(Player p) {
		String msg = "";
		if(!ChallengesMainClass.t.isStarted()) {
			if(ChallengesMainClass.t.getMillsecounds() == 0) {
				msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6noch nicht gestartet worden§a.", p.getUniqueId());
			} else if(ChallengesMainClass.t.isPaused()) {
				msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6bereits gestoppt§a.", p.getUniqueId());
			}
		} else {
			msg = PREFIX + LanguagesManager.translate("§aDer CountDown ist §6bereits gestartet§a.", p.getUniqueId());
		}
		return msg;
	}
	
}
