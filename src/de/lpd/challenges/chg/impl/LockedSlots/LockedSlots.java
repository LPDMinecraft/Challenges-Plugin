package de.lpd.challenges.chg.impl.LockedSlots;

import de.lpd.challenges.languages.LanguagesManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

import java.util.ArrayList;

public class LockedSlots extends Challenge {
	
	private Config cfg;
	
	public LockedSlots(ChallengesMainClass plugin) {
		super(plugin, "lockslots", "config.yml", "lockslots", 3*9, true, "Locked Slots", "chmenu", "challenge-lockslots", "Challenges Menu");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(isEnabled("all")) {
					for(Player c : Bukkit.getOnlinePlayers()) {
						if(c.getGameMode() == GameMode.SURVIVAL) {
							a(c);
						}
					}
				}
				if(!isEnabled("all")) {
					for(Player c : Bukkit.getOnlinePlayers()) {
						c.getInventory().remove(Material.BARRIER);
					}
				}
			}
			
		}, 0, 1L);
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public ItemStack getItem(Player p) {
		ItemBuilder ib = new ItemBuilder(Material.RED_STAINED_GLASS);
		ib.setDisplayName(LanguagesManager.translate("§6Locked Slots", p.getUniqueId()));
		String[] lore = new String[3];
		lore[0] = Starter.START_PREFIX + LanguagesManager.translate("§aIn dieser Challenge muss man Minecraft mit", p.getUniqueId());
		lore[1] = LanguagesManager.translate("§aX Slots durchspielen.", p.getUniqueId());
		lore[2] = LanguagesManager.translate("§6Mittelklick §7> §aOpen Inventory", p.getUniqueId());
		
		ib.setLoreString(lore);
		return ib.build();
	}
	
	public void a(Player p) {
		if(isToggled("all")) {
			p.getInventory().remove(Material.BARRIER);
		}
		if(isEnabled("all")) {
			for(int i = 0; i < (double)getOption(cfg, "lockedslots.max", 0); i++) {
				if(!(i > 4*9)) {
					p.getInventory().setItem(((4*9)-1)-i, new ItemBuilder(Material.BARRIER).build());
				}
			}
		} 
	}
	
	@Override
	public void onRightClick(Player p) {
		/*setOption(cfg, "lockedslots.max", (double)getOption(cfg, "lockedslots.max", 0) + 1);
		a(p);*/
	}
	
	@Override
	public void onLeftClick(Player p) {
		/*if((double)getOption(cfg, "lockedslots.max", 0) > 1) {
			setOption(cfg, "lockedslots.max", (double)getOption(cfg, "lockedslots.max", 0) - 1);
			a(p);
		}*/

	}
	
	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}
	
	@Override
	public void reset() {
		setOption(cfg, "lockedslots.max", 0.00);
	}

	@Override
	public void ifPlayerDies(Player p) {

	}

	@Override
	public void normalClickAnyInventory(Player p, ItemStack currentItem, InventoryClickEvent e) {
		if(e.getCurrentItem().getType() == Material.BARRIER) {
			if(e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
				if(isEnabled("all")) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL && isEnabled("all")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL && isEnabled("all")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().remove(new ItemBuilder(Material.BARRIER).build());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player c = e.getPlayer();
		if(isEnabled("all")) {
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

	String plusLockedSlots1 = "§6Sperre ein 1 Slot mehr",
			minusLockedSlots1 = "§6Entsperre ein weiteren 1 Slot",
			namei;

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		namei = LanguagesManager.translate("§6Locked Slots(" + isToggled("all") + "): ", p.getUniqueId()) + getOption(cfg, "lockedslots.max", 0) + " Slots sind locked";
		plusLockedSlots1 = LanguagesManager.translate("§6Sperre ein 1 Slot mehr", p.getUniqueId());
		minusLockedSlots1 = LanguagesManager.translate("§6Entsperre ein weiteren 1 Slot", p.getUniqueId());

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusLockedSlots1)) {
			double l = Double.valueOf(String.valueOf(getOption(cfg, "lockedslots.max", 0.00)));
			l = l + 0.5;
			setOption(cfg, "lockedslots.max", l);
			a(p);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusLockedSlots1)) {
			double l = Double.valueOf(String.valueOf(getOption(cfg, "lockedslots.max", 0.00)));
			if(l > 1) {
				l = l - 0.5;
				setOption(cfg, "lockedslots.max", l);
				a(p);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle("all");
		}
	}

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		namei = LanguagesManager.translate("§6Locked Slots(" + isToggled("all") + "): ", p.getUniqueId()) + getOption(cfg, "lockedslots.max", 0) + " Slots sind locked";
		plusLockedSlots1 = LanguagesManager.translate("§6Sperre ein 1 Slot mehr", p.getUniqueId());
		minusLockedSlots1 = LanguagesManager.translate("§6Entsperre ein weiteren 1 Slot", p.getUniqueId());
		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusLockedSlots1).build());
		if(isToggled("all")) {
			inv.setItem(9, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayName(namei).build());
		} else {
			inv.setItem(9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(namei).build());
		}
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusLockedSlots1).build());
		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

		return getPage(items, inv, page, 0);
	}
	
}
