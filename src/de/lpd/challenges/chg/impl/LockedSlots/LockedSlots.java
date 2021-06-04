package de.lpd.challenges.chg.impl.LockedSlots;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class LockedSlots extends Challenge {
	
	public static int status = 0;
	private Config cfg;
	private ChallengesMainClass plugin;
	private boolean a = false;
	
	public LockedSlots(ChallengesMainClass plugin) {
		super(plugin, "lockedslots", "lockedslots.yml", "lockedslots");
		this.setPlugin(plugin);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(ChallengesMainClass.t.isStarted() != a) {
					a = ChallengesMainClass.t.isStarted();
					if(a) {
						if(isEnabled()) {
							for(Player c : Bukkit.getOnlinePlayers()) {
								if(c.getGameMode() == GameMode.SURVIVAL) {
									a(c);
								}
							}
						}
					} else {
						if(isEnabled()) {
							for(Player c : Bukkit.getOnlinePlayers()) {
								if(c.getGameMode() == GameMode.SURVIVAL) {
									c.getInventory().remove(Material.BARRIER);
								}
							}
						}
					}
				}
			}
			
		}, 0, 1L);
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
		status = (int) getOption(cfg, "lockedslots.max", 0);
	}
	
	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.RED_STAINED_GLASS);
		if(isEnabled()) {
			ib.setDisplayName("§6LockedSlots " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6LockedSlots " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Slots durchspielen.";
		lore[2] = "§7Derzeitig gespeerte Slots§8: §6" + status;
		lore[3] = "§6Linksklick §7> §a-1 Slot";
		lore[4] = "§6Rechtsklick §7> §a+1 Slot";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}
	
	public void a(Player p) {
		if(isEnabled()) {
			p.getInventory().remove(Material.BARRIER);
			if(ChallengesMainClass.t.isStarted()) {
				for(int i = 0; i < status; i++) {
					p.getInventory().setItem(((4*9)-1)-i, new ItemBuilder(Material.BARRIER).build());
				}
			}
		}
	}
	
	@Override
	public void onRightClick(Player p) {
		status++;
		setOption(cfg, "lockedslots.max", status);
		a(p);
	}
	
	@Override
	public void onLeftClick(Player p) {
		if(status > 1) {
			status--;
			setOption(cfg, "lockedslots.max", status);
			a(p);
		}
	}
	
	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}
	
	@Override
	public void reset() {
		
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			if(e.getCurrentItem() != null) {
				if(e.getCurrentItem().getType() != null) {
					if(e.getCurrentItem().getType() == Material.BARRIER) {
						if(e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
							if(ChallengesMainClass.t.isStarted()) {
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeatzh(PlayerDeathEvent e) {
		e.getDrops().remove(new ItemBuilder(Material.BARRIER).build());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player c = e.getPlayer();
		if(a && isEnabled()) {
			if(ChallengesMainClass.t.isStarted()) {
				if(c.getGameMode() == GameMode.SURVIVAL) {
					a(c);
				}
			} else {
				c.getInventory().remove(Material.BARRIER);
			}
		} else {
			c.getInventory().remove(Material.BARRIER);
		}
	}

	public ChallengesMainClass getPlugin() {
		return plugin;
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	
}
