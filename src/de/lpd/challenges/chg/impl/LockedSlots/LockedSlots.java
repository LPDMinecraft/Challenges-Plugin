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
	private ChallengesMainClass plugin;
	
	public LockedSlots(ChallengesMainClass plugin) {
		super(plugin, "lockedslots", "lockedslots.yml", "lockedslots", 6*9, true, "LockedSlots", "chmenu", "challenge-lockedslots");
		this.setPlugin(plugin);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(isEnabled()) {
					for(Player c : Bukkit.getOnlinePlayers()) {
						if(c.getGameMode() == GameMode.SURVIVAL) {
							a(c);
						}
					}
				}
				if(!isEnabled()) {
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
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.RED_STAINED_GLASS);

		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Slots durchspielen.";
		lore[2] = "§7Derzeitig gespeerte Slots§8: §6" + getOption(cfg, "lockedslots.max", 0);
		lore[3] = "§6Mittelklick §7> §aOpen Inventory";
		
		ib.setLoreString(lore);
		return ib.build();
	}
	
	public void a(Player p) {
		if(isToggled()) {
			p.getInventory().remove(Material.BARRIER);
		}
		if(isEnabled()) {
			for(int i = 0; i < (int)getOption(cfg, "lockedslots.max", 0); i++) {
				p.getInventory().setItem(((4*9)-1)-i, new ItemBuilder(Material.BARRIER).build());
			}
		} 
	}
	
	@Override
	public void onRightClick(Player p) {
		/*setOption(cfg, "lockedslots.max", (int)getOption(cfg, "lockedslots.max", 0) + 1);
		a(p);*/
	}
	
	@Override
	public void onLeftClick(Player p) {
		/*if((int)getOption(cfg, "lockedslots.max", 0) > 1) {
			setOption(cfg, "lockedslots.max", (int)getOption(cfg, "lockedslots.max", 0) - 1);
			a(p);
		}*/

	}
	
	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}
	
	@Override
	public void reset() {
		setOption(cfg, "lockedslots.max", 0);
	}

	@Override
	public void ifPlayerDies() {

	}

	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			if(e.getCurrentItem() != null) {
				if(e.getCurrentItem().getType() != null) {
					if(e.getCurrentItem().getType() == Material.BARRIER) {
						if(e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
							if(isEnabled()) {
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
		if(e.getBlock().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL && isEnabled()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getType() == Material.BARRIER && e.getPlayer().getGameMode() == GameMode.SURVIVAL && isEnabled()) {
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
		if(isEnabled()) {
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

	String plusLockedSlots1 = "§6Sperre ein 1 Slot mehr",
			minusLockedSlots1 = "§6Entsperre ein weiteren 1 Slot",
			itemdisplayname = "";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		if(isToggled()) {
			itemdisplayname = "§6Locked Slots " + Starter.START_PREFIX + "§aOn";
		} else {
			itemdisplayname = "§6Locked Slots " + Starter.START_PREFIX + "§cOff";
		}

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusLockedSlots1) && item.getType() == Material.STONE_BUTTON) {
			setOption(cfg, "lockedslots.max", (int)getOption(cfg, "lockedslots.max", 0) + 1);
			a(p);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusLockedSlots1) && item.getType() == Material.STONE_BUTTON) {
			if((int)getOption(cfg, "lockedslots.max", 0) > 1) {
				setOption(cfg, "lockedslots.max", (int) getOption(cfg, "lockedslots.max", 0) - 1);
				a(p);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(itemdisplayname)) {
			toggle();
		}
		p.openInventory(getInventory(page, p));
	}

	@Override
	public Inventory getInventory(int page, Player p) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		if(isToggled()) {
			itemdisplayname = "§6Locked Slots " + Starter.START_PREFIX + "§aOn";
		} else {
			itemdisplayname = "§6Locked Slots " + Starter.START_PREFIX + "§cOff";
		}

		ArrayList<ItemStack> items = new ArrayList<>();

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusLockedSlots1).build());
		inv.setItem(9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(itemdisplayname).build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusLockedSlots1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	
}
