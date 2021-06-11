package de.lpd.challenges.commands;

import org.bukkit.command.CommandSender;

import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Command;

public class TimerCommand extends Command {
	
	public TimerCommand(ChallengesMainClass plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run(CommandSender s, org.bukkit.command.Command cmd, String label, String[] args) {
		if(args.length == 0) {
			s.sendMessage(getStatus());
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("status")) {
				s.sendMessage(getStatus());
			} else if(args[0].equalsIgnoreCase("start")) {
				if(!ChallengesMainClass.t.isStarted()) {
					if(ChallengesMainClass.t.getMillsecounds() == 0) {
						ChallengesMainClass.t.start();
						s.sendMessage(PREFIX + "§aDer Countdown wurde gestartet.");
					} else {
						s.sendMessage(PREFIX + "§cDer Countdown wurde bereits gestartet. " + getHelpMessage("timer resume"));
					}
				} else {
					s.sendMessage(PREFIX + "§cDer Countdown ist bereit gestartet.");
				}
			} else if(args[0].equalsIgnoreCase("reset")) {
				ChallengesMainClass.t.reset();
				s.sendMessage(PREFIX + "§aDer Countdown wurde resetet.");
			} else if(args[0].equalsIgnoreCase("resume")) {
				if(!ChallengesMainClass.t.isStarted()) {
					if(ChallengesMainClass.t.getMillsecounds() != 0) {
						ChallengesMainClass.t.resume();
					} else {
						s.sendMessage(PREFIX + "§cDer Countdown wurde noch nicht gestartet.");
					}
				} else {
					s.sendMessage(PREFIX + "§cDer Countdown l§uft bereits.");
				}
			} else if(args[0].equalsIgnoreCase("pause")) {
				if(ChallengesMainClass.t.isStarted()) {
					if(ChallengesMainClass.t.getMillsecounds() != 0) {
						ChallengesMainClass.t.pause();
					} else {
						s.sendMessage(PREFIX + "§cDer Countdown wurde noch nicht gestartet.");
					}
				} else {
					s.sendMessage(PREFIX + "§cDer Countdown wurde noch nicht gestartet.");
				}
			} else {
				s.sendMessage(getHelpMessage("timer", "timer [start, reset, resume, pause, status]"));
			}
		} else {
			s.sendMessage(getHelpMessage("timer", "timer [start, reset, resume, pause, status]"));
		}
		return false;
	}
	
	public static String getStatus() {
		String msg = "";
		if(!ChallengesMainClass.t.isStarted()) {
			if(ChallengesMainClass.t.getMillsecounds() == 0) {
				msg = PREFIX + "§aDer CountDown ist §6noch nicht gestartet worden§a.";
			} else if(ChallengesMainClass.t.isPaused()) {
				msg = PREFIX + "§aDer CountDown ist §6bereits gestoppt§a.";
			}
		} else {
			msg = PREFIX + "§aDer CountDown ist §6bereits gestartet§a.";
		}
		return msg;
	}
	
}
