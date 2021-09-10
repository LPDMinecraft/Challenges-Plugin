package de.lpd.challenges.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.lpd.challenges.main.ChallengesMainClass;

public class DeathEvent implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		ChallengesMainClass.fail(2, e.getEntity());
		e.getEntity().setHealth(e.getEntity().getMaxHealth());
	}
	
	@EventHandler
	public void onEnderDeath(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.ENDER_DRAGON) {
			ChallengesMainClass.fail(0, null);
		}
	}
	
}
